package com.acheh.demo.supercook.api.rest.v1;

import com.acheh.demo.supercook.api.rest.v1.dto.CategoryDto;
import com.acheh.demo.supercook.api.rest.v1.dto.InstructionDto;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeDto;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeFilterDto;
import com.acheh.demo.supercook.api.rest.v1.dto.RecipeIngredientDto;
import com.acheh.demo.supercook.api.rest.v1.dto.SimpleRecipeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@Profile("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecipeRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private RecipeRestController recipeRestController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(recipeRestController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void findById_recipeWithIdOneExists_shouldReturnFullRecipe() throws Exception {
        Integer recipeId = 1;
        this.mockMvc.perform(get("/api/v1/recipes/" + recipeId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(recipeId))
                .andExpect(jsonPath("$.title").value("Bolognese Stuffed Bell Peppers"));
    }

    @Test
    void findById_recipeWithGivenIdDoesNotExist_shouldReturnNotFoundStatusCode() throws Exception {
        this.mockMvc.perform(get("/api/v1/recipes/" + 1000000000))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByFilter_allFilterCriteriaAreSet_shouldReturnFirstRecipeAndFilterSecond() throws Exception {
        RecipeFilterDto filterDto = new RecipeFilterDto();
        filterDto.setSearch("title*Bolognese"); // search for title containing "Bolognese"
        filterDto.setCategoryIds(List.of(2));
        filterDto.setInstructionKeywords(Arrays.asList("oven", "Bake"));
        filterDto.setIncludeIngredients(Arrays.asList(2, 1));
        filterDto.setExcludeIngredients(List.of(6));

        this.mockMvc.perform(get("/api/v1/recipes/")
                        .param("search", filterDto.getSearch())
                        .param("categoryIds", replaceBrackets(filterDto.getCategoryIds().toString()))
                        .param("instructionKeywords", replaceBrackets(filterDto.getInstructionKeywords().toString()))
                        .param("includeIngredients", replaceBrackets(filterDto.getIncludeIngredients().toString()))
                        .param("excludeIngredients", replaceBrackets(filterDto.getExcludeIngredients().toString()))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Bolognese Stuffed Bell Peppers"));
    }

    @Test
    void createRecipe_allPropertiesAreValid_recipeSuccessfullyCreated() throws Exception {
        RecipeDto createRecipeDto = new RecipeDto();
        createRecipeDto.setTitle("Test Recipe");
        createRecipeDto.setDescription("Test Description");
        createRecipeDto.setServings(4);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(3);
        createRecipeDto.setCategories(List.of(categoryDto));

        InstructionDto instructionDtoOne = new InstructionDto();
        instructionDtoOne.setStep(1);
        instructionDtoOne.setDescription("Step 1");
        InstructionDto instructionDtoTwo = new InstructionDto();
        instructionDtoTwo.setStep(2);
        instructionDtoTwo.setDescription("Step 2");
        createRecipeDto.setInstructions(List.of(instructionDtoOne, instructionDtoTwo));

        RecipeIngredientDto ingredientDtoOne = new RecipeIngredientDto();
        ingredientDtoOne.setIngredientId(1);
        ingredientDtoOne.setMeasurement("5 cups");
        RecipeIngredientDto ingredientDtoTwo = new RecipeIngredientDto();
        ingredientDtoTwo.setIngredientId(2);
        ingredientDtoTwo.setMeasurement("1/2 kilo");
        RecipeIngredientDto ingredientDtoThree = new RecipeIngredientDto();
        ingredientDtoThree.setIngredientId(3);
        ingredientDtoThree.setMeasurement("200 milliliters");
        createRecipeDto.setIngredients(List.of(ingredientDtoOne, ingredientDtoTwo, ingredientDtoThree));

        this.mockMvc.perform(post("/api/v1/recipes/").content(objectMapper.writeValueAsString(createRecipeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Recipe"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.servings").value(4))
                .andExpect(jsonPath("$.categories[0].id").value(3))
                .andExpect(jsonPath("$.instructions[0].step").value(1))
                .andExpect(jsonPath("$.instructions[0].description").value("Step 1"))
                .andExpect(jsonPath("$.instructions[1].step").value(2))
                .andExpect(jsonPath("$.instructions[1].description").value("Step 2"))
                .andExpect(jsonPath("$.ingredients[0].ingredientId").value(1))
                .andExpect(jsonPath("$.ingredients[0].measurement").value("5 cups"))
                .andExpect(jsonPath("$.ingredients[1].ingredientId").value(2))
                .andExpect(jsonPath("$.ingredients[1].measurement").value("1/2 kilo"))
                .andExpect(jsonPath("$.ingredients[2].ingredientId").value(3))
                .andExpect(jsonPath("$.ingredients[2].measurement").value("200 milliliters"));
    }

    @Test
    void updateExistingRecipe_provideNewTitle_TitleShouldBeUpdated() throws Exception {
        SimpleRecipeDto simpleRecipeDto = new SimpleRecipeDto();
        simpleRecipeDto.setTitle("Updated Title");
        this.mockMvc.perform(put("/api/v1/recipes/2").content(objectMapper.writeValueAsString(simpleRecipeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("This Bolognese filling is spicy, meaty, and creamy. If you're in a hurry you can serve it over pasta instead of filling the peppers; just omit the rice or orzo."));
    }

    @Test
    void deleteExistingRecipe_verifyRecipeWasDeletedSuccessfully() throws Exception {
        this.mockMvc.perform(delete("/api/v1/recipes/2"))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/v1/recipes/" + 2))
                .andExpect(status().isNotFound());
    }

    private String replaceBrackets(String string) {
        return string.replace("[", "").replace("]", "");
    }

}