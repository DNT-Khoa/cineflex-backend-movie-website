package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.TicketTypeDto;
import com.khoa.CineFlex.model.TicketType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketTypeMapper {
    TicketType ticketTypeDtoToTicketType(TicketTypeDto ticketTypeDto);

    TicketTypeDto ticketTypeToDto(TicketType ticketType);
}
