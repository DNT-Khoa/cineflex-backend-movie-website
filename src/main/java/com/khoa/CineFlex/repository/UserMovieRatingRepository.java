package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.UserMovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating, Long> {
    void deleteByUserEmail(String userEmail);
    void deleteByMovieId(Long movieId);

    UserMovieRating findByUserIdAndMovieId(Long userId, Long movieId);

    void deleteByUserIdAndMovieId(Long userId, Long movieId);
}
