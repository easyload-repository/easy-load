package com.oocl.easyload.server.dto;

import com.oocl.easyload.model.entity.ELStatus;

import java.util.Date;

public class ELRoundAttenderDTO {
  private String roundAttenderId;
  private String activityId;
  private String roundId;
  private ELAttenderDTO elAttenderDto;
  private ELStatus runningStatus;
  private Date createTime;
  private Date updateTime;

  public String getRoundAttenderId() {
    return roundAttenderId;
  }

  public void setRoundAttenderId(String roundAttenderId) {
    this.roundAttenderId = roundAttenderId;
  }

  public String getRoundId() {
    return roundId;
  }

  public void setRoundId(String roundId) {
    this.roundId = roundId;
  }

  public ELAttenderDTO getElAttenderDto() {
    return elAttenderDto;
  }

  public void setElAttenderDto(ELAttenderDTO elAttenderDto) {
    this.elAttenderDto = elAttenderDto;
  }

  public ELStatus getRunningStatus() {
    return runningStatus;
  }

  public void setRunningStatus(ELStatus runningStatus) {
    this.runningStatus = runningStatus;
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

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }
}
