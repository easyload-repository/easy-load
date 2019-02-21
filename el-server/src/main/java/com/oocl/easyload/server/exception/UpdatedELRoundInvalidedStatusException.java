package com.oocl.easyload.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
    code = HttpStatus.BAD_REQUEST,
    reason = "This round is in process, you can not update it.")
public class UpdatedELRoundInvalidedStatusException extends RuntimeException {}
