package com.oocl.easyload.node.dto;

public enum NoticeStatus {
  COMPLETE("Complete"),
  ERROR("Error");

  private String value;

  NoticeStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
