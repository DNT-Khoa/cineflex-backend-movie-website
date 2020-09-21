package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
