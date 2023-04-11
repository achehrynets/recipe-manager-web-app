package com.acheh.demo.supercook.api.configuration;

import com.acheh.demo.supercook.api.rest.v1.dto.converter.IngredientToIngredientDtoConverter;
import com.acheh.demo.supercook.api.rest.v1.dto.converter.InstructionDtoToInstructionConverter;
import com.acheh.demo.supercook.api.rest.v1.dto.converter.InstructionToInstructionDtoConverter;
import com.acheh.demo.supercook.api.rest.v1.dto.converter.RecipeDtoToRecipeConverter;
import com.acheh.demo.supercook.api.rest.v1.dto.converter.RecipeIngredientToRecipeIngredientDtoConverter;
import com.acheh.demo.supercook.api.rest.v1.dto.converter.RecipeToRecipeDtoConverter;
import com.acheh.demo.supercook.api.rest.v1.dto.converter.RecipeToSimpleRecipeDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper configuration class
 */
@Configuration
public class ModelMapperConfig {

    /**
     * ModelMapper bean
     * @return ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);

        mapper.addConverter(new IngredientToIngredientDtoConverter());
        mapper.addConverter(new InstructionToInstructionDtoConverter());
        mapper.addConverter(new RecipeToSimpleRecipeDtoConverter());
        mapper.addConverter(new RecipeIngredientToRecipeIngredientDtoConverter());
        mapper.addConverter(new InstructionDtoToInstructionConverter());
        mapper.addConverter(new RecipeToRecipeDtoConverter(mapper));
        mapper.addConverter(new RecipeDtoToRecipeConverter(mapper));
        return mapper;
    }

}
