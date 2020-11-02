package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.exception.CineFlexException;
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

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CineFlexException("Cannot find category with id: " + categoryId));
        return this.categoryMapper.categoryToDto(category);
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category check = categoryRepository.findByName(categoryDto.getName());
        if (check != null) {
            throw new CineFlexException("Category name already exits!");
        }

        Category category = new Category();
        category.setName(categoryDto.getName());

        Category save = categoryRepository.save(category);

        return categoryMapper.categoryToDto(save);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryMapper.listCategoryToListDto(categoryRepository.findAll());
    }

    @Transactional
    public boolean updateCategoryById(Long id, CategoryDto categoryDto) {
        Category check = categoryRepository.findById(id).orElseThrow(() -> new CineFlexException("Cannot find category with id " + id));
        if (check.getName().equals(categoryDto.getName())) {
            return false;
        }

        Category categoryToBeUpdated = categoryMapper.dtoToCategory(categoryDto);

        this.categoryRepository.save(categoryToBeUpdated);

        return true;
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Transactional
    public List<CategoryDto> findCategoryBySearchKey(String searchKey) {
         return this.categoryMapper.listCategoryToListDto(this.categoryRepository.findCategoryBySearchKey(searchKey));
    }
}
