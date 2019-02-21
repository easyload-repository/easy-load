package com.oocl.easyload.monitor.utils;

import com.oocl.easyload.monitor.constant.MonitorName;
import com.oocl.easyload.monitor.factory.QuartzSchedulerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class MonitorManagerUtil {
  private static final Logger logger = LoggerFactory.getLogger(MonitorManagerUtil.class);
  public static List<String> types = Arrays.asList("ws", "exception");

  public static boolean clearAll() {
    try {
      Scheduler scheduler = QuartzSchedulerFactory.scheduler;
      for (String groupName : scheduler.getJobGroupNames()) {
        String jobName = groupName.replace("JobGroup", "Job");
        QuartzUtils.removeJob(scheduler, jobName, groupName);
        logger.info("MonitorManagerUtil.java - clearAll - remove [" + jobName + "] Success!");
      }
      logger.info("MonitorManagerUtil.java - clearAll - remove all monitors success!");
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean removeByDomain(String activeId, String roundId, String domain) {
    for (String type : types) {
      Scheduler scheduler = QuartzSchedulerFactory.scheduler;
      MonitorName monitor = new MonitorName(activeId, roundId, domain, type);
      if (isContain(monitor.getJobGroupName())) {
        boolean removeJob =
            QuartzUtils.removeJob(scheduler, monitor.getJobName(), monitor.getJobGroupName());
        if (!removeJob) {
          logger.error(
              "MonitorManagerUtil.java - removeByDomain - remove ["
                  + monitor.getJobName()
                  + "] fail!");
        } else {
          logger.info(
              "MonitorManagerUtil.java - removeByDomain - remove ["
                  + monitor.getJobName()
                  + "] Success!");
        }
      } else {
        logger.error(
            "MonitorManagerUtil.java - removeByDomain - The scheduler doesn't contain the ["
                + monitor.getJobName()
                + "] job!");
      }
    }
    return true;
  }

  public static boolean isContain(String groupName) {
    Scheduler scheduler = QuartzSchedulerFactory.scheduler;
    try {
      List<String> jobGroupNames = scheduler.getJobGroupNames();
      for (String name : jobGroupNames) {
        if (name.equals(groupName)) return true;
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }
}
