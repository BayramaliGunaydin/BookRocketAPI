package com.example.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class JWTNotFoundException extends RuntimeException{
    @ExceptionHandler(value = JWTNotFoundException.class)
    public ResponseEntity<Object> exception(JWTNotFoundException exception) {
        return new ResponseEntity<>("JWT bulunamadı.", HttpStatus.NOT_FOUND);
    }
}
