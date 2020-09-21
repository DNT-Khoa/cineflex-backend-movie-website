package com.khoa.CineFlex.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoa.CineFlex.DTO.LoginRequest;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserRepository userRepository;
    private final Environment environment;

    public AuthenticationFilter(UserRepository userRepository, Environment environment, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequest.class);

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        String email = ( (User) auth.getPrincipal()).getUsername();
        com.khoa.CineFlex.model.User userDetails = userRepository.findByEmail(email);

        String token = Jwts.builder()
                .setSubject(userDetails.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();

        res.addHeader("token", token);
        res.addHeader("userId", userDetails.getEmail());
    }
}
