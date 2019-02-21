package com.oocl.easyload.server.dto.cache;

import java.util.concurrent.ConcurrentHashMap;

public class ELWebServiceSearchDTO {
  private String activityId;
  private String roundId;
  private String domain;
  private String splunkUrl;
  private ConcurrentHashMap<String, ELWebServiceEventDTO> events = new ConcurrentHashMap<>();

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

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getSplunkUrl() {
    return splunkUrl;
  }

  public void setSplunkUrl(String splunkUrl) {
    this.splunkUrl = splunkUrl;
  }

  public ConcurrentHashMap<String, ELWebServiceEventDTO> getEvents() {
    if (events == null) {
      this.events = new ConcurrentHashMap<>();
    }
    return events;
  }
}
