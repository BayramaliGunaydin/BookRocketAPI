package com.example.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class IdAlreadyExistException extends RuntimeException{
    @ExceptionHandler(value = IdAlreadyExistException.class)
    public ResponseEntity<Object> exception(IdAlreadyExistException exception) {
        return new ResponseEntity<>("Girilen id bulunmakta.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
