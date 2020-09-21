package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.TicketTypeDto;
import com.khoa.CineFlex.exception.TicketTypeNotFoundException;
import com.khoa.CineFlex.mapper.TicketTypeMapper;
import com.khoa.CineFlex.model.TicketDetail;
import com.khoa.CineFlex.model.TicketType;
import com.khoa.CineFlex.repository.TicketTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TicketTypeService {
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketTypeMapper ticketTypeMapper;

    @Transactional
    public TicketTypeDto createTicketType(TicketTypeDto ticketTypeDto) {
        TicketType ticketType = ticketTypeMapper.ticketTypeDtoToTicketType(ticketTypeDto);

        TicketType save = ticketTypeRepository.save(ticketType);

        return ticketTypeMapper.ticketTypeToDto(save);
    }

    @Transactional(readOnly = true)
    public TicketTypeDto getTicketTypeById(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id).orElseThrow(()->new TicketTypeNotFoundException("Cannot find ticket type with id " + id));

        return ticketTypeMapper.ticketTypeToDto(ticketType);
    }

    @Transactional
    public void deleteTicketTypeById(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id).orElseThrow(()->new TicketTypeNotFoundException("Cannot find ticket type with id " + id));

        for (TicketDetail ticketDetail : ticketType.getTicketDetails()) {
            ticketDetail.setTicketType(null);
        }

        ticketTypeRepository.deleteById(id);
    }
}
