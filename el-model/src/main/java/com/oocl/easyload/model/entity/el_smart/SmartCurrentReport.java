package com.oocl.easyload.model.entity.el_smart;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "el_smart_report_current")
@EntityListeners(AuditingEntityListener.class)
public class SmartCurrentReport {

  @Id private String id;
  private String roundId;
  private String activityId;
  private String server;
  private String host;
  private String url;
  private double qa_p90_time;
  private double qa_avg_bytes;
  private double qa_max_time;
  private int qa_hitcount;
  private double qa_avg_time;
  private double qa_expected_avg_time;
  private double prod_p90_time;
  private double prod_avg_bytes;
  private double prod_max_time;
  private int prod_hitcount;
  private double prod_avg_time;

  public SmartCurrentReport() {}

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public SmartCurrentReport(
      String id,
      String roundId,
      String activityId,
      String server,
      String host,
      String url,
      double qa_p90_time,
      double qa_avg_bytes,
      double qa_max_time,
      int qa_hitcount,
      double qa_avg_time,
      double qa_expected_avg_time,
      double prod_p90_time,
      double prod_avg_bytes,
      double prod_max_time,
      int prod_hitcount,
      double prod_avg_time) {
    this.id = id;
    this.roundId = roundId;
    this.activityId = activityId;
    this.server = server;
    this.host = host;
    this.url = url;
    this.qa_p90_time = qa_p90_time;
    this.qa_avg_bytes = qa_avg_bytes;
    this.qa_max_time = qa_max_time;
    this.qa_hitcount = qa_hitcount;
    this.qa_avg_time = qa_avg_time;
    this.qa_expected_avg_time = qa_expected_avg_time;
    this.prod_p90_time = prod_p90_time;
    this.prod_avg_bytes = prod_avg_bytes;
    this.prod_max_time = prod_max_time;
    this.prod_hitcount = prod_hitcount;
    this.prod_avg_time = prod_avg_time;
  }

  public double getQa_expected_avg_time() {
    return qa_expected_avg_time;
  }

  public void setQa_expected_avg_time(double qa_expected_avg_time) {
    this.qa_expected_avg_time = qa_expected_avg_time;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRoundId() {
    return roundId;
  }

  public void setRoundId(String roundId) {
    this.roundId = roundId;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public double getQa_p90_time() {
    return qa_p90_time;
  }

  public void setQa_p90_time(double qa_p90_time) {
    this.qa_p90_time = qa_p90_time;
  }

  public double getQa_avg_bytes() {
    return qa_avg_bytes;
  }

  public void setQa_avg_bytes(double qa_avg_bytes) {
    this.qa_avg_bytes = qa_avg_bytes;
  }

  public double getQa_max_time() {
    return qa_max_time;
  }

  public void setQa_max_time(double qa_max_time) {
    this.qa_max_time = qa_max_time;
  }

  public int getQa_hitcount() {
    return qa_hitcount;
  }

  public void setQa_hitcount(int qa_hitcount) {
    this.qa_hitcount = qa_hitcount;
  }

  public double getQa_avg_time() {
    return qa_avg_time;
  }

  public void setQa_avg_time(double qa_avg_time) {
    this.qa_avg_time = qa_avg_time;
  }

  public double getProd_p90_time() {
    return prod_p90_time;
  }

  public void setProd_p90_time(double prod_p90_time) {
    this.prod_p90_time = prod_p90_time;
  }

  public double getProd_avg_bytes() {
    return prod_avg_bytes;
  }

  public void setProd_avg_bytes(double prod_avg_bytes) {
    this.prod_avg_bytes = prod_avg_bytes;
  }

  public double getProd_max_time() {
    return prod_max_time;
  }

  public void setProd_max_time(double prod_max_time) {
    this.prod_max_time = prod_max_time;
  }

  public int getProd_hitcount() {
    return prod_hitcount;
  }

  public void setProd_hitcount(int prod_hitcount) {
    this.prod_hitcount = prod_hitcount;
  }

  public double getProd_avg_time() {
    return prod_avg_time;
  }

  public void setProd_avg_time(double prod_avg_time) {
    this.prod_avg_time = prod_avg_time;
  }
}
