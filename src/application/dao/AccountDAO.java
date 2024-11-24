package application.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVWriter;

import application.Account;

public class AccountDAO implements DAOInt<Account>{
	
	private static final String ACCOUNTS_FILE = "db/accounts.csv"; // path to accounts.csv
	private static final Map<String, Account> ACCOUNTS = load();
	
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
	        
	        ACCOUNTS.put(obj.getName(), obj);
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    }
	}

	@Override
	public void update(Account curr, Account updated) {
		// TODO Auto-generated method stub
		
	}

	//parses data from accounts.csv into an array of accounts
	private static Map<String, Account> load() {
		HashMap<String, Account> accounts = new HashMap<>();
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
                accounts.put(account.getName(), account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
	}
	
	public Map<String, Account> getAccounts() {
		return ACCOUNTS;
	}
}
