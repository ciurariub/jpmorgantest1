package dao;

import java.util.List;

import dao.entities.Instruction;

/**
 * offers the support for data layer operations
 * @author ciurariu
 *
 */
public interface InstructionsDao {

	List<Instruction> getInstructions();

}
