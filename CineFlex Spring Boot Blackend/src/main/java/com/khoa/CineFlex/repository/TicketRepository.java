package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
