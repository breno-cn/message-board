package com.forum.board.config;

import com.forum.board.model.UserModel;
import com.forum.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("DEBUG: " + username);
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        log.info(user.toString());

//        UserModel user = userRepository.findByUsername(username)
//                .orElse(new UserModel("placeholder", "placeholder", "placeholder"));

        log.info("TEST: " + user.getPassword());

//        return user;
        return new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                user.getAuthorities());
//        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), true, true, true, true, user.getAuthorities());
    }

}
