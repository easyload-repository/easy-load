package com.oocl.easyload.server.dto;

public class NodeNoticeDTO {
  private String scriptId;
  private String scriptStatus;
  private String errorInfo;

  public String getScriptId() {
    return scriptId;
  }

  public void setScriptId(String scriptId) {
    this.scriptId = scriptId;
  }

  public String getScriptStatus() {
    return scriptStatus;
  }

  public void setScriptStatus(String scriptStatus) {
    this.scriptStatus = scriptStatus;
  }

  public String getErrorInfo() {
    return errorInfo;
  }

  public void setErrorInfo(String errorInfo) {
    this.errorInfo = errorInfo;
  }
}
