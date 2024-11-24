package application.controller;

import java.io.IOException;
import java.util.Map;

import application.Account;
import application.ScheduledTransaction;
import application.TransactionType;
import application.dao.AccountDAO;
import application.dao.ScheduledTransactionDAO;
import application.dao.TransactionTypeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EditScheduledTransactionController {

    @FXML
    private Label accountErrorMsg;

    @FXML
    private ChoiceBox<String> accountSelect;

    @FXML
    private Label amountErrorMsg;

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private TextField dueDate;

    @FXML
    private Label dueDateErrorMsg;

    @FXML
    private Label frequencyErrorMsg;

    @FXML
    private ChoiceBox<String> frequencySelect;

    @FXML
    private Label nameErrorMsg;

    @FXML
    private TextField paymentAmount;

    @FXML
    private TextField scheduleName;

    @FXML
    private Label transactionTypeErrorMsg;

    @FXML
    private ChoiceBox<String> typeSelect;
    
    private static ScheduledTransaction scheduledTransaction;
    private final ScheduledTransactionDAO scheduledTransactionDAO = new ScheduledTransactionDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionTypeDAO transactionTypeDAO = new TransactionTypeDAO();
    private Map<String, Account> accounts;
    private Map<String, TransactionType> transactionTypes;
    
    public static void initializeScheduledTransaction(ScheduledTransaction obj) {
    	scheduledTransaction = obj;
    }
    
    public void initialize() {
    	accounts = accountDAO.getAccounts();
        accountSelect.getItems().addAll(accounts.values().stream().map(Account::getName).toArray(String[]::new));
        accountSelect.getSelectionModel().select(scheduledTransaction.getAccount().getName()); // Set default to first item

        transactionTypes = transactionTypeDAO.getTransactionTypes();
        typeSelect.getItems().addAll(transactionTypes.values().stream().map(TransactionType::getTransactionType).toArray(String[]::new));
        typeSelect.getSelectionModel().select(scheduledTransaction.getType().getTransactionType()); // Set default to first item
        
        frequencySelect.getItems().add("Monthly");
        frequencySelect.getSelectionModel().selectFirst(); // Set default to first item
    	
    	scheduleName.setText(scheduledTransaction.getScheduleName());
    	dueDate.setText(Integer.toString(scheduledTransaction.getDueDate()));
    	paymentAmount.setText(Double.toString(scheduledTransaction.getAmount()));
    } 

    @FXML
    void onCancelAction(ActionEvent event) {
    	try {
    		// Load the Home.fxml file
    		Parent homeView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewScheduledTransactions.fxml"));
    		
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
    void onSaveAction(ActionEvent event) {
    	if(validate()) {
    		String name = scheduleName.getText();
    		Account account = accountDAO.getAccounts().get(accountSelect.getValue());
    		TransactionType type = transactionTypeDAO.getTransactionTypes().get(typeSelect.getValue());
    		String frequency = frequencySelect.getValue();
    		int due = Integer.parseInt(dueDate.getText());
    		double amount = Double.parseDouble(paymentAmount.getText());
    		
    		ScheduledTransaction update = new ScheduledTransaction(name, account, type, frequency, due, amount);
    		scheduledTransactionDAO.update(scheduledTransaction, update);
    		
    		try {
	    		// Load the Home.fxml file
	    		Parent scheduledTransationView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewScheduledTransactions.fxml"));
	    		
	    		// Get the current stage
				Stage stage = (Stage) createAccountPane.getScene().getWindow();
				
				// Set the new scene
				stage.setScene(new Scene(scheduledTransationView));
				stage.setTitle("Home"); // Optional: Set the window title
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private boolean validate() {
    	// boolean to verify valid inputs
    	boolean inputValidate = true;
    	
    	// check if account name input
    	if(scheduleName.getText().equals("")) {
    		nameErrorMsg.setText("Please enter the schedule name");
    		inputValidate = false;
    	} 
    	else if(!scheduledTransaction.getScheduleName().equals(scheduleName.getText()) && 
    			scheduledTransactionDAO.getScheduledTransactions().get(scheduleName.getText()) != null) {
    		nameErrorMsg.setText("Schedule name taken. Please enter a unique name");
    		inputValidate = false;
    	}
    	else {
    		nameErrorMsg.setText("");
    	}
    	
    	if(accountSelect.getValue() == null) {
    		accountErrorMsg.setText("Please select an account");
    		inputValidate = false;
    	}
    	else {
    		accountErrorMsg.setText("");
    	}
    	
    	if(typeSelect.getValue() == null) {
    		transactionTypeErrorMsg.setText("Please select a transaction type");
    		inputValidate = false;
    	}
    	else {
    		transactionTypeErrorMsg.setText("");
    	}
    	
    	if(frequencySelect.getValue() == null) {
    		frequencyErrorMsg.setText("Please select the frequency");
    		inputValidate = false;
    	}
    	else {
    		frequencyErrorMsg.setText("");
    	}
    	
    	if(dueDate.getText().equals("")) {
    		dueDateErrorMsg.setText("Please enter the due date");
    		inputValidate = false;
    	}
    	else {
    		dueDateErrorMsg.setText("");
    		try {
    			Integer.parseInt(dueDate.getText());
    		} catch(NumberFormatException e) {
    			dueDateErrorMsg.setText("Please enter a integer value");
        		inputValidate = false;
    		}
    	}
    	
    	if(paymentAmount.getText().equals("")) {
    		amountErrorMsg.setText("Please enter the payment amount");
    		inputValidate = false;
    	}
    	else {
    		amountErrorMsg.setText("");
    		try {
    			Double.parseDouble(paymentAmount.getText());
    		} catch(NumberFormatException e) {
    			amountErrorMsg.setText("Please enter a double value");
        		inputValidate = false;
    		}
    	}
    	return inputValidate;
    }

}
