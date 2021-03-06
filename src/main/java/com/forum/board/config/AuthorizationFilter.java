package com.forum.board.config;

import static com.forum.board.config.SecurityConstants.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.forum.board.model.Role;
import com.forum.board.model.UserModel;
import com.forum.board.repository.RoleRepository;
import com.forum.board.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final RoleRepository roleRepository;

    public AuthorizationFilter(AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        super(authenticationManager);
        this.roleRepository = roleRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if (session == null) {
            chain.doFilter(request, response);
            return;
        }

        UserModel user = (UserModel) request.getUserPrincipal();
        String username = user.getUsername();
        String password = user.getPassword();
        List<Role> roles = roleRepository.findAllByUserModelsUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, roles);

        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest req,
//                                    HttpServletResponse res,
//                                    FilterChain chain) throws IOException, ServletException {
//        String header = req.getHeader(HEADER_STRING);
//
//        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
//            chain.doFilter(req, res);
//            return;
//        }
//
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(req, res);
//    }
//
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
//        String token = req.getHeader(HEADER_STRING);
//        if (token != null) {
//            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
//                    .build()
//                    .verify(token.replace(TOKEN_PREFIX, ""))
//                    .getSubject();
//
//            if (user != null) {
////                TODO: get user authorizations!!!!!!!
//                log.info("DEBUG AUTHORIZATION FILTER USER " + user);
////                UserModel temp = userRepository.findByUsername(user)
////                        .orElseThrow(() -> new UsernameNotFoundException(user));
////                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//                List<Role> roles = roleRepository.findAllByUserModelsUsername(user);
////                log.info("DEBUG TEMP " + temp.getRoles());
////                List<Role> roles = new ArrayList<>();
////                Role role = new Role();
////                role.setRoleName("ROLE_ADMIN");
////                roles.add(role);
//                return new UsernamePasswordAuthenticationToken(user, null, roles);
//            }
//            return null;
//        }
//        return null;
//    }

}
