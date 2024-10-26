package application;

import application.Account;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataAccessLayer {
	
	
	private AccountDAO accDAO;
	
	public DataAccessLayer() {
		accDAO = new AccountDAO();
	}
	
	public void createAccount(Account acc) {
		
	}
	
	public void updateAccount(Account acc) {
		
	}
	
	private static final String ACCOUNTS_FILE = "accounts.csv"; // path to accounts.csv
	
	//parses data from accounts.csv into an array of accounts
	public static List<Account> loadAccounts() {
        List<Account> accounts = new ArrayList<>();

        try (InputStream is = DataAccessLayer.class.getResourceAsStream(ACCOUNTS_FILE);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");

                String name = data[0];
                LocalDate openingDate = LocalDate.parse(data[1]);
                double balance = Double.parseDouble(data[2]);

                Account account = new Account(name, openingDate, balance);
                accounts.add(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }

}
