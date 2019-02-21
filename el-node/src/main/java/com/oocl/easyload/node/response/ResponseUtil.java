package com.oocl.easyload.node.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

  private ResponseUtil() {}

  public static ResponseEntity success(Object o) {
    Response<Object> response = new Response(o);
    response.setCode(ResponseCode.SUCCESS.getValue());
    return new ResponseEntity(response, HttpStatus.OK);
  }

  public static ResponseEntity response(Object o, HttpStatus httpStatus) {
    Response<Object> response = new Response(o);
    response.setCode(ResponseCode.SUCCESS.getValue());
    return new ResponseEntity(response, httpStatus);
  }

  public static ResponseEntity responseWithoutContent(HttpStatus httpStatus) {
    Response<Object> response = new Response(ResponseCode.SUCCESS.getValue());
    return new ResponseEntity(response, httpStatus);
  }

  public static ResponseEntity fail(String msg) {
    Response<Object> response = new Response(ResponseCode.FAIL.getValue());
    response.setMsg(msg);
    return new ResponseEntity(response, HttpStatus.OK);
  }

  public static ResponseEntity successInRestful(Object o) {
    return new ResponseEntity(o, HttpStatus.OK);
  }
}
