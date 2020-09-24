package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);

    void deleteByToken(String token);
}
