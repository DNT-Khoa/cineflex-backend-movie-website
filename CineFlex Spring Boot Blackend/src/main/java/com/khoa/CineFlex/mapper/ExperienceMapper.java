package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.ExperienceDto;
import com.khoa.CineFlex.model.Experience;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {
    ExperienceDto experienceToDto(Experience experience);

    Experience experienceDtoToExperience(ExperienceDto experienceDto);
}
