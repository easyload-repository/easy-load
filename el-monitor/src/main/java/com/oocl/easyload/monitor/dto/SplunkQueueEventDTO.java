package com.oocl.easyload.monitor.dto;

public class SplunkQueueEventDTO {
  private String searchId;

  public SplunkQueueEventDTO() {}

  public SplunkQueueEventDTO(String searchId) {
    this.searchId = searchId;
  }

  public String getSearchId() {
    return searchId;
  }

  public void setSearchId(String searchId) {
    this.searchId = searchId;
  }
}
