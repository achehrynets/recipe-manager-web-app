package org.acheh.demo.recipe.manager.api.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecipeIngredientMeasureKey implements Serializable {

    @Column(name = "recipe_id")
    private Integer recipeId;
    @Column(name = "ingredient_id")
    private Integer ingredientId;
    @Column(name = "measure_id")
    private Integer measureId;

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Integer measureId) {
        this.measureId = measureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientMeasureKey that = (RecipeIngredientMeasureKey) o;
        return Objects.equals(recipeId, that.recipeId) && Objects.equals(ingredientId, that.ingredientId) && Objects.equals(measureId, that.measureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientId, measureId);
    }
}
