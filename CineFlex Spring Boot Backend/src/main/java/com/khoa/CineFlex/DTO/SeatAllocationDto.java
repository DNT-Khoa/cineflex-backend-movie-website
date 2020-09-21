package com.khoa.CineFlex.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatAllocationDto {
    private Long id;
    private char row;
    private int seat;
    private Long scheduleId;
    private int status;
}
