package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.ScheduleDto;
import com.khoa.CineFlex.model.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule scheduleDtoToSchedule(ScheduleDto scheduleDto);

    ScheduleDto scheduleToDto(Schedule schedule);
}
