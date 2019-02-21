package com.oocl.easyload.node.response;

public enum ResponseCode {
  SUCCESS("SUCCESS"),
  FAIL("FAIL");

  private String value;

  private ResponseCode(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
