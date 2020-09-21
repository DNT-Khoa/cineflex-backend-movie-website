package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.MovieTypeDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.exception.MovieTypeNotFoundException;
import com.khoa.CineFlex.service.MovieTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/movietypes")
public class MovieTypeController {
    private final MovieTypeService movieTypeService;

    @PostMapping
    public ResponseEntity<MovieTypeDto> createMovieType(@RequestBody MovieTypeDto movieTypeDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(movieTypeService.createMovieType(movieTypeDto));
    }

    @GetMapping
    public ResponseEntity<List<MovieTypeDto>> getAllMovieTypes() {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieTypeService.getAll());
    }

    @DeleteMapping(path = "/{movieTypeId}")
    public ResponseEntity<Object> deleteMovieType(@PathVariable Long movieTypeId) {
        try {
            movieTypeService.deleteMovieType(movieTypeId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (MovieTypeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
