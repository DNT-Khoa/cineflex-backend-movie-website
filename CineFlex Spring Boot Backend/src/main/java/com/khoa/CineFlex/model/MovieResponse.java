package com.khoa.CineFlex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String directorName;
    private String actorList;
    private String trailerLink;
    private String imageLink;
    private int length;
}
