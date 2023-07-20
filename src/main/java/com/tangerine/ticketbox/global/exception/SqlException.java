package com.tangerine.ticketbox.global.exception;

public class SqlException extends RuntimeException {

    public SqlException() {
    }

    public SqlException(String message) {
        super(message);
    }

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlException(Exception exception) {
        super(exception);
    }

}
