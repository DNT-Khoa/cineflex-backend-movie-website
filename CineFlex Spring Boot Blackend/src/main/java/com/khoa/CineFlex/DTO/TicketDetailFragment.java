package com.khoa.CineFlex.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailFragment {
    private Long allocationId;
    private Long ticketTypeId;
}
