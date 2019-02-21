package com.oocl.easyload.server.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ELServerReportVo implements Serializable {

  private List<String> serverList = new ArrayList<>();
  private List<Integer> qaHitcountList = new ArrayList<>();
  private List<Integer> prodHitcountList = new ArrayList<>();
  private List<Float> prodAvgTimeList = new ArrayList<>();
  private List<Float> qaAvgTimeList = new ArrayList<>();
  private List<Float> prodMaxTimeList = new ArrayList<>();
  private List<Float> qaMaxTimeList = new ArrayList<>();
  private List<Float> prodP90TimeList = new ArrayList<>();
  private List<Float> qaP90TimeList = new ArrayList<>();
  private List<Float> avgTimeRatioList = new ArrayList<>();
  private List<Float> p90TimeRatioList = new ArrayList<>();
  private List<Float> maxTimeRatioList = new ArrayList<>();
  private List<Float> hitcountRatioList = new ArrayList<>();
  private AxisVo axisVo;

  public List<String> getServerList() {
    return serverList;
  }

  public void setServerList(List<String> serverList) {
    this.serverList = serverList;
  }

  public List<Integer> getQaHitcountList() {
    return qaHitcountList;
  }

  public void setQaHitcountList(List<Integer> qaHitcountList) {
    this.qaHitcountList = qaHitcountList;
  }

  public List<Integer> getProdHitcountList() {
    return prodHitcountList;
  }

  public void setProdHitcountList(List<Integer> prodHitcountList) {
    this.prodHitcountList = prodHitcountList;
  }

  public List<Float> getProdAvgTimeList() {
    return prodAvgTimeList;
  }

  public void setProdAvgTimeList(List<Float> prodAvgTimeList) {
    this.prodAvgTimeList = prodAvgTimeList;
  }

  public List<Float> getQaAvgTimeList() {
    return qaAvgTimeList;
  }

  public void setQaAvgTimeList(List<Float> qaAvgTimeList) {
    this.qaAvgTimeList = qaAvgTimeList;
  }

  public List<Float> getProdMaxTimeList() {
    return prodMaxTimeList;
  }

  public void setProdMaxTimeList(List<Float> prodMaxTimeList) {
    this.prodMaxTimeList = prodMaxTimeList;
  }

  public List<Float> getQaMaxTimeList() {
    return qaMaxTimeList;
  }

  public void setQaMaxTimeList(List<Float> qaMaxTimeList) {
    this.qaMaxTimeList = qaMaxTimeList;
  }

  public List<Float> getProdP90TimeList() {
    return prodP90TimeList;
  }

  public void setProdP90TimeList(List<Float> prodP90TimeList) {
    this.prodP90TimeList = prodP90TimeList;
  }

  public List<Float> getQaP90TimeList() {
    return qaP90TimeList;
  }

  public void setQaP90TimeList(List<Float> qaP90TimeList) {
    this.qaP90TimeList = qaP90TimeList;
  }

  public List<Float> getAvgTimeRatioList() {
    return avgTimeRatioList;
  }

  public void setAvgTimeRatioList(List<Float> avgTimeRatioList) {
    this.avgTimeRatioList = avgTimeRatioList;
  }

  public List<Float> getP90TimeRatioList() {
    return p90TimeRatioList;
  }

  public void setP90TimeRatioList(List<Float> p90TimeRatioList) {
    this.p90TimeRatioList = p90TimeRatioList;
  }

  public List<Float> getMaxTimeRatioList() {
    return maxTimeRatioList;
  }

  public void setMaxTimeRatioList(List<Float> maxTimeRatioList) {
    this.maxTimeRatioList = maxTimeRatioList;
  }

  public List<Float> getHitcountRatioList() {
    return hitcountRatioList;
  }

  public void setHitcountRatioList(List<Float> hitcountRatioList) {
    this.hitcountRatioList = hitcountRatioList;
  }

  public AxisVo getAxisVo() {
    return axisVo;
  }

  public void setAxisVo(AxisVo axisVo) {
    this.axisVo = axisVo;
  }
}
