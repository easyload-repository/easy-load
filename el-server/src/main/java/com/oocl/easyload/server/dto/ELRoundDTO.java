package com.oocl.easyload.server.dto;

import com.oocl.easyload.model.entity.ELStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ELRoundDTO {
  private String activityId;
  private String activityName;
  private String roundId;
  private String roundName;
  private String times;
  private int version;
  private String description;
  private String owner;
  private ELStatus status;
  private boolean autoTrigger;
  private List<ELRoundAttenderDTO> elRoundAttenderDtos = new ArrayList<>();
  private Date expectedStartTime;
  private Date expectedEndTime;
  private Date actuallyStartTime;
  private Date actuallyEndTime;
  private Date createTime;
  private Date updateTime;

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

  public String getRoundId() {
    return roundId;
  }

  public void setRoundId(String roundId) {
    this.roundId = roundId;
  }

  public String getRoundName() {
    return roundName;
  }

  public void setRoundName(String roundName) {
    this.roundName = roundName;
  }

  public String getTimes() {
    return times;
  }

  public void setTimes(String times) {
    this.times = times;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public ELStatus getStatus() {
    return status;
  }

  public void setStatus(ELStatus status) {
    this.status = status;
  }

  public Date getExpectedStartTime() {
    return expectedStartTime;
  }

  public void setExpectedStartTime(Date expectedStartTime) {
    this.expectedStartTime = expectedStartTime;
  }

  public Date getExpectedEndTime() {
    return expectedEndTime;
  }

  public void setExpectedEndTime(Date expectedEndTime) {
    this.expectedEndTime = expectedEndTime;
  }

  public Date getActuallyStartTime() {
    return actuallyStartTime;
  }

  public void setActuallyStartTime(Date actuallyStartTime) {
    this.actuallyStartTime = actuallyStartTime;
  }

  public Date getActuallyEndTime() {
    return actuallyEndTime;
  }

  public void setActuallyEndTime(Date actuallyEndTime) {
    this.actuallyEndTime = actuallyEndTime;
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

  public List<ELRoundAttenderDTO> getElRoundAttenderDtos() {
    return elRoundAttenderDtos;
  }

  public void setElRoundAttenderDtos(List<ELRoundAttenderDTO> elRoundAttenderDtos) {
    this.elRoundAttenderDtos = elRoundAttenderDtos;
  }

  public boolean isAutoTrigger() {
    return autoTrigger;
  }

  public void setAutoTrigger(boolean autoTrigger) {
    this.autoTrigger = autoTrigger;
  }
}
