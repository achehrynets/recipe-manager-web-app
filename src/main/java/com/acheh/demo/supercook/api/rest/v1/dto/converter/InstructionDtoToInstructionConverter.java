package com.acheh.demo.supercook.api.rest.v1.dto.converter;

import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.rest.v1.dto.InstructionDto;
import org.modelmapper.AbstractConverter;

public class InstructionDtoToInstructionConverter extends AbstractConverter<InstructionDto, Instruction> {

    @Override
    public Instruction convert(InstructionDto source) {
        Instruction instruction = new Instruction();
        instruction.setId(source.getId());
        instruction.setDescription(source.getDescription());
        instruction.setStep(source.getStep());
        instruction.setRecipeId(source.getRecipeId());
        return instruction;
    }

}
