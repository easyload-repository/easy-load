package com.oocl.easyload.monitor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Scheduler execution fails!")
public class QuartzException extends RuntimeException {}
