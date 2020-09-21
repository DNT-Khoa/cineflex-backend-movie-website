package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.CinemaDto;
import com.khoa.CineFlex.model.Cinema;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    @Mapping(target = "id", source = "id")
    Cinema cinemaDtoToCinema(CinemaDto cinemaDto);

    @InheritInverseConfiguration
    CinemaDto cinemaToDto(Cinema cinema);
}
