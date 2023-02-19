package com.example.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class UsernameAlreadyExistException extends RuntimeException {
    @ExceptionHandler(value = UsernameAlreadyExistException.class)
    public ResponseEntity<Object> exception(UsernameAlreadyExistException exception) {
        return new ResponseEntity<>("Girilen kullanıcı adı bulunmakta", HttpStatus.BAD_REQUEST);
    }
}
