package com.forum.board.advice;

import com.forum.board.exception.EmailExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class EmailExistsAdvice {

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Map<String, Object>> emailExistsHandler(EmailExistsException e) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("message", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
//    public ResponseEntity<String> emailExistsHandler(EmailExistsException e) {
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT)
//                .body(e.getMessage());
//    }

}
