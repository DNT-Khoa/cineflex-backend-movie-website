package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.PostRequest;
import com.khoa.CineFlex.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/api/posts")
    public ResponseEntity<?> getAllPosts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.getAllPosts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/posts/byCategory")
    public ResponseEntity<?> getAllPostsByCategory(@RequestParam("categoryId") Long categoryId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.getAllPostsByCategory(categoryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/posts/count")
    public ResponseEntity<?> getCountPostPerCategory(@RequestParam("categoryId") Long categoryId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.getCountPostPerCategory(categoryId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/posts/search")
    public ResponseEntity<?> searchPostByKey(@RequestParam("key") String key) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.searchPostByKey(key));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.getPostById(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("api/posts/top/4")
    public ResponseEntity<?> getFourTopNews() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.getFourTopNews());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/admin/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.createPost(postRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/admin/posts/{postId}")
    public ResponseEntity<?> editPost(@PathVariable Long postId, @RequestBody PostRequest postRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.editPost(postId, postRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
        }
    }

    @PutMapping("/api/posts/updateView/{postId}")
    public ResponseEntity<?> updateView(@PathVariable Long postId) {
        try {
            this.postService.updateView(postId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
        }
    }

    @DeleteMapping("/admin/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            this.postService.deletePost(postId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
