package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.SeatAllocationDto;
import com.khoa.CineFlex.model.SeatAllocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatAllocationMapper {
    SeatAllocation seatAllocationDtoToSeatAllocation(SeatAllocationDto seatAllocationDto);

    SeatAllocationDto seatAllocationToDto(SeatAllocation seatAllocation);
}
