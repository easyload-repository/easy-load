package com.oocl.easyload.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "some attender have not ready")
public class AttenderNotReadyException extends RuntimeException {}
