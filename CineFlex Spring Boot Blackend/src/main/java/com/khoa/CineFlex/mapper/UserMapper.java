package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.RegisterRequest;
import com.khoa.CineFlex.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User registerRequestToUser(RegisterRequest registerRequest);
}
