package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.exception.MovieNotFoundException;
import com.khoa.CineFlex.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/movies")
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Object> createMovie(@RequestBody MovieDto movieDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(movieDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/{movieId}")
    public ResponseEntity<Object> getMovieById(@PathVariable Long movieId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieById(movieId));
        } catch (MovieNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

//    @GetMapping
//    public ResponseEntity<List<MovieDto>> getMovieByMovieType(@RequestParam("movieTypeId") Long movieTypeId) {
//        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieByMovieType(movieTypeId));
//    }

    @GetMapping
    public ResponseEntity<Object> getMovieByMovieGenre(@RequestParam("movieGenreId") Long movieGenreId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieByMovieGenre(movieGenreId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/{movieId}")
    public ResponseEntity<String> updateMovieById(@PathVariable Long movieId, @RequestBody MovieDto movieDto) {
        try {
            movieService.updateMovieById(movieId, movieDto);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated movie with id " + movieId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something wrong has happed!");
        }
    }

    @DeleteMapping(path = "/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
