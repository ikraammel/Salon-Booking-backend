package com.ikram.service;

import com.ikram.dto.SalonDto;
import com.ikram.model.Category;

import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDto salonDto);
    Set<Category> getAllCategoriesBySalon(Long salonId);
    Category getCategoryById(Long id) throws Exception;
    void deleteCategory(Long id,Long salonId) throws Exception;


}
