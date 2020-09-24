package com.khoa.CineFlex.controller;


import com.khoa.CineFlex.DTO.AuthenticationResponse;
import com.khoa.CineFlex.DTO.LoginRequest;
import com.khoa.CineFlex.DTO.RefreshTokenRequest;
import com.khoa.CineFlex.DTO.RegisterRequest;
import com.khoa.CineFlex.repository.RefreshTokenRepository;
import com.khoa.CineFlex.service.AuthService;
import com.khoa.CineFlex.service.JwtUtil;
import com.khoa.CineFlex.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        if (authService.signup(registerRequest, "User")) {
            return new ResponseEntity<>("User Registration Successful", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Email already exists!", HttpStatus.CONFLICT);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping(path = "/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest, "Admin");

        return new ResponseEntity<>("Admin created!", HttpStatus.CREATED);
    }

    @PostMapping(path = "/refresh/token")
    public ResponseEntity<?> refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(refreshTokenRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Refresh Token has been successfully deleted!");
    }
}
