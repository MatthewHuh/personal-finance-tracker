package application;

import java.time.LocalDate;

/**
 * Represents a financial account with a name, opening date, and balance.
 * Instances of this class store basic account information and provide
 * getters and setters for each field. The {@link #toString()} method
 * returns the account's name as a string representation.
 */
public class Account {
	private String name;
	private LocalDate openingDate;
	private double balance;
	
	/**
     * Constructs a new {@code Account} with the specified name, opening date, and balance.
     *
     * @param name         The name of the account.
     * @param openingDate  The date on which the account was opened.
     * @param balance      The initial balance of the account.
     */
	public Account(String name, LocalDate openingDate, double balance) {
		this.name = name;
		this.openingDate = openingDate;
		this.balance = balance;
	}
	
	// getter methods
	public String getName() {
		return name;
	}
	
	public LocalDate getOpeningDate() {
		return openingDate;
	}
	
	public double getBalance() {
		return balance;
	}
	
	// setter methods
	public void setName(String name) {
		this.name = name;
	}
	
	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	 /**
     * Returns the string representation of this account.
     * In this case, the account's name is returned.
     *
     * @return A string representing the account name.
     */
	@Override
	public String toString() {
		return name;
	}
}
