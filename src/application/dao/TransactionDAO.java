package application.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.time.LocalDate;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import application.Transaction;
import application.Account;
import application.TransactionType;

public class TransactionDAO implements DAOInt<Transaction>, SearchableDAO<Transaction> {
	private static final String TRANSACTION_FILE = "db/transactions.csv"; // Path to transactions.csv
	private static final List<Transaction> TRANSACTIONS = load();

	@Override
	public void create(Transaction obj) {
		File file = new File(TRANSACTION_FILE);

		try (FileWriter outputfile = new FileWriter(file, true);
			 CSVWriter writer = new CSVWriter(outputfile, ',',
					 CSVWriter.NO_QUOTE_CHARACTER,
					 CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					 CSVWriter.DEFAULT_LINE_END)) {

			String[] data = {
					obj.getAccount().getName(),
					obj.getTransactionType().getTransactionType(),
					obj.getTransactionDate().toString(),
					obj.getDescription(),
					String.valueOf(obj.getPaymentAmount()),
					String.valueOf(obj.getDepositAmount())
			};

			writer.writeNext(data);
			
			TRANSACTIONS.add(obj);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Transaction curr, Transaction updated) {
		// TODO Auto-generated method stub
		try {
			CSVReader reader = new CSVReader(new FileReader(new File(TRANSACTION_FILE)));
			List<String[]> transactions = reader.readAll();
			
			for(int i = 0; i < transactions.size(); i++) {
				if(curr.getDescription().equals(transactions.get(i)[3])) {
					String[] data = {
							updated.getAccount().getName(),
							updated.getTransactionType().getTransactionType(),
							updated.getTransactionDate().toString(),
							updated.getDescription(),
							String.valueOf(updated.getPaymentAmount() ),
							String.valueOf(updated.getDepositAmount() )
					};
					transactions.set(i, data);
				}
			}
			File file = new File(TRANSACTION_FILE);
			FileWriter outputfile = new FileWriter(file, false);
				 CSVWriter writer = new CSVWriter(outputfile, ',',
						 CSVWriter.NO_QUOTE_CHARACTER,
						 CSVWriter.DEFAULT_ESCAPE_CHARACTER,
						 CSVWriter.DEFAULT_LINE_END);
				 writer.writeAll(transactions);
				 
				 TRANSACTIONS.remove(curr);
				 TRANSACTIONS.add(updated);
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
	public List<Transaction> search(String subStr) {
		List<Transaction> results = new ArrayList<>();

		for (Transaction transaction : TRANSACTIONS) {
			if (transaction.getDescription().toLowerCase().contains(subStr.toLowerCase())) {
				results.add(transaction);
			}
		}

		return results;
	}	
	
	private static List<Transaction> load() {
		List<Transaction> transactions = new ArrayList<>();
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

				String accountName = data[0];
				AccountDAO accDao = new AccountDAO();
				Map<String, Account> accounts = accDao.getAccounts();
				Account account = accounts.get(accountName);

				TransactionType transactionType = new TransactionType(data[1]);
				LocalDate transactionDate = LocalDate.parse(data[2]);
				String description = data[3];
				double paymentAmount = Double.parseDouble(data[4]);
				double depositAmount = Double.parseDouble(data[5]);

				Transaction transaction = new Transaction(account, transactionType, transactionDate, description, paymentAmount, depositAmount);
				transactions.add(transaction);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return transactions;
	}

	// Method to get transactions by Account
	public List<Transaction> getTransactionsByAccount(Account account) {
		List<Transaction> accountTransactions = new ArrayList<>();
		for (Transaction transaction : TRANSACTIONS) {
			if (transaction.getAccount().equals(account)) {
				accountTransactions.add(transaction);
			}
		}
		return accountTransactions;
	}

	// Method to get transactions by TransactionType
	public List<Transaction> getTransactionsByType(TransactionType transactionType) {
		List<Transaction> typeTransactions = new ArrayList<>();
		for (Transaction transaction : TRANSACTIONS) {
			if (transaction.getTransactionType().equals(transactionType)) {
				typeTransactions.add(transaction);
			}
		}
		return typeTransactions;
	}

	public List<Transaction> getTransactions() {
		return TRANSACTIONS;
	}
}
