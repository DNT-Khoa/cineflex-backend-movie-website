package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.UserMovieRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMovieRatingRepository extends JpaRepository<UserMovieRating, Long> {
    void deleteByUserEmail(String userEmail);
    void deleteByMovieId(Long movieId);
    
}
