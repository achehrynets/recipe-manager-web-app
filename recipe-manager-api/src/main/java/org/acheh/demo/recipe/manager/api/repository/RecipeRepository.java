package org.acheh.demo.recipe.manager.api.repository;

import org.acheh.demo.recipe.manager.api.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
