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

    @PostMapping("/user/comments/likeComment")
    public ResponseEntity<?> userLikeComment(Long commentId, @RequestParam("email")String email) {
        try {
            this.commentService.likeComment(commentId, email);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/user/comments/unLikeComment")
    public ResponseEntity<?> userUnLikeComment(Long commentId, @RequestParam("email") String email) {
        try {
            this.commentService.unlikeComment(commentId, email);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/comments/byTmdbId/{tmdbId}")
    public ResponseEntity<?> getAllCommentsByTmdbId(@PathVariable Long tmdbId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.commentService.getAllCommentsByTmdbId(tmdbId));
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
