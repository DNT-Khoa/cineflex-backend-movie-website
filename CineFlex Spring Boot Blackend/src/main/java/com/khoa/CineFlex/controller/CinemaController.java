package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.CinemaDto;
import com.khoa.CineFlex.service.CinemaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/cinemas")
@AllArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;


    @PostMapping
    public ResponseEntity<CinemaDto> createCinema(@RequestBody CinemaDto cinemaDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cinemaService.createCinema(cinemaDto));
    }

    @GetMapping(path = "/{cinemaId}")
    public ResponseEntity<Object> getCinemaById(@PathVariable Long cinemaId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cinemaService.getCinemaById(cinemaId));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find cinema with id " + cinemaId);
        }
    }

    @DeleteMapping(path = "/{cinemaId}")
    public ResponseEntity<Void> deleteCinemaById(@PathVariable Long cinemaId) {
        cinemaService.deleteCinemaById(cinemaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
