package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
