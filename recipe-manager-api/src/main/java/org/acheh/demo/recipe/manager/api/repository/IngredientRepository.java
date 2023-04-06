package org.acheh.demo.recipe.manager.api.repository;

import org.acheh.demo.recipe.manager.api.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
