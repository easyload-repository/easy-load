package com.oocl.easyload.monitor.constant;

import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

@Setter
@Getter
public class MonitorName {
  private static String jobNameTemplet = "{0}_{1}_{2}_{3}_Job";
  private static String jobGroupNameTemplet = "{0}_{1}_{2}_{3}_JobGroup";
  private static String triggerNameTemplet = "{0}_{1}_{2}_{3}_Trigger";
  private static String triggerGroupNameTemplet = "{0}_{1}_{2}_{3}_TriggerGroup";
  private String jobName;
  private String jobGroupName;
  private String triggerName;
  private String triggerGroupName;

  public MonitorName() {}

  public MonitorName(String activeId, String roundId, String domain, String type) {
    this.jobName = MessageFormat.format(jobNameTemplet, activeId, roundId, domain, type);
    this.jobGroupName = MessageFormat.format(jobGroupNameTemplet, activeId, roundId, domain, type);
    this.triggerName = MessageFormat.format(triggerNameTemplet, activeId, roundId, domain, type);
    this.triggerGroupName =
        MessageFormat.format(triggerGroupNameTemplet, activeId, roundId, domain, type);
  }

  public static String jobGroupName(String activeId, String roundId, String domain, String type) {
    return MessageFormat.format(jobGroupNameTemplet, activeId, roundId, domain, type);
  }
}
