package application.dao;

import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;


import application.Account;
import application.ScheduledTransaction;
import application.TransactionType;

/**
 * A Data Access Object (DAO) implementation for managing {@link ScheduledTransaction} objects.
 * This DAO uses a CSV file as its data store. The file's path is specified by {@code TRANSACTION_FILE}.
 * 
 * <p>In addition to basic create and update operations defined by {@link DAOInt}, this class 
 * also implements {@link SearchableDAO} to allow searching scheduled transactions by a substring 
 * of their schedule name, and provides a method to retrieve transactions due on the current day.</p>
 */
public class ScheduledTransactionDAO implements DAOInt<ScheduledTransaction>, SearchableDAO<ScheduledTransaction> {
	private static final String TRANSACTION_FILE = "db/scheduledTransactions.csv"; // Path to scheduledTransactions.csv
	private static final Map<String, ScheduledTransaction> SCHEDULED_TRANSACTIONS = load();
	
	/**
     * Creates a new {@link ScheduledTransaction} record and appends it to the CSV file.
     * The newly created scheduled transaction is also added to the in-memory cache.
     *
     * @param obj The {@code ScheduledTransaction} object to be created.
     */
	@Override
	public void create(ScheduledTransaction obj) {
		File file = new File(TRANSACTION_FILE);

		try (FileWriter outputfile = new FileWriter(file, true);
			 CSVWriter writer = new CSVWriter(outputfile, ',',
					 CSVWriter.NO_QUOTE_CHARACTER,
					 CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					 CSVWriter.DEFAULT_LINE_END)) {

			String[] data = {
					obj.getScheduleName(),
					obj.getAccount().getName(),
					obj.getType().getTransactionType(),
					obj.getFrequency(),
					String.valueOf(obj.getDueDate() ),
					String.valueOf(obj.getAmount() )
			};

			writer.writeNext(data);
			
			SCHEDULED_TRANSACTIONS.put(obj.getScheduleName(), obj);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * Updates an existing {@link ScheduledTransaction} record with new values.
     * This method reads all scheduled transactions from the CSV file, finds the record 
     * matching {@code curr}, replaces it with {@code updated}, and then writes all 
     * records back to the file.
     *
     * @param curr    The current {@code ScheduledTransaction} record as stored.
     * @param updated The {@code ScheduledTransaction} object with updated values.
     */
	@Override
	public void update(ScheduledTransaction curr, ScheduledTransaction updated) {
		// TODO Auto-generated method stub
		try {
			CSVReader reader = new CSVReader(new FileReader(new File(TRANSACTION_FILE)));
			List<String[]> scheduledTransactions = reader.readAll();
			
			for(int i = 0; i < scheduledTransactions.size(); i++) {
				if(curr.getScheduleName().equals(scheduledTransactions.get(i)[0])) {
					String[] data = {
							updated.getScheduleName(),
							updated.getAccount().getName(),
							updated.getType().getTransactionType(),
							updated.getFrequency(),
							String.valueOf(updated.getDueDate() ),
							String.valueOf(updated.getAmount() )
					};
					scheduledTransactions.set(i, data);
				}
			}
			File file = new File(TRANSACTION_FILE);
			FileWriter outputfile = new FileWriter(file, false);
				 CSVWriter writer = new CSVWriter(outputfile, ',',
						 CSVWriter.NO_QUOTE_CHARACTER,
						 CSVWriter.DEFAULT_ESCAPE_CHARACTER,
						 CSVWriter.DEFAULT_LINE_END);
				 writer.writeAll(scheduledTransactions);
				 
				 SCHEDULED_TRANSACTIONS.remove(curr.getScheduleName());
				 SCHEDULED_TRANSACTIONS.put(updated.getScheduleName(), updated);
				 writer.close();
				 reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
     * Searches for {@link ScheduledTransaction} objects whose schedule name contains the given substring.
     *
     * @param subStr The substring to search for in the schedule names.
     * @return A list of {@code ScheduledTransaction} objects that contain the substring in their schedule name.
     */
	@Override
	public List<ScheduledTransaction> search(String subStr) {
		List<ScheduledTransaction> results = new ArrayList<>();

		for (ScheduledTransaction transaction : SCHEDULED_TRANSACTIONS.values()) {
			if (transaction.getScheduleName().toLowerCase().contains(subStr.toLowerCase())) {
				results.add(transaction);
			}
		}

		return results;
	}

	/**
     * Loads all {@link ScheduledTransaction} records from the CSV file into a map, keyed by schedule name.
     * This static method is called once at class initialization and populates the in-memory cache.
     *
     * @return A map of schedule names to their corresponding {@link ScheduledTransaction} objects.
     */
	private static HashMap<String, ScheduledTransaction> load() {
		HashMap<String, ScheduledTransaction> scheduledTransactions = new HashMap<>();
		File file = new File(TRANSACTION_FILE);

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			boolean isFirstLine = true;

			while ((line = br.readLine()) != null) {
				if (isFirstLine) {
					isFirstLine = false;
					continue;
				}

				String[] data = line.split(",");
				
				String scheduleName = data[0];
				String accountName = data[1];
				AccountDAO accDao = new AccountDAO();
				Map<String, Account> accounts = accDao.getAccounts();
				Account account = accounts.get(accountName);

				TransactionType transactionType = new TransactionType(data[2]);
				String frequency = data[3];
				int dueDate = Integer.parseInt(data[4]);
				double amount = Double.parseDouble(data[5]);

				ScheduledTransaction transaction = new ScheduledTransaction(scheduleName, account, transactionType, frequency, dueDate, amount);
				scheduledTransactions.put(scheduleName, transaction);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return scheduledTransactions;
	}
	
	/**
     * Retrieves a list of {@link ScheduledTransaction} objects that are due today.
     * A scheduled transaction is considered due today if its due date matches today's day of the month.
     *
     * @return A list of scheduled transactions due today.
     */
	public List<ScheduledTransaction> getTransactionsDueToday() {
		List<ScheduledTransaction> dueToday = new ArrayList<>();
		int currentDay = LocalDate.now().getDayOfMonth();

		for (ScheduledTransaction transaction : SCHEDULED_TRANSACTIONS.values()) {
			if (transaction.getDueDate() == currentDay) {
				dueToday.add(transaction);
			}
		}

		return dueToday;
	}

	 /**
     * Retrieves all loaded {@link ScheduledTransaction} objects.
     * The returned map is a cached representation of the scheduled transactions loaded from the CSV file.
     *
     * @return A map of schedule names to their corresponding {@link ScheduledTransaction} objects.
     */
	public Map<String, ScheduledTransaction> getScheduledTransactions() {
		return SCHEDULED_TRANSACTIONS;
	}
}
