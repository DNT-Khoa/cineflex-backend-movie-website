package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.ScheduleDto;
import com.khoa.CineFlex.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(scheduleDto));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getScheduleById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getScheduleById(id));
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping(path = "/{scheduleId}")
    public ResponseEntity<Object> deleteScheduleById(@PathVariable Long scheduleId) {
        try {
            scheduleService.deleteScheduleById(scheduleId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
