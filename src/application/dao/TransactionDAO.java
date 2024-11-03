package application.dao;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import com.opencsv.CSVWriter;

import application.Transaction;
import application.Account;
import application.TransactionType;

public class TransactionDAO implements DAOInt<Transaction> {
	private static final String TRANSACTION_FILE = "db/transactions.csv"; // Path to transactions.csv

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

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Transaction obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Transaction> load() {
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
				Account account = accDao.search(accountName);

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

	@Override
	public Transaction search(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
