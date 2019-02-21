package com.oocl.easyload.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Node server not available ")
public class NodeServerNotAvaiableException extends RuntimeException {}
