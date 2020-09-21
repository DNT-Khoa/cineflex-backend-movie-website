package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.MovieTypeDto;
import com.khoa.CineFlex.model.MovieType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieTypeMapper {
    MovieType movieTypeDtoToMovieType(MovieTypeDto movieTypeDto);

    MovieTypeDto movieTypeToDto(MovieType movieType);

    List<MovieTypeDto> listMovieTypeToListDto(List<MovieType> movieTypes);
}
