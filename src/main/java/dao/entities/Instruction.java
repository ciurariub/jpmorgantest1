package dao.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import common.Currency;
import common.TransactionType;

/**
 * This entity represents an instruction performed by a financial entity.
 * 
 * @author ciurariu
 *
 */
public class Instruction extends EntityBase {

	private String name;

	private TransactionType type;

	private BigDecimal agrredFx;

	private Currency currency;

	private LocalDate instructionDate;

	private LocalDate settlementDate;

	private Long units;

	private BigDecimal pricePerUnit;

	public Instruction() {
	}

	/**
	 * @param id
	 *            - primary key
	 * @param name
	 *            - A financial entity whose shares are to be bought or sold.
	 * @param type
	 *            - transaction type
	 * @param agrredFx
	 *            - Agreed Fx is the foreign exchange rate with respect to USD that
	 *            was agreed
	 * @param currency
	 *            - currency used upon the transaction.
	 * @param instructionDate
	 *            - Date on which the instruction was sent to JP Morgan by various
	 *            clients
	 * @param settlementDate
	 *            - The date on which the client wished for the instruction to be
	 *            settled with respect
	 * @param units
	 *            - Number of shares to be bought or sold
	 * @param pricePerUnit
	 */
	public Instruction(Long id, String name, TransactionType type, BigDecimal agrredFx, Currency currency,
			LocalDate instructionDate, LocalDate settlementDate, Long units, BigDecimal pricePerUnit) {
		super(id);
		this.name = name;
		this.type = type;
		this.agrredFx = agrredFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public BigDecimal getAgrredFx() {
		return agrredFx;
	}

	public void setAgrredFx(BigDecimal agrredFx) {
		this.agrredFx = agrredFx;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public void setInstructionDate(LocalDate instructionDate) {
		this.instructionDate = instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Long getUnits() {
		return units;
	}

	public void setUnits(Long units) {
		this.units = units;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public BigDecimal getUSDAmountTrade() {
		return pricePerUnit.multiply(agrredFx).multiply(BigDecimal.valueOf(units));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instruction other = (Instruction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Instruction [name=" + name + ", USDAmountTrade=" + getUSDAmountTrade() + ", type=" + type + ", agrredFx=" + agrredFx + ", currency=" + currency
				+ ", instructionDate=" + instructionDate + ", settlementDate=" + settlementDate + ", units=" + units
				+ ", pricePerUnit=" + pricePerUnit + ", toString()=" + super.toString() + "]";
	}

}
