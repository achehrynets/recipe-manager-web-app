package com.acheh.demo.supercook.api.service.impl;

import com.acheh.demo.supercook.api.repository.model.Category;
import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.repository.model.RecipeIngredient;
import com.acheh.demo.supercook.api.service.CategoryService;
import com.acheh.demo.supercook.api.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

@Profile("test")
@SpringBootTest
@Transactional
class RecipeServiceImplTest {


    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testSomeMethod() {
        Recipe recipe = new Recipe();
        recipe.setTitle("Test");
        recipe.setDescription("Test description");
        recipe.setServings(6);

        RecipeIngredient recipeIngredient1 = new RecipeIngredient();
        recipeIngredient1.setIngredientId(1);
        recipeIngredient1.setMeasurement("1 cup");
        recipe.addIngredient(recipeIngredient1);

        RecipeIngredient recipeIngredient2 = new RecipeIngredient();
        recipeIngredient2.setIngredientId(2);
        recipeIngredient2.setMeasurement("2 cup");
        recipe.addIngredient(recipeIngredient2);

        Instruction instruction1 = new Instruction();
        instruction1.setDescription("Instruction 1");
        instruction1.setStep(1);
        Instruction instruction2 = new Instruction();
        instruction2.setDescription("Instruction 2");
        instruction2.setStep(2);
        recipe.addInstruction(instruction1);
        recipe.addInstruction(instruction2);

        Category category = new Category();
        category.setId(5);
        recipe.addCategory(category);

        this.recipeService.save(recipe);

        Recipe newRecipe = this.recipeService.findById(recipe.getId());
        System.out.println(newRecipe.getCategories().size());

        Page<Category> categories = this.categoryService.find(null, Pageable.unpaged());
        System.out.println(categories.getContent().size());

        this.recipeService.deleteById(recipe.getId());

        Page<Category> categories1 = this.categoryService.find(null, Pageable.unpaged());
        System.out.println(categories1.getContent().size());

        Page<Recipe> recipes = this.recipeService.find("", Pageable.unpaged());
        System.out.println(recipes.getContent().size());
    }

}