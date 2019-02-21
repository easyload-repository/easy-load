package com.oocl.easyload.monitor.factory;

import com.oocl.easyload.monitor.constant.Tips;
import com.oocl.easyload.monitor.dto.RoundStartDTO;
import com.oocl.easyload.monitor.dto.SchedulerDTO;
import com.oocl.easyload.monitor.exceptions.QuartzException;
import com.oocl.easyload.monitor.splunk.PrdSplunkService;
import com.oocl.easyload.monitor.utils.QuartzUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class QuartzSchedulerFactory {
  private static final Logger logger = LoggerFactory.getLogger(PrdSplunkService.class);

  public static Scheduler scheduler;
  public static List<String> types = Arrays.asList("ws", "exception");

  static {
    try {
      scheduler = QuartzUtils.schedulerFactory.getScheduler();
    } catch (SchedulerException e) {
      e.printStackTrace();
      throw new QuartzException();
    }
  }

  public static Scheduler init(RoundStartDTO roundInfo) throws SchedulerException {
    for (String domain : roundInfo.getDomains()) {
      if (isContain(roundInfo, domain)) continue;
      for (String type : types) {
        String jobGroupName =
            MessageFormat.format(
                "{0}_{1}_{2}_{3}_JobGroup",
                roundInfo.getActiveId(), roundInfo.getRoundId(), domain, type);
        SchedulerDTO dto = SchedulerDTO.init(roundInfo, domain, type);
        QuartzUtils.addJob(dto.job(), dto.cron(), scheduler);
        logger.info(
            "QuartzSchedulerFactory.java - init - ["
                + jobGroupName
                + "]"
                + Tips.INIT_SCHEDULER_SUCCESS);
      }
    }
    return scheduler;
  }

  private static boolean isContain(RoundStartDTO roundInfo, String domain) {
    for (String type : types) {
      String jobGroupName =
          MessageFormat.format(
              "{0}_{1}_{2}_{3}_JobGroup",
              roundInfo.getActiveId(), roundInfo.getRoundId(), domain, type);
      try {
        if (scheduler.getJobGroupNames().contains(jobGroupName)) {
          logger.error(
              "QuartzSchedulerFactory.java - isContain - ["
                  + jobGroupName
                  + "]"
                  + Tips.START_ROUND_GROUP_NAME_EXTING);
          return true;
        }
      } catch (SchedulerException e) {
        e.printStackTrace();
        throw new QuartzException();
      }
    }
    return false;
  }
}
