package com.acheh.demo.supercook.api.repository;

import com.acheh.demo.supercook.api.repository.model.Instruction;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Instruction entity.
 */
@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Integer>, EntityGraphJpaSpecificationExecutor<Instruction> {

    Optional<Instruction> findByRecipeIdAndId(Integer recipeId, Integer instructionId);

}
