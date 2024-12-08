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

/**
 * A Data Access Object (DAO) implementation for managing {@link Account} objects.
 * This DAO uses a CSV file as its data store. The file's path is specified by {@code ACCOUNTS_FILE}.
 */
public class AccountDAO implements DAOInt<Account>{
	
	private static final String ACCOUNTS_FILE = "db/accounts.csv"; // path to accounts.csv
	private static final Map<String, Account> ACCOUNTS = load();
	
	/**
     * Creates a new {@link Account} record and appends it to the CSV file.
     * The newly created account is also added to the in-memory cache.
     *
     * @param obj The {@code Account} object to be created.
     */
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

	/**
     * Updates an existing {@link Account}. Currently, this method is not implemented.
     * To implement updates, you would need to:
     * <ul>
     *   <li>Load all accounts from the CSV file</li>
     *   <li>Find and replace the current account record with the updated one</li>
     *   <li>Overwrite the CSV file with the new records</li>
     * </ul>
     *
     * @param curr    The current {@code Account} record as stored.
     * @param updated The {@code Account} object with updated values.
     */
	@Override
	public void update(Account curr, Account updated) {
		// TODO Auto-generated method stub
		
	}

	/**
     * Loads all {@link Account} records from the CSV file into a map, keyed by account name.
     * This static method is called once at class initialization and populates the in-memory cache.
     *
     * @return A map of account names to their corresponding {@link Account} objects.
     */
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
	
	/**
     * Retrieves all loaded {@link Account} objects.
     * The returned map is a cached representation of the accounts loaded from the CSV file.
     *
     * @return A map of account names to their corresponding {@link Account} objects.
     */
	public Map<String, Account> getAccounts() {
		return ACCOUNTS;
	}
}
