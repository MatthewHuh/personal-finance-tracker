package application.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import application.Account;
import application.Transaction;
import application.TransactionType;
import application.dao.AccountDAO;
import application.dao.TransactionDAO;
import application.dao.TransactionTypeDAO;
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

public class EditTransactionController {

	@FXML
    private Label accountErrorMsg;

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
    private ChoiceBox<String> typeSelect;

    @FXML
    private Label transactionTypeErrorMsg;
    
    private static Transaction transaction;
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionTypeDAO transactionTypeDAO = new TransactionTypeDAO();
    private Map<String, Account> accounts;
    private Map<String, TransactionType> transactionTypes;
    
    public static void initializeTransaction(Transaction obj) {
    	transaction = obj;
    }
    
    public void initialize() {
        accounts = accountDAO.getAccounts();
        accountSelect.getItems().addAll(accounts.values().stream().map(Account::getName).toArray(String[]::new));
        accountSelect.getSelectionModel().select(transaction.getAccount().getName()); // Set default to current account name

        transactionTypes = transactionTypeDAO.getTransactionTypes();
        typeSelect.getItems().addAll(transactionTypes.values().stream().map(TransactionType::getTransactionType).toArray(String[]::new));
        typeSelect.getSelectionModel().select(transaction.getTransactionType().getTransactionType()); // Set default to current transaction type
        
        datePicker.setValue(transaction.getTransactionDate());
        
        transactionDescription.setText(transaction.getDescription());
        
        paymentAmount.setText(Double.toString(transaction.getPaymentAmount()));
        depositAmount.setText(Double.toString(transaction.getDepositAmount()));
    } 

    @FXML
    void onCancelAction(ActionEvent event) {
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

    @FXML
    void onSaveAction(ActionEvent event) {
    	if (validateFields()) {
            Account selectedAccount = accounts.values().stream()
                .filter(a -> a.getName().equals(accountSelect.getValue()))
                .findFirst()
                .orElse(null);

            TransactionType selectedTransactionType = transactionTypes.values().stream()
                .filter(t -> t.getTransactionType().equals(typeSelect.getValue()))
                .findFirst()
                .orElse(null);

            LocalDate transactionDate = datePicker.getValue();
            String description = transactionDescription.getText();
            double payment = paymentAmount.getText().isEmpty() ? 0 : Double.parseDouble(paymentAmount.getText());
            double deposit = depositAmount.getText().isEmpty() ? 0 : Double.parseDouble(depositAmount.getText());

            Transaction updatedTransaction = new Transaction(selectedAccount, selectedTransactionType, transactionDate, description, payment, deposit);
            transactionDAO.update(transaction, updatedTransaction);

            // Close window or navigate to another page if needed
            try {
        		// Load the Home.fxml file
        		Parent transactionsView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewTransactions.fxml"));
        		
        		// Get the current stage
    			Stage stage = (Stage) createAccountPane.getScene().getWindow();
    			
    			// Set the new scene
    			stage.setScene(new Scene(transactionsView));
    			stage.setTitle("Home"); // Optional: Set the window title
    			stage.show();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }	
    }
    
    private boolean validateFields() {
        boolean isValid = true;

        if (accountSelect.getValue() == null) {
            accountErrorMsg.setText("Please select an account.");
            isValid = false;
        } else {
        	accountErrorMsg.setText("");
        }

        if (typeSelect.getValue() == null) {
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