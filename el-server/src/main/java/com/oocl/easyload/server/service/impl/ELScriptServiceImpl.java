package com.oocl.easyload.server.service.impl;

import com.oocl.easyload.model.entity.*;
import com.oocl.easyload.server.cache.NodeNoticeCache;
import com.oocl.easyload.server.cache.RunningStatusMonitorCache;
import com.oocl.easyload.server.common.NodeConstatnce;
import com.oocl.easyload.server.exception.IdInvalidException;
import com.oocl.easyload.server.repository.ELRoundAttenderRepository;
import com.oocl.easyload.server.repository.ELRoundRepository;
import com.oocl.easyload.server.repository.ELScriptRepository;
import com.oocl.easyload.server.service.ELScriptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ELScriptServiceImpl implements ELScriptService {
  private static final Logger logger = LoggerFactory.getLogger(ELScriptServiceImpl.class);
  private ELRoundRepository elRoundRepository;
  private ELRoundAttenderRepository elRoundAttenderRepository;
  private ELScriptRepository elScriptRepository;

  @Override
  @Transactional
  public void handleNodeNotice(String scriptId, String status) {
    // 找出相关对象
    Optional<ELScript> scriptOptional = elScriptRepository.findById(scriptId);
    if (!scriptOptional.isPresent()) {
      throw new IdInvalidException();
    }
    ELScript script = scriptOptional.get();
    ELAttender attender = script.getElServerFolder().getElAttender();
    ELRoundAttender roundAttender =
        elRoundAttenderRepository.findFirstByAttenderAndRunningStatusIn(
            attender,
            ELRoundAttenderRepository.IN_PROCESS_STATUSES,
            ELRoundAttenderRepository.CREATE_TIME_ASC);
    ELRound elRound = roundAttender.getElRound();
    // add to cache
    NodeNoticeCache.addScript(attender.getAttenderId(), scriptId, status);
    // 处理逻辑
    if (Objects.equals(NodeConstatnce.COMPLETE, status)) {
      if (roundAttender.getRunningStatus() == ELStatus.TRIAL_RUN_IN_PROGRESS) {
        if (NodeNoticeCache.isAttenderReadyOrComplete(
            attender.getAttenderId(), attender.getElServerFolder().getElScripts())) {
          RunningStatusMonitorCache.markToReady(attender.getDomain());
          roundAttender.setRunningStatus(ELStatus.READY);
        }
        if (isAllRoundAttenderStatusEqual(elRound, ELStatus.READY)) {
          elRound.setStatus(ELStatus.READY);
        }

      } else /* if (roundAttender.getRunningStatus() == ELStatus.IN_PROCESS) */ {
        // TODO: remove above if comment
        if (NodeNoticeCache.isAttenderReadyOrComplete(
            attender.getAttenderId(), attender.getElServerFolder().getElScripts())) {
          roundAttender.setRunningStatus(ELStatus.COMPLETE);
          RunningStatusMonitorCache.markToComplete(attender.getDomain());
        }
        if (isAllRoundAttenderStatusEqual(elRound, ELStatus.IN_PROCESS)) {
          elRound.setStatus(ELStatus.COMPLETE);
        }
      }
    } else if (Objects.equals(NodeConstatnce.ERROR, status)) {

      roundAttender.setRunningStatus(ELStatus.ERROR);
      RunningStatusMonitorCache.markToError(attender.getDomain());
      elRound.setStatus(ELStatus.ERROR);
    }
    elRoundRepository.save(elRound);
    elRoundAttenderRepository.save(roundAttender);
  }

  private boolean isAllRoundAttenderStatusEqual(ELRound elRound, ELStatus status) {
    for (ELRoundAttender elRoundAttender : elRound.getElRoundAttenders()) {
      if (elRoundAttender.getRunningStatus() != status) {
        return false;
      }
    }
    return true;
  }

  @Autowired
  public void setElRoundRepository(ELRoundRepository elRoundRepository) {
    this.elRoundRepository = elRoundRepository;
  }

  @Autowired
  public void setElRoundAttenderRepository(ELRoundAttenderRepository elRoundAttenderRepository) {
    this.elRoundAttenderRepository = elRoundAttenderRepository;
  }

  @Autowired
  public void setElScriptRepository(ELScriptRepository elScriptRepository) {
    this.elScriptRepository = elScriptRepository;
  }
}
