package com.oocl.easyload.server.service.impl;


import com.oocl.easyload.model.entity.ELRound;
import com.oocl.easyload.model.entity.ELRoundAttender;
import com.oocl.easyload.server.exception.IdInvalidException;
import com.oocl.easyload.server.repository.ELRoundAttenderRepository;
import com.oocl.easyload.server.repository.ELRoundRepository;
import com.oocl.easyload.server.service.ELRoundAttenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ELRoundAttenderServiceImpl implements ELRoundAttenderService {
  @Autowired private ELRoundAttenderRepository elRoundAttenderRepository;
  @Autowired private ELRoundRepository elRoundRepository;

  @Override
  public List<ELRoundAttender> findByRoundId(String elRoundId) {
    return elRoundAttenderRepository.findByRoundId(elRoundId);
  }

  @Override
  public List<ELRoundAttender> getLatestByActivity(String activityId) {
    ELRound elRound =
        elRoundRepository.findFirstByElActivity_ActivityIdOrderByCreateTimeDesc(activityId);
    if (elRound == null) {
      throw new IdInvalidException();
    }
    return elRound.getElRoundAttenders();
  }
}
