package com.khoa.CineFlex.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String imageLink;
    private String accessibility;
    private String openingHours;
    private String generalInformation;
}
