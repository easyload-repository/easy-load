package com.oocl.easyload.model.entity.el_monitor;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "el_monitor_splunk_search")
@EntityListeners(AuditingEntityListener.class)
public class SplunkSearch {
  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String id;

  private String jobId;
  private int eventCount;
  private int resultCount;
  private float runDuration;
  private int jobExpiresIn;
  private String activityId;
  private String roundId;
  private String domain;
  private String type;
  private String environment;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "searchId", referencedColumnName = "id")
  private List<SplunkWsEvent> events;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "searchId", referencedColumnName = "id")
  private Set<SplunkWsException> exceptions;

  @OneToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      targetEntity = SplunkSearchDetails.class)
  @JoinColumn(name = "detailId", referencedColumnName = "id")
  private SplunkSearchDetails detail;

  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  public SplunkSearch() {}

  public SplunkSearch(
      String jobId,
      int eventCount,
      int resultCount,
      float runDuration,
      int jobExpiresIn,
      String activityId,
      String roundId,
      String domain,
      List<SplunkWsEvent> events,
      Set<SplunkWsException> exceptions,
      SplunkSearchDetails detail,
      Date createTime,
      Date updateTime,
      String type,
      String environment) {
    this.jobId = jobId;
    this.eventCount = eventCount;
    this.resultCount = resultCount;
    this.runDuration = runDuration;
    this.jobExpiresIn = jobExpiresIn;
    this.activityId = activityId;
    this.roundId = roundId;
    this.domain = domain;
    this.events = events;
    this.exceptions = exceptions;
    this.detail = detail;
    this.createTime = createTime;
    this.updateTime = updateTime;
    this.type = type;
    this.environment = environment;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public int getEventCount() {
    return eventCount;
  }

  public void setEventCount(int eventCount) {
    this.eventCount = eventCount;
  }

  public int getResultCount() {
    return resultCount;
  }

  public void setResultCount(int resultCount) {
    this.resultCount = resultCount;
  }

  public float getRunDuration() {
    return runDuration;
  }

  public void setRunDuration(float runDuration) {
    this.runDuration = runDuration;
  }

  public int getJobExpiresIn() {
    return jobExpiresIn;
  }

  public void setJobExpiresIn(int jobExpiresIn) {
    this.jobExpiresIn = jobExpiresIn;
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

  public List<SplunkWsEvent> getEvents() {
    return events;
  }

  public void setEvents(List<SplunkWsEvent> events) {
    this.events = events;
  }

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

  public Set<SplunkWsException> getExceptions() {
    return exceptions;
  }

  public void setExceptions(Set<SplunkWsException> exceptions) {
    this.exceptions = exceptions;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public SplunkSearchDetails getDetail() {
    return detail;
  }

  public void setDetail(SplunkSearchDetails detail) {
    this.detail = detail;
  }

  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }
}
