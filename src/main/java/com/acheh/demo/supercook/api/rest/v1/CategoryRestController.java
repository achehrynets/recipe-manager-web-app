package com.acheh.demo.supercook.api.rest.v1;

import com.acheh.demo.supercook.api.constant.OpenApiParamDescriptionConstants;
import com.acheh.demo.supercook.api.repository.model.Category;
import com.acheh.demo.supercook.api.rest.v1.dto.CategoryDto;
import com.acheh.demo.supercook.api.service.CategoryService;
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
@RequestMapping("/api/v1/categories")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final ModelMapper mapper;

    public CategoryRestController(CategoryService categoryService, ModelMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @Operation(summary = "Find a category by id")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable("id") Integer id) {
        Category category = this.categoryService.findById(id);
        CategoryDto categoryDto = this.mapper.map(category, CategoryDto.class);
        return ResponseEntity.ok(categoryDto);
    }

    @Operation(summary = "Find categories by by search criteria")
    @GetMapping
    public ResponseEntity<Page<CategoryDto>> find(@Parameter(description = OpenApiParamDescriptionConstants.SEARCH_PARAM_DESCRIPTION)
                                                  @RequestParam(required = false) String search,
                                                  Pageable pageable) {
        Page<Category> categories = this.categoryService.find(search, pageable);
        Page<CategoryDto> categoryDtos = categories.map(category -> this.mapper.map(category, CategoryDto.class));
        return ResponseEntity.ok(categoryDtos);
    }

    @Operation(summary = "Create a new category")
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto) {
        Category category = this.mapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryService.save(category);
        CategoryDto createdCategoryDto = this.mapper.map(savedCategory, CategoryDto.class);
        return ResponseEntity.created(URI.create("/api/v1/categories/" + createdCategoryDto.getId()))
                .body(createdCategoryDto);
    }

    @Operation(summary = "Update a category")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Integer id,
                                              @RequestBody CategoryDto updateCategoryDto) {
        Category updateCategory = this.mapper.map(updateCategoryDto, Category.class);
        Category updatedCategory = this.categoryService.update(id, updateCategory);
        return ResponseEntity.ok(this.mapper.map(updatedCategory, CategoryDto.class));
    }

    @Operation(summary = "Delete a category")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        this.categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
