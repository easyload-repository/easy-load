package com.oocl.easyload.server.dto.cache;

import java.util.concurrent.ConcurrentHashMap;

public class ELExceptionSearchDTO {
  private String activityId;
  private String roundId;
  private String domain;
  private String splunkUrl;
  private ConcurrentHashMap<String, ELExceptionEventDTO> events = new ConcurrentHashMap<>();

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

  public ConcurrentHashMap<String, ELExceptionEventDTO> getEvents() {
    return events;
  }

  public void setEvents(ConcurrentHashMap<String, ELExceptionEventDTO> events) {
    this.events = events;
  }
}
