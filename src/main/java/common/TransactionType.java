package common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * describes the type of instruction (Sell/Buy)
 * 
 * @author ciurariu
 *
 */
public enum TransactionType {
	SELL(0), BUY(1);

	private int index;

	private static Map<Integer, TransactionType> indexedMap = null;

	static {
		indexedMap = generateIndexedMap();
	}

	private TransactionType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	private static Map<Integer, TransactionType> generateIndexedMap() {
		Map<Integer, TransactionType> indexedMap = new HashMap<>();

		for (TransactionType entityType : TransactionType.values()) {
			indexedMap.put(entityType.getIndex(), entityType);
		}

		return indexedMap;
	}

	public static TransactionType getEntityTypeByIndex(int index) {

		Optional<TransactionType> tOptional = Optional.ofNullable(indexedMap.get(index));

		if (!tOptional.isPresent()) {
			throw new IllegalArgumentException("The provided index is not present");
		}

		return tOptional.get();
	}

}
