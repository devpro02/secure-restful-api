package com.project.securerestfulapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
public class TooManyRequestsException  extends RuntimeException {
    public TooManyRequestsException(String msg) {
        super(msg);
    }
}
