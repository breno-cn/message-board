package com.forum.board.advice;

import com.forum.board.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class PostNotFoundAdvice {

    @ExceptionHandler(PostNotFoundException.class)final
    ResponseEntity<Map<String, Object>> postNotFoundHandler(PostNotFoundException e) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }


//    ResponseEntity<String> postNotFoundHandler(PostNotFoundException e) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(e.getMessage());
//    }

}
