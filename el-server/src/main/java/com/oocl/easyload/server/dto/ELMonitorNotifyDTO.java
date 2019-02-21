package com.oocl.easyload.server.dto;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ELMonitorNotifyDTO {

  private String activityId;
  private String roundId;
  private List<String> domains;
  private int interval = 5;
  private String earliest;
  private String latest;
  private String env;

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getRoundId() {
    return roundId;
  }

  public void setRoundId(String roundId) {
    this.roundId = roundId;
  }

  public List<String> getDomains() {
    if (CollectionUtils.isEmpty(this.domains)) {
      this.domains = new ArrayList<>();
    }
    return domains;
  }

  public int getInterval() {
    return interval;
  }

  public void setInterval(int interval) {
    this.interval = interval;
  }

  public String getEarliest() {
    return earliest;
  }

  public void setEarliest(String earliest) {
    this.earliest = earliest;
  }

  public String getLatest() {
    return latest;
  }

  public void setLatest(String latest) {
    this.latest = latest;
  }

  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }
}
