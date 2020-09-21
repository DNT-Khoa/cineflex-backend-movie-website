package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.ViolenceLevelDto;
import com.khoa.CineFlex.model.ViolenceLevel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ViolenceLevelMapper {
    ViolenceLevel violenceLevelDtoToViolenceLevel(ViolenceLevelDto violenceLevelDto);

    ViolenceLevelDto violenceLevelToDto(ViolenceLevel violenceLevel);
}
