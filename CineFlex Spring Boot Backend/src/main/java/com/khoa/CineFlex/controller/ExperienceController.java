package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.ExperienceDto;
import com.khoa.CineFlex.service.ExperienceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experiences")
@AllArgsConstructor
public class ExperienceController {
    private final ExperienceService experienceService;

    @PostMapping
    public ResponseEntity<ExperienceDto> createExperience(@RequestBody ExperienceDto experienceDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.createExperience(experienceDto));
    }

    @DeleteMapping(path = "/{experienceId}")
    public ResponseEntity<Void> deleteExperienceById(@PathVariable Long experienceId) {
        experienceService.deleteExperienceById(experienceId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
