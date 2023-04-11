package com.acheh.demo.supercook.api.rest.v1;

import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeDto;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeFilterDto;
import com.acheh.demo.supercook.api.rest.v1.dto.SimpleRecipeDto;
import com.acheh.demo.supercook.api.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeRestController {

    private final RecipeService recipeService;
    private final ModelMapper mapper;

    public RecipeRestController(RecipeService recipeService, ModelMapper mapper) {
        this.recipeService = recipeService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> findById(@PathVariable("id") Integer id) {
        Recipe recipe = this.recipeService.findById(id);
        RecipeDto recipeDto = this.mapper.map(recipe, RecipeDto.class);
        return ResponseEntity.ok(recipeDto);
    }

    @GetMapping
    public ResponseEntity<Page<SimpleRecipeDto>> find(@RequestBody(required = false) RecipeFilterDto filter,
                                                      Pageable pageable) {
        Page<Recipe> recipes = this.recipeService.find(filter, pageable);
        Page<SimpleRecipeDto> recipeDtos = recipes.map(recipe -> this.mapper.map(recipe, SimpleRecipeDto.class));
        return ResponseEntity.ok(recipeDtos);
    }

    @PostMapping
    public ResponseEntity<RecipeDto> create(@RequestBody RecipeDto recipeDto) {
        Recipe recipe = this.mapper.map(recipeDto, Recipe.class);
        Recipe savedRecipe = this.recipeService.save(recipe);
        RecipeDto createdRecipeDto = this.mapper.map(savedRecipe, RecipeDto.class);
        return ResponseEntity.created(URI.create("/api/v1/recipes/" + createdRecipeDto.getId()))
                .body(createdRecipeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> update(@PathVariable Integer id,
                                            @RequestBody SimpleRecipeDto updateRecipeDto) {
        Recipe updateRecipe = this.mapper.map(updateRecipeDto, Recipe.class);
        Recipe updatedRecipe = this.recipeService.update(id, updateRecipe);
        return ResponseEntity.ok(this.mapper.map(updatedRecipe, RecipeDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        this.recipeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
