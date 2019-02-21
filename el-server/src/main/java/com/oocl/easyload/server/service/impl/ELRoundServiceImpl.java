package com.oocl.easyload.server.service.impl;

import com.oocl.easyload.model.entity.*;
import com.oocl.easyload.server.cache.NodeNoticeCache;
import com.oocl.easyload.server.cache.RunningStatusMonitorCache;
import com.oocl.easyload.server.client.NodeClient;
import com.oocl.easyload.server.dto.ELMonitorNotifyDTO;
import com.oocl.easyload.server.dto.ELRoundAttenderDTO;
import com.oocl.easyload.server.dto.ELRoundDTO;
import com.oocl.easyload.server.exception.AttenderNotReadyException;
import com.oocl.easyload.server.exception.IdInvalidException;
import com.oocl.easyload.server.exception.UpdatedELRoundInvalidedStatusException;
import com.oocl.easyload.server.mapper.ELRoundAttenderMapper;
import com.oocl.easyload.server.mapper.ELRoundMapper;
import com.oocl.easyload.server.repository.*;
import com.oocl.easyload.server.service.ELMonitorService;
import com.oocl.easyload.server.service.ELRoundAttenderService;
import com.oocl.easyload.server.service.ELRoundService;
import com.oocl.easyload.server.util.SplunkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ELRoundServiceImpl implements ELRoundService {
  private static Logger logger = LoggerFactory.getLogger(ELRoundServiceImpl.class);
  @Autowired private ELRoundRepository elRoundRepository;
  @Autowired private ELAttenderRepository elAttenderRepository;
  @Autowired private ELActivityRepository elActivityRepository;
  @Autowired private ELRoundAttenderService elRoundAttenderService;
  @Autowired private ELRoundAttenderRepository elRoundAttenderRepository;
  @Autowired private ELMonitorService elMonitorService;
  private ELScriptRepository elScriptRepository;
  private NodeClient nodeClient;

  @Override
  public ELRoundDTO saveELRoundDto(ELRoundDTO elRoundDTO) {
    ELRoundMapper elRoundMapper = ELRoundMapper.getInstance();
    ELRound elRound = elRoundMapper.toEntity(elRoundDTO);
    elRound.setElActivity(elActivityRepository.getOne(elRoundDTO.getActivityId()));
    for (ELRoundAttenderDTO elRoundAttenderDto : elRoundDTO.getElRoundAttenderDtos()) {
      ELRoundAttender elRoundAttender =
          ELRoundAttenderMapper.getInstance().toEntity(elRoundAttenderDto);
      elRoundAttender.setElRound(elRound);
      elRoundAttender.setAttender(
          elAttenderRepository.getOne(elRoundAttenderDto.getElAttenderDto().getId()));
      elRound.getElRoundAttenders().add(elRoundAttender);
    }
    return elRoundMapper.toDto(elRoundRepository.save(elRound));
  }

  @Override
  public ELRoundDTO findById(String elRoundId) {
    ELRoundMapper elRoundMapper = ELRoundMapper.getInstance();
    Optional<ELRound> roundOptional = elRoundRepository.findById(elRoundId);
    if (!roundOptional.isPresent()) {
      throw new IdInvalidException();
    }
    ELRound elRound = roundOptional.get();
    ELRoundDTO elRoundDTO = elRoundMapper.toDto(elRound);
    elRoundDTO.setElRoundAttenderDtos(this.mapperELRoundAttenders(elRound.getElRoundAttenders()));
    return elRoundDTO;
  }

  @Override
  public ELRound getMostRecentlyExecuteRound() {
    ELRound mostRecentlyExecute = elRoundRepository.findFirstByStatus(ELStatus.IN_PROCESS);
    if (mostRecentlyExecute != null) {
      return mostRecentlyExecute;
    }
    mostRecentlyExecute =
        elRoundRepository.findFirstByExpectedStartTimeAfterOrderByExpectedStartTimeAsc(new Date());
    return mostRecentlyExecute;
  }

  @Override
  public void saveAndTrailRun(String roundId, String attenderId, List<String> scriptIds) {
    Optional<ELRound> roundOptional = elRoundRepository.findById(roundId);
    Optional<ELAttender> attenderOptional = elAttenderRepository.findById(attenderId);
    ELRoundAttender roundAttender =
        elRoundAttenderRepository.findByRoundIdAAndAttenderId(roundId, attenderId);
    if (!roundOptional.isPresent() || !attenderOptional.isPresent() || roundAttender == null) {
      throw new IdInvalidException();
    }

    ELRound round = roundOptional.get();
    ELAttender attender = attenderOptional.get();
    // First: clean trial run cache by domain
    elMonitorService.cleanCacheByDomain(
        round.getElActivity().getActivityId(), round.getRoundId(), attender.getDomain());
    NodeNoticeCache.clearAll();
    NodeNoticeCache.clear(attenderId);
    // create RoundAttender
    roundAttender.setRunningStatus(ELStatus.TRIAL_RUN_IN_PROGRESS);
    elRoundAttenderRepository.save(roundAttender);

    // Update status cache
    RunningStatusMonitorCache.markToTrialRunInProgress(attender.getDomain());

    // Mark script have been selected
    List<ELScript> scripts = attender.getElServerFolder().getElScripts();
    List<ELScript> trailScript =
        scripts.stream()
            .peek(
                script -> {
                  script.setLastExecute(false);
                })
            .filter(script -> scriptIds.contains(script.getScriptId()))
            .peek(
                script -> {
                  script.setLastExecute(true);
                })
            .collect(Collectors.toList());
    elScriptRepository.saveAll(scripts);

    // call node server
    nodeClient.trailRun(
        attender.getElServerFolder().getElServer().getHost(),
        attender.getElServerFolder().getElServer().getPort(),
        trailScript);
  }

  @Override
  public void execute(String roundId) {
    Optional<ELRound> roundOptional = elRoundRepository.findById(roundId);
    if (!roundOptional.isPresent()) {
      throw new IdInvalidException();
    }
    // clean all cache for Load test
    elMonitorService.cleanAllCache();
    NodeNoticeCache.clearAll();

    ELRound elRound = roundOptional.get();
    // 如果有attender的状态Ready，则不执行
    for (ELRoundAttender elRoundAttender : elRound.getElRoundAttenders()) {
      if (elRoundAttender.getRunningStatus() != ELStatus.READY) {
        throw new AttenderNotReadyException();
      }
    }
    // 更新状态
    Date now = new Date();
    elRound.setActuallyStartTime(now);
    elRound.setStatus(ELStatus.IN_PROCESS);
    elRound
        .getElRoundAttenders()
        .forEach(
            elRoundAttender -> {
              elRoundAttender.setRunningStatus(ELStatus.IN_PROCESS);
              // update status cache
              RunningStatusMonitorCache.markToInProgress(elRoundAttender.getAttender().getDomain());
            });
    elRoundRepository.save(elRound);

    // group by el server
    Map<ELServer, List<ELScript>> serverScriptMap = new HashMap<>();
    for (ELRoundAttender elRoundAttender : elRound.getElRoundAttenders()) {
      for (ELScript script : elRoundAttender.getAttender().getElServerFolder().getElScripts()) {
        if (script.getLastExecute()) {
          ELServer elServer = script.getElServerFolder().getElServer();
          if (!serverScriptMap.containsKey(elServer)) {
            serverScriptMap.put(elServer, new ArrayList<>());
          }
          serverScriptMap.get(elServer).add(script);
        }
      }
    }
    // call node server to execute
    int durationSecond =
        (int)
            ((elRound.getExpectedEndTime().getTime() - elRound.getExpectedStartTime().getTime())
                / 1000);
    for (Map.Entry<ELServer, List<ELScript>> elServerListEntry : serverScriptMap.entrySet()) {
      logger.info(
          "host:{}, port:{}, script:{}",
          elServerListEntry.getKey().getHost(),
          elServerListEntry.getKey().getPort(),
          elServerListEntry.getValue());
      // TODO: 需要处理异常
      nodeClient.execute(
          elServerListEntry.getKey().getHost(),
          elServerListEntry.getKey().getPort(),
          durationSecond,
          elServerListEntry.getValue());
    }

    // notify monitor
    ELMonitorNotifyDTO notifyDTO = new ELMonitorNotifyDTO();
    notifyDTO.setActivityId(elRound.getElActivity().getActivityId());
    notifyDTO.setRoundId(elRound.getRoundId());
    notifyDTO.setEnv(elRound.getElActivity().getEnvironment());
    notifyDTO.setEarliest(SplunkUtil.getSplunkDate(elRound.getActuallyStartTime()));
    notifyDTO.setLatest(
        SplunkUtil.getSplunkDate(
            new Date(
                elRound.getActuallyStartTime().getTime()
                    + (elRound.getExpectedEndTime().getTime()
                        - elRound.getExpectedStartTime().getTime()))));
    notifyDTO.setInterval(1);
    for (ELRoundAttender elRoundAttender : elRound.getElRoundAttenders()) {
      notifyDTO.getDomains().add(elRoundAttender.getAttender().getDomain());
    }
    elMonitorService.notifyMonitorStart(notifyDTO);
  }

  @Override
  public void stopAll(String roundId) {
    Optional<ELRound> roundOptional = elRoundRepository.findById(roundId);
    if (!roundOptional.isPresent()) {
      throw new IdInvalidException();
    }
    List<String> domains = new ArrayList<>();
    ELRound round = roundOptional.get();
    for (ELRoundAttender roundAttender : round.getElRoundAttenders()) {
      String host = roundAttender.getAttender().getElServerFolder().getElServer().getHost();
      int port = roundAttender.getAttender().getElServerFolder().getElServer().getPort();
      domains.add(roundAttender.getAttender().getDomain());

      roundAttender.setRunningStatus(ELStatus.STOP);
      elRoundAttenderRepository.save(roundAttender);
      nodeClient.stopAll(host, port);
    }

    // Mark status to DB
    round.setStatus(ELStatus.STOP);
    elRoundRepository.save(round);

    for (String domain : domains) {
      RunningStatusMonitorCache.markToStop(domain);
    }

    // Call monitor
    ELMonitorNotifyDTO notifyDTO = new ELMonitorNotifyDTO();
    notifyDTO.setActivityId(round.getElActivity().getActivityId());
    notifyDTO.setRoundId(round.getRoundId());
    notifyDTO.getDomains().addAll(domains);
    elMonitorService.notifyMonitorStop(notifyDTO);
  }

  @Override
  public void stopAllByAttender(String roundId, String attenderId) {
    Optional<ELRound> elRoundOptional = elRoundRepository.findById(roundId);
    Optional<ELAttender> attenderOptional = elAttenderRepository.findById(attenderId);
    if (!attenderOptional.isPresent() || !elRoundOptional.isPresent()) {
      throw new IdInvalidException();
    }
    ELRound elRound = elRoundOptional.get();
    ELAttender attender = attenderOptional.get();
    // send to webservice
    RunningStatusMonitorCache.markToStop(attender.getDomain());

    markDataBaseStopStatus(attenderId, elRound);

    String host = attender.getElServerFolder().getElServer().getHost();
    int port = attender.getElServerFolder().getElServer().getPort();
    nodeClient.stop(host, port, attender.getElServerFolder().getElScripts());
    // Call monitor
    ELMonitorNotifyDTO notifyDTO = new ELMonitorNotifyDTO();
    notifyDTO.setActivityId(elRound.getElActivity().getActivityId());
    notifyDTO.setRoundId(elRound.getRoundId());
    notifyDTO.getDomains().add(attender.getDomain());
    elMonitorService.notifyMonitorStop(notifyDTO);
  }

  private void markDataBaseStopStatus(String attenderId, ELRound elRound) {
    boolean isAllStop = true;
    for (ELRoundAttender roundAttender : elRound.getElRoundAttenders()) {
      if (roundAttender.getAttender().getAttenderId().equals(attenderId)) {
        roundAttender.setRunningStatus(ELStatus.STOP);
        elRoundAttenderRepository.save(roundAttender);
      }
      if (roundAttender.getRunningStatus() == ELStatus.IN_PROCESS) {
        isAllStop = false;
        break;
      }
    }
    if (isAllStop) {
      elRound.setStatus(ELStatus.STOP);
      elRoundRepository.save(elRound);
    }
  }

  @Override
  public void autoTrigger() {
    Duration duration = Duration.ofDays(1);
    long second = duration.getSeconds();
    Date now = new Date();
    Date oneDayAgo = new Date(now.getTime() - second * 1000);
    Optional<String> readyRoundId =
        elRoundRepository.findIdByExpectedExecuteDateAfterAndNotExistNoReadyAttender(
            oneDayAgo, now, ELStatus.READY, ELRoundRepository.EXPECTED_START_TIME_ASC);
    readyRoundId.ifPresent(this::execute);
  }

  @Override
  public ELRoundDTO updateELRound(ELRoundDTO elRoundDto) {
    if (elRoundDto.getStatus().equals(ELStatus.NEW)) {
      List<ELRoundAttender> removedELRoundAttenders = getRemoveELAttenders(elRoundDto);
      if (!CollectionUtils.isEmpty(removedELRoundAttenders)) {
        this.elRoundAttenderRepository.deleteInBatch(removedELRoundAttenders);
      }
      return this.saveELRoundDto(elRoundDto);
    } else {
      throw new UpdatedELRoundInvalidedStatusException();
    }
  }

  @Override
  public Page<ELRound> getELRoundByExample(ELRound example, Pageable pageable) {
    Page<ELRound> elRounds =
        elRoundRepository.findAll(
            Example.of(
                example,
                ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withStringMatcher(ExampleMatcher.StringMatcher.STARTING)),
            pageable);
    return elRounds;
  }

  @Override
  public String getNextVersion(String activityId, String times) {
    List<ELRound> elRounds = elRoundRepository.findByELActivityIdAndTimes(activityId, times);
    if (CollectionUtils.isEmpty(elRounds)) {
      return "1";
    }
    return String.valueOf(Integer.valueOf(elRounds.get(0).getVersion()) + 1);
  }

  private List<ELRoundAttender> getRemoveELAttenders(ELRoundDTO elRoundDto) {
    List<ELRoundAttender> elRoundAttenders =
        elRoundAttenderService.findByRoundId(elRoundDto.getRoundId());
    List<ELRoundAttender> removedELRoundAttenders = new ArrayList<>();

    List<ELRoundAttenderDTO> elRoundAttenderDtos = elRoundDto.getElRoundAttenderDtos();
    for (ELRoundAttender elRoundAttender : elRoundAttenders) {
      List<ELRoundAttenderDTO> noNeedRemovedElRoundAttenders =
          elRoundAttenderDtos.stream()
              .filter(
                  elRoundAttenderDto ->
                      Objects.equals(
                          elRoundAttenderDto.getRoundAttenderId(),
                          elRoundAttender.getRoundAttenderId()))
              .collect(Collectors.toList());
      if (CollectionUtils.isEmpty(noNeedRemovedElRoundAttenders)) {
        removedELRoundAttenders.add(elRoundAttender);
      }
    }
    return removedELRoundAttenders;
  }

  private List<ELRoundAttenderDTO> mapperELRoundAttenders(List<ELRoundAttender> elRoundAttenders) {
    List<ELRoundAttenderDTO> elRoundAttenderDTOs = new ArrayList<>();
    ELRoundAttenderMapper elRoundAttenderMapper = ELRoundAttenderMapper.getInstance();
    for (ELRoundAttender elRoundAttender : elRoundAttenders) {
      elRoundAttenderDTOs.add(elRoundAttenderMapper.toDto(elRoundAttender));
    }
    return elRoundAttenderDTOs;
  }

  @Autowired
  public void setElScriptRepository(ELScriptRepository elScriptRepository) {
    this.elScriptRepository = elScriptRepository;
  }

  @Autowired
  public void setElRoundRepository(ELRoundRepository elRoundRepository) {
    this.elRoundRepository = elRoundRepository;
  }

  @Autowired
  public void setNodeClient(NodeClient nodeClient) {
    this.nodeClient = nodeClient;
  }
}
