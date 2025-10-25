package com.ikram.service;

import com.ikram.dto.SalonDto;
import com.ikram.model.Category;
import com.ikram.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ICategoryService implements CategoryService{

    private final CategoryRepository categoryRepository;
    @Override
    public Category saveCategory(Category category, SalonDto salonDto) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDto.getId());

        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long salonId) {
        return categoryRepository.findBySalonId(salonId);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            throw new Exception("category not exist with id" +id);
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id,Long salonId) throws Exception {
        Category category = getCategoryById(id);
        if (!category.getSalonId().equals(salonId)){
            throw new Exception("you don't have permission to delete this category");
        }
        categoryRepository.deleteById(id);
    }
}
