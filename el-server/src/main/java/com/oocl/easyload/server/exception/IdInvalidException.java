package com.oocl.easyload.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.BAD_REQUEST,
    reason = "The id you entered could not find the corresponding record！")
public class IdInvalidException extends RuntimeException {}
