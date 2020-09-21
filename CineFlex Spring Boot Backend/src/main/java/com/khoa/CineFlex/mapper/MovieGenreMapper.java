package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.MovieGenreDto;
import com.khoa.CineFlex.model.MovieGenre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieGenreMapper {
    MovieGenre movieGenreDtoToMovieGenre(MovieGenreDto movieGenreDto);

    MovieGenreDto movieGenreToDto(MovieGenre movieGenre);

    List<MovieGenreDto> listMovieGenreToListDto(List<MovieGenre> movieGenres);
}
