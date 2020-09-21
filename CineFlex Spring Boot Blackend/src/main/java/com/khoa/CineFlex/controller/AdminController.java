package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.RegisterRequest;
import com.khoa.CineFlex.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping(path = "/status/check")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("You are authorized as an admin", HttpStatus.OK);
    }

}
