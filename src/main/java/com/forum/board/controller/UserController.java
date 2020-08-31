package com.forum.board.controller;

import com.forum.board.exception.EmailExistsException;
import com.forum.board.exception.MissingUserPropertyException;
import com.forum.board.exception.PasswordExistsException;
import com.forum.board.exception.UsernameExistException;
import com.forum.board.model.User;
import com.forum.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/profile/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getProfileById(@PathVariable(name = "id") Long id) {
        User user = userRepository.findById(id)
                .orElse(new User("PLACEHOLDER", "PLACEHOLDER", "PLACEHOLDER"));
        log.debug("TEST: " + user);
        Map<String, Object> profile = new LinkedHashMap<>();
        profile.put("username", user.getUsername());
        profile.put("email", user.getEmail());
        profile.put("password", user.getPassword());
        profile.put("id", user.getId());

        return ResponseEntity.ok(profile);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody User user) throws RuntimeException {
        log.info("Usuário: " + user);
        if (user == null) {
            throw new MissingUserPropertyException();
        }

        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();

        if (username == null || email == null || password == null) {
            log.debug("NULL!");
            throw new MissingUserPropertyException();
        }
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            log.debug("MISSING!");
            throw new MissingUserPropertyException();
        }

        if (userRepository.existsOneByUsername(username)) {
            log.debug("USERNAME!");
            throw new UsernameExistException(username);
        }
        if (userRepository.existsOneByEmail(email)) {
            log.debug("EMAIL");
            throw new EmailExistsException(email);
        }
        if (userRepository.existsOneByPassword(password)) {
            log.debug("PASSWORD");
            throw new PasswordExistsException(password);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userRepository.save(user));
    }

}
