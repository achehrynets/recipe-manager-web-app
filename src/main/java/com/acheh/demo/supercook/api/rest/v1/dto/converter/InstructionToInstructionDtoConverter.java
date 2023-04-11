package com.acheh.demo.supercook.api.rest.v1.dto.converter;

import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.rest.v1.dto.InstructionDto;
import org.modelmapper.AbstractConverter;

public class InstructionToInstructionDtoConverter extends AbstractConverter<Instruction, InstructionDto> {

    @Override
    public InstructionDto convert(Instruction source) {
        InstructionDto instructionDto = new InstructionDto();
        instructionDto.setId(source.getId());
        instructionDto.setDescription(source.getDescription());
        instructionDto.setRecipeId(source.getRecipe().getId());
        instructionDto.setStep(source.getStep());
        return instructionDto;
    }

}
