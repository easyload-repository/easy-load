package com.oocl.easyload.node.response;

public class Response<T> {
  private String code;
  private String msg;
  private T data;

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Response(String code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public Response(String code, T data) {
    this.code = code;
    this.data = data;
  }

  public Response(T data) {
    this.data = data;
  }

  public Response(String code) {
    this.code = code;
  }
}
