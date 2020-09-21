package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {
}
