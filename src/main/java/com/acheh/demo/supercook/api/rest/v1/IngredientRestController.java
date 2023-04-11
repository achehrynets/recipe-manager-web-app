package com.acheh.demo.supercook.api.rest.v1;

import com.acheh.demo.supercook.api.constant.OpenApiParamDescriptionConstants;
import com.acheh.demo.supercook.api.repository.model.Ingredient;
import com.acheh.demo.supercook.api.rest.v1.dto.IngredientDto;
import com.acheh.demo.supercook.api.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientRestController {

    private final ModelMapper mapper;
    private final IngredientService ingredientService;

    public IngredientRestController(ModelMapper mapper, IngredientService ingredientService) {
        this.mapper = mapper;
        this.ingredientService = ingredientService;
    }

    @Operation(summary = "Find an ingredient by id")
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> findById(@PathVariable("id") Integer id) {
        Ingredient ingredient = this.ingredientService.findById(id);
        IngredientDto ingredientDto = this.mapper.map(ingredient, IngredientDto.class);
        return ResponseEntity.ok(ingredientDto);
    }

    @Operation(summary = "Find ingredients by search criteria")
    @GetMapping
    public ResponseEntity<Page<IngredientDto>> find(@RequestParam(required = false) String search,
                                                    @Parameter(description = OpenApiParamDescriptionConstants.SEARCH_PARAM_DESCRIPTION)
                                                    Pageable pageable) {
        Page<Ingredient> categories = this.ingredientService.find(search, pageable);
        Page<IngredientDto> ingredientDto = categories.map(ingredient -> this.mapper.map(ingredient, IngredientDto.class));
        return ResponseEntity.ok(ingredientDto);
    }

    @Operation(summary = "Create new ingredient")
    @PostMapping
    public ResponseEntity<IngredientDto> create(@RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = this.mapper.map(ingredientDto, Ingredient.class);
        Ingredient savedIngredient = this.ingredientService.save(ingredient);
        IngredientDto createdIngredientDto = this.mapper.map(savedIngredient, IngredientDto.class);
        return ResponseEntity.created(URI.create("/api/v1/ingredients/" + createdIngredientDto.getId()))
                .body(createdIngredientDto);
    }

    @Operation(summary = "Update an ingredient")
    @PutMapping("/{id}")
    public ResponseEntity<IngredientDto> update(@PathVariable Integer id,
                                                @RequestBody IngredientDto updateIngredientDto) {
        Ingredient updateIngredient = this.mapper.map(updateIngredientDto, Ingredient.class);
        Ingredient updatedIngredient = this.ingredientService.update(id, updateIngredient);
        return ResponseEntity.ok(this.mapper.map(updatedIngredient, IngredientDto.class));
    }

    @Operation(summary = "Delete an ingredient")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        this.ingredientService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
