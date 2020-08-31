package com.forum.board.advice;

import com.forum.board.exception.UsernameExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsernameExistAdvice {

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<String> usernameExistsHandler(UsernameExistException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

}
