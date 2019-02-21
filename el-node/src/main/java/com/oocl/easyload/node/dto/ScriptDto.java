package com.oocl.easyload.node.dto;

public class ScriptDto {

  private String scriptId;
  private String scriptName;
  private String scriptType;
  private String scriptCommand;
  private ScriptStatus scriptStatus;
  private String exceptionLog;
  private String outputLog;
  private String errorLog;
  private String domainName;

  public String getScriptName() {
    return scriptName;
  }

  public void setScriptName(String scriptName) {
    this.scriptName = scriptName;
  }

  public String getScriptType() {
    return scriptType;
  }

  public void setScriptType(String scriptType) {
    this.scriptType = scriptType;
  }

  public String getScriptId() {
    return scriptId;
  }

  public void setScriptId(String scriptId) {
    this.scriptId = scriptId;
  }

  public String getScriptCommand() {
    return scriptCommand;
  }

  public void setScriptCommand(String scriptCommand) {
    this.scriptCommand = scriptCommand;
  }

  public ScriptStatus getScriptStatus() {
    return scriptStatus;
  }

  public void setScriptStatus(ScriptStatus scriptStatus) {
    this.scriptStatus = scriptStatus;
  }

  public String getExceptionLog() {
    return exceptionLog;
  }

  public void setExceptionLog(String exceptionLog) {
    this.exceptionLog = exceptionLog;
  }

  public String getOutputLog() {
    return outputLog;
  }

  public void setOutputLog(String outputLog) {
    this.outputLog = outputLog;
  }

  public String getErrorLog() {
    return errorLog;
  }

  public void setErrorLog(String errorLog) {
    this.errorLog = errorLog;
  }

  public String getDomainName() {
    return domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }
}
