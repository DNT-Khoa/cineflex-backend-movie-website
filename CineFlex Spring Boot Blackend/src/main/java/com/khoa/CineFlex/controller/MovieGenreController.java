package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.MovieGenreDto;
import com.khoa.CineFlex.service.MovieGenreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/moviegenres")
@AllArgsConstructor
@Slf4j
public class MovieGenreController {
    private final MovieGenreService movieGenreService;

    @PostMapping
    public ResponseEntity<MovieGenreDto> createMovieGenre(@RequestBody MovieGenreDto movieGenreDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieGenreService.createMovieGenre(movieGenreDto));
    }

    @GetMapping
    public ResponseEntity<List<MovieGenreDto>>getAllMovieGenres() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(movieGenreService.getAll());
    }

    @DeleteMapping(path = "/{movieGenreId}")
    public ResponseEntity<Void> deleteMovieGenre(@PathVariable("movieGenreId") Long movieGenreId) {
        this.movieGenreService.deleteMovieGenre(movieGenreId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
