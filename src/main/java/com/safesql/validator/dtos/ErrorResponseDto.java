package com.safesql.validator.dtos;

public class ErrorResponseDto {
    private boolean status;
    private String message;

    public ErrorResponseDto() {
        this.status = false;
        this.message = "SQL injection detected in field: ";
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
