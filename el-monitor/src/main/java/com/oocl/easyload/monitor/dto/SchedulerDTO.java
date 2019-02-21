package com.oocl.easyload.monitor.dto;

import com.oocl.easyload.monitor.constant.MonitorName;
import com.oocl.easyload.monitor.factory.QuartzJobFactoury;
import com.oocl.easyload.monitor.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SchedulerDTO {

  private static final Logger logger = LoggerFactory.getLogger(SchedulerDTO.class);

  private static List<String> prodEnvs = Arrays.asList("prod");
  private static List<String> qaEnvs = Arrays.asList("qa1", "qa2", "qa3", "qa4");

  RoundStartDTO round;
  // JobDetails
  Class jobClz;
  String jobName;
  String jobGroupName;
  // CronTrigger
  String triggerName;
  String triggerGroupName;
  String time;
  Date expectStartDate; // 希望开始时间，load test开始时间
  Date expectEndDate; // 希望结束的时间，load test结束时间
  Date startDate; // Job中设置的启动的时间 = 希望开始时间（分钟） + 间隔时间（分钟） + 1
  Date endDate; // Job中设置结束的时间 = 希望结束的时间（分钟） + 间隔时间（分钟） + 1
  Date jobFrom; // 当前这次job所需在splunk中搜索的开始时间
  Date jobTo; // 当前这次job所需在splunk中搜索的结束时间
  // Supplement
  String domain;
  String intervalUrl; // 间隔URL
  String roundUrl; // 当前的URL
  // Supplement
  String type;
  boolean isDomainLatest;
  boolean isRoundLatest;

  public static SchedulerDTO init(RoundStartDTO roundInfo, String domain, String type) {
    String activeId = roundInfo.getActiveId();
    String roundId = roundInfo.getRoundId();
    MonitorName monitorName = new MonitorName(activeId, roundId, domain, type);
    String time = "0 0/" + roundInfo.getInterval() + " * * * ? *";
    Date expectStartDate =
        DateUtils.stringToDate(roundInfo.getEarliest(), DateUtils.SPLUNK_TIME_FORMAT);
    Date expectEndDate =
        DateUtils.stringToDate(roundInfo.getLatest(), DateUtils.SPLUNK_TIME_FORMAT);
    Date startDate =
        new Date(expectStartDate.getTime() + (roundInfo.getInterval() + 1) * 60 * 1000);
    Date endDate = new Date(expectEndDate.getTime() + (roundInfo.getInterval() + 1) * 60 * 1000);
    Date jobFrom = expectStartDate;
    Date jobTo = new Date(expectStartDate.getTime() + roundInfo.getInterval() * 60 * 1000);
    String intervalUrl = "";
    String roundUrl = "";
    return new SchedulerDTO(
        roundInfo,
        QuartzJobFactoury.of(type),
        monitorName.getJobName(),
        monitorName.getJobGroupName(),
        monitorName.getTriggerName(),
        monitorName.getTriggerGroupName(),
        time,
        expectStartDate,
        expectEndDate,
        startDate,
        endDate,
        jobFrom,
        jobTo,
        domain,
        intervalUrl,
        roundUrl,
        type,
        false,
        false);
  }

  public JobDetail job() {
    JobDataMap map = new JobDataMap();
    map.put("job", this);
    JobDetail job =
        newJob(this.getJobClz())
            .withIdentity(this.getJobName(), this.getJobGroupName())
            .setJobData(map)
            .build();
    return job;
  }

  public CronTrigger cron() {
    CronTrigger trigger =
        newTrigger()
            .withIdentity(this.getTriggerName(), this.getTriggerGroupName())
            .withSchedule(cronSchedule(this.getTime()))
            .startAt(this.getStartDate())
            .endAt(this.getEndDate())
            .build();
    logger.info("SchedulerDTO.java - cron - start : " + this.getStartDate());
    logger.info("SchedulerDTO.java - cron - end : " + this.getEndDate());
    logger.info("SchedulerDTO.java - cron - time : " + this.getTime());
    return trigger;
  }

  public SchedulerDTO updateFromAndToDate() {
    this.setJobFrom(this.getJobTo());
    this.setJobTo(new Date(this.getJobTo().getTime() + this.getRound().getInterval() * 60 * 1000));
    return this;
  }

  public String getFrom() {
    return DateUtils.dateToString(this.getJobFrom(), DateUtils.SPLUNK_TIME_FORMAT);
  }

  public String getTo() {
    return DateUtils.dateToString(this.getJobTo(), DateUtils.SPLUNK_TIME_FORMAT);
  }

  public String getLoadTestStartDate() {
    return DateUtils.dateToString(this.getExpectStartDate(), DateUtils.SPLUNK_TIME_FORMAT);
  }

  public String getSearchInternalUrl() {
    if (prodEnvs.contains(this.getRound().getEnv())) {
      return "http://hklp738p:8300/en-US/app/IR4_APP_LOG_ANALYSIS/search?q="
          + this.getIntervalUrl();
    }
    return "http://hln2038p:8100/zh-CN/app/search/search?q=" + this.getIntervalUrl();
  }

  public String getSearchRoundUrl() {
    if (prodEnvs.contains(this.getRound().getEnv())) {
      return "http://hklp738p:8300/en-US/app/IR4_APP_LOG_ANALYSIS/search?q=" + this.getRoundUrl();
    }
    return "http://hln2038p:8100/zh-CN/app/search/search?q=" + this.getRoundUrl();
  }
}
