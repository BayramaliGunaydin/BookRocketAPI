package com.example.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class DefaultException extends RuntimeException{
    @ExceptionHandler(value = DefaultException.class)
    public ResponseEntity<Object> exception(DefaultException exception) {
        return new ResponseEntity<>("Hata", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}