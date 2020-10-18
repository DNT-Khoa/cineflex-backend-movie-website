package com.khoa.CineFlex.repository;

import com.khoa.CineFlex.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
    List<Category> findCategoryBySearchKey(String searchKey);
}
