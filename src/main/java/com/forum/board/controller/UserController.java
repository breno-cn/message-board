package com.forum.board.controller;

import com.forum.board.model.User;
import com.forum.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TEST
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> newUser(@RequestBody User user) {
        log.info(user.toString());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userRepository.save(user));
    }

}
