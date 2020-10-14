package com.forum.board.controller;

import com.forum.board.model.UserModel;
import com.forum.board.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
// TODO: UserAssembler and links to user actions
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel) throws RuntimeException {

        // TODO: add roles in new user

        UserModel newUser = userService.saveNewUser(userModel);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newUser);
    }

//    TODO: delete this method
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserModel userModel) {
        String username = userModel.getUsername();
        String password = userModel.getPassword();
//        String password = passwordEncoder.encode(userModel.getPassword());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        SecurityContextHolder.clearContext();
        if (session != null) {
            log.info("SESSION: " + session);
            session.invalidate();
        }

        return ResponseEntity.ok("LOGOUT PLACEHOLDER");
    }

}
