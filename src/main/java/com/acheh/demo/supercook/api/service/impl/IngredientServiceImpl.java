package com.acheh.demo.supercook.api.service.impl;

import com.acheh.demo.supercook.api.exception.EntityNotFoundException;
import com.acheh.demo.supercook.api.repository.IngredientRepository;
import com.acheh.demo.supercook.api.repository.model.Ingredient;
import com.acheh.demo.supercook.api.service.IngredientService;
import com.acheh.demo.supercook.api.util.search.specification.GenericSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient findById(Integer id) {
        return this.ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ingredient with %d not found", id)));
    }

    @Transactional
    @Override
    public Ingredient save(Ingredient entity) {
        return this.ingredientRepository.save(entity);
    }

    @Transactional
    @Override
    public Ingredient update(Integer id, Ingredient entity) {
        Ingredient ingredient = this.findById(id);
        ingredient.setName(entity.getName());
        return this.ingredientRepository.save(ingredient);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        Ingredient ingredient = this.findById(id);
        this.ingredientRepository.delete(ingredient);
    }

    @Override
    public Page<Ingredient> find(String search, Pageable pageable) {
        Specification<Ingredient> spec = GenericSpecification.fromSearchParams(search, GenericSpecification<Ingredient>::new);
        return this.ingredientRepository.findAll(spec, pageable);
    }
}
