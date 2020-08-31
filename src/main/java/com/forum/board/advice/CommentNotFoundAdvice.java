package com.forum.board.advice;

import com.forum.board.exception.CommentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommentNotFoundAdvice {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> CommentNotFoundHandler(CommentNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

}
