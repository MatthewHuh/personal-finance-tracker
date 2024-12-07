package application;

import java.time.LocalDate;

public class Account {
	private String name;
	private LocalDate openingDate;
	private double balance;
	
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

	// Override toString to display only the account name
	@Override
	public String toString() {
		return name;
	}
}
