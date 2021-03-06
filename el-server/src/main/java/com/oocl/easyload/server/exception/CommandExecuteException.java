package com.oocl.easyload.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "can not execute comman")
public class CommandExecuteException extends RuntimeException {
  public CommandExecuteException(String message) {
    super(message);
  }
}
