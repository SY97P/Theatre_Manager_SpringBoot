package com.tangerine.theatre_manager.global.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.extern.slf4j.Slf4j;
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
        log.error("Internal Server Error: ", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, INTERNAL_SERVER_ERROR, "서버 에러");
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

    @ExceptionHandler(AuthorizedException.class)
    public ResponseEntity<ProblemDetail> authorizedException(AuthorizedException exception) {
        log.warn("Authorization Error: ", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, exception.getErrorStatus(),
                exception.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

    @ExceptionHandler(ForbiddenAgeException.class)
    public ResponseEntity<ProblemDetail> authorizedException(ForbiddenAgeException exception) {
        log.warn("ForbiddenAge Error: ", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, exception.getErrorStatus(),
                exception.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

    @ExceptionHandler(GrantRequestException.class)
    public ResponseEntity<ProblemDetail> authorizedException(GrantRequestException exception) {
        log.warn("GrantRequest Error: ", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, exception.getErrorStatus(),
                exception.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ProblemDetail> orderException(OrderException exception) {
        log.warn("Performance Error: ", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, exception.getErrorStatus(),
                exception.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

    @ExceptionHandler(PerformanceException.class)
    public ResponseEntity<ProblemDetail> performanceException(PerformanceException exception) {
        log.warn("Performance Error: ", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, exception.getErrorStatus(),
                exception.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ProblemDetail> performanceException(UserException exception) {
        log.warn("User Error: ", exception);
        ErrorResponse errorResponse = ErrorResponse.create(exception, exception.getErrorStatus(),
                exception.getMessage());
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse.getBody());
    }

}
