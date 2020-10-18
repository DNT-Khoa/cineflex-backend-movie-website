package com.khoa.CineFlex.controller;

import com.khoa.CineFlex.DTO.CategoryDto;
import com.khoa.CineFlex.exception.CineFlexException;
import com.khoa.CineFlex.service.CategoryService;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
        } catch (CineFlexException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/admin/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something wrong happend!");
        }
    }

    @PutMapping(path = "/admin/categories/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        if (this.categoryService.updateCategoryById(categoryId, categoryDto)) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exits");
    }

    @DeleteMapping(path = "/admin/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        this.categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
