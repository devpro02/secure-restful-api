package com.project.securerestfulapi.advice;

import com.project.securerestfulapi.exception.*;
import com.project.securerestfulapi.exception.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalControllerAdvice {
    private static final String REASON_NOT_FOUND = "Entity Not Found";
    private static final String BAD_REQUEST_EXCEPTION = "Bad Request Exception";
    private static final String REASON_INTERNAL_ERROR = "Internal Error";
    private static final String TOO_MANY_REQUESTS = "Too Many Requests";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Error> handleEntityNotFoundException(EntityNotFoundException exception) {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.NOT_FOUND.value()));
        error.setDescription(exception.getMessage());
        error.setReasonCode(REASON_NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Error> handleBadRequestException(BadRequestException exception) {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        error.setDescription(exception.getMessage());
        error.setReasonCode(BAD_REQUEST_EXCEPTION);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<Error> handleTooManyRequestsException(Exception exception) {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.TOO_MANY_REQUESTS.value()));
        error.setDescription(exception.getMessage());
        error.setReasonCode(TOO_MANY_REQUESTS);
        return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleInternalErrorException(Exception exception) {
        Error error = new Error();
        error.setCode(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        error.setDescription(exception.getMessage());
        error.setReasonCode(REASON_INTERNAL_ERROR);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

