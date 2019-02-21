package com.oocl.easyload.node.dto;

public enum ScriptType {
  JAVA("Java"),
  NODEJS("NodeJS"),
  SOAPUI("SoapUI"),
  LOADRUNNER("LoadRunner");

  private String value;

  private ScriptType(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
