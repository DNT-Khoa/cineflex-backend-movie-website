package com.khoa.CineFlex.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {
    private String content;
    private Long parentCommentId;
    private Long movieOrPostId;
    private String commentType;

}
