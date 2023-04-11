package com.acheh.demo.supercook.api.rest.v1.dto;

import java.util.List;

public class RecipeDto {

    private Integer id;
    private String title;
    private String description;
    private Integer servings;

    private List<CategoryDto> categories;

    private List<InstructionDto> instructions;

    private List<RecipeIngredientDto> ingredients;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public List<InstructionDto> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<InstructionDto> instructions) {
        this.instructions = instructions;
    }

    public List<RecipeIngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredientDto> ingredients) {
        this.ingredients = ingredients;
    }
}
