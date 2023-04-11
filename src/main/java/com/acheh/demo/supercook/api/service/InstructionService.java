package com.acheh.demo.supercook.api.service;

import com.acheh.demo.supercook.api.repository.model.Instruction;

import java.util.List;

public interface InstructionService extends BaseService<Instruction, Integer> {

    List<Instruction> saveAll(List<Instruction> instructions);

}
