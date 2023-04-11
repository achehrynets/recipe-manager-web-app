package com.acheh.demo.supercook.api.rest.v1.dto.converter;

import com.acheh.demo.supercook.api.repository.model.RecipeIngredient;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeIngredientDto;
import org.modelmapper.AbstractConverter;

public class RecipeIngredientToRecipeIngredientDtoConverter extends AbstractConverter<RecipeIngredient, RecipeIngredientDto> {


    @Override
    protected RecipeIngredientDto convert(RecipeIngredient recipeIngredient) {
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
        recipeIngredientDto.setId(recipeIngredient.getId());
        recipeIngredientDto.setRecipeId(recipeIngredient.getRecipeId());
        recipeIngredientDto.setIngredientId(recipeIngredient.getIngredientId());
        recipeIngredientDto.setName(recipeIngredient.getIngredient().getName());
        recipeIngredientDto.setMeasurement(recipeIngredient.getMeasurement());
        return recipeIngredientDto;
    }
}
