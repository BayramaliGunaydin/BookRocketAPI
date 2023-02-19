package com.example.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class NullJWTException extends RuntimeException{
    @ExceptionHandler(value = NullJWTException.class)
    public ResponseEntity<Object> exception(NullJWTException exception) {
        return new ResponseEntity<>("JWT Boş!", HttpStatus.UNAUTHORIZED);
    }
}
