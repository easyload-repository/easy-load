package com.oocl.easyload.monitor.service.impl;

import com.oocl.easyload.model.entity.el_monitor.SplunkSearch;
import com.oocl.easyload.model.entity.el_monitor.SplunkSearchDetails;
import com.oocl.easyload.model.entity.el_monitor.SplunkWsEvent;
import com.oocl.easyload.model.entity.el_monitor.SplunkWsException;
import com.oocl.easyload.monitor.constant.Queue;
import com.oocl.easyload.monitor.constant.Tips;
import com.oocl.easyload.monitor.constant.Topic;
import com.oocl.easyload.monitor.convert.ObjectConvert;
import com.oocl.easyload.monitor.dto.*;
import com.oocl.easyload.monitor.exceptions.UnexpectedException;
import com.oocl.easyload.monitor.factory.QuartzSchedulerFactory;
import com.oocl.easyload.monitor.factory.SplunkSearchObjFactory;
import com.oocl.easyload.monitor.factory.SplunkServiceFactory;
import com.oocl.easyload.monitor.jms.Producer;
import com.oocl.easyload.monitor.repository.SplunkSearchRepository;
import com.oocl.easyload.monitor.service.SplunkSearchService;
import com.oocl.easyload.monitor.utils.JsonUtils;
import com.oocl.easyload.monitor.utils.MonitorManagerUtil;
import com.oocl.easyload.monitor.utils.QuartzUtils;
import com.splunk.Job;
import com.splunk.JobArgs;
import com.splunk.JobResultsPreviewArgs;
import com.splunk.ResultsReaderXml;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.*;

@Service
public class SplunkSearchServiceImpl implements SplunkSearchService {

  private static final Logger logger = LoggerFactory.getLogger(SplunkSearchServiceImpl.class);

  @Resource SplunkSearchRepository repository;

  @Autowired private Producer producer;

  @Override
  @Async("asyncServiceExecutor")
  public void searchForEs(SplunkSearchDTO criteria) {
    try {
      if (!criteria.validate()) return;
      SplunkSearch splunkSearch = saveSplunkEven(criteria, null);
      sendMsgToEs(splunkSearch);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(
          "SplunkSearchServiceImpl.java - search - " + Tips.EXCEPTION_WHEN_SEARCH_IN_SPLUNK);
      throw new UnexpectedException();
    }
  }

  @Override
  @Async("asyncServiceExecutor")
  public void search(SplunkSearchDTO criteria, SchedulerDTO job) {
    try {
      SplunkSearch splunkSearch = saveSplunkEven(criteria, job);
      sendMsg(job, splunkSearch);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(
          "SplunkSearchServiceImpl.java - search - " + Tips.EXCEPTION_WHEN_SEARCH_IN_SPLUNK);
      throw new UnexpectedException();
    }
  }

  @Transactional
  SplunkSearch saveSplunkEven(SplunkSearchDTO criteria, SchedulerDTO job) {
    SplunkSearch splunkSearch = new SplunkSearch();
    com.splunk.Service service = SplunkServiceFactory.of(criteria.getEnv());
    Class clz = SplunkSearchObjFactory.classOf(criteria.getType());
    InputStream stream = search(service, criteria, splunkSearch);
    splunkSearch = parse(stream, clz, splunkSearch);
    if (job != null) {
      splunkSearch = supplement(splunkSearch, job);
      splunkSearch = detailSupplemnt(splunkSearch, job);
    } else {
      splunkSearch.setType(criteria.getType());
      splunkSearch.setEnvironment(criteria.getEnv());
    }
    splunkSearch = repository.save(splunkSearch);
    return splunkSearch;
  }

  @Override
  public void start(RoundStartDTO startCriteria) throws Exception {
    if (!startCriteria.validate()) return;
    Scheduler scheduler = QuartzSchedulerFactory.init(startCriteria);
    QuartzUtils.startJobs(scheduler);
  }

  @Override
  public void stop(StopMonitorDTO dto) {
    for (String domain : dto.getDomains()) {
      MonitorManagerUtil.removeByDomain(dto.getActiveId(), dto.getRoundId(), domain);
    }
  }

  @Override
  public void stopAll(StopMonitorDTO dto) {
    MonitorManagerUtil.clearAll();
  }

