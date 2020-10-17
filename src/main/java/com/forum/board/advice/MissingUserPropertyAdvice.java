package com.forum.board.advice;

import com.forum.board.exception.MissingUserPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class MissingUserPropertyAdvice {

    @ExceptionHandler(MissingUserPropertyException.class)
    public ResponseEntity<Map<String, Object>> missingUserPropertyHandler() {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("message", "Missing user property.");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

//    public ResponseEntity<String> missingUserPropertyHandler() {
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body("Missing user property.");
//    }

}
