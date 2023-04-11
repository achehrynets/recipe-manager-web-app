package com.acheh.demo.supercook.api.repository;

import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Recipe entity.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>,
        EntityGraphJpaSpecificationExecutor<Recipe> {

}
