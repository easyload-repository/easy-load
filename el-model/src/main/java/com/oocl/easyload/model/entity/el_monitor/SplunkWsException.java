package com.oocl.easyload.model.entity.el_monitor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "el_monitor_splunk_ws_exception")
@EntityListeners(AuditingEntityListener.class)
public class SplunkWsException {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String host;

  @Column(columnDefinition = "text")
  private String exp_info;

  private String source;
  private int count;
  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  public SplunkWsException() {}

  public SplunkWsException(
      String host, String exp_info, String source, int count, Date createTime, Date updateTime) {
    this.host = host;
    this.exp_info = exp_info;
    this.source = source;
    this.count = count;
    this.createTime = createTime;
    this.updateTime = updateTime;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getExp_info() {
    return exp_info;
  }

  public void setExp_info(String exp_info) {
    this.exp_info = exp_info;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
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
