package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.dao.AccountDAO;

public class DataAccessLayer {
	
	
	private static AccountDAO accDAO = new AccountDAO();
	
	
	public void createAccount(Account acc) {
		accDAO.create(acc);
	}
	
	public void updateAccount(Account acc) {
		
	}
	
	public static List<Account> loadAccounts() {
        return accDAO.load();
    }
	
	public static Account searchAccount(String name) {
		return accDAO.search(name);
	}

}
