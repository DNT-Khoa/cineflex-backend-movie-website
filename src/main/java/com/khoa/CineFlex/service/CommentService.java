package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.CommentRequest;
import com.khoa.CineFlex.DTO.CommentResponse;
import com.khoa.CineFlex.DTO.UserDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.UserMapper;
import com.khoa.CineFlex.model.Comment;
import com.khoa.CineFlex.model.User;
import com.khoa.CineFlex.repository.CommentRepository;
import com.khoa.CineFlex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public CommentResponse addNewComment(CommentRequest commentRequest, String email) {
        Comment comment = new Comment();

        // We save the comment first so that we can get the auto generated id and add it to the path
        comment = this.commentRepository.save(comment);

        comment.setContent(commentRequest.getContent());
        comment.setCommentDate(Instant.now());
        comment.setCommentType(commentRequest.getCommentType());
        comment.setMovieOrPostId(comment.getMovieOrPostId());
        comment.setParentCommentId(comment.getParentCommentId());
        comment.setLikedByUsers(new ArrayList<>());
        comment.setEmail(email);

        String generatedPathForComment = this.generatePathForComment(commentRequest.getParentCommentId(), comment.getId());
        comment.setPath(generatedPathForComment);

        // Save the second time
        this.commentRepository.save(comment);

        return new CommentResponse(comment.getId(), comment.getContent(), comment.getCommentDate(), comment.getPath(), email, new ArrayList<>());
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllCommentsByTmdbId(Long tmdbId) {
        List<Comment> comments = this.commentRepository.getAllCommentsByTmdbId(tmdbId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setContent(comment.getContent());
            commentResponse.setCommentDate(comment.getCommentDate());
            commentResponse.setPath(comment.getPath());
            commentResponse.setEmail(comment.getEmail());
            commentResponse.setLikedByUsers(this.userMapper.listUsersToListDtos(comment.getLikedByUsers()));
        }

        return commentResponses;
    }

    @Transactional
    public void likeComment(Long commentId, String email) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new CineFlexException("Cannot find comment with id: " + commentId));

        User user = this.userRepository.findByEmail(email);
        user.getLikedComments().add(comment);
        comment.getLikedByUsers().add(user);

        this.commentRepository.save(comment);
    }

    @Transactional
    public void unlikeComment(Long commentId, String email) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new CineFlexException("Cannot find comment with id: " + commentId));

        User user = this.userRepository.findByEmail(email);
        user.getLikedComments().remove(comment);
        comment.getLikedByUsers().remove(user);

        this.commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = this.commentRepository.getAllCommentsByPostId(postId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setId(comment.getId());
            commentResponse.setContent(comment.getContent());
            commentResponse.setCommentDate(comment.getCommentDate());
            commentResponse.setPath(comment.getPath());
            commentResponse.setEmail(comment.getEmail());
            commentResponse.setLikedByUsers(this.userMapper.listUsersToListDtos(comment.getLikedByUsers()));
        }

        return commentResponses;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new CineFlexException("Cannot find comment with id: " + commentId));

        for (User user : comment.getLikedByUsers()) {
            user.getLikedComments().remove(comment);
        }

        this.commentRepository.deleteAllCommentsOfUser(comment.getPath());
    }

    private String generatePathForComment(Long commentParentId, Long commentId) {
        String prefix;
        // If the comment is the root node
        if (commentParentId == null) {
            prefix = "";
        } else {
            Comment parentComment = this.commentRepository.findById(commentParentId).orElseThrow(() -> new CineFlexException("Cannot find comment with id: " + commentParentId));
            prefix = parentComment.getPath();
        }

        return prefix + String.format("%03d", commentId);
    }
}
