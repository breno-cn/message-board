package com.forum.board.controller;

import com.forum.board.model.UserModel;
import com.forum.board.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
// TODO: UserAssembler and links to user actions
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel) throws RuntimeException {

        // TODO: add roles in new user

        UserModel newUser = userService.saveNewUser(userModel);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newUser);
    }

}
