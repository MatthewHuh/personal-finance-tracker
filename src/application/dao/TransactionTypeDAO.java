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

/**
 * A Data Access Object (DAO) implementation for managing {@link TransactionType} objects.
 * This DAO uses a CSV file as its data store. The file's path is specified by {@code TRANSACTION_TYPE_FILE}.
 * 
 * <p>The {@code TransactionTypeDAO} maintains an in-memory cache of transaction types 
 * which is loaded from the CSV file once at class initialization.</p>
 */
public class TransactionTypeDAO implements DAOInt<TransactionType> {

	private static final String TRANSACTION_TYPE_FILE = "db/transactionTypes.csv"; // path to accounts.csv
	private static final Map<String, TransactionType> TRANSACTION_TYPES = load();

	/**
     * Creates a new {@link TransactionType} record and appends it to the CSV file.
     * The newly created transaction type is also added to the in-memory cache.
     *
     * @param obj The {@code TransactionType} object to be created.
     */
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

	/**
     * Updates an existing {@link TransactionType} record with new values.
     * Currently, this method is not implemented.
     * To implement updates, you would need to:
     * <ul>
     *   <li>Load all transaction types from the CSV file</li>
     *   <li>Find and replace the current transaction type record with the updated one</li>
     *   <li>Overwrite the CSV file with the new records</li>
     * </ul>
     *
     * @param curr    The current {@code TransactionType} record as stored.
     * @param updated The {@code TransactionType} object with updated values.
     */
	@Override
	public void update(TransactionType curr, TransactionType updated) {
		// TODO Auto-generated method stub
		
	}

	/**
     * Loads all {@link TransactionType} records from the CSV file into a map, keyed by transaction type name.
     * This static method is called once at class initialization and populates the in-memory cache.
     *
     * @return A map of transaction type names to their corresponding {@link TransactionType} objects.
     */
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
	
	/**
     * Retrieves all loaded {@link TransactionType} objects.
     * The returned map is a cached representation of the transaction types loaded from the CSV file.
     *
     * @return A map of transaction type names to their corresponding {@link TransactionType} objects.
     */
	public Map<String, TransactionType> getTransactionTypes() {
		return TRANSACTION_TYPES;
	}
}
