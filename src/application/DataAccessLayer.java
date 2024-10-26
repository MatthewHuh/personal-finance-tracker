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
		if(searchAccount(acc.getName()) != null) {
			return false;
		}
		accDAO.createAccount(acc);
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
	
	/**
	 * Searches for an account by name in the list of accounts.
	 *
	 * This method loads all accounts and iterates through them to find an
	 * account that matches the specified name. If a matching account is
	 * found, it is returned. If no matching account is found, the method
	 * returns null.
	 *
	 * @param name the name of the account to search for
	 * @return the Account object matching the specified name, or null
	 *         if no account with that name exists
	 */
	public static Account searchAccount(String name) {
		List <Account> accounts = loadAccounts();
		for (Account acc : accounts) {
			if (name.equals(acc.getName() ) ) {
				return acc;
			}
		}
		return null;
	}

}
