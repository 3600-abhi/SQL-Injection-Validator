package com.safesql.validator.controller;

import com.safesql.validator.dtos.RequestDto;
import com.safesql.validator.dtos.SuccessResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ValidatorController {

    @PostMapping(value = "/checkSqlInjection")
    public ResponseEntity<SuccessResponseDto> checkSqlInjectionInRequest(@RequestBody RequestDto requestDto) {
        return new ResponseEntity<>(new SuccessResponseDto(), HttpStatus.OK);
    }
}
