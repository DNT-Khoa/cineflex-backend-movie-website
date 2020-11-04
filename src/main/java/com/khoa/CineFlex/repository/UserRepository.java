package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.role = 'Admin'")
    User findAdminByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email LIKE %?1%")
    List<User> searchAdminByEmailKey(String key);

    @Query("SELECT u FROM User u WHERE u.role = 'Admin'")
    List<User> findAllAdmins();

    void deleteByEmail(String email);
}
