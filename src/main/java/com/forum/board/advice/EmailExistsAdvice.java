package com.forum.board.advice;

import com.forum.board.exception.EmailExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmailExistsAdvice {

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<String> emailExistsHandler(EmailExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

}
