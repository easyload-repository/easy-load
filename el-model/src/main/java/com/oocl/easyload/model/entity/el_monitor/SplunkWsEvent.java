package com.oocl.easyload.model.entity.el_monitor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "el_monitor_splunk_ws_event")
@EntityListeners(AuditingEntityListener.class)
public class SplunkWsEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private float p90_time;
  private String dtstr;
  private float avg_bytes;
  private float max_time;
  private String url;
  private int hitcount;
  private float avg_time;
  private String host;
  private String server;
  @CreatedDate private Date createTime;
  @LastModifiedDate private Date updateTime;

  public SplunkWsEvent() {}

  public SplunkWsEvent(
      float p90_time,
      String dtstr,
      float avg_bytes,
      float max_time,
      String url,
      int hitcount,
      float avg_time) {
    this.p90_time = p90_time;
    this.dtstr = dtstr;
    this.avg_bytes = avg_bytes;
    this.max_time = max_time;
    this.url = url;
    this.hitcount = hitcount;
    this.avg_time = avg_time;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public float getP90_time() {
    return p90_time;
  }

  public void setP90_time(float p90_time) {
    this.p90_time = p90_time;
  }

  public String getDtstr() {
    return dtstr;
  }

  public void setDtstr(String dtstr) {
    this.dtstr = dtstr;
  }

  public float getAvg_bytes() {
    return avg_bytes;
  }

  public void setAvg_bytes(float avg_bytes) {
    this.avg_bytes = avg_bytes;
  }

  public float getMax_time() {
    return max_time;
  }

  public void setMax_time(float max_time) {
    this.max_time = max_time;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getHitcount() {
    return hitcount;
  }

  public void setHitcount(int hitcount) {
    this.hitcount = hitcount;
  }

  public float getAvg_time() {
    return avg_time;
  }

  public void setAvg_time(float avg_time) {
    this.avg_time = avg_time;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
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
