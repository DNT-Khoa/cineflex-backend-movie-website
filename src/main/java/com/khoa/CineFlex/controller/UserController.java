package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.ChangePasswordRequest;
import com.khoa.CineFlex.DTO.LikeRequest;
import com.khoa.CineFlex.DTO.LoginRequest;
import com.khoa.CineFlex.DTO.UserEditRequest;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam("email") String userEmail) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserDetails(userEmail));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user/like")
    public ResponseEntity<?> userLikesMovie(@RequestBody LikeRequest likeRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.likeMovie(likeRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user/unlike")
    public ResponseEntity<?> userUnLikesMovie(@RequestBody LikeRequest likeRequest) {
        try {
            this.userService.unlikeMovie(likeRequest);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user/check/like")
    public ResponseEntity<?> checkIfUserHasLikedMovie(@RequestBody LikeRequest likeRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.checkIfUserHasLikedMovie(likeRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/user/edit")
    public ResponseEntity<?> editUserDetails(@RequestBody UserEditRequest userEditRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.editUserDetails(userEditRequest));
        } catch (CineFlexException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/user/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            this.userService.changePassword(changePasswordRequest);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/user/deleteAccount")
    public ResponseEntity<?> deleteAccount(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            this.userService.deleteAccount(email, password);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
