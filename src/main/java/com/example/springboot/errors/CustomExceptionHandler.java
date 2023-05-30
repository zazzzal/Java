package com.example.springboot.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOError;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, MethodArgumentNotValidException.class, IOError.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static ResponseEntity<Object> handleBadRequest(Throwable ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Bad Request Error", errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
