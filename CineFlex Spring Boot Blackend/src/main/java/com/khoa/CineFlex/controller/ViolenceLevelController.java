package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.ViolenceLevelDto;
import com.khoa.CineFlex.model.ViolenceLevel;
import com.khoa.CineFlex.service.ViolenceLevelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/violencelevels")
@AllArgsConstructor
public class ViolenceLevelController {
    private final ViolenceLevelService violenceLevelService;

    @PostMapping
    public ResponseEntity createViolenceLevel(@RequestBody ViolenceLevelDto violenceLevelDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(violenceLevelService.createViolenceLevel(violenceLevelDto));
    }

    @DeleteMapping(path = "/{violenceLevelId}")
    public ResponseEntity<Void> deleteViolenceLevel(@PathVariable Long violenceLevelId) {
        violenceLevelService.deleteViolenceLevel(violenceLevelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
