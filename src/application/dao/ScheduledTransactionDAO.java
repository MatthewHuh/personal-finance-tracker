package application.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

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
	public void update(ScheduledTransaction obj) {
		// TODO Auto-generated method stub
		
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

	public Map<String, ScheduledTransaction> getScheduledTransactions() {
		return SCHEDULED_TRANSACTIONS;
	}
}
