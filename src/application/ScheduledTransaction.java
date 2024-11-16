package application;

public class ScheduledTransaction {
	private String scheduleName;
	private Account account;
	private TransactionType type;
	private String frequency;
	private int dueDate;
	private double amount;
	
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
