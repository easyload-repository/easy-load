package com.oocl.easyload.server.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.easyload.server.cache.ExceptionSearchCache;
import com.oocl.easyload.server.cache.RunningStatusMonitorCache;
import com.oocl.easyload.server.cache.WebServiceSearchCache;
import com.oocl.easyload.server.dto.ELMonitorNotifyDTO;
import com.oocl.easyload.server.dto.cache.ELExceptionEventDTO;
import com.oocl.easyload.server.dto.cache.ELExceptionSearchDTO;
import com.oocl.easyload.server.dto.cache.ELWebServiceSearchDTO;
import com.oocl.easyload.server.service.ELMonitorService;
import com.oocl.easyload.server.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ELMonitorServiceImpl implements ELMonitorService {

  private static final Logger logger = LoggerFactory.getLogger(ELMonitorServiceImpl.class);

  @Value("${easyload.constance.host}")
  private String HOST;

  private String NOTIFY_MONITOR_START_URL;
  private String NOTIFY_MONITOR_STOP_URL;
  private String NOTIFY_MONITOR_STOP_ALL_URL;

  @PostConstruct
  public void init() {
    NOTIFY_MONITOR_START_URL = HOST + "/api/splunk/start";
    NOTIFY_MONITOR_STOP_URL = HOST + "/api/splunk/stop";
    NOTIFY_MONITOR_STOP_ALL_URL = HOST + "/api/splunk/stopAll";
  }

  @Override
  public String notifyMonitorStart(ELMonitorNotifyDTO notifyDTO) {
    try {
      logger.info("[Notify monitor]" + new ObjectMapper().writeValueAsString(notifyDTO));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    HashMap<String, String> param = new HashMap<>();
    param.put("activeId", notifyDTO.getActivityId());
    param.put("roundId", notifyDTO.getRoundId());
    param.put("domains", StringUtils.join(notifyDTO.getDomains(), ","));
    param.put("interval", "" + notifyDTO.getInterval());
    param.put("earliest", notifyDTO.getEarliest());
    param.put("latest", notifyDTO.getLatest());
    param.put("env", notifyDTO.getEnv());

    return HttpClientUtil.doGet(NOTIFY_MONITOR_START_URL, param);
  }

  @Override
  public String notifyMonitorStop(ELMonitorNotifyDTO notifyDTO) {
    HashMap<String, String> param = new HashMap<>();
    param.put("activeId", notifyDTO.getActivityId());
    param.put("roundId", notifyDTO.getRoundId());
    param.put("domains", StringUtils.join(notifyDTO.getDomains(), ","));

    return HttpClientUtil.doGet(NOTIFY_MONITOR_STOP_URL, param);
  }

  @Override
  public String notifyMonitorStopAll() {
    return HttpClientUtil.doGet(NOTIFY_MONITOR_STOP_ALL_URL);
  }

  @Override
  public ELWebServiceSearchDTO getWebServiceLevelEvent(
      String activityId, String roundId, String domain) {
    return WebServiceSearchCache.cache.get(getKey(activityId, roundId, domain));
  }

  @Override
  public Collection<ELWebServiceSearchDTO> getAllWebServiceLevelEvent() {
    return WebServiceSearchCache.cache.values();
  }

  @Override
  public ELExceptionSearchDTO getExceptionLevelEvent(
      String activityId, String roundId, String domain) {
    return ExceptionSearchCache.cache.get(getKey(activityId, roundId, domain));
  }

  @Override
  public Collection<ELExceptionSearchDTO> getAllExceptionLevelEvent() {
    return ExceptionSearchCache.cache.values();
  }

  @Override
  public Map<String, Integer> getExceptionLevelEventCount(
      String activityId, String roundId, String domain) {
    Map<String, Integer> domainExceptionMap = new HashMap<>();
    ELExceptionSearchDTO elExceptionSearchDTO =
        ExceptionSearchCache.cache.get(getKey(activityId, roundId, domain));
    int totalException = 0;
    for (ELExceptionEventDTO event : elExceptionSearchDTO.getEvents().values()) {
      totalException += event.getCount();
    }
    domainExceptionMap.put(domain, totalException);
    return domainExceptionMap;
  }

  @Override
  public Map<String, Integer> getAllExceptionLevelEventCount() {
    Map<String, Integer> allExceptionMap = new HashMap<>();
    for (String key : ExceptionSearchCache.cache.keySet()) {
      String domain = key.split("_")[2];
      int domainExceptionCount = 0;
      ELExceptionSearchDTO elExceptionSearchDTO = ExceptionSearchCache.cache.get(key);
      for (ELExceptionEventDTO event : elExceptionSearchDTO.getEvents().values()) {
        domainExceptionCount += event.getCount();
      }
      allExceptionMap.put(domain, domainExceptionCount);
    }
    return allExceptionMap;
  }

  @Override
  public String getStatusByDomain(String domain) {
    return RunningStatusMonitorCache.cache.get(domain);
  }

  @Override
  public Map<String, String> getAllStatus() {
    return RunningStatusMonitorCache.cache;
  }

  @Override
  public void cleanCacheByDomain(String activityId, String roundId, String domain) {
    logger.info("[Clean cache]" + getKey(activityId, roundId, domain));
    ExceptionSearchCache.cache.remove(getKey(activityId, roundId, domain));
    WebServiceSearchCache.cache.remove(getKey(activityId, roundId, domain));
    RunningStatusMonitorCache.cache.remove(domain);
  }

  @Override
  public void cleanAllCache() {
    logger.info("[Clean All Cache]");
    ExceptionSearchCache.cache.clear();
    WebServiceSearchCache.cache.clear();
    RunningStatusMonitorCache.cache.clear();
  }

  private String getKey(String activityId, String roundId, String domain) {
    return activityId + "_" + roundId + "_" + domain;
  }
}
