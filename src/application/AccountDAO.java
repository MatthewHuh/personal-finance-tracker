package application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AccountDAO {

	@SuppressWarnings("unchecked")
	public void createAccount(Account acc) {
		// Load existing accounts data from the file
		JSONArray accountsArray = loadExistingAccounts();

		// Create a new JSON object for the account and add it to the array
		JSONObject accountObject = new JSONObject();
		accountObject.put("name", acc.getName());
		accountObject.put("date", acc.getOpeningDate().toString());
		accountObject.put("balance", acc.getBalance());

		// Add the new account to the existing array
		accountsArray.add(accountObject);

		// Write the updated array back to the file
		try (FileWriter file = new FileWriter("DB/accounts.json")) {
			file.write(accountsArray.toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JSONArray loadExistingAccounts() {
		JSONParser jsonParser = new JSONParser();
		JSONArray accountsArray;

		try (FileReader reader = new FileReader("DB/accounts.json")) {
			Object obj = jsonParser.parse(reader);
			accountsArray = (JSONArray) obj;
		} catch (IOException | ParseException e) {
			// If the file does not exist or cannot be parsed, return a new empty array
			accountsArray = new JSONArray();
		}

		return accountsArray;
	}
}
