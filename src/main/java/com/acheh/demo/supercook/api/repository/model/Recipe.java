package com.acheh.demo.supercook.api.repository.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class which represents a recipe.
 */
@Table(name = "recipes")
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "servings")
    private Integer servings;

    @ManyToMany
    @JoinTable(
            name = "recipe_categories",
            joinColumns = {@JoinColumn(name = "fk_recipe_id")},
            inverseJoinColumns = {@JoinColumn(name = "fk_category_id")}
    )
    private Set<Category> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Instruction> instructions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> ingredients = new LinkedHashSet<>();

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

    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public Set<Instruction> getInstructions() {
        return Collections.unmodifiableSet(instructions);
    }

    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
        instruction.setRecipe(this);
    }

    public Set<RecipeIngredient> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    public void addIngredient(RecipeIngredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
        ingredient.setRecipeId(this.id);
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void setInstructions(Set<Instruction> instructions) {
        this.instructions = instructions;
    }

    public void setIngredients(Set<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

