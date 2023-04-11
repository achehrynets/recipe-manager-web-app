package com.acheh.demo.supercook.api.service.impl;

import com.acheh.demo.supercook.api.exception.EntityNotFoundException;
import com.acheh.demo.supercook.api.repository.CategoryRepository;
import com.acheh.demo.supercook.api.repository.model.Category;
import com.acheh.demo.supercook.api.service.CategoryService;
import com.acheh.demo.supercook.api.util.search.specification.GenericSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Service Interface for managing {@link com.acheh.demo.supercook.api.repository.model.Category}.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findById(Integer id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with %d not found", id)));
    }

    @Transactional
    @Override
    public Category save(Category entity) {
        return this.categoryRepository.save(entity);
    }

    @Transactional
    @Override
    public Category update(Integer id, Category entity) {
        Category category = this.findById(id);
        category.setName(entity.getName());
        return this.categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Category category = this.findById(id);
        this.categoryRepository.delete(category);
    }

    @Override
    public Page<Category> find(String search, Pageable pageable) {
        Specification<Category> spec = GenericSpecification.fromSearchParams(search, GenericSpecification<Category>::new);
        return this.categoryRepository.findAll(spec, pageable);
    }

}
