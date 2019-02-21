package com.oocl.easyload.server.job;

import com.oocl.easyload.server.service.ELRoundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

// @Component
public class RoundAutoTriggerJob {
  private static final Logger logger = LoggerFactory.getLogger(RoundAutoTriggerJob.class);

  private ELRoundService elRoundService;

  @Scheduled(cron = "0 0/1 * * * ?")
  public void autoTrigger() {
    // logger.info("auto trigger");
    try {
      elRoundService.autoTrigger();
    } catch (Exception e) {
      logger.error("unexpected exception", e);
    }
  }

  @Autowired
  public void setElRoundService(ELRoundService elRoundService) {
    this.elRoundService = elRoundService;
  }
}
