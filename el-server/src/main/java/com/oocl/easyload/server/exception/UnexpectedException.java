package com.oocl.easyload.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 当controller抛出该异常的时候 <br>
 *
 * <pre>
 * Status: 400
 * Body:
 * {
 *     "timestamp": "2018-12-21T05:33:08.064+0000",
 *     "status": 400,
 *     "error": "Bad Request",
 *     "message": "my exception",
 *     "path": "/api/attender"
 * }
 * </pre>
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "unexpected exception occur!")
public class UnexpectedException extends RuntimeException {}
