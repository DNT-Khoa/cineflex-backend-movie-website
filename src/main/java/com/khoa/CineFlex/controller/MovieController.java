package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/api/movies/all")
    public ResponseEntity<?> getAllMovies() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getAllMovies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/movies/all/4")
    public ResponseEntity<?> getAllMoviesLimit4() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getAllMoviesLimit4());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/movies/{tmdbId}")
    public ResponseEntity<?> getMovieByTmdbId(@PathVariable Long tmdbId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getMovieByTmdbId(tmdbId));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/movies/comingsoon")
    public ResponseEntity<?> getAllComingMovies() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getAllComingMovies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/movies/comingsoon/4")
    public ResponseEntity<?> getFourLatestComingSoonMovies() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getFourLatestComingSoonMovies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/api/movies/nowplaying")
    public ResponseEntity<?> getAllNowPlayingMovies() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getAllNowPlayingMovies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Get four latest now playing movies
    @GetMapping("/api/movies/nowplaying/4")
    public ResponseEntity<?> getFourLatestNowPlayingMovies() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getFourLatestNowPlayingMovies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("api/movies/toprated")
    public ResponseEntity<?> getTopRatedMovies() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getTopRatedMovies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("api/movies/toprated/4")
    public ResponseEntity<?> getTopRatedMoviesLimit4() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.getTopRatedMoviesLimit4());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("api/movies/search")
    public ResponseEntity<?> searchMoviesByQueryKey(@RequestParam("key") String key) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.movieService.searchMovieByQueryKey(key));
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
