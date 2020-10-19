package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.model.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto movieToDto(Movie movie);

    Movie movieDtoToMovie(MovieDto movieDto);

    List<MovieDto> listMovieToListDto(List<Movie> movies);
}