  private void sendMsg(SchedulerDTO job, SplunkSearch splunkSearch) {
    producer.sendMessage(
        Queue.EL_PROJECT_EVENT_QUEUE,
        JsonUtils.obj2String(new SplunkQueueEventDTO(splunkSearch.getId())));
    if (job != null && job.isRoundLatest()) {
      RoundStartDTO round = job.getRound();
      producer.sendMessage(
          Topic.EL_MONITOR_LATEST_TOPIC,
          JsonUtils.obj2String(
              new SplunkQueueLatestDto(
                  round.getActiveId(),
                  round.getRoundId(),
                  job.getDomain(),
                  job.isDomainLatest(),
                  job.isRoundLatest())));
    } else {
      if (job != null && job.isDomainLatest()) {
        RoundStartDTO round = job.getRound();
        producer.sendMessage(
            Topic.EL_MONITOR_LATEST_TOPIC,
            JsonUtils.obj2String(
                new SplunkQueueLatestDto(
                    round.getActiveId(),
                    round.getRoundId(),
                    job.getDomain(),
                    job.isDomainLatest(),
                    job.isRoundLatest())));
      }
    }
  }

  private void sendMsgToEs(SplunkSearch splunkSearch) {
    producer.sendMessage(
        Queue.EL_SMART_EVENT_QUEUE,
        JsonUtils.obj2String(new SplunkQueueEventDTO(splunkSearch.getId())));
  }

  private SplunkSearch supplement(SplunkSearch splunkSearch, SchedulerDTO job) {
    splunkSearch.setType(job.getType());
    splunkSearch.setDomain(job.getDomain());
    splunkSearch.setActivityId(job.getRound().getActiveId());
    splunkSearch.setRoundId(job.getRound().getRoundId());
    splunkSearch.setEnvironment(job.getRound().getEnv());
    return splunkSearch;
  }

  private SplunkSearch detailSupplemnt(SplunkSearch splunkSearch, SchedulerDTO job) {
    SplunkSearchDetails splunkSearchIDetails =
        new SplunkSearchDetails(job.getSearchInternalUrl(), job.getSearchRoundUrl());
    splunkSearch.setDetail(splunkSearchIDetails);
    return splunkSearch;
  }

  private InputStream search(
      com.splunk.Service service, SplunkSearchDTO criteria, SplunkSearch splunkSearch) {
    JobArgs jobargs = new JobArgs();
    jobargs.setExecutionMode(JobArgs.ExecutionMode.NORMAL);
    Job job = service.getJobs().create(criteria.getCriteria(), jobargs);
    while (!job.isDone()) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        logger.error(
            "SplunkSearchServiceImpl.java - search - " + Tips.EXCEPTION_WHEN_SEARCH_IN_SPLUNK);
        throw new UnexpectedException();
      }
    }
    JobResultsPreviewArgs previewArgs = new JobResultsPreviewArgs();
    previewArgs.setCount(10000); // Retrieve 300 previews at a time
    // Get the search results and use the built-in XML parser to display them
    InputStream resultsNormalSearch = job.getResultsPreview(previewArgs);
    splunkSearch.setJobId(job.getSid());
    splunkSearch.setEventCount(job.getEventCount());
    splunkSearch.setResultCount(job.getResultCount());
    splunkSearch.setRunDuration(job.getRunDuration());
    splunkSearch.setJobExpiresIn(job.getTtl());
    logger.info("SplunkSearchServiceImpl.java - search - " + Tips.SEARCH_SUCCESS);
    return resultsNormalSearch;
  }

  private SplunkSearch parse(InputStream stream, Class clz, SplunkSearch splunkSearch) {
    ResultsReaderXml resultsReaderNormalSearch;
    try {
      resultsReaderNormalSearch = new ResultsReaderXml(stream);
      HashMap<String, String> event;
      List<Object> results = new ArrayList<>();

      while ((event = resultsReaderNormalSearch.getNextEvent()) != null) {
        Object result = ObjectConvert.toObject(event, clz);
        results.add(result);
        //                System.out.println("****************EVENT****************");
        //                for (String key: event.keySet())
        //                    System.out.println("   " + key + ":  " + event.getClz(key));
      }
      splunkSearch = setParams(splunkSearch, results, clz);
      logger.info("SplunkSearchServiceImpl.java - parse - " + Tips.PARSE_SUCCESS);
      return splunkSearch;
    } catch (Exception e) {
      logger.error(
          "SplunkSearchServiceImpl.java - parse - " + Tips.EXCEPTION_WHEN_SEARCH_IN_SPLUNK);
      throw new UnexpectedException();
    }
  }

  private SplunkSearch setParams(SplunkSearch splunkSearch, Object results, Class clz)
      throws Exception {
    if (clz == null) return splunkSearch;

    if (clz.getName().contains("SplunkWsEvent")) {
      List<SplunkWsEvent> events = (List<SplunkWsEvent>) results;
      splunkSearch.setEvents(events);
      return splunkSearch;
    }
    if (clz.getName().contains("SplunkWsException")) {
      Set<SplunkWsException> exceptions = new HashSet((List<SplunkWsException>) results);
      splunkSearch.setExceptions(exceptions);
      return splunkSearch;
    }
    return splunkSearch;
  }
}
