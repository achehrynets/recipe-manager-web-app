package com.acheh.demo.supercook.api.repository;

import com.acheh.demo.supercook.api.repository.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {

    Optional<RecipeIngredient> findByRecipeIdAndIngredientId(Integer recipeId, Integer ingredientId);

}
