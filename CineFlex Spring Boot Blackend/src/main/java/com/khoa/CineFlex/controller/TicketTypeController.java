package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.TicketTypeDto;
import com.khoa.CineFlex.model.TicketType;
import com.khoa.CineFlex.service.TicketTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/tickettypes")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    @PostMapping
    public ResponseEntity<TicketTypeDto> createTicketType(@RequestBody TicketTypeDto ticketTypeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketTypeService.createTicketType(ticketTypeDto));
    }

    @GetMapping(path = "/{ticketTypeId}")
    public ResponseEntity<Object> getTicketTypeById(@PathVariable Long ticketTypeId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ticketTypeService.getTicketTypeById(ticketTypeId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{ticketTypeId}")
    public ResponseEntity<Void> deleteTicketTypeById(@PathVariable Long ticketTypeId) {
        ticketTypeService.deleteTicketTypeById(ticketTypeId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
