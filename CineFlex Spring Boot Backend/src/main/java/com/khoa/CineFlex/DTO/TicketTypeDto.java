package com.khoa.CineFlex.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketTypeDto {
    private Long id;
    private String name;
    private int price;
}
