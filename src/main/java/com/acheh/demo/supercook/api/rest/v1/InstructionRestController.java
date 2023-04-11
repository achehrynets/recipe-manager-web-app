package com.acheh.demo.supercook.api.rest.v1;

import com.acheh.demo.supercook.api.constant.OpenApiParamDescriptionConstants;
import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.rest.v1.dto.InstructionDto;
import com.acheh.demo.supercook.api.service.InstructionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes/{recipeId}/instructions")
public class InstructionRestController {

    private final ModelMapper mapper;
    private final InstructionService instructionService;

    public InstructionRestController(ModelMapper mapper, InstructionService instructionService) {
        this.mapper = mapper;
        this.instructionService = instructionService;
    }

    @Operation(summary = "Find instructions from a specific recipe")
    @GetMapping
    public ResponseEntity<Page<InstructionDto>> find(@PathVariable Integer recipeId,
                                                     @Parameter(description = OpenApiParamDescriptionConstants.SEARCH_PARAM_DESCRIPTION)
                                                     @RequestParam(required = false) String search,
                                                     Pageable pageable) {
        Page<Instruction> instructions = this.instructionService.find(recipeId, search, pageable);
        Page<InstructionDto> instructionDtos
                = instructions.map(category -> this.mapper.map(category, InstructionDto.class));
        return ResponseEntity.ok(instructionDtos);
    }

    @Operation(summary = "Add instructions to a specific recipe")
    @PostMapping
    public ResponseEntity<List<InstructionDto>> addInstructions(@PathVariable Integer recipeId,
                                                                List<InstructionDto> instructionDtos) {
        List<Instruction> instructions = new ArrayList<>();
        for (InstructionDto instructionDto : instructionDtos) {
            instructions.add(this.mapper.map(instructionDto, Instruction.class));
        }
        List<Instruction> savedInstructions = this.instructionService.save(recipeId, instructions);
        List<InstructionDto> savedInstructionDtos = new ArrayList<>();
        for (Instruction instruction : savedInstructions) {
            savedInstructionDtos.add(this.mapper.map(instruction, InstructionDto.class));
        }
        return ResponseEntity.ok(savedInstructionDtos);
    }

    @Operation(summary = "Update instructions in a specific recipe")
    @PutMapping
    public ResponseEntity<List<InstructionDto>> updateInstructions(@PathVariable Integer recipeId,
                                                                   List<InstructionDto> instructionDtos) {
        List<Instruction> instructions = new ArrayList<>();
        for (InstructionDto instructionDto : instructionDtos) {
            instructions.add(this.mapper.map(instructionDto, Instruction.class));
        }
        List<Instruction> savedInstructions = this.instructionService.update(recipeId, instructions);
        List<InstructionDto> savedInstructionDtos = new ArrayList<>();
        for (Instruction instruction : savedInstructions) {
            savedInstructionDtos.add(this.mapper.map(instruction, InstructionDto.class));
        }
        return ResponseEntity.ok(savedInstructionDtos);
    }

}
