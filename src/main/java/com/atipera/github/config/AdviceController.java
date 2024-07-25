package com.atipera.github.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.atipera.github.exception.GithubRestException;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(GithubRestException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(GithubRestException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }
}
