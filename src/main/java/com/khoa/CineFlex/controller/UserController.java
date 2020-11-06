package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.*;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.service.ImageService;
import com.khoa.CineFlex.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(@RequestParam("email") String userEmail) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserDetails(userEmail));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/likedMovies")
    public ResponseEntity<?> getLikedMoviesOfAUser(@RequestParam("email") String userEmail) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.getLikedMoviesOfUser(userEmail));
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

    @PostMapping("/user/rateMovie")
    public ResponseEntity<?> userRatesMovie(@RequestBody RatingDto ratingDto) {
        try {
            this.userService.rateMovie(ratingDto);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
        }
    }

    @GetMapping("/user/check/rateMovie")
    public ResponseEntity<?> checkIfUserHasRatedMovie(@RequestParam("email") String email, @RequestParam("movieId") Long movieId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.checkIfUserHasRatedMovie(email, movieId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("user/value/rateMovie")
    public ResponseEntity<?> getRatingOfUserForMovie(@RequestParam("email") String email, @RequestParam("movieId") Long movieId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.userService.getRatingOfUserForMovie(email, movieId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("user/deleteMovieRating")
    public ResponseEntity<?> deleteMovieRating(@RequestParam("email") String email, @RequestParam("movieId") Long movieId) {
        try {
            this.userService.deleteRatingRecord(email, movieId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
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
    public ResponseEntity<?> deleteAccount(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("refreshToken") String refreshToken) {
        try {
            this.userService.deleteAccount(email, password, refreshToken);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user/changeAvatar")
    public ResponseEntity<?> changeAvatar(@RequestParam("imageFile") MultipartFile file, @RequestParam("email") String email) {
        try {
            this.imageService.uploadImage(file, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/getAvatar")
    public ResponseEntity<?> getAvatar(@RequestParam("email") String email) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.imageService.getAvatar(email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
