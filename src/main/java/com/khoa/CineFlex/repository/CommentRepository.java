package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.path LIKE ?1%")
    void deleteAllCommentsOfUser(String pathOfUser);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.movieOrPostId = ?1 AND c.commentType = 'Movie'")
    void deleteAllCommentsByMovieId(Long movieId);

    @Query("SELECT c FROM Comment c WHERE c.email = ?1")
    List<Comment> getAllCommentsOfUser(String email);

    @Query("SELECT c FROM Comment c WHERE c.movieOrPostId = ?1 AND c.commentType = 'Movie' ORDER BY c.path")
    List<Comment> getAllCommentsByMovieId(Long movieId);

    @Query("SELECT c FROM Comment c WHERE c.movieOrPostId = ?1 AND c.commentType = 'News' ORDER BY c.path")
    List<Comment> getAllCommentsByPostId(Long postId);
}
