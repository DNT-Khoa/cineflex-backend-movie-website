package com.khoa.CineFlex.DTO;

import com.khoa.CineFlex.model.MovieGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;
    private String title;
    private String directorName;
    private String actorList;
    private String trailerLink;
    private String imageLink;
    private int length;
    private Long movieTypeId;
    private List<Long> movieGenreIdList;
    private List<Long> violenceLevelIdList;
    private List<Long> experienceIdList;
}
