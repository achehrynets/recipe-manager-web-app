package com.acheh.demo.supercook.api.service;

import com.acheh.demo.supercook.api.repository.model.Instruction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InstructionService {

    List<Instruction> update(Integer recipeId, List<Instruction> instructions);
    List<Instruction> save(Integer recipeId, List<Instruction> instructions);

    void delete(Integer recipeId, Integer instructionId);

    /**
     * Find all instructions based on search criteria
     * @param pageable pagination information
     * @return page of entities
     */
    Page<Instruction> find(Integer recipeId, String search, Pageable pageable);

}
