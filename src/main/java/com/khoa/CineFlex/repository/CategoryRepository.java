package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.model.Category;
import com.khoa.CineFlex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
