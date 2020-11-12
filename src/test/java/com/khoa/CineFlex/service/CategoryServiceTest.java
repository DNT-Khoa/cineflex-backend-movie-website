package com.khoa.CineFlex.service;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.mapper.CategoryMapper;
import com.khoa.CineFlex.model.Category;
import com.khoa.CineFlex.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @BeforeEach
    public void setup() {
        this.categoryService = new CategoryService(categoryRepository, categoryMapper);
    }

    @Test
    void getCategoryById() {
        Category category = new Category((long)1, "Category 1", new ArrayList<>(), new ArrayList<>());
        CategoryDto categoryDto = new CategoryDto((long)1, "Category 1");

        Mockito.when(categoryRepository.findById((long)1)).thenReturn(java.util.Optional.of(category));
        Mockito.when(categoryMapper.categoryToDto(category)).thenReturn(categoryDto);

        CategoryDto actualCategoryDto = categoryService.getCategoryById((long)1);

        Assertions.assertThat(actualCategoryDto).isEqualTo(categoryDto);
        Assertions.assertThat(actualCategoryDto.getName()).isEqualTo(categoryDto.getName());
    }

    @Test
    void getCategoryByIdThrowException() {
        Category category = new Category((long)1, "Category 1", new ArrayList<>(), new ArrayList<>());
        CategoryDto categoryDto = new CategoryDto((long)1, "Category 1");

        Mockito.when(categoryRepository.findById((long)1)).thenThrow(new CineFlexException("Cannot find category with id: 1"));


        Assertions.assertThatThrownBy(() -> {
            CategoryDto actualCategoryDto = categoryService.getCategoryById((long)1);
        }).isInstanceOf(CineFlexException.class).hasMessage("Cannot find category with id: 1");
    }

    @Test
    void createCategory() {
        CategoryDto categoryDto = new CategoryDto((long)1, "Category 1");

        Mockito.when(categoryRepository.findByName("Category 1")).thenReturn(null);

        categoryService.createCategory(categoryDto);

        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));
    }

    @Test
    void createCategoryThrowException() {
        CategoryDto categoryDto = new CategoryDto((long)1, "Category 1");
        Category category = new Category((long)1, "Category 1", new ArrayList<>(), new ArrayList<>());

        Mockito.when(categoryRepository.findByName("Category 1")).thenReturn(category);

        Assertions.assertThatThrownBy(() -> {
            categoryService.createCategory(categoryDto);
        }).isInstanceOf(CineFlexException.class).hasMessage("Category name already exists!");
    }

    @Test
    void getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category((long)1, "Category 1", new ArrayList<>(), new ArrayList<>()));

        List<CategoryDto> categoryDtos = new ArrayList<>();
        categoryDtos.add(new CategoryDto((long)1, "Category 1"));

        Mockito.when(categoryRepository.findAll()).thenReturn(categoryList);
        Mockito.when(categoryMapper.listCategoryToListDto(categoryList)).thenReturn(categoryDtos);

        List<CategoryDto> actualCategoryDtos = categoryService.getAllCategories();

        Assertions.assertThat(actualCategoryDtos.size()).isEqualTo(categoryDtos.size());
        Assertions.assertThat(actualCategoryDtos.get(0)).isEqualTo(categoryDtos.get(0));
    }


    @Test
    void deleteCategoryById() {
        categoryService.deleteCategoryById((long)1);

        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById((long)1);
    }

    @Test
    void findCategoryBySearchKey() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category((long)1, "Category 1", new ArrayList<>(), new ArrayList<>()));

        List<CategoryDto> categoryDtos = new ArrayList<>();
        categoryDtos.add(new CategoryDto((long)1, "Category 1"));

        Mockito.when(categoryRepository.findCategoryBySearchKey("key")).thenReturn(categoryList);
        Mockito.when(categoryMapper.listCategoryToListDto(categoryList)).thenReturn(categoryDtos);

        List<CategoryDto> actualCategoryDtos = categoryService.findCategoryBySearchKey("key");

        Assertions.assertThat(actualCategoryDtos.size()).isEqualTo(categoryDtos.size());
        Assertions.assertThat(actualCategoryDtos.get(0)).isEqualTo(categoryDtos.get(0));
    }
}
