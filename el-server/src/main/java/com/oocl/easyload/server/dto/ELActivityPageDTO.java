package com.oocl.easyload.server.dto;

import java.util.Date;

public class ELActivityPageDTO {
  private String activityId;
  private String activityName;
  private String activityDescription;
  private String owner;
  private Date startTime;
  private Date endTime;
  private ELRoundDTO latestRound;
  private Integer roundCounts;
  private Date createTime;
  private Date updateTime;
  private String environment;

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public String getActivityDescription() {
    return activityDescription;
  }

  public void setActivityDescription(String activityDescription) {
    this.activityDescription = activityDescription;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public ELRoundDTO getLatestRound() {
    return latestRound;
  }

  public void setLatestRound(ELRoundDTO latestRound) {
    this.latestRound = latestRound;
  }

  public Integer getRoundCounts() {
    return roundCounts;
  }

  public void setRoundCounts(Integer roundCounts) {
    this.roundCounts = roundCounts;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }
}
