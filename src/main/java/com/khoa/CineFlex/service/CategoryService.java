package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.mapper.CategoryMapper;
import com.khoa.CineFlex.model.Category;
import com.khoa.CineFlex.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.dtoToCategory(categoryDto);

        Category save = categoryRepository.save(category);

        return categoryMapper.categoryToDto(save);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.listCategoryToListDto(categoryRepository.findAll());
    }
}
