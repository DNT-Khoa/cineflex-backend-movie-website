package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.MovieDto;
import com.khoa.CineFlex.model.Movie;
import com.khoa.CineFlex.model.MovieResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "directorName", source = "directorName")
    @Mapping(target = "actorList", source = "actorList")
    @Mapping(target = "trailerLink", source = "trailerLink")
    @Mapping(target = "imageLink", source = "imageLink")
    @Mapping(target = "length", source = "length")
    Movie movieDtoToMovie(MovieDto movieDto);

    MovieDto movietoDto(Movie movie);

    MovieResponse movieToMovieResponse(Movie movie);

    List<MovieDto> movieListToListDto(List<Movie> movies);
}
