package com.khoa.CineFlex.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ScheduleDto {
    private Long id;
    private Date date;
    private Long cinemaId;
    private Long movieId;
    private Long experienceId;
    private String startTime;
    private String endTime;
    private int roomNumber;
}
