package application.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import application.Transaction;
import application.Account;
import application.Format;
import application.TransactionType;
import application.dao.TransactionDAO;
import application.dao.AccountDAO;
import application.dao.TransactionTypeDAO;


public class TransactionController {

    @FXML
    private Label AccountErrorMsg;

    @FXML
    private ChoiceBox<String> accountSelect;

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField depositAmount;

    @FXML
    private Label depositAmountErrorMsg;

    @FXML
    private TextField paymentAmount;

    @FXML
    private Label paymentAmountErrorMsg;

    @FXML
    private Label transactionDateErrorMsg;

    @FXML
    private Label transactionDescErrorMsg;

    @FXML
    private TextArea transactionDescription;

    @FXML
    private ChoiceBox<String> transactionSelect;

    @FXML
    private Label transactionTypeErrorMsg;
    
    @FXML
    private Label pageTitle;
    
    private static Transaction transaction;
    private static Format format;
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionTypeDAO transactionTypeDAO = new TransactionTypeDAO();
    private Map<String, Account> accounts;
    private Map<String, TransactionType> transactionTypes;

    public static void initialize(Format form) {
    	transaction = null;
    	format = form;
    }
    
    public static void initialize(Transaction theTransaction, Format theFormat) {
    	transaction = theTransaction;
    	format = theFormat;
    }
    
    public void initialize() {
    	if(format.equals(Format.CREATE)) {
        	initializeCreate();
    	}
    	else if(format.equals(Format.EDIT)) {
    		initializeEdit();
    	}
    	
    }
    
    private void initializeCreate() {
    	// Populate account and transaction type ChoiceBoxes
    	accounts = accountDAO.getAccounts();
        accountSelect.getItems().addAll(accounts.values().stream().map(Account::getName).toArray(String[]::new));
        accountSelect.getSelectionModel().selectFirst(); // Set default to first item

        transactionTypes = transactionTypeDAO.getTransactionTypes();
        transactionSelect.getItems().addAll(transactionTypes.values().stream().map(TransactionType::getTransactionType).toArray(String[]::new));
        transactionSelect.getSelectionModel().selectFirst(); // Set default to first item

        // Set default date to today
        datePicker.setValue(LocalDate.now());
        
        pageTitle.setText("Enter Transaction");
    }
    
    private void initializeEdit() {
    	accounts = accountDAO.getAccounts();
        accountSelect.getItems().addAll(accounts.values().stream().map(Account::getName).toArray(String[]::new));
        accountSelect.getSelectionModel().select(transaction.getAccount().getName()); // Set default to current account name

        transactionTypes = transactionTypeDAO.getTransactionTypes();
        transactionSelect.getItems().addAll(transactionTypes.values().stream().map(TransactionType::getTransactionType).toArray(String[]::new));
        transactionSelect.getSelectionModel().select(transaction.getTransactionType().getTransactionType()); // Set default to current transaction type
        
        datePicker.setValue(transaction.getTransactionDate());
        
        transactionDescription.setText(transaction.getDescription());
        
        paymentAmount.setText(Double.toString(transaction.getPaymentAmount()));
        depositAmount.setText(Double.toString(transaction.getDepositAmount()));
        
        pageTitle.setText("Edit Transaction");
    } 


    @FXML
    void onCancelAction(ActionEvent event) {
    	if(format.equals(Format.CREATE)) {
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
    	else if(format.equals(Format.EDIT)) {
    		try {
        		// Load the Home.fxml file
        		Parent homeView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewTransactions.fxml"));
        		
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
    	
    }

    @FXML
    void onSubmitAction(ActionEvent event) {
    	if (validateFields()) {
            Account selectedAccount = accounts.values().stream()
                .filter(a -> a.getName().equals(accountSelect.getValue()))
                .findFirst()
                .orElse(null);

            TransactionType selectedTransactionType = transactionTypes.values().stream()
                .filter(t -> t.getTransactionType().equals(transactionSelect.getValue()))
                .findFirst()
                .orElse(null);

            LocalDate transactionDate = datePicker.getValue();
            String description = transactionDescription.getText();
            double payment = paymentAmount.getText().isEmpty() ? 0 : Double.parseDouble(paymentAmount.getText());
            double deposit = depositAmount.getText().isEmpty() ? 0 : Double.parseDouble(depositAmount.getText());
            
            Transaction newTransaction = new Transaction(selectedAccount, selectedTransactionType, transactionDate, description, payment, deposit);
            
            if(format.equals(Format.CREATE)) {
                transactionDAO.create(newTransaction);
        	}
        	else if(format.equals(Format.EDIT)) {
                transactionDAO.update(transaction, newTransaction);
        	}
            

            // Close window or navigate to another page
            if(format.equals(Format.CREATE)) {
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
        	else if(format.equals(Format.EDIT)) {
        		try {
            		// Load the Home.fxml file
            		Parent transactionsView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewTransactions.fxml"));
            		
            		// Get the current stage
        			Stage stage = (Stage) createAccountPane.getScene().getWindow();
        			
        			// Set the new scene
        			stage.setScene(new Scene(transactionsView));
        			stage.setTitle("View Transactions"); // Optional: Set the window title
        			stage.show();
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	}
            
        }	
    }
    
    private boolean validateFields() {
        boolean isValid = true;

        if (accountSelect.getValue() == null) {
            AccountErrorMsg.setText("Please select an account.");
            isValid = false;
        } else {
            AccountErrorMsg.setText("");
        }

        if (transactionSelect.getValue() == null) {
            transactionTypeErrorMsg.setText("Please select a transaction type.");
            isValid = false;
        } else {
            transactionTypeErrorMsg.setText("");
        }

        if (datePicker.getValue() == null) {
            transactionDateErrorMsg.setText("Please select a date.");
            isValid = false;
        } else {
            transactionDateErrorMsg.setText("");
        }

        if (transactionDescription.getText().isEmpty()) {
            transactionDescErrorMsg.setText("Please enter a description.");
            isValid = false;
        } else {
            transactionDescErrorMsg.setText("");
        }

        if (paymentAmount.getText().isEmpty() && depositAmount.getText().isEmpty()) {
            paymentAmountErrorMsg.setText("Enter payment or deposit amount.");
            depositAmountErrorMsg.setText("Enter payment or deposit amount.");
            isValid = false;
        } else {
            paymentAmountErrorMsg.setText("");
            depositAmountErrorMsg.setText("");
            if(!paymentAmount.getText().isEmpty()) {
	            try {
	    			Double.parseDouble(paymentAmount.getText());
	    		} catch(NumberFormatException e) {
	    			paymentAmountErrorMsg.setText("Please enter a valid decimal number");
	    			isValid = false;
	    		}
            }
            if(!depositAmount.getText().isEmpty()) {
	            try {
	    			Double.parseDouble(depositAmount.getText());
	    		} catch(NumberFormatException e) {
	    			depositAmountErrorMsg.setText("Please enter a valid decimal number");
	    			isValid = false;
	    		}
            }
        }

        return isValid;
    }
    
   

}
