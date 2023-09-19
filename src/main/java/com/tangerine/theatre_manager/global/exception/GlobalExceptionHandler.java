package com.tangerine.theatre_manager.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> internalServerErrorException(Exception exception) {
        log.error("Internal Server Error: {}", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러");
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

    @ExceptionHandler(AuthorizedException.class)
    public ResponseEntity<ProblemDetail> authorizedException(AuthorizedException exception) {
        log.warn("Authorization Error: {}", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.FORBIDDEN, "권한이 부족합니다.");
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

    @ExceptionHandler(PerformanceException.class)
    public ResponseEntity<ProblemDetail> performanceException(PerformanceException exception) {
        log.warn("Performance Error: {}", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, "암튼 부적절함");
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

}
