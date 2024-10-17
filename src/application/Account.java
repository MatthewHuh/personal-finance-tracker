package application;

public class Account {
	private String name;
	private String openingDate;
	private double balance;
	
	public Account(String name, String openingDate, double balance) {
		this.name = name;
		this.openingDate = openingDate;
		this.balance = balance;
	}
	
	public String getName() {
		return name;
	}
	
	public String openingDate() {
		return openingDate;
	}
	
	public double balance() {
		return balance;
	}
}
