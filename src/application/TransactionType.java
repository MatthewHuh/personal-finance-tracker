package application;

public class TransactionType {

	private String transactionType;
	
	public TransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	// Override toString to display the transaction type
	@Override
	public String toString() {
		return transactionType;  // This will be displayed in the ChoiceBox
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		TransactionType that = (TransactionType) obj;
		return this.transactionType.equals(that.transactionType);
	}
}
