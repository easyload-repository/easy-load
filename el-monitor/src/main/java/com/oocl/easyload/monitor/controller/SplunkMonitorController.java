package com.oocl.easyload.monitor.controller;

import com.oocl.easyload.monitor.dto.RoundStartDTO;
import com.oocl.easyload.monitor.dto.SplunkSearchDTO;
import com.oocl.easyload.monitor.dto.StopMonitorDTO;
import com.oocl.easyload.monitor.exceptions.QuartzException;
import com.oocl.easyload.monitor.service.SplunkSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/splunk")
public class SplunkMonitorController {
  private static final Logger logger = LoggerFactory.getLogger(SplunkMonitorController.class);

  @Value("${project.version}")
  private String version;

  @Autowired
  SplunkSearchService service;

  @GetMapping(value = "search")
  @RequestMapping(
      value = "search",
      method = {RequestMethod.POST, RequestMethod.GET})
  public @ResponseBody String search(SplunkSearchDTO dto) {
    service.searchForEs(dto);
    return "Success";
  }

  @RequestMapping(
      value = "start",
      method = {RequestMethod.POST, RequestMethod.GET})
  public @ResponseBody String start(RoundStartDTO dto) {
    try {
      service.start(dto);
    } catch (Exception e) {
      e.printStackTrace();
      throw new QuartzException();
    }
    return "Success";
  }

  @RequestMapping(
      value = "stop",
      method = {RequestMethod.POST, RequestMethod.GET})
  public @ResponseBody String stop(StopMonitorDTO dto) {
    service.stop(dto);
    return "Success";
  }

  @RequestMapping(
      value = "stopAll",
      method = {RequestMethod.POST, RequestMethod.GET})
  public @ResponseBody String stopAll(StopMonitorDTO dto) {
    service.stopAll(dto);
    return "Success";
  }

  @RequestMapping(
      value = "version",
      method = {RequestMethod.GET, RequestMethod.POST})
  public @ResponseBody String version() {
    return version;
  }
}
