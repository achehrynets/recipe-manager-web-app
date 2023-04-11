package com.acheh.demo.supercook.api.rest.v1;

import com.acheh.demo.supercook.api.repository.model.Category;
import com.acheh.demo.supercook.api.rest.v1.dto.CategoryDto;
import com.acheh.demo.supercook.api.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipes/{recipeId}/categories")
public class RecipeCategoryRestController {

    private final ModelMapper mapper;
    private final RecipeService recipeService;

    public RecipeCategoryRestController(ModelMapper mapper, RecipeService recipeService) {
        this.mapper = mapper;
        this.recipeService = recipeService;
    }

    @PostMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> addCategory(@PathVariable Integer recipeId,
                                         @PathVariable Integer categoryId) {
        Category addedCategory = this.recipeService.addRecipeToCategory(recipeId, categoryId);
        CategoryDto categoryDto = this.mapper.map(addedCategory, CategoryDto.class);
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer recipeId,
                                         @PathVariable Integer categoryId) {
        this.recipeService.removeRecipeFromCategory(recipeId, categoryId);
        return ResponseEntity.ok().build();
    }

}
