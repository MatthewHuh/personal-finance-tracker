package application.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

import application.Account;
import application.ScheduledTransaction;
import application.TransactionType;

public class ScheduledTransactionDAO implements DAOInt<ScheduledTransaction> {
	private static final String TRANSACTION_FILE = "db/scheduledTransactions.csv"; // Path to scheduledTransactions.csv
	
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(ScheduledTransaction obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ScheduledTransaction> load() {
		List<ScheduledTransaction> scheduledTransactions = new ArrayList<>();
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
				Account account = accDao.search(accountName);

				TransactionType transactionType = new TransactionType(data[2]);
				String frequency = data[3];
				int dueDate = Integer.parseInt(data[4]);
				double amount = Double.parseDouble(data[5]);

				ScheduledTransaction transaction = new ScheduledTransaction(scheduleName, account, transactionType, frequency, dueDate, amount);
				scheduledTransactions.add(transaction);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return scheduledTransactions;
	}

	@Override
	public ScheduledTransaction search(String id) {
		List <ScheduledTransaction> scheduledTransactions = load();
		for (ScheduledTransaction tran : scheduledTransactions) {
			if (id.equals(tran.getScheduleName() ) ) {
				return tran;
			}
		}
		return null;
	}

}
