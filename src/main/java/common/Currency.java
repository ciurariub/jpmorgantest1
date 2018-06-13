package common;

/**
 * enumerate the entire list of currencies used by the application.
 * 
 * @author ciurariu
 *
 */
public enum Currency {
	EUR(false), USD(false), SGP(true), AED(true, 7, 4), CHF(false);

	/**
	 * true if for selected currency, work week starts Sunday and ends Thursday,
	 * otherwise false
	 */
	private boolean exception;

	/**
	 * work week starts
	 */
	private int workWeekStart = 1;

	/**
	 * work week ends
	 */
	private int workWeekEnd = 5;

	/**
	 * @param exception
	 *            -
	 */
	private Currency(boolean exception) {
		this.exception = exception;
	}

	/**
	 * @param exception
	 * @param workWeekStart
	 * @param workWeekEnd
	 */
	private Currency(boolean exception, int workWeekStart, int workWeekEnd) {
		this.exception = exception;
		this.workWeekStart = workWeekStart;
		this.workWeekEnd = workWeekEnd;
	}

	public boolean isException() {
		return exception;
	}

	public int getWorkWeekStart() {
		return workWeekStart;
	}

	public int getWorkWeekEnd() {
		return workWeekEnd;
	}

}
