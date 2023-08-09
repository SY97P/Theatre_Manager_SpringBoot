package com.tangerine.theatre_manager.global.response;

import org.springframework.http.HttpStatus;

import java.util.StringJoiner;

public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final String code;

    public ErrorResponse(
            final HttpStatus status,
            final String code
    ) {
        this.httpStatus = status;
        this.code = code;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpStatus(),
                errorCode.getCode()
        );
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorResponse.class.getSimpleName() + "[", "]")
                .add("httpStatus=" + httpStatus)
                .add("code='" + code + "'")
                .toString();
    }
}
