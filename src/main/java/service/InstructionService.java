package service;

import java.util.List;

import dao.entities.Instruction;

/**
 * contains usual operations over the instructions
 * @author ciurariu
 *
 */
public interface InstructionService {

	List<Instruction> getInstructions();

}
