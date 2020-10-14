package com.forum.board.controller;
//TEST CLASS

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public ResponseEntity<?> admin(Authentication authentication) {
        return ResponseEntity
                .ok("ADMIN " + authentication.getName());
    }

}
