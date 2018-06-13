package engines;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import common.Currency;
import common.TransactionType;
import dao.entities.Instruction;
import service.InstructionService;

public class ProcessorTest {

	private final ByteArrayOutputStream out = new ByteArrayOutputStream();

	private final ByteArrayOutputStream err = new ByteArrayOutputStream();

	@InjectMocks
	private Processor processor = new Processor();

	@Mock
	private InstructionService instructionService;

	@Before
	public void before() {

		System.setOut(new PrintStream(out));

		System.setErr(new PrintStream(err));

		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testEmptyList() {

		List<Instruction> instructions = Arrays.asList();

		Mockito.when(instructionService.getInstructions()).thenReturn(instructions);

		processor.generateRaport();

		assertEquals("Generate The report date=amount for BUY\n" + "-----------------------------------------\n"
				+ "Generate The report date=amount for SELL\n" + "-----------------------------------------\n"
				+ "Generate the rank for each entity according with type of transcation =BUY\n"
				+ "-----------------------------------------\n"
				+ "Generate the rank for each entity according with type of transcation =SELL\n"
				+ "-----------------------------------------\n" + "", out.toString());
	}

	@Test
	public void testInstructionChangeSettlementDateforWeekend() {

		List<Instruction> instructions = Arrays
				.asList(new Instruction(1l, "foo", TransactionType.BUY, BigDecimal.valueOf(0.5d), Currency.EUR,
						LocalDate.of(2016, 1, 1), LocalDate.of(2016, 1, 9), 300l, BigDecimal.valueOf(140.25d)));

		Mockito.when(instructionService.getInstructions()).thenReturn(instructions);

		processor.generateRaport();

		assertEquals("Generate The report date=amount for BUY\n" + "2016-01-11=21037.500\n"
				+ "-----------------------------------------\n" + "Generate The report date=amount for SELL\n"
				+ "-----------------------------------------\n"
				+ "Generate the rank for each entity according with type of transcation =BUY\n"
				+ "Rank 1 for entity foo\n" + "-----------------------------------------\n"
				+ "Generate the rank for each entity according with type of transcation =SELL\n"
				+ "-----------------------------------------\n" + "", out.toString());
	}

	@Test
	public void testAlgorithm() {

		List<Instruction> instructions = Arrays.asList(
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

		Mockito.when(instructionService.getInstructions()).thenReturn(instructions);

		processor.generateRaport();

		assertEquals("Generate The report date=amount for BUY\n" + 
				"2016-01-12=10025.000\n" + 
				"2016-01-11=21550.000\n" + 
				"2016-01-02=27618.750\n" + 
				"-----------------------------------------\n" + 
				"Generate The report date=amount for SELL\n" + 
				"2016-01-13=14899.500\n" + 
				"2016-01-11=19408.500\n" + 
				"2016-01-07=18116.780\n" + 
				"-----------------------------------------\n" + 
				"Generate the rank for each entity according with type of transcation =BUY\n" + 
				"Rank 1 for entity foo\n" + 
				"Rank 2 for entity foo1\n" + 
				"Rank 3 for entity bar1\n" + 
				"-----------------------------------------\n" + 
				"Generate the rank for each entity according with type of transcation =SELL\n" + 
				"Rank 1 for entity bar1\n" + 
				"Rank 2 for entity bar2\n" + 
				"Rank 3 for entity bar\n" + 
				"-----------------------------------------\n" + 
				"" + "", out.toString());
	}

	@After
	public void after() {
		System.setOut(System.out);

		System.setErr(System.err);
	}

}
