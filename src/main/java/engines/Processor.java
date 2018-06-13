package engines;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import common.Currency;
import common.TransactionType;
import dao.entities.Instruction;
import service.InstructionService;
import service.InstructionsServiceImpl;

/**
 * Process the incoming and outgoing instructions
 * 
 * @author ciurariu
 *
 */
public class Processor {

	public static final String FORMAT_RANK_MESSAGE = "Rank %d for entity %s";
	/**
	 * service used for fetching data
	 */
	private InstructionService instructionService;

	/**
	 * 
	 */
	public Processor() {

		this.instructionService = new InstructionsServiceImpl();
	}

	/**
	 * process the incoming and outgoing instructions and generate the report
	 */
	public void generateRaport() {

		List<Instruction> instructions = instructionService.getInstructions();

		// If an instructed settlement date falls on a weekend, then the settlement date
		// should be changed to
		// the next working day.
		List<Instruction> adjustedInstuctions = adjustDateFromWeekend(instructions);

		// generate incoming amount report for each date
		calculateAmountByTransaction(adjustedInstuctions, TransactionType.BUY);

		// generate the outgoing amount report for each date
		calculateAmountByTransaction(adjustedInstuctions, TransactionType.SELL);

		// calculate the rank of each entity based on incoming
		calculateRank(adjustedInstuctions, TransactionType.BUY);

		// calculate the rank of each entity based on incoming
				calculateRank(adjustedInstuctions, TransactionType.SELL);
	}

	/**
	 * calculate the rank for specific type of transactions
	 * @param adjustedInstuctions
	 * @param transactionType
	 */
	private void calculateRank(List<Instruction> adjustedInstuctions, TransactionType transactionType) {
		
		System.out.println("Generate the rank for each entity according with type of transcation =" + transactionType);
		
		Map<String, Instruction> entiesWithHighestTransactions = adjustedInstuctions.stream()
				.filter(inst -> inst.getType() == transactionType)
				.collect(Collectors.groupingBy(Instruction::getName,
						Collectors.collectingAndThen(
								Collectors.reducing((Instruction inst1,
										Instruction inst2) -> inst1.getUSDAmountTrade()
												.compareTo(inst2.getUSDAmountTrade()) > 0 ? inst1 : inst2),
								Optional::get)));

		// sort the resulted entities after the highest amount
		List<String> sortedEntities = entiesWithHighestTransactions.values().stream().sorted(new Comparator<Instruction>() {

			@Override
			public int compare(Instruction i1, Instruction i2) {

				return -i1.getUSDAmountTrade().compareTo(i2.getUSDAmountTrade());
			}
		}).map(Instruction::getName).collect(Collectors.toList());
		
		//type to console the ranks
		for (int i=0; i < sortedEntities.size(); i++) {
			System.out.println(String.format(FORMAT_RANK_MESSAGE, i+1, sortedEntities.get(i)));
		}
		
		System.out.println("-----------------------------------------");
	}

	/**
	 * adjust the settlement to occur in a working date
	 * 
	 * @param instructions
	 * @return
	 */
	private List<Instruction> adjustDateFromWeekend(List<Instruction> instructions) {
		return instructions.stream().map(inst -> {

			LocalDate settlementDate = inst.getSettlementDate();
			int dayOfWeek = settlementDate.getDayOfWeek().getValue();
			Currency currency = inst.getCurrency();
			boolean exception = currency.isException();
			int workWeekStart = currency.getWorkWeekStart();
			int workWeekEnd = currency.getWorkWeekEnd();

			if ((!exception && dayOfWeek > workWeekEnd)
					|| (exception && dayOfWeek > workWeekEnd && dayOfWeek < workWeekStart)) {
				// change the settlement date to the next working date
				inst.setSettlementDate(
						settlementDate.plusDays(exception ? workWeekStart - dayOfWeek : workWeekStart + 7 - dayOfWeek));
			}

			return inst;
		}).collect(Collectors.toList());
	}

	/**
	 * generate the report for type of transactions
	 * 
	 * @param instuctions
	 * @param transactionType
	 */
	private void calculateAmountByTransaction(List<Instruction> instuctions, TransactionType transactionType) {
		
		System.out.println("Generate The report date=amount for " + transactionType);
		
		instuctions.stream().filter(inst -> inst.getType() == transactionType)
				.collect(Collectors.groupingBy(Instruction::getSettlementDate,
						Collectors.reducing(BigDecimal.ZERO, Instruction::getUSDAmountTrade, BigDecimal::add)))
				.forEach((localDate, amount) -> System.out.println(localDate + "=" + amount));
		
		System.out.println("-----------------------------------------");

	}

}
