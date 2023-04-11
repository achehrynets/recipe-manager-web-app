package com.acheh.demo.supercook.api.rest.v1.dto.converter;

import com.acheh.demo.supercook.api.repository.model.Category;
import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.repository.model.RecipeIngredient;
import com.acheh.demo.supercook.api.rest.v1.dto.CategoryDto;
import com.acheh.demo.supercook.api.rest.v1.dto.InstructionDto;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeDto;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeIngredientDto;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

public class RecipeDtoToRecipeConverter extends AbstractConverter<RecipeDto, Recipe> {

    private final ModelMapper mapper;

    public RecipeDtoToRecipeConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected Recipe convert(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setTitle(recipeDto.getTitle());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setServings(recipeDto.getServings());
        if (CollectionUtils.isNotEmpty(recipeDto.getCategories())) {
            for (CategoryDto category: recipeDto.getCategories()) {
                recipe.addCategory(this.mapper.map(category, Category.class));
            }
        }
        if (CollectionUtils.isNotEmpty(recipeDto.getInstructions())) {
            for (InstructionDto instructionDto : recipeDto.getInstructions()) {
                recipe.addInstruction(this.mapper.map(instructionDto, Instruction.class));
            }
        }

        if (CollectionUtils.isNotEmpty(recipeDto.getIngredients())) {
            for (RecipeIngredientDto recipeIngredientDto : recipeDto.getIngredients()) {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setId(recipeIngredientDto.getId());
                recipeIngredient.setIngredientId(recipeIngredientDto.getIngredientId());
                recipeIngredient.setMeasurement(recipeIngredientDto.getMeasurement());
                recipe.addIngredient(recipeIngredient);
            }
        }

        return recipe;
    }

}
