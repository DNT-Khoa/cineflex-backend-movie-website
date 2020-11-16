package com.khoa.CineFlex.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {
    private Long id;
    private String content;
    private Instant commentDate;
    private String path;
    private String email;
    private List<UserDto> likedByUsers;
}
