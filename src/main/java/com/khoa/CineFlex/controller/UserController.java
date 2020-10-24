package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.LikeRequest;
import com.khoa.CineFlex.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

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
}
