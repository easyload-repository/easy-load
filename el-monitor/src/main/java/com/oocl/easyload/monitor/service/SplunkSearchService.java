package com.oocl.easyload.monitor.service;

import com.oocl.easyload.monitor.dto.RoundStartDTO;
import com.oocl.easyload.monitor.dto.SchedulerDTO;
import com.oocl.easyload.monitor.dto.SplunkSearchDTO;
import com.oocl.easyload.monitor.dto.StopMonitorDTO;

public interface SplunkSearchService {
  public void search(SplunkSearchDTO criteria, SchedulerDTO job);

  public void searchForEs(SplunkSearchDTO criteria);

  public void start(RoundStartDTO startCriteria) throws Exception;

  public void stop(StopMonitorDTO dto);

  void stopAll(StopMonitorDTO dto);
}
