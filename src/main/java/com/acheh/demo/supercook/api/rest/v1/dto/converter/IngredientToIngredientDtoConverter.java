package com.acheh.demo.supercook.api.rest.v1.dto.converter;

import com.acheh.demo.supercook.api.repository.model.Ingredient;
import com.acheh.demo.supercook.api.rest.v1.dto.IngredientDto;
import org.modelmapper.AbstractConverter;

public class IngredientToIngredientDtoConverter extends AbstractConverter<Ingredient, IngredientDto> {

    @Override
    public IngredientDto convert(Ingredient source) {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(source.getId());
        ingredientDto.setName(source.getName());
        return ingredientDto;
    }

}
