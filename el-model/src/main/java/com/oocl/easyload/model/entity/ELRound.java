package com.oocl.easyload.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "el_round")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@EntityListeners(AuditingEntityListener.class)
public class ELRound {

  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String roundId;

  @Column(length = 32)
  private String roundName;

  @Column(length = 5)
  private String times;

  private int version;

  @Column(length = 50)
  private String description;
  /** 预留字段 */
  @Column(length = 32)
  private String owner;

  @Enumerated(value = EnumType.STRING)
  private ELStatus status = ELStatus.NEW;

  private boolean autoTrigger;
  private Date expectedStartTime;
  private Date expectedEndTime;
  private Date actuallyStartTime;
  private Date actuallyEndTime;
  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  @ManyToOne
  @JoinColumn(name = "activityId", referencedColumnName = "activityId")
  private ELActivity elActivity;

  @OneToMany(mappedBy = "elRound", cascade = CascadeType.ALL)
  private List<ELRoundAttender> elRoundAttenders = new ArrayList<>();

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

  public ELActivity getElActivity() {
    return elActivity;
  }

  public void setElActivity(ELActivity elActivity) {
    this.elActivity = elActivity;
  }

  public List<ELRoundAttender> getElRoundAttenders() {
    return elRoundAttenders;
  }

  public void setElRoundAttenders(List<ELRoundAttender> elRoundAttenders) {
    this.elRoundAttenders = elRoundAttenders;
  }

  public boolean isAutoTrigger() {
    return autoTrigger;
  }

  public void setAutoTrigger(boolean autoTrigger) {
    this.autoTrigger = autoTrigger;
  }
}
