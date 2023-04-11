package com.acheh.demo.supercook.api.rest.v1.dto;

import java.util.List;

public class RecipeFilterDto {

    private String search;
    private List<Integer> includeIngredients;
    private List<Integer> excludeIngredients;
    private List<Integer> categoryIds;

    private List<String> instructionKeywords;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Integer> getIncludeIngredients() {
        return includeIngredients;
    }

    public void setIncludeIngredients(List<Integer> includeIngredients) {
        this.includeIngredients = includeIngredients;
    }

    public List<Integer> getExcludeIngredients() {
        return excludeIngredients;
    }

    public void setExcludeIngredients(List<Integer> excludeIngredients) {
        this.excludeIngredients = excludeIngredients;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<String> getInstructionKeywords() {
        return instructionKeywords;
    }

    public void setInstructionKeywords(List<String> instructionKeywords) {
        this.instructionKeywords = instructionKeywords;
    }
}
