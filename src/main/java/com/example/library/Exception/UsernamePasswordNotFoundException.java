package com.example.library.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class UsernamePasswordNotFoundException extends RuntimeException{
    @ExceptionHandler(value = UsernamePasswordNotFoundException .class)
    public ResponseEntity<Object> exception(UsernamePasswordNotFoundException  exception) {
        return new ResponseEntity<>("Kullanıcı adı veya şifre yanlış.", HttpStatus.UNAUTHORIZED);
    }
}
