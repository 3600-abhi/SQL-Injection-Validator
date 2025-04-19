package com.safesql.validator.dtos;

public class SuccessResponseDto {
    private String status;
    private String message;

    public SuccessResponseDto() {
        this.status = "OK";
        this.message = "No SQL Injection Found";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
