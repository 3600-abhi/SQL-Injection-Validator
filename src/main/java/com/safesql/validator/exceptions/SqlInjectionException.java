package com.safesql.validator.exceptions;

import org.springframework.http.HttpStatus;

public class SqlInjectionException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public SqlInjectionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
