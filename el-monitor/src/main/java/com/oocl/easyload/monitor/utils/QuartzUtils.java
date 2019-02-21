package com.oocl.easyload.monitor.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

public class QuartzUtils {
  public static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

  private static String JOB_NAME = "SPLUNK_JOB_NAME";
  private static String JOB_GROUP_NAME = "SPLUNK_JOBGTOUP_NAME";
  private static String TRIGGER_NAME = "SPLUNK_TRIGGER_NAME";
  private static String TRIGGER_GROUP_NAME = "SPLUNK_TRIGGER_NAME";

  /**
   * @param sched:调度器
   * @param jobClass:任务
   * @param time:时间设置，CronExpression表达式 @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
   */
  public static void addJob(
      Scheduler sched, @SuppressWarnings("rawtypes") Class jobClass, String time) {
    addJob(sched, jobClass, time, JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
  }

  /**
   * @param sched:调度器
   * @param jobClass:任务
   * @param time:时间设置，CronExpression表达式
   * @param jobName:任务名
   * @param jobGroupName:任务组名
   * @param triggerName:触发器名
   * @param triggerGroupName:触发器组名 @Description: 添加一个定时任务
   */
  public static void addJob(
      Scheduler sched,
      @SuppressWarnings("rawtypes") Class jobClass,
      String time,
      String jobName,
      String jobGroupName,
      String triggerName,
      String triggerGroupName) {

    JobDetail job = newJob(jobClass).withIdentity(jobName, jobGroupName).build();
    CronTrigger trigger =
        newTrigger()
            .withIdentity(triggerName, triggerGroupName)
            .withSchedule(cronSchedule(time))
            .build();
    try {
      // 返回为 null 添加失败
      Date ft = sched.scheduleJob(job, trigger);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param sched:调度器
   * @param jobClass:任务
   * @param time:时间设置，CronExpression表达式
   * @param jobName:任务名
   * @param jobGroupName:任务组名
   * @param triggerName:触发器名
   * @param triggerGroupName:触发器组名
   * @param map:job的map
   * @param endDate:结束时间
   * @param startDate:开始时间 @Description: 添加一个定时任务
   */
  public static void addJob(
      Scheduler sched,
      @SuppressWarnings("rawtypes") Class jobClass,
      String time,
      String jobName,
      String jobGroupName,
      String triggerName,
      String triggerGroupName,
      Map<String, Object> map,
      Date startDate,
      Date endDate) {

    JobDetail job =
        newJob(jobClass)
            .withIdentity(jobName, jobGroupName)
            .setJobData(new JobDataMap(map))
            .build();

    CronTrigger trigger =
        newTrigger()
            .withIdentity(triggerName, triggerGroupName)
            .withSchedule(cronSchedule(time))
            .startAt(startDate)
            .endAt(endDate)
            .build();
    try {
      // 返回为 null 添加失败
      Date ft = sched.scheduleJob(job, trigger);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  public static void addJob(JobDetail job, CronTrigger trigger, Scheduler sched)
      throws SchedulerException {
    // 返回为 null 添加失败
    Date ft = sched.scheduleJob(job, trigger);
  }

  /**
   * @param sched:调度器
   * @param time @Description: 定义一个任务之后进行触发设定(使用默认的任务组名 ， 触发器名 ， 触发器组名)
   */
  @SuppressWarnings("rawtypes")
  public static void addJObLaterUse(
      Scheduler sched, @SuppressWarnings("rawtypes") Class jobClass, String time) {

    addJObLaterUse(sched, jobClass, time, JOB_NAME, JOB_GROUP_NAME);
  }

  /**
   * @param sched:调度器
   * @param time
   * @param jobName:任务名
   * @param jobGroupName:任务组名 @Description: 定义一个任务之后进行触发设定
   */
  @SuppressWarnings("rawtypes")
  public static void addJObLaterUse(
      Scheduler sched,
      @SuppressWarnings("rawtypes") Class jobClass,
      String time,
      String jobName,
      String jobGroupName) {

    JobDetail job = newJob(jobClass).withIdentity(jobName, jobGroupName).storeDurably().build();

    try {
      sched.addJob(job, false);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param sched:调度器
   * @param time：时间 JOB_NAME:任务名 JOB_GROUP_NAME:任务组名 @Description: 对已存储的任务进行scheduling(使用默认的任务组名 ，
   *     触发器名 ， 触发器组名)
   */
  @SuppressWarnings("rawtypes")
  public static void schedulingStoredJOb(
      Scheduler sched, @SuppressWarnings("rawtypes") Class jobClass, String time) {
    schedulingStoredJOb(
        sched, jobClass, time, JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
  }

  /**
   * @param sched:调度器
   * @param time
   * @param jobName:任务名
   * @param jobGroupName:任务组名 @Description: 对已存储的任务进行scheduling
   */
  @SuppressWarnings("rawtypes")
  public static void schedulingStoredJOb(
      Scheduler sched,
      @SuppressWarnings("rawtypes") Class jobClass,
      String time,
      String jobName,
      String jobGroupName,
      String triggerName,
      String triggerGroupName) {
    Trigger trigger =
        newTrigger()
            .withIdentity(triggerName, triggerGroupName)
            .startNow()
            .forJob(jobKey(jobName, jobGroupName))
            .build();
    try {
      sched.scheduleJob(trigger);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param sched:调度器
   * @param time @Description: 修改一个任务的触发时间(使用默认的任务组名 ， 触发器名 ， 触发器组名)
   */
  @SuppressWarnings("rawtypes")
  public static void modifyJobTime(Scheduler sched, String time) {
    modifyJobTime(sched, TRIGGER_NAME, TRIGGER_GROUP_NAME, time);
  }

  /**
   * @param sched:调度器
   * @param triggerName
   * @param triggerGroupName
   * @param time @Description: 修改一个任务的触发时间
   */
  public static void modifyJobTime(
      Scheduler sched, String triggerName, String triggerGroupName, String time) {
    Trigger trigger =
        newTrigger()
            .withIdentity(triggerName, triggerGroupName)
            .withSchedule(cronSchedule(time))
            .startNow()
            .build();

    try {
      sched.rescheduleJob(triggerKey(triggerName, triggerGroupName), trigger);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  /** @param sched:调度器 @Description: 修改一个任务(使用默认的任务组名 ， 任务名) */
  @SuppressWarnings("rawtypes")
  public static void modifyJob(Scheduler sched, @SuppressWarnings("rawtypes") Class jobClass) {
    modifyJob(sched, jobClass, JOB_NAME, JOB_GROUP_NAME);
  }

  /**
   * @param sched:调度器
   * @param jobName:任务名
   * @param jobGroupName:任务组名 @Description: 修改一个任务
   */
  public static void modifyJob(
      Scheduler sched,
      @SuppressWarnings("rawtypes") Class jobClass,
      String jobName,
      String jobGroupName) {
    JobDetail job1 = newJob(jobClass).withIdentity(jobName, jobGroupName).build();
    try {
      sched.addJob(job1, true);
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param sched:调度器
   * @param triggerName
   * @param triggerGroupName @Description: 删除一个任务的的trigger
   */
  public static void unschedulingJob(Scheduler sched, String triggerName, String triggerGroupName) {
    try {
      sched.unscheduleJob(triggerKey(triggerName, triggerGroupName));
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param sched:调度器
   * @param jobName @Description: 移除一个任务，以及任务的所有trigger
   */
  public static boolean removeJob(Scheduler sched, String jobName, String jobGroupName) {
    try {
      boolean isDelete = sched.deleteJob(jobKey(jobName, jobGroupName));
      return isDelete;
    } catch (SchedulerException e) {
      e.printStackTrace();
      return false;
    }
  }

  /** @param sched:调度器 @Description:启动所有定时任务 */
  public static void startJobs(Scheduler sched) {
    try {
      sched.start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /** @param sched:调度器 @Description:关闭所有定时任务 */
  public static void shutdownJobs(Scheduler sched) {
    try {
      if (!sched.isShutdown()) {
        // 未传参或false：不等待执行完成便结束；true：等待任务执行完才结束
        sched.shutdown();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static boolean isFinish(Scheduler scheduler) {
    try {
      for (String groupName : scheduler.getJobGroupNames()) {
        for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
          // getClz job's trigger
          List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
          Date nextFireTime = triggers.get(0).getNextFireTime();
          if (nextFireTime != null) return false;
        }
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
