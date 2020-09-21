package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.SeatAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatAllocationRepository extends JpaRepository<SeatAllocation, Long> {
}
