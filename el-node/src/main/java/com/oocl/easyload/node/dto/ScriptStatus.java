package com.oocl.easyload.node.dto;

public enum ScriptStatus {
  RUNNING("RUNNING"),
  DESTROYED("DESTROYED"),
  ScriptNotFound("ScriptNotFound"),
  RunSuccess("RunSuccess"),
  RunFail("RunFail"),
  DestroySuccess("DestroySuccess"),
  DestroyFail("DestroyFail");

  private String value;

  private ScriptStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
