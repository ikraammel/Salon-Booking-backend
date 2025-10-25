package com.ikram.controller;

import com.ikram.dto.SalonDto;
import com.ikram.model.Category;
import com.ikram.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);
        Category savedCategory = categoryService.saveCategory(category,salonDto);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws Exception {
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);
        categoryService.deleteCategory(id, salonDto.getId());
        return ResponseEntity.ok("category deleted successfully");
    }

}
