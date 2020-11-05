package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.AdminInvitationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminInvitationTokenRepository extends JpaRepository<AdminInvitationToken, Long> {
    AdminInvitationToken findByToken(String token);

    void deleteAllByEmail(String email);
}
