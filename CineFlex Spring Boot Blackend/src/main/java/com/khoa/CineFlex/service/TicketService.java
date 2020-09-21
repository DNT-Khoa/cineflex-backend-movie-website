package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.TicketDetailFragment;
import com.khoa.CineFlex.DTO.TicketDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.exception.ScheduleNotFoundException;
import com.khoa.CineFlex.exception.SeatAllocationNotFoundException;
import com.khoa.CineFlex.exception.TicketTypeNotFoundException;
import com.khoa.CineFlex.mapper.TicketMapper;
import com.khoa.CineFlex.model.*;
import com.khoa.CineFlex.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final SeatAllocationRepository seatAllocationRepository;
    private final TicketTypeRepository ticketTypeRepository;

    @Transactional
    public String createTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();

        User user = userRepository.findById(ticketDto.getUserId()).orElseThrow(()-> new CineFlexException("Cannot find user with id " + ticketDto.getUserId()));
        Schedule schedule = scheduleRepository.findById(ticketDto.getScheduleId()).orElseThrow(()->new ScheduleNotFoundException("Cannot find schedule with id " + ticketDto.getScheduleId()));

        ticket.setUser(user);
        ticket.setSchedule(schedule);

        user.getTickets().add(ticket);
        schedule.getTickets().add(ticket);

        List<TicketDetail> ticketDetailList = new ArrayList<>();
        for (TicketDetailFragment ticketDetailFragment : ticketDto.getTicketDetailFragments()) {
            TicketDetail ticketDetail = new TicketDetail();

            ticketDetail.setTicket(ticket);

            SeatAllocation seatAllocation = seatAllocationRepository.findById(ticketDetailFragment.getAllocationId()).orElseThrow(()->new SeatAllocationNotFoundException("Cannot find seat allocation with id " + ticketDetailFragment.getAllocationId()));
            TicketType ticketType = ticketTypeRepository.findById(ticketDetailFragment.getTicketTypeId()).orElseThrow(()->new TicketTypeNotFoundException("Cannot find ticket type with id " + ticketDetailFragment.getTicketTypeId()));

            ticketDetail.setSeatAllocation(seatAllocation);
            ticketDetail.setTicketType(ticketType);

            seatAllocation.setTicketDetail(ticketDetail);
            ticketType.getTicketDetails().add(ticketDetail);

            ticketDetailList.add(ticketDetail);
        }

        ticket.setTicketDetails(ticketDetailList);

        ticketRepository.save(ticket);

        return "Successfully created the ticket";
    }
}
