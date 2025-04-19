package com.safesql.validator.exceptionHandler;

import com.safesql.validator.dtos.ErrorResponseDto;
import com.safesql.validator.exceptions.SqlInjectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SqlInjectionException.class)
    public ResponseEntity<ErrorResponseDto> handleSqlInjection(SqlInjectionException ex) {
        ErrorResponseDto response = new ErrorResponseDto();
        response.setMessage(ex.getMessage());
        response.setStatus(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}