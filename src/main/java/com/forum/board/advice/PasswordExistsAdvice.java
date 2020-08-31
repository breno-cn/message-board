package com.forum.board.advice;

import com.forum.board.exception.PasswordExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PasswordExistsAdvice {

    @ExceptionHandler(PasswordExistsException.class)
    public ResponseEntity<String> passwordExistsHandler(PasswordExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

}
