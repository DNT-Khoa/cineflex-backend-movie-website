package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.PostResponse;
import com.khoa.CineFlex.model.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse postToPostResponse(Post post);

    List<PostResponse> listPostToListPostResponse(List<Post> posts);
}
