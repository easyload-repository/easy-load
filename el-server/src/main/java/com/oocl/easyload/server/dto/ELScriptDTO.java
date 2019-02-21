package com.oocl.easyload.server.dto;

public class ELScriptDTO {
  private String scriptId;
  private String name;
  private String Type;
  /** 该Script是否上次执行 */
  private Boolean lastExecute;

  public String getScriptId() {
    return scriptId;
  }

  public void setScriptId(String scriptId) {
    this.scriptId = scriptId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return Type;
  }

  public void setType(String type) {
    Type = type;
  }

  public Boolean getLastExecute() {
    return lastExecute;
  }

  public void setLastExecute(Boolean lastExecute) {
    this.lastExecute = lastExecute;
  }
}
