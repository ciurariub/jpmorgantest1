package dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import common.Currency;
import common.TransactionType;
import dao.entities.Instruction;

/**
 * data layer operations
 * 
 * @author ciurariu
 *
 */
public class InstructionsDaoImpl implements InstructionsDao {

	private List<Instruction> instructions;

	{
		initInstructuonWithSamples();
	}

	@Override
	public List<Instruction> getInstructions() {

		return instructions;
	}

	private void initInstructuonWithSamples() {
		// create mock data
		instructions = Arrays.asList(
				new Instruction(1l, "foo", TransactionType.BUY, BigDecimal.valueOf(0.5d), Currency.SGP,
						LocalDate.of(2016, 1, 1), LocalDate.of(2016, 1, 2), 200l, BigDecimal.valueOf(100.25d)),
				new Instruction(2l, "bar", TransactionType.SELL, BigDecimal.valueOf(0.22d), Currency.AED,
						LocalDate.of(2016, 1, 5), LocalDate.of(2016, 1, 7), 458l, BigDecimal.valueOf(140.5d)),
				new Instruction(1l, "foo1", TransactionType.BUY, BigDecimal.valueOf(0.5d), Currency.SGP,
						LocalDate.of(2016, 1, 1), LocalDate.of(2016, 1, 2), 250l, BigDecimal.valueOf(140.75d)),
				new Instruction(2l, "bar", TransactionType.SELL, BigDecimal.valueOf(0.22d), Currency.AED,
						LocalDate.of(2016, 1, 5), LocalDate.of(2016, 1, 7), 150l, BigDecimal.valueOf(120.0d)),
				new Instruction(1l, "bar1", TransactionType.BUY, BigDecimal.valueOf(0.5d), Currency.CHF,
						LocalDate.of(2016, 1, 7), LocalDate.of(2016, 1, 9), 100l, BigDecimal.valueOf(10.25d)),
				new Instruction(2l, "bar2", TransactionType.SELL, BigDecimal.valueOf(0.12d), Currency.EUR,
						LocalDate.of(2016, 1, 5), LocalDate.of(2016, 1, 10), 150l, BigDecimal.valueOf(250.5d)),
				new Instruction(1l, "foo", TransactionType.BUY, BigDecimal.valueOf(0.5d), Currency.EUR,
						LocalDate.of(2016, 1, 1), LocalDate.of(2016, 1, 9), 300l, BigDecimal.valueOf(140.25d)),
				new Instruction(2l, "bar1", TransactionType.SELL, BigDecimal.valueOf(0.22d), Currency.EUR,
						LocalDate.of(2016, 1, 5), LocalDate.of(2016, 1, 9), 450l, BigDecimal.valueOf(150.5d)),
				new Instruction(1l, "foo1", TransactionType.BUY, BigDecimal.valueOf(0.5d), Currency.EUR,
						LocalDate.of(2016, 1, 1), LocalDate.of(2016, 1, 12), 200l, BigDecimal.valueOf(100.25d)),
				new Instruction(2l, "bar2", TransactionType.SELL, BigDecimal.valueOf(0.22d), Currency.CHF,
						LocalDate.of(2016, 1, 5), LocalDate.of(2016, 1, 13), 450l, BigDecimal.valueOf(150.5d)));

	}

}
