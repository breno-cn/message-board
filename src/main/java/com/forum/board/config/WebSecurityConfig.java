package com.forum.board.config;

import com.forum.board.repository.RoleRepository;
import com.forum.board.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.Cookie;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

   private final RoleRepository roleRepository;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, RoleRepository roleRepository) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
//        auth.userDetailsService(userDetailsService);
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //////////////////// ANTIGO FUNCIONA
//        http.cors().and().csrf().disable()
//          http
//                .authorizeRequests()
////                .antMatchers("/user/profile/**").hasAnyRole("ADMIN")
////                .antMatchers("/user/profile/**").authenticated()
////                .antMatchers("/user/profile/**").hasAuthority("ROLE_ADMIN")
//                .antMatchers("/user/profile/**").hasRole("ADMIN")
//                .antMatchers("/post/board/**/new").authenticated()
//                .antMatchers("/login").permitAll()
//                .anyRequest().permitAll()
//                .and().formLogin();
////                .httpBasic()
////                  .and()
////                  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().and().csrf().disable();
////                .formLogin();
////                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
////                .addFilter(new JWTAuthorizationFilter(authenticationManager(), roleRepository))
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().and().csrf().disable();
        http
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/boards/**/posts").authenticated()
                    .antMatchers(HttpMethod.POST, "/posts/**/comments").authenticated()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().permitAll()
                .and()
                    .addFilter(new AuthenticationFilter(authenticationManager()))
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandler());
//                .logout(logout -> logout
//                            .logoutUrl("/test_logout")
//                            .addLogoutHandler((request, response, authentication) -> {
//                                SecurityContextHolder
//                                        .getContext()
//                                        .setAuthentication(null);
//                                request.getSession().invalidate();
//                                response.setStatus(HttpStatus.OK.value());
//                            }));
//                .and().addFilter(new AuthenticationFilter(authenticationManager())); // OLD IT WORKS
//                      .addFilter(new AuthorizationFilter(authenticationManager(), roleRepository));
//                .logout(logout -> logout
//                    .logoutUrl("/logout")
//                    .addLogoutHandler((request, response, auth) -> {
//                        for (Cookie cookie : request.getCookies()) {
//                            String cookieName = cookie.getName();
//                            Cookie cookieToDelete = new Cookie(cookieName, null);
//                            cookieToDelete.setMaxAge(0);
//                            response.addCookie(cookieToDelete);
//                        }
//                    }));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
