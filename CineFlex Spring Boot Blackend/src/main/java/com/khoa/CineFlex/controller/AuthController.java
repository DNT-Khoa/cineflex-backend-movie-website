package com.khoa.CineFlex.controller;


import com.khoa.CineFlex.DTO.RegisterRequest;
import com.khoa.CineFlex.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        if (authService.signup(registerRequest, "User")) {
            return new ResponseEntity<>("User Registration Successful", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Email already exists!", HttpStatus.CONFLICT);
    }

    @PostMapping(path = "/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest, "Admin");

        return new ResponseEntity<>("Admin created!", HttpStatus.CREATED);
    }
}
