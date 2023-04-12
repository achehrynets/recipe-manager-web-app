package com.acheh.demo.supercook.api.service.impl;

import com.acheh.demo.supercook.api.repository.model.Category;
import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.repository.model.RecipeIngredient;
import com.acheh.demo.supercook.api.service.CategoryService;
import com.acheh.demo.supercook.api.service.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@Profile("test")
@Rollback
@Transactional
@SpringBootTest
class RecipeServiceImplTest {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryService categoryService;

    @Test
    void saveRecipe_allPropertiesAreCorrect_ShouldSaveAndPropertyShouldMatch() {
        Recipe recipeToSave = new Recipe();
        recipeToSave.setTitle("Test");
        recipeToSave.setDescription("Test description");
        recipeToSave.setServings(6);

        RecipeIngredient recipeIngredient1 = new RecipeIngredient();
        recipeIngredient1.setIngredientId(1);
        recipeIngredient1.setMeasurement("1 cup");
        recipeToSave.addIngredient(recipeIngredient1);

        RecipeIngredient recipeIngredient2 = new RecipeIngredient();
        recipeIngredient2.setIngredientId(2);
        recipeIngredient2.setMeasurement("2 cup");
        recipeToSave.addIngredient(recipeIngredient2);

        Instruction instruction1 = new Instruction();
        instruction1.setDescription("Instruction 1");
        instruction1.setStep(1);
        Instruction instruction2 = new Instruction();
        instruction2.setDescription("Instruction 2");
        instruction2.setStep(2);
        recipeToSave.addInstruction(instruction1);
        recipeToSave.addInstruction(instruction2);

        Category category = new Category();
        category.setId(5);
        recipeToSave.addCategory(category);

        Recipe savedRecipe = this.recipeService.save(recipeToSave);
        Assertions.assertNotNull(savedRecipe.getId(), "Recipe ID should not be null");
        Recipe recipe = this.recipeService.findById(savedRecipe.getId());
        Assertions.assertEquals(recipeToSave.getTitle(), recipe.getTitle(), "Recipe title should be the same");
        Assertions.assertEquals(recipeToSave.getCategories().size(), recipe.getCategories().size(), "Recipe categories should be the same");
        Assertions.assertEquals(recipeToSave.getInstructions().size(), recipe.getInstructions().size(), "Recipe instructions should be the same");
        Assertions.assertEquals(recipeToSave.getIngredients().size(), recipe.getIngredients().size(), "Recipe ingredients should be the same");
    }

    @Test
    void addRecipeToCategory_allPropertiesAreCorrect_shouldPass() {
        Recipe recipe = this.recipeService.findById(1);
        Assertions.assertNotNull(recipe, "Recipe should not be null");
        Category category = this.categoryService.findById(5);
        Assertions.assertNotNull(category, "Category should not be null");
        int categoryCountBeforeSave = recipe.getCategories().size();
        this.recipeService.addRecipeToCategory(recipe.getId(), category.getId());
        Recipe updatedRecipe = this.recipeService.findById(recipe.getId());
        Assertions.assertEquals(categoryCountBeforeSave + 1, updatedRecipe.getCategories().size(), "Category count should be increased by 1");
        Assertions.assertTrue(recipe.getCategories().contains(category), "Recipe should contain the category");
    }

    @Test
    void addRecipeFromCategory_allPropertiesAreCorrect_shouldPass() {
        Recipe recipe = this.recipeService.findById(1);
        Assertions.assertNotNull(recipe, "Recipe should not be null");
        Category category = this.categoryService.findById(2);
        Assertions.assertNotNull(category, "Category should not be null");
        int categoryCountBeforeRemove = recipe.getCategories().size();
        this.recipeService.removeRecipeFromCategory(recipe.getId(), category.getId());
        Recipe updatedRecipe = this.recipeService.findById(recipe.getId());
        Assertions.assertEquals(categoryCountBeforeRemove - 1, updatedRecipe.getCategories().size(), "Category count should be decreased by 1");
        Assertions.assertFalse(recipe.getCategories().contains(category), "Recipe should not contain the category");
    }

}