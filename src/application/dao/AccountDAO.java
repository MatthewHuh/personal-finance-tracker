package application.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import application.Account;

public class AccountDAO {

	public void createAccount(Account acc) {
		// specify path of the csv file
	    File file = new File("db/accounts.csv");
	    try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file, true); 
	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter writer = new CSVWriter(outputfile, ',' , 
	        		CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END); 
	  
	        // add data to csv 
	        String[] data = { acc.getName(), acc.getOpeningDate().toString(), String.valueOf(acc.getBalance()) }; 
	        writer.writeNext(data); 
	  
	        // closing writer connection 
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    }
	}
	
	
}
