package com.oocl.easyload.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "el_round_splunk")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@EntityListeners(AuditingEntityListener.class)
public class ELRoundSplunk {

  @Id
  @GeneratedValue(generator = "jpa-uuid")
  @Column(length = 32)
  private String checkPointId;

  @Column(length = 32)
  private String splunkSearchId;

  @Column(columnDefinition = "text")
  private String splunkUrl;

  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  public String getCheckPointId() {
    return checkPointId;
  }

  public void setCheckPointId(String checkPointId) {
    this.checkPointId = checkPointId;
  }

  public String getSplunkSearchId() {
    return splunkSearchId;
  }

  public void setSplunkSearchId(String splunkSearchId) {
    this.splunkSearchId = splunkSearchId;
  }

  public String getSplunkUrl() {
    return splunkUrl;
  }

  public void setSplunkUrl(String splunkUrl) {
    this.splunkUrl = splunkUrl;
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
}
