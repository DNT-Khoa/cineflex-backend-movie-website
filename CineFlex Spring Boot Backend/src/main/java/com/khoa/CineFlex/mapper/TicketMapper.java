package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.TicketDto;
import com.khoa.CineFlex.model.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    Ticket ticketDtoToTicket(TicketDto ticketDto);

    TicketDto ticketToDto(Ticket ticket);
}
