package com.oocl.easyload.server.service;

import com.oocl.easyload.server.dto.ELMonitorNotifyDTO;
import com.oocl.easyload.server.dto.cache.ELExceptionSearchDTO;
import com.oocl.easyload.server.dto.cache.ELWebServiceSearchDTO;

import java.util.Collection;
import java.util.Map;

public interface ELMonitorService {

  String notifyMonitorStart(ELMonitorNotifyDTO notifyDTO);

  String notifyMonitorStop(ELMonitorNotifyDTO notifyDTO);

  String notifyMonitorStopAll();

  ELWebServiceSearchDTO getWebServiceLevelEvent(String activityId, String roundId, String domain);

  Collection<ELWebServiceSearchDTO> getAllWebServiceLevelEvent();

  ELExceptionSearchDTO getExceptionLevelEvent(String activityId, String roundId, String domain);

  Collection<ELExceptionSearchDTO> getAllExceptionLevelEvent();

  Map<String, Integer> getExceptionLevelEventCount(
      String activityId, String roundId, String domain);

  Map<String, Integer> getAllExceptionLevelEventCount();

  String getStatusByDomain(String domain);

  Map<String, String> getAllStatus();

  void cleanCacheByDomain(String activityId, String roundId, String domain);

  void cleanAllCache();
}
