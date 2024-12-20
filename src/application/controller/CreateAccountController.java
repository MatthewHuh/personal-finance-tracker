package application.controller;

import java.io.IOException;
import java.time.LocalDate;

import application.Account;
import application.dao.AccountDAO;
import application.dao.DAOInt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CreateAccountController {

    @FXML
    private Label accountNameErrorMsg;

    @FXML
    private TextField accountNameText;

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private Label openingBalanceErrorMsg;

    @FXML
    private TextField openingBalanceText;

    @FXML
    private Label openingDateErrorMsg;

    @FXML
    private DatePicker openingDatePicker;

	private DAOInt<Account> accDao;

    // set default value in date picker to current date
    @FXML
    public void initialize() {
    	openingDatePicker.setValue(LocalDate.now());
    	accDao = new AccountDAO(); // initialize accountDAO so that the same one is used
    }   
    
    @FXML
    void onCancelAction(ActionEvent event) {
    	try {
    		// Load the Home.fxml file
    		Parent homeView = FXMLLoader.load(getClass().getClassLoader().getResource("view/Home.fxml"));
    		
    		// Get the current stage
			Stage stage = (Stage) createAccountPane.getScene().getWindow();
			
			// Set the new scene
			stage.setScene(new Scene(homeView));
			stage.setTitle("Home"); // Optional: Set the window title
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void onDateAction(ActionEvent event) {

    }

    @FXML
    void onSubmitAction(ActionEvent event) {
    	// verify valid inputs
    	if(inputValidate()) {
    		//create the account object and create account
    		Account acc = new Account(accountNameText.getText(), openingDatePicker.getValue(), Double.parseDouble(openingBalanceText.getText()));
    		accDao.create(acc);
    		
		    try {
		    	// return to home page
		    	// Load the Home.fxml file
		    	Parent homeView = FXMLLoader.load(getClass().getClassLoader().getResource("view/Home.fxml"));
	
		    	// Get the current stage
				Stage stage = (Stage) createAccountPane.getScene().getWindow();
					
				// Set the new scene
				stage.setScene(new Scene(homeView));
				stage.setTitle("Home"); // Set the window title
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private boolean inputValidate() {
    	// boolean to verify valid inputs
    	boolean inputValidate = true;
    	
    	// check if account name input
    	if(accountNameText.getText().equals("")) {
    		accountNameErrorMsg.setText("Please enter the name");
    		inputValidate = false;
    	} // check if name is unique
    	else if(((AccountDAO) accDao).getAccounts().get(accountNameText.getText()) != null) {
    		accountNameErrorMsg.setText("Account name taken. Please enter a unique name");
    		inputValidate = false;
    	}
    	else {
    		accountNameErrorMsg.setText("");
    	}
    	
    	// check if date selected
    	if(openingDatePicker.getValue() == null) {
    		openingDateErrorMsg.setText("Please select a date");
    		inputValidate = false;
    	}
    	else {
    		openingDateErrorMsg.setText("");
    	}
    	
    	// check if opening balance input
    	if(openingBalanceText.getText().equals("")) {
    		openingBalanceErrorMsg.setText("Please enter the opening balance");
    		inputValidate = false;
    	}
    	else {
    		openingBalanceErrorMsg.setText("");
    		try {
    			Double.parseDouble(openingBalanceText.getText());
    		} catch(NumberFormatException e) {
    			openingBalanceErrorMsg.setText("Please enter a valid decimal number");
        		inputValidate = false;
    		}
    	}
    	return inputValidate;
    }
    
}
