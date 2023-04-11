package com.acheh.demo.supercook.api.rest.v1;

import com.acheh.demo.supercook.api.repository.model.RecipeIngredient;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeIngredientDto;
import com.acheh.demo.supercook.api.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipes/{recipeId}/ingredients")
public class RecipeIngredientRestController {

    private final ModelMapper mapper;
    private final RecipeService recipeService;

    public RecipeIngredientRestController(ModelMapper mapper, RecipeService recipeService) {
        this.mapper = mapper;
        this.recipeService = recipeService;
    }

    @PostMapping("/{ingredientId}")
    public ResponseEntity<RecipeIngredientDto> addRecipeIngredient(@PathVariable Integer recipeId,
                                                                   @PathVariable Integer ingredientId,
                                                                   @RequestBody RecipeIngredientDto recipeIngredientDto) {
        RecipeIngredient recipeIngredient = this.mapper.map(recipeIngredientDto, RecipeIngredient.class);
        RecipeIngredient addedRecipeIngredient = this.recipeService.addIngredientToRecipe(recipeId, ingredientId, recipeIngredient);
        RecipeIngredientDto ingredientDto = this.mapper.map(addedRecipeIngredient, RecipeIngredientDto.class);
        return ResponseEntity.ok(ingredientDto);
    }

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<?> deleteRecipeIngredient(@PathVariable Integer recipeId,
                                            @PathVariable Integer ingredientId) {
        this.recipeService.removeIngredientFromRecipe(recipeId, ingredientId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{ingredientId}")
    public ResponseEntity<RecipeIngredientDto> updateRecipeIngredient(@PathVariable Integer recipeId,
                                                                   @PathVariable Integer ingredientId,
                                                                   @RequestBody RecipeIngredientDto recipeIngredientDto) {
        RecipeIngredient recipeIngredient = this.mapper.map(recipeIngredientDto, RecipeIngredient.class);
        RecipeIngredient addedRecipeIngredient = this.recipeService.updateIngredientInRecipe(recipeId, ingredientId, recipeIngredient);
        RecipeIngredientDto ingredientDto = this.mapper.map(addedRecipeIngredient, RecipeIngredientDto.class);
        return ResponseEntity.ok(ingredientDto);
    }

}
