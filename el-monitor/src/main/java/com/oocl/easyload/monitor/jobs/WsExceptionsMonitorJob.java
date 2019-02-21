package com.oocl.easyload.monitor.jobs;

import com.oocl.easyload.monitor.constant.Tips;
import com.oocl.easyload.monitor.dto.SchedulerDTO;
import com.oocl.easyload.monitor.dto.SplunkSearchDTO;
import com.oocl.easyload.monitor.service.SplunkSearchService;
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

@Setter
@Getter
public class WsExceptionsMonitorJob implements Job {

  private static final Logger logger = LoggerFactory.getLogger(WsExceptionsMonitorJob.class);

  private String type = "exception";
  public String pattern =
      "search index=\"ir4_app_wls_log_idx*{0}*\" (source=\"*WLS*{1}*out\") earliest={2} AND latest={3} Exception \n"
          + "| eval raw_=urldecode(_raw) \n"
          + "| rex field=raw_ \"(?<javaEx>java.*Exception.*)\"  \n"
          + "| rex field=raw_ \"(?<err_source>oocl\\.ir4[a-zA-Z\\.0-9\\_\\.\\(\\)]*java:[0-9]*\\))\" \n"
          + "| rex field=raw_ \"Caused by:(?<causedBy>.*java.*)\"  \n"
          + "| eval exp_info=if(isnull(err_source),javaEx,err_source) \n"
          + "| stats count by host,exp_info,source\n"
          + "| sort count desc";

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
      logger.error("WsExceptionsMonitorJob.java - execute - " + Tips.MAPPER_IS_NULL);
      return;
    }
    SchedulerDTO job = (SchedulerDTO) map.get("job");
    String criteria = getIntervalUrl(job);
    SplunkSearchDTO searchCriteria =
        new SplunkSearchDTO(job.getRound().getEnv(), criteria, this.getType());
    job = job.updateFromAndToDate();
    job.setIntervalUrl(getIntervalUrl(job));
    job.setRoundUrl(getIntervalUrl(job));
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

  private String getRoundUrl(SchedulerDTO job) {
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
