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

public class ScheduledTransactionDAO implements DAOInt<ScheduledTransaction>, SearchableDAO<ScheduledTransaction> {
	private static final String TRANSACTION_FILE = "db/scheduledTransactions.csv"; // Path to scheduledTransactions.csv
	private static final Map<String, ScheduledTransaction> SCHEDULED_TRANSACTIONS = load();
	
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

	public Map<String, ScheduledTransaction> getScheduledTransactions() {
		return SCHEDULED_TRANSACTIONS;
	}
}
