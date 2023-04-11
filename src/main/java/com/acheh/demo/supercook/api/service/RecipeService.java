package com.acheh.demo.supercook.api.service;

import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService extends BaseService<Recipe, Integer> {

    Page<Recipe> find(RecipeFilterDto search, Pageable pageable);
}
