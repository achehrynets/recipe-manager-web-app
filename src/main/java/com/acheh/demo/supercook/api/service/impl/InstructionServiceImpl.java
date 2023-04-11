package com.acheh.demo.supercook.api.service.impl;

import com.acheh.demo.supercook.api.exception.EntityNotFoundException;
import com.acheh.demo.supercook.api.repository.InstructionRepository;
import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.acheh.demo.supercook.api.repository.model.Recipe;
import com.acheh.demo.supercook.api.service.InstructionService;
import com.acheh.demo.supercook.api.service.RecipeService;
import com.acheh.demo.supercook.api.util.search.specification.GenericSpecification;
import com.cosium.spring.data.jpa.entity.graph.domain2.DynamicEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service Interface for managing {@link com.acheh.demo.supercook.api.repository.model.Instruction}.
 */
@Service
public class InstructionServiceImpl implements InstructionService {

    private final ModelMapper mapper;
    private final RecipeService recipeService;
    private final InstructionRepository instructionRepository;

    public InstructionServiceImpl(ModelMapper mapper, RecipeService recipeService, InstructionRepository instructionRepository) {
        this.mapper = mapper;
        this.recipeService = recipeService;
        this.instructionRepository = instructionRepository;
    }

    @Override
    public Page<Instruction> find(Integer recipeId, String search, Pageable pageable) {
        Specification spec =
                Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("recipeId"), recipeId))
                        .and(GenericSpecification.fromSearchParams(search, GenericSpecification<Object>::new));
        EntityGraph entityGraph = DynamicEntityGraph.fetching().addPath("recipe").build();
        return this.instructionRepository.findAll(spec, pageable, entityGraph);
    }

    @Transactional
    @Override
    public List<Instruction> update(Integer recipeId, List<Instruction> instructions) {
        Recipe recipe = this.recipeService.findById(recipeId);
        instructions.forEach(instruction -> instruction.setRecipe(recipe));
        this.mapper.map(instructions, recipe.getInstructions());
        return this.instructionRepository.saveAll(instructions);
    }

    @Transactional
    @Override
    public List<Instruction> save(Integer recipeId, List<Instruction> instructions) {
        Recipe recipe = this.recipeService.findById(recipeId);
        instructions.forEach(instruction -> instruction.setRecipe(recipe));
        return this.instructionRepository.saveAll(instructions);
    }

    @Transactional
    @Override
    public void delete(Integer recipeId, Integer instructionId) {
        Instruction instruction = this.instructionRepository.findByRecipeIdAndId(recipeId, instructionId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Instruction with %d not found", instructionId)));
        this.instructionRepository.delete(instruction);
    }
}
