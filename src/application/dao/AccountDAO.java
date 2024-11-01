package application.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

import application.Account;

public class AccountDAO implements DAOInt<Account>{
	
	private static final String ACCOUNTS_FILE = "db/accounts.csv"; // path to accounts.csv
	
	@Override
	public void create(Account obj) {
		// specify path of the csv file
	    File file = new File(ACCOUNTS_FILE);
	    try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file, true); 
	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter writer = new CSVWriter(outputfile, ',' , 
	        		CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END); 
	  
	        // add data to csv 
	        String[] data = { obj.getName(), obj.getOpeningDate().toString(), String.valueOf(obj.getBalance()) }; 
	        writer.writeNext(data); 
	  
	        // closing writer connection 
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    }
	}

	@Override
	public void update(Account obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	//parses data from accounts.csv into an array of accounts
	public List<Account> load() {
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
	@Override
	public Account search(String id) {
		List <Account> accounts = load();
		for (Account acc : accounts) {
			if (id.equals(acc.getName() ) ) {
				return acc;
			}
		}
		return null;
	}
	
	
}
