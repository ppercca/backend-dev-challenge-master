package com.directa24.main.challenge.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<String> handleCustomException(final CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.getMessage());
    }

}
