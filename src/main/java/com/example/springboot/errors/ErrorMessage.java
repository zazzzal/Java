package com.example.springboot.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    public LocalDateTime time;
    public HttpStatus status;
    public String message;
    public List<String> errors;

    public ErrorMessage(LocalDateTime time, HttpStatus status, String message, List<String> errors) {
        this.time = time;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
