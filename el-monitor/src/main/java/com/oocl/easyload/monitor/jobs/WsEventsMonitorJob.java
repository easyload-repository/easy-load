package com.oocl.easyload.monitor.jobs;

import com.oocl.easyload.monitor.constant.Tips;
import com.oocl.easyload.monitor.dto.SchedulerDTO;
import com.oocl.easyload.monitor.dto.SplunkSearchDTO;
import com.oocl.easyload.monitor.service.SplunkSearchService;
import com.oocl.easyload.monitor.utils.QuartzUtils;
import com.oocl.easyload.monitor.utils.SpringUtil;
import lombok.Getter;
import lombok.Setter;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

@Getter
@Setter
public class WsEventsMonitorJob implements Job {

  private static final Logger logger = LoggerFactory.getLogger(WsEventsMonitorJob.class);

  private String type = "ws";
  //    public String pattern = "search index=\"ir4_app_wls_log_idx*{0}*\"
  // (source=\"*WLS*{1}*access*\")\n" +
  //            "earliest={2} latest={3}\n" +
  //            "status=200  | \n" +
  //            "eval \n" +
  //            "dtstr=_time | convert timeformat=\"%m/%d/%y %H:%M:%S\" ctime(dtstr) | \n" +
  //            "stats count as hitcount, avg(time_taken) as avg_time, perc90(time_taken) as
  // p90_time, max(time_taken) as max_time, avg(bytes) as avg_bytes by \n" +
  //            "dtstr,url,host,server |\n" +
  //            "sort by d\n" +
  //            "tstr, url,host";

  public String pattern =
      "search index=\"ir4_app_wls_log_idx*{0}*\" (source=\"*WLS*{1}*access*\")\n"
          + "earliest={2} latest={3}\n"
          + "NOT (status=404 OR status=405 OR status=500)\n"
          + "NOT (url=\"*gif\" OR url=\"*jpg\" OR url=\"*png\" OR url=\"*html\" OR url=\"*js\" OR url=\"*jsp\" OR url=\"*/restful/frm/art/submit\" OR url=\"*/restful/frm/art/submitBF\")\n"
          + "status=200 | \n"
          + "eval \n"
          + "dtstr=_time | convert timeformat=\"%m/%d/%y %H\" ctime(dtstr) | \n"
          + "stats count as hitcount, avg(time_taken) as avg_time, perc90(time_taken) as p90_time, max(time_taken) as max_time, avg(bytes) as avg_bytes by \n"
          + "dtstr,url,host,server |\n"
          + "sort by d\n"
          + "tstr, url,host";

  SplunkSearchService service =
      SpringUtil.getBean(SplunkSearchService.class, "splunkMonitorService");

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.info(
        "START TIME - "
            + context.getScheduledFireTime()
            + " - NEXT TIME - "
            + context.getNextFireTime());
    JobDataMap map = context.getJobDetail().getJobDataMap();
    if (map == null) {
      logger.error("WsEventsMonitorJob.java - execute - " + Tips.MAPPER_IS_NULL);
      return;
    }
    SchedulerDTO job = (SchedulerDTO) map.get("job");
    String intervalUrl = getIntervalUrl(job);
    String roundUrl = getRounUrl(job);
    SplunkSearchDTO searchCriteria =
        new SplunkSearchDTO(job.getRound().getEnv(), intervalUrl, this.getType());
    job = job.updateFromAndToDate();
    job.setIntervalUrl(intervalUrl);
    job.setRoundUrl(roundUrl);
    if (context.getNextFireTime() == null) {
      job.setDomainLatest(true);
    }
    if (QuartzUtils.isFinish(context.getScheduler())) {
      job.setRoundLatest(true);
    }
    context.getJobDetail().getJobDataMap().put("job", job);
    service.search(searchCriteria, job);
  }

  private String getIntervalUrl(SchedulerDTO job) {
    String criteria =
        MessageFormat.format(
            this.getPattern(),
            job.getRound().getEnv(),
            job.getDomain(),
            job.getFrom(),
            job.getTo());
    return criteria;
  }

  private String getRounUrl(SchedulerDTO job) {
    String criteria =
        MessageFormat.format(
            this.getPattern(),
            job.getRound().getEnv(),
            job.getDomain(),
            job.getLoadTestStartDate(),
            job.getTo());
    return criteria;
  }
}
