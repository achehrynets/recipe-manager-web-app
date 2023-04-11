package com.acheh.demo.supercook.api.rest.v1.dto.converter;

import com.acheh.demo.supercook.api.repository.model.Category;
import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.repository.model.RecipeIngredient;
import com.acheh.demo.supercook.api.rest.v1.dto.CategoryDto;
import com.acheh.demo.supercook.api.rest.v1.dto.InstructionDto;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeDto;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeIngredientDto;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class RecipeToRecipeDtoConverter extends AbstractConverter<Recipe, RecipeDto> {

    private final ModelMapper modelMapper;

    public RecipeToRecipeDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    protected RecipeDto convert(Recipe source) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(source.getId());
        recipeDto.setTitle(source.getTitle());
        recipeDto.setDescription(source.getDescription());
        recipeDto.setServings(source.getServings());
        List<CategoryDto> categories = new ArrayList<>();
        for (Category category : source.getCategories()) {
            categories.add(this.modelMapper.map(category, CategoryDto.class));
        }
        recipeDto.setCategories(categories);
        List<InstructionDto> instructions = new ArrayList<>();
        for (Instruction instruction : source.getInstructions()) {
            instructions.add(this.modelMapper.map(instruction, InstructionDto.class));
        }
        recipeDto.setInstructions(instructions);
        List<RecipeIngredientDto> ingredients = new ArrayList<>();
        for (RecipeIngredient ingredient : source.getIngredients()) {
            ingredients.add(this.modelMapper.map(ingredient, RecipeIngredientDto.class));
        }
        recipeDto.setIngredients(ingredients);
        return recipeDto;
    }

}
