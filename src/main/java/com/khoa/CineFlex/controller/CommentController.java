package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.CommentRequest;
import com.khoa.CineFlex.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/user/comments")
    public ResponseEntity<?> addNewComments(@RequestBody CommentRequest commentRequest, @RequestParam("email")String email) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.commentService.addNewComment(commentRequest, email));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/comments/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable Long commentId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getCommentById(commentId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user/comments/likeComment/{commentId}")
    public ResponseEntity<?> userLikeComment(@PathVariable Long commentId, @RequestParam("email")String email) {
        try {
            this.commentService.likeComment(commentId, email);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user/comments/unLikeComment/{commentId}")
    public ResponseEntity<?> userUnLikeComment(@PathVariable Long commentId, @RequestParam("email") String email) {
        try {
            this.commentService.unlikeComment(commentId, email);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/comments/byMovieId/{movieId}")
    public ResponseEntity<?> getAllCommentsByTmdbId(@PathVariable Long movieId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getAllCommentsByMovieId(movieId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/comments/byPostId/{postId}")
    public ResponseEntity<?> getAllCommentsByPostId(@PathVariable Long postId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getAllCommentsByPostId(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/user/comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long commentId) {
        this.commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
