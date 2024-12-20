package application;

import java.time.LocalDate;

public class Transaction {

	private Account account;
	private TransactionType transactionType;
	private LocalDate transactionDate;
	private String description;
	private double paymentAmount;
	private double depositAmount;
	
	public Transaction(Account account, TransactionType transactionType, LocalDate transactionDate, 
						String description, double paymentAmount, double depositAmount) {
		this.account = account;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.description = description;
		this.paymentAmount = paymentAmount;
		this.depositAmount = depositAmount;
	}
	
	//getter methods
	public Account getAccount() {
		return account;
	}
	
	public TransactionType getTransactionType() {
		return transactionType;
	}
	
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getPaymentAmount() {
		return paymentAmount;
	}
	
	public double getDepositAmount() {
		return depositAmount;
	}
	
	//setter methods
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}
}
