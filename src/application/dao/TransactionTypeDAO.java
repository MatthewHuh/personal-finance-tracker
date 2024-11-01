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
import application.TransactionType;

public class TransactionTypeDAO implements DAOInt<TransactionType> {

	private static final String TRANSACTION_TYPE_FILE = "db/transactionTypes.csv"; // path to accounts.csv

	@Override
	public void create(TransactionType obj) {
		File file = new File(TRANSACTION_TYPE_FILE);
	    try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file, true); 
	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter writer = new CSVWriter(outputfile, ',' , 
	        		CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END); 
	  
	        // add data to csv 
	        String[] data = { obj.getTransactionType()}; 
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
	public void update(TransactionType obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TransactionType> load() {
		List<TransactionType> types = new ArrayList<>();
        File file = new File(TRANSACTION_TYPE_FILE);

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

                TransactionType type = new TransactionType(name);
                types.add(type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return types;
	}

	@Override
	public TransactionType search(String id) {
		List <TransactionType> types = load();
		for (TransactionType type : types) {
			if (id.equals(type.getTransactionType() ) ) {
				return type;
			}
		}
		return null;
	}
}
