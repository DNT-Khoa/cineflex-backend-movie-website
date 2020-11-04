package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.RegisterRequest;
import com.khoa.CineFlex.DTO.UserDto;
import com.khoa.CineFlex.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User registerRequestToUser(RegisterRequest registerRequest);

    UserDto userToDto(User user);

    List<UserDto> listUsersToListDtos(List<User> users);
}
