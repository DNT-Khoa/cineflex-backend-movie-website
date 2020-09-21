package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.SeatAllocationDto;
import com.khoa.CineFlex.exception.ScheduleNotFoundException;
import com.khoa.CineFlex.exception.SeatAllocationNotFoundException;
import com.khoa.CineFlex.mapper.SeatAllocationMapper;
import com.khoa.CineFlex.model.Schedule;
import com.khoa.CineFlex.model.SeatAllocation;
import com.khoa.CineFlex.repository.ScheduleRepository;
import com.khoa.CineFlex.repository.SeatAllocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SeatAllocationService {
    private final SeatAllocationMapper seatAllocationMapper;
    private final SeatAllocationRepository seatAllocationRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void updateSeatAllocationStatus(Long id) {
        SeatAllocation seatAllocation = seatAllocationRepository.findById(id).orElseThrow(()->new SeatAllocationNotFoundException("Cannot find seat allocation with id " + id));
        seatAllocation.setStatus(1);

        seatAllocationRepository.save(seatAllocation);
    }

}
