package service;

import java.util.List;

import dao.InstructionsDao;
import dao.InstructionsDaoImpl;
import dao.entities.Instruction;

/**
 * 
 * perform usual operations over the instructions
 * 
 * @author ciurariu
 *
 */
public class InstructionsServiceImpl implements InstructionService {

	private InstructionsDao instructionDao;

	/**
	 * @param instructionDao
	 */
	public InstructionsServiceImpl() {
		this.instructionDao = new InstructionsDaoImpl();
	}

	/**
	 * returns the entire list of instructions for being processed
	 */
	@Override
	public List<Instruction> getInstructions() {

		return instructionDao.getInstructions();
	}

}
