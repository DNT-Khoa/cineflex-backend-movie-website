package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.TicketDto;
import com.khoa.CineFlex.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<String> createTicket(@RequestBody TicketDto ticketDto) {
        try {

            return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(ticketDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
