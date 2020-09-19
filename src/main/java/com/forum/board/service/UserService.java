package com.forum.board.service;

import com.forum.board.exception.MissingUserPropertyException;
import com.forum.board.exception.UsernameExistException;
import com.forum.board.model.UserModel;
import com.forum.board.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel saveNewUser(UserModel userModel) throws MissingUserPropertyException, UsernameExistException {
        if (userModel == null) {
            // TODO new exception
            throw new MissingUserPropertyException();
        }

        String username = userModel.getUsername();
        String password = userModel.getPassword();
        String email = userModel.getEmail();

        if (username == null || password == null || email == null ||
            username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            throw new MissingUserPropertyException();
        }

        String encodedPassword = passwordEncoder.encode(password);

        if (userRepository.existsByUsernameOrPasswordOrEmail(username, encodedPassword, email)) {
            // TODO new exception
            throw new UsernameExistException(username);
        }

        userModel.setPassword(encodedPassword);

        return userRepository.save(userModel);
    }

}
