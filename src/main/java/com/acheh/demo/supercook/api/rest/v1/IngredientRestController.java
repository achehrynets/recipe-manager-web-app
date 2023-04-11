package com.acheh.demo.supercook.api.rest.v1;

import com.acheh.demo.supercook.api.repository.model.Ingredient;
import com.acheh.demo.supercook.api.rest.v1.dto.IngredientDto;
import com.acheh.demo.supercook.api.service.IngredientService;
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

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> findById(@PathVariable("id") Integer id) {
        Ingredient ingredient = this.ingredientService.findById(id);
        IngredientDto ingredientDto = this.mapper.map(ingredient, IngredientDto.class);
        return ResponseEntity.ok(ingredientDto);
    }

    @GetMapping
    public ResponseEntity<Page<IngredientDto>> find(@RequestParam(required = false) String search,
                                                  Pageable pageable) {
        Page<Ingredient> categories = this.ingredientService.find(search, pageable);
        Page<IngredientDto> ingredientDto = categories.map(ingredient -> this.mapper.map(ingredient, IngredientDto.class));
        return ResponseEntity.ok(ingredientDto);
    }

    @PostMapping
    public ResponseEntity<IngredientDto> create(@RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = this.mapper.map(ingredientDto, Ingredient.class);
        Ingredient savedIngredient = this.ingredientService.save(ingredient);
        IngredientDto createdIngredientDto = this.mapper.map(savedIngredient, IngredientDto.class);
        return ResponseEntity.created(URI.create("/api/v1/ingredients/" + createdIngredientDto.getId()))
                .body(createdIngredientDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientDto> update(@PathVariable Integer id,
                                              @RequestBody IngredientDto updateIngredientDto) {
        Ingredient updateIngredient = this.mapper.map(updateIngredientDto, Ingredient.class);
        Ingredient updatedIngredient = this.ingredientService.update(id, updateIngredient);
        return ResponseEntity.ok(this.mapper.map(updatedIngredient, IngredientDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        this.ingredientService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
