package com.oocl.easyload.server.service;

import com.oocl.easyload.model.entity.ELRoundAttender;

import java.util.List;

public interface ELRoundAttenderService {
  List<ELRoundAttender> findByRoundId(String elRoundId);

  /**
   * 获取activity最新的round的attender
   *
   * @param activityId
   * @return
   */
  List<ELRoundAttender> getLatestByActivity(String activityId);
}
