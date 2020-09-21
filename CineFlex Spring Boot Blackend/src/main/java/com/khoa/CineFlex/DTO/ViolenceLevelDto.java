package com.khoa.CineFlex.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ViolenceLevelDto {
    private Long id;
    private String name;
    private String imageLink;
}
