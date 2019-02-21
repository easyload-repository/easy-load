package com.oocl.easyload.server.controller;

import com.oocl.easyload.model.entity.ELRound;
import com.oocl.easyload.model.entity.ELStatus;
import com.oocl.easyload.server.dto.ELRoundAttenderDTO;
import com.oocl.easyload.server.dto.ELRoundDTO;
import com.oocl.easyload.server.mapper.ELRoundAttenderMapper;
import com.oocl.easyload.server.mapper.ELRoundMapper;
import com.oocl.easyload.server.service.ELRoundService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/round")
public class ELRoundController {
  private static Logger logger = LoggerFactory.getLogger(ELRoundController.class);
  private ELRoundService elRoundService;

  /**
   * 获取最近将要执行的ELRound
   *
   * @return
   */
  @GetMapping("/most-recently-execute")
  public ELRoundDTO getMostRecentlyExecuteRound() {
    ELRound elRound = elRoundService.getMostRecentlyExecuteRound();
    if (elRound == null) {
      return null;
    }
    ELRoundDTO dto = ELRoundMapper.getInstance().toDto(elRound);
    dto.setElRoundAttenderDtos(
        elRound.getElRoundAttenders().stream()
            .map(elRoundAttender -> ELRoundAttenderMapper.getInstance().toDto(elRoundAttender))
            .collect(Collectors.toList()));
    return dto;
  }

  @PostMapping
  public ELRoundDTO createELRound(@RequestBody ELRoundDTO requestDto) {
    initStatus(requestDto);
    return elRoundService.saveELRoundDto(requestDto);
  }

  private void initStatus(@RequestBody ELRoundDTO requestDto) {
    requestDto.setStatus(ELStatus.NEW);
    for (ELRoundAttenderDTO elRoundAttenderDTO : requestDto.getElRoundAttenderDtos()) {
      if (StringUtils.isEmpty(elRoundAttenderDTO.getRoundAttenderId())) {
        elRoundAttenderDTO.setRunningStatus(ELStatus.NEW);
      }
    }
  }

  @PutMapping
  public ELRoundDTO updateELRound(@RequestBody ELRoundDTO requestDto) {
    initStatus(requestDto);
    return elRoundService.updateELRound(requestDto);
  }

  @GetMapping("/{elRoundId}")
  public ELRoundDTO findById(@PathVariable String elRoundId) {
    return elRoundService.findById(elRoundId);
  }

  @PostMapping("/{roundId}/attender/{attenderId}/trial-run")
  public void saveAndTrailRun(
      @PathVariable String roundId,
      @PathVariable String attenderId,
      @RequestBody List<String> scriptIds) {
    logger.debug(
        "trail-run roundId:{}, attenderId:{}, scriptIds:{}", roundId, attenderId, scriptIds);
    elRoundService.saveAndTrailRun(roundId, attenderId, scriptIds);
  }

  @PostMapping("/{roundId}/execute")
  public void execute(@PathVariable String roundId) {
    logger.debug("execute roundId:{}", roundId);
    elRoundService.execute(roundId);
  }

  @PostMapping("/{roundId}/attender/stop/all")
  public void stopAll(@PathVariable String roundId) {
    elRoundService.stopAll(roundId);
  }

  @PostMapping("/{roundId}/attender/{attenderId}/stop/all")
  public void stopAllByAttender(@PathVariable String roundId, @PathVariable String attenderId) {
    elRoundService.stopAllByAttender(roundId, attenderId);
  }

  @GetMapping("/getNextVersion")
  public String getNextVersion(String activityId, String times) {
    return elRoundService.getNextVersion(activityId, times);
  }

  @Autowired
  public void setElRoundService(ELRoundService elRoundService) {
    this.elRoundService = elRoundService;
  }
}
