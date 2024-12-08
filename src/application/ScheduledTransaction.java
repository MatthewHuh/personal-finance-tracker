package application;

/**
 * Represents a scheduled financial transaction with a defined frequency and due date.
 * Instances of this class store information about the scheduled transaction, including 
 * which account it belongs to, the transaction type, when it is due, and how often it occurs.
 */
public class ScheduledTransaction {
	private String scheduleName;
	private Account account;
	private TransactionType type;
	private String frequency;
	private int dueDate;
	private double amount;
	
	/**
     * Constructs a new {@code ScheduledTransaction} with the specified details.
     *
     * @param scheduleName The unique name or identifier for this scheduled transaction.
     * @param account      The {@link Account} associated with this transaction.
     * @param type         The {@link TransactionType} describing the nature of this transaction.
     * @param frequency    The frequency with which this transaction recurs (e.g., "Monthly").
     * @param dueDate      The day of the month on which the transaction is due.
     * @param amount       The monetary amount of the scheduled transaction.
     */
	public ScheduledTransaction(String scheduleName, Account account, TransactionType type, 
								String frequency, int dueDate, double amount) {
		this.scheduleName = scheduleName;
		this.account = account;
		this.type = type;
		this.frequency= frequency;
		this.dueDate = dueDate;
		this.amount = amount;
	}
	
	//getter methods
	public String getScheduleName() {
		return scheduleName;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public TransactionType getType() {
		return type;
	}
	
	public String getFrequency() {
		return frequency;
	}
	
	public int getDueDate() {
		return dueDate;
	}
	
	public double getAmount() {
		return amount;
	}
	
	//setter methods
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public void setType(TransactionType type) {
		this.type = type;
	}
	
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public void setDueDate(int dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
