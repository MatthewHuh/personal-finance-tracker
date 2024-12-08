package application;

/**
 * Represents a type or category of a financial transaction, such as "Salary", "Rent", or "Groceries".
 * Each instance of this class holds a single transaction type name, which can be retrieved and updated.
 */
public class TransactionType {

	private String transactionType;
	
	/**
     * Constructs a new {@code TransactionType} with the specified type name.
     *
     * @param transactionType The name of the transaction type (e.g., "Rent").
     */
	public TransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	// getter
	public String getTransactionType() {
		return transactionType;
	}
	
	// setter
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
     * Returns a string representation of this transaction type.
     * In this case, the transaction type name is returned.
     *
     * @return A string representing the transaction type.
     */
	@Override
	public String toString() {
		return transactionType;  // This will be displayed in the ChoiceBox
	}

	/**
     * Indicates whether some other object is "equal to" this one.
     * Two {@code TransactionType} objects are considered equal if they have the same transaction type name.
     *
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TransactionType that = (TransactionType) obj;
		return this.transactionType.equals(that.transactionType);
	}
}
