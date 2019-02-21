package com.oocl.easyload.server.controller;

import com.oocl.easyload.server.cache.RunningStatusMonitorCache;
import com.oocl.easyload.server.dto.cache.ELExceptionSearchDTO;
import com.oocl.easyload.server.dto.cache.ELWebServiceSearchDTO;
import com.oocl.easyload.server.service.ELMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/api/monitor")
public class ELMonitorController {

  @Autowired private ELMonitorService elMonitorService;

  @GetMapping("/splunk/ws")
  public @ResponseBody ELWebServiceSearchDTO getWebServiceLevelEvent(
      String activityId, String roundId, String domain) {
    return elMonitorService.getWebServiceLevelEvent(activityId, roundId, domain);
  }

  @GetMapping("/splunk/ws/all")
  public @ResponseBody Collection<ELWebServiceSearchDTO> getAllWebServiceLevelEvent() {
    return elMonitorService.getAllWebServiceLevelEvent();
  }

  @GetMapping("/splunk/exception")
  public @ResponseBody ELExceptionSearchDTO getExceptionLevelEvent(
      String activityId, String roundId, String domain) {
    return elMonitorService.getExceptionLevelEvent(activityId, roundId, domain);
  }

  @GetMapping("/splunk/exception/all")
  public @ResponseBody Collection<ELExceptionSearchDTO> getAllExceptionLevelEvent() {
    return elMonitorService.getAllExceptionLevelEvent();
  }

  @GetMapping("/splunk/exception/count")
  public @ResponseBody Map<String, Integer> getExceptionLevelEventCount(
      String activityId, String roundId, String domain) {
    return elMonitorService.getExceptionLevelEventCount(activityId, roundId, domain);
  }

  @GetMapping("/splunk/exception/all/count")
  public @ResponseBody Map<String, Integer> getAllExceptionLevelEventCount() {
    return elMonitorService.getAllExceptionLevelEventCount();
  }

  @GetMapping("/status/{domain}")
  public @ResponseBody String getStatusByDomain(@PathVariable("domain") String domain) {
    return elMonitorService.getStatusByDomain(domain);
  }

  @GetMapping("/status/all")
  public @ResponseBody Map<String, String> getAllStatus() {
    return elMonitorService.getAllStatus();
  }

  @GetMapping("/status/setting")
  public Map<String, String> setStatus(String domain, String status) {
    RunningStatusMonitorCache.cache.put(domain, status);
    return RunningStatusMonitorCache.cache;
  }
}
