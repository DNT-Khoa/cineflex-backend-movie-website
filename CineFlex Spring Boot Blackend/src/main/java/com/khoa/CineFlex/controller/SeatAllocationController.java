package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.SeatAllocationDto;
import com.khoa.CineFlex.model.SeatAllocation;
import com.khoa.CineFlex.service.SeatAllocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/seatallocations")
public class SeatAllocationController {
    private final SeatAllocationService seatAllocationService;

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSeatStatus(@PathVariable Long id) {
        try {
            seatAllocationService.updateSeatAllocationStatus(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
