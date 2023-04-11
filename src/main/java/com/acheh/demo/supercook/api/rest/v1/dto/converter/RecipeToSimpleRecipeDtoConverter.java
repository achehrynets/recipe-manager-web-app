package com.acheh.demo.supercook.api.rest.v1.dto.converter;

import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.rest.v1.dto.SimpleRecipeDto;
import org.modelmapper.AbstractConverter;

public class RecipeToSimpleRecipeDtoConverter extends AbstractConverter<Recipe, SimpleRecipeDto> {

    @Override
    protected SimpleRecipeDto convert(Recipe source) {
        SimpleRecipeDto recipeDto = new SimpleRecipeDto();
        recipeDto.setId(source.getId());
        recipeDto.setTitle(source.getTitle());
        recipeDto.setDescription(source.getDescription());
        recipeDto.setServings(source.getServings());
        return recipeDto;
    }

}
