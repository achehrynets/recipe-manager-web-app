package com.acheh.demo.supercook.api.service.impl;

import com.acheh.demo.supercook.api.exception.BadRequestException;
import com.acheh.demo.supercook.api.exception.EntityNotFoundException;
import com.acheh.demo.supercook.api.repository.RecipeIngredientRepository;
import com.acheh.demo.supercook.api.repository.RecipeRepository;
import com.acheh.demo.supercook.api.repository.model.Category;
import com.acheh.demo.supercook.api.repository.model.Ingredient;
import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.repository.model.RecipeIngredient;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeFilterDto;
import com.acheh.demo.supercook.api.service.CategoryService;
import com.acheh.demo.supercook.api.service.IngredientService;
import com.acheh.demo.supercook.api.service.RecipeService;
import com.acheh.demo.supercook.api.util.search.specification.GenericSpecification;
import com.acheh.demo.supercook.api.util.search.specification.RecipeSpecification;
import com.cosium.spring.data.jpa.entity.graph.domain2.DynamicEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

/**
 * Service Interface for managing {@link com.acheh.demo.supercook.api.repository.model.Recipe}.
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private final ModelMapper mapper;
    private final RecipeRepository recipeRepository;

    private final CategoryService categoryService;

    private final IngredientService ingredientService;

    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeServiceImpl(ModelMapper mapper, RecipeRepository recipeRepository, CategoryService categoryService,
                             IngredientService ingredientService, RecipeIngredientRepository recipeIngredientRepository) {
        this.mapper = mapper;
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    public Recipe findById(Integer id) {
        Specification<Recipe> spec = GenericSpecification.fromSearchParams("id:" + id);
        EntityGraph entityGraph = DynamicEntityGraph.fetching()
                .addPath("categories")
                .addPath("instructions")
                .addPath("ingredients", "ingredient")
                .build();
        return this.recipeRepository.findOne(spec, entityGraph)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
    }

    @Transactional
    @Override
    public Recipe save(Recipe entity) {
        for (Category categoryToSave : entity.getCategories()) {
            Category category = this.categoryService.findById(categoryToSave.getId());
            categoryToSave.setName(category.getName());
            entity.addCategory(category);
        }
        for (RecipeIngredient recipeIngredient: entity.getIngredients()) {
            Ingredient ingredient = this.ingredientService.findById(recipeIngredient.getIngredientId());
            recipeIngredient.setIngredient(ingredient);
            entity.addIngredient(recipeIngredient);
        }
        for (Instruction instructionToSave : entity.getInstructions()) {
            entity.addInstruction(instructionToSave);
        }
        return this.recipeRepository.save(entity);
    }

    @Transactional
    @Override
    public Recipe update(Integer id, Recipe entity) {
        Recipe existing = this.findById(id);
        this.mapper.map(entity, existing);
        return this.recipeRepository.save(existing);
    }

    @Override
    public void deleteById(Integer id) {
        Recipe recipe = this.findById(id);
        this.recipeRepository.delete(recipe);
    }

    @Override
    public Page<Recipe> find(String search, Pageable pageable) {
        Specification<Recipe> spec = GenericSpecification.fromSearchParams(search, GenericSpecification::new);
        return this.recipeRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Recipe> find(@RequestBody RecipeFilterDto search, Pageable pageable) {
        Specification<Recipe> spec = null;
        if (search != null) {
            spec = GenericSpecification.fromSearchParams(search.getSearch(), GenericSpecification<Recipe>::new)
                    .and(RecipeSpecification.includeOrExcludeIngredients(search.getIncludeIngredients(), search.getExcludeIngredients()))
                    .and(RecipeSpecification.hasCategories(search.getCategoryIds()))
                    .and(RecipeSpecification.instructionsLike(search.getInstructionKeywords()));
        }
        return spec == null ? this.recipeRepository.findAll(pageable) : this.recipeRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public void removeRecipeFromCategory(Integer id, Integer categoryId) {
        Category category = this.categoryService.findById(categoryId);
        Recipe recipe = this.findById(id);
        if (!recipe.removeCategory(category)) {
            throw new EntityNotFoundException("Recipe is not assigned to " + category.getName());
        }
    }

    @Transactional
    @Override
    public Category addRecipeToCategory(Integer id, Integer categoryId) {
        Category category = this.categoryService.findById(categoryId);
        Recipe recipe = this.findById(id);
        if (!recipe.addCategory(category)) {
            throw new BadRequestException("Recipe is already in '" + category.getName() + "' category");
        }
        return category;
    }

    @Transactional
    @Override
    public RecipeIngredient addIngredientToRecipe(Integer recipeId, Integer ingredientId, RecipeIngredient recipeIngredient) {
        this.recipeIngredientRepository.findByRecipeIdAndIngredientId(recipeId, ingredientId)
                .ifPresent(ri -> { throw new BadRequestException("Ingredient already exists in recipe"); });
        Ingredient ingredient = this.ingredientService.findById(ingredientId);
        Recipe recipe = this.findById(recipeId);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setRecipe(recipe);
        recipe.addIngredient(recipeIngredient);
        return recipeIngredient;
    }

    @Transactional
    @Override
    public void removeIngredientFromRecipe(Integer recipeId, Integer ingredientId) {
        RecipeIngredient recipeIngredient = this.recipeIngredientRepository.findByRecipeIdAndIngredientId(recipeId, ingredientId)
                .orElseThrow(() -> new BadRequestException("Ingredient does not exist in recipe"));
        Recipe recipe = this.findById(recipeId);
        recipe.removeIngredient(recipeIngredient);
    }

    @Transactional
    @Override
    public RecipeIngredient updateIngredientInRecipe(Integer recipeId, Integer ingredientId, RecipeIngredient recipeIngredient) {
        RecipeIngredient existing = this.recipeIngredientRepository.findByRecipeIdAndIngredientId(recipeId, ingredientId)
                .orElseThrow(() -> new BadRequestException("Ingredient does not exist in recipe"));
        Ingredient ingredient = this.ingredientService.findById(recipeIngredient.getIngredientId());
        existing.setMeasurement(recipeIngredient.getMeasurement());
        existing.setIngredient(ingredient);
        return this.recipeIngredientRepository.save(existing);
    }
}
