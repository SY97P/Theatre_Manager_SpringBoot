package com.tangerine.ticketbox.global.exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException() {
        super();
    }

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundException(Exception e) {
        super(e);
    }

}
