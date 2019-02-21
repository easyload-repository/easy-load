package com.oocl.easyload.server.service;

import com.oocl.easyload.model.entity.ELRound;
import com.oocl.easyload.server.dto.ELRoundDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ELRoundService {
  ELRoundDTO saveELRoundDto(ELRoundDTO elRoundDto);

  ELRoundDTO findById(String elRoundId);

  ELRound getMostRecentlyExecuteRound();

  /** save script need to execute and trail run */
  void saveAndTrailRun(String roundId, String attenderId, List<String> scriptIds);

  void execute(String roundId);

  void stopAll(String roundId);

  void stopAllByAttender(String roundId, String attenderId);

  void autoTrigger();

  ELRoundDTO updateELRound(ELRoundDTO elRoundDto);

  Page<ELRound> getELRoundByExample(ELRound example, Pageable pageable);

  String getNextVersion(String activityId, String times);
}
