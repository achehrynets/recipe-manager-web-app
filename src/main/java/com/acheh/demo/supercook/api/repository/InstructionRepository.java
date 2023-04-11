package com.acheh.demo.supercook.api.repository;

import com.acheh.demo.supercook.api.repository.model.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Instruction entity.
 */
@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Integer>, JpaSpecificationExecutor<Instruction> {

}
