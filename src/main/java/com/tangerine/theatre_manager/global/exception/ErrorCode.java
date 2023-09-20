package com.tangerine.theatre_manager.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter(value = AccessLevel.PROTECTED)
public enum ErrorCode {

    // authorization
    FORBIDDEN_AUTH(FORBIDDEN, "권한이 부족합니다."),

    // age rate
    FORBIDDEN_AGE(BAD_REQUEST, "관람 가능 연령이 아닙니다."),

    // order
    NOT_FOUND_ORDER(NOT_FOUND, "주문 정보가 없습니다."),
    OUT_OF_BOUND_ORDER_STATUS(BAD_REQUEST, "이미 사용된 주문입니다."),

    // performance
    NOT_FOUND_PERFORMANCE(NOT_FOUND, "공연 정보가 없습니다."),

    // user
    NOT_FOUND_USER(NOT_FOUND, "유저가 정보가 없습니다."),

    // grant request
    NOT_REQUEST_USER(BAD_REQUEST, "극단 권한을 요청하지 않은 유저입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
