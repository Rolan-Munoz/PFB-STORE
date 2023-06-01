package com.kreitek.store.application.service.impl;

import com.kreitek.store.application.dto.CategoryDTO;
import com.kreitek.store.application.mapper.CategoryMapper;
import com.kreitek.store.application.service.CategoryService;
import com.kreitek.store.domain.entity.Category;
import com.kreitek.store.domain.persistence.CategoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryPersistence categoryPersistence;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryPersistence categoryPersistence, CategoryMapper categoryMapper) {
        this.categoryPersistence = categoryPersistence;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = this.categoryPersistence.getAllCategories();
        return categoryMapper.toDto(categories);
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long categoryId) {
        return this.categoryPersistence.getCategoryById(categoryId).map(categoryMapper::toDto);
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = this.categoryPersistence.saveCategory(this.categoryMapper.toEntity(categoryDTO));
        return this.categoryMapper.toDto(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        this.categoryPersistence.deleteCategory(categoryId);
    }

    @Override
    public List<CategoryDTO> getAllCategoriesMatchPartialName(String partialName) {
        List<Category> categories = this.categoryPersistence.getCategoriesByName(partialName);
        return  categoryMapper.toDto(categories);
    }
}
