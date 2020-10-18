package com.khoa.CineFlex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    @GetMapping(path = "/status/check")
    public ResponseEntity<?> getStatus() {
        return ResponseEntity.status(200).body("You are authorized as user");
    }
}
