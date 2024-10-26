package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataAccessLayer {
	
	
	private AccountDAO accDAO;
	
	public DataAccessLayer() {
		accDAO = new AccountDAO();
	}
	
	public boolean createAccount(Account acc) {
		return true;
	}
	
	public void updateAccount(Account acc) {
		
	}
	
	private static final String ACCOUNTS_FILE = "db/accounts.csv"; // path to accounts.csv
	
	//parses data from accounts.csv into an array of accounts
	public static List<Account> loadAccounts() {
        List<Account> accounts = new ArrayList<>();
        File file = new File(ACCOUNTS_FILE);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

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
