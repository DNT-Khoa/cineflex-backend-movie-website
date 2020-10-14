package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.service.CategoryService;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something wrong happend!");
        }
    }

}
