package application.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVWriter;

import application.TransactionType;

public class TransactionTypeDAO implements DAOInt<TransactionType> {

	private static final String TRANSACTION_TYPE_FILE = "db/transactionTypes.csv"; // path to accounts.csv
	private static final Map<String, TransactionType> TRANSACTION_TYPES = load();

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
	        
	        //
	        TRANSACTION_TYPES.put(obj.getTransactionType(), obj);
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

	private static HashMap<String, TransactionType> load() {
		HashMap<String, TransactionType> types = new HashMap<>();
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
                types.put(type.getTransactionType(), type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return types;
	}
	
	public Map<String, TransactionType> getTransactionTypes() {
		return TRANSACTION_TYPES;
	}
}
