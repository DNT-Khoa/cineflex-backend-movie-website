package com.khoa.CineFlex.mapper;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper{
    CategoryDto categoryToDto(Category category);

    Category dtoToCategory(CategoryDto categoryDto);

    List<CategoryDto> listCategoryToListDto(List<Category> categories);

}
