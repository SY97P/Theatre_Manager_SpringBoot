package com.tangerine.theatre_manager.global.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenAgeException extends RuntimeException {

    private final ErrorCode errorCode;

    public ForbiddenAgeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorStatus() {
        return errorCode.getHttpStatus();
    }

    public String getMessage() {
        return errorCode.getMessage();
    }

}
