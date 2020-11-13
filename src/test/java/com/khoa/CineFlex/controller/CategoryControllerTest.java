package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.config.JwtAuthenticationEntryPoint;
import com.khoa.CineFlex.repository.UserRepository;
import com.khoa.CineFlex.service.CategoryService;
import com.khoa.CineFlex.service.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    public void getCategoryById() throws Exception {
        CategoryDto categoryDto = new CategoryDto(1L, "Category 1");
        Mockito.when(this.categoryService.getCategoryById(1L)).thenReturn(categoryDto);

        this.mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Category 1"));

    }

    @Test
    public void getAllCategories() throws Exception {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categoryDtos.add(new CategoryDto(1L, "Category 1"));
        categoryDtos.add(new CategoryDto(2L, "Category 2"));

        Mockito.when(this.categoryService.getAllCategories()).thenReturn(categoryDtos);

        this.mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category 2"));

    }
}
