package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/admin/movies/comingsoon")
    public ResponseEntity<?> getAllComingMovies() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getAllComingMovies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/admin/movies")
    public ResponseEntity<?> createNewComingMovies(@RequestBody MovieDto movieDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.movieService.createComingMovie(movieDto));
        } catch (CineFlexException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Movie already exits!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/admin/movies/{movieId}")
    public ResponseEntity<?> editMovie(@PathVariable Long movieId, @RequestBody MovieDto movieDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.editMovie(movieId, movieDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/movies/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long movieId) {
        try {
            this.movieService.deleteMovieById(movieId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
