package com.directa24.main.challenge.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MOVIES_NOT_FOUND("Movies not found", HttpStatus.NOT_FOUND),
    EXTERNAL_API_ERROR("Movies API is failing", HttpStatus.SERVICE_UNAVAILABLE),
    PARSING_API_ERROR("Parsing API response is failing", HttpStatus.SERVICE_UNAVAILABLE),
    THRESHOLD_ERROR("Incorrect value for threshold", HttpStatus.INTERNAL_SERVER_ERROR),
    UNEXPECTED_ERROR("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
