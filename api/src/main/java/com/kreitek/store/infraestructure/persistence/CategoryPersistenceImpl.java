package com.kreitek.store.infraestructure.persistence;


import com.kreitek.store.domain.entity.Category;
import com.kreitek.store.domain.persistence.CategoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryPersistenceImpl implements CategoryPersistence {

    private final CategoryRepository categoryRepository;


    @Autowired   // con esto hace similar a @Repository
    public CategoryPersistenceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return this.categoryRepository.findById(categoryId);
    }

    @Override
    public Category saveCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        this.categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<Category> getCategoriesByName(String partialName) {
        return this.categoryRepository.findByNameContainsIgnoreCase(partialName);

    }
}
