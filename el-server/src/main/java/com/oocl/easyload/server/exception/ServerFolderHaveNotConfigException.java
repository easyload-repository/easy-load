package com.oocl.easyload.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Attender have not config server folder")
public class ServerFolderHaveNotConfigException extends RuntimeException {}
