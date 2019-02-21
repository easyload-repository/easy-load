package com.oocl.easyload.model.entity.el_smart;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "el_smart_prod_base_line")
@EntityListeners(AuditingEntityListener.class)
public class ProdBaseLineData {
  @Id private String id;
  private String url;
  private double avg_time;
  private double p90_time;
  private double max_time;
  private int hitcount;
  private double avg_bytes;
  private String server;
  private String dtstr;

  public ProdBaseLineData() {}

  public ProdBaseLineData(
      String id,
      double p90_time,
      String dtstr,
      double avg_bytes,
      double max_time,
      String url,
      int hitcount,
      double avg_time,
      String server) {
    this.id = id;
    this.p90_time = p90_time;
    this.dtstr = dtstr;
    this.avg_bytes = avg_bytes;
    this.max_time = max_time;
    this.url = url;
    this.hitcount = hitcount;
    this.avg_time = avg_time;
    this.server = server;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public double getP90_time() {
    return p90_time;
  }

  public void setP90_time(double p90_time) {
    this.p90_time = p90_time;
  }

  public String getDtstr() {
    return dtstr;
  }

  public void setDtstr(String dtstr) {
    this.dtstr = dtstr;
  }

  public double getAvg_bytes() {
    return avg_bytes;
  }

  public void setAvg_bytes(double avg_bytes) {
    this.avg_bytes = avg_bytes;
  }

  public double getMax_time() {
    return max_time;
  }

  public void setMax_time(double max_time) {
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

  public double getAvg_time() {
    return avg_time;
  }

  public void setAvg_time(double avg_time) {
    this.avg_time = avg_time;
  }
}
