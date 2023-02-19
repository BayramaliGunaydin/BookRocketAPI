package com.example.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class IdNotFoundException extends RuntimeException{
    @ExceptionHandler(value = IdNotFoundException.class)
    public ResponseEntity<Object> exception(IdNotFoundException exception) {
        return new ResponseEntity<>("Girilen id bulunamadı.", HttpStatus.NOT_FOUND);
    }
}
