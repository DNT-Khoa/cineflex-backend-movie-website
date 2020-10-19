package com.khoa.CineFlex.DTO;

import com.khoa.CineFlex.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDto {
    private Long id;
    private Long tmdbId;
    private String title;
    private Double rating;
    private String posterLink;
    private String backdropLink;
    private String movieType;
    private String filmLink;
    private List<CategoryDto> categories;
}
