package com.oocl.easyload.server.dto.cache;

public class ELWebServiceEventDTO {
  private String url;
  private String server;
  private int hitcount;
  private double avgTime;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

  public int getHitcount() {
    return hitcount;
  }

  public void setHitcount(int hitcount) {
    this.hitcount = hitcount;
  }

  public double getAvgTime() {
    return avgTime;
  }

  public void setAvgTime(double avgTime) {
    this.avgTime = avgTime;
  }
}
