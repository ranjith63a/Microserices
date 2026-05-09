package com.microservices.student.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorResponse {
    private int status;
    private String message;
    private String error;
    private LocalDateTime timestamp;
    private String path;

    public ApiErrorResponse(int status, String message, String error, LocalDateTime timestamp, String path) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = timestamp;
        this.path = path;
    }
}
