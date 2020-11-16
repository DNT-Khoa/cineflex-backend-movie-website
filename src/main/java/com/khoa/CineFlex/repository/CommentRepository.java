package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("DELETE FROM Comment c WHERE c.path LIKE ?1%")
    void deleteAllCommentsOfUser(String pathOfUser);

    @Query("SELECT c FROM Comment c WHERE c.email = ?1 AND c.parentCommentId IS NULL ")
    List<Comment> getAllRootCommentsOfUser(String email);

    @Query("SELECT c FROM Comment c WHERE c.parentCommentId = ?1 AND c.commentType = 'Movie'")
    List<Comment> getAllCommentsByTmdbId(Long tmdbId);

    @Query("SELECT c FROM Comment c WHERE c.parentCommentId = ?1 AND c.commentType = 'News'")
    List<Comment> getAllCommentsByPostId(Long postId);
}
