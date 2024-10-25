package application;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

public class AccountDAO {
	
	private JSONObject obj;
	
	public AccountDAO() {
		obj = new JSONObject();
	}
	
	public void createAccount(Account acc) {
		obj.put("name", acc.getName());
		obj.put("date", acc.getOpeningDate());
		obj.put("balance", acc.getBalance());
		
		try(FileWriter file = new FileWriter("DB/accounts.json")){
			file.write(obj.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
