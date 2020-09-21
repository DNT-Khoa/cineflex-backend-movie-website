package com.khoa.CineFlex.DTO;

import com.khoa.CineFlex.model.SeatAllocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDto {
    private Long id;
    private Long userId;
    private Long scheduleId;
    private List<TicketDetailFragment> ticketDetailFragments;
}
