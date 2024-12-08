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
import application.dao.DAOInt;
import application.dao.TransactionTypeDAO;

/**
 * Controller class for creating or editing transactions. This class supports two modes:
 * <ul>
 *   <li>{@link Format#CREATE}: creating a new transaction</li>
 *   <li>{@link Format#EDIT}: editing an existing transaction</li>
 * </ul>
 * Users can select an account, a transaction type, specify the date and amounts (payment or deposit),
 * and provide a description. The transactions are persisted via a DAO.
 */
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
    private final DAOInt<Transaction> transactionDAO = new TransactionDAO();
    private final DAOInt<Account> accountDAO = new AccountDAO();
    private final DAOInt<TransactionType> transactionTypeDAO = new TransactionTypeDAO();

    /**
     * Initializes the controller for the CREATE mode. Clears any existing transaction reference
     * and sets the {@link Format} to CREATE.
     *
     * @param form The format to set, typically {@link Format#CREATE}.
     */
    public static void initialize(Format form) {
    	transaction = null;
    	format = form;
    }
    
    /**
     * Initializes the controller for the EDIT mode with an existing transaction.
     *
     * @param theTransaction The existing transaction to edit.
     * @param theFormat      The format to set, typically {@link Format#EDIT}.
     */
    public static void initialize(Transaction theTransaction, Format theFormat) {
    	transaction = theTransaction;
    	format = theFormat;
    }
    
    /**
     * Initializes the UI components after the root element has been processed.
     * Based on the current {@link Format}, it sets up the UI for either creating 
     * or editing a transaction.
     */
    public void initialize() {
    	if(format.equals(Format.CREATE)) {
        	initializeCreate();
    	}
    	else if(format.equals(Format.EDIT)) {
    		initializeEdit();
    	}
    }
   
    /**
     * Initializes the UI elements for the CREATE mode:
     * <ul>
     *   <li>Populates the account choice box with available accounts</li>
     *   <li>Populates the transaction type choice box with available transaction types</li>
     *   <li>Sets the default date picker value to the current date</li>
     *   <li>Updates the page title</li>
     * </ul>
     */
    private void initializeCreate() {
    	// Populate account and transaction type ChoiceBoxes
    	Map<String, Account> accounts;
    	accounts = ((AccountDAO) accountDAO).getAccounts();
        accountSelect.getItems().addAll(accounts.values().stream().map(Account::getName).toArray(String[]::new));
        accountSelect.getSelectionModel().selectFirst(); // Set default to first item

        Map<String, TransactionType> transactionTypes;
        transactionTypes = ((TransactionTypeDAO) transactionTypeDAO).getTransactionTypes();
        transactionSelect.getItems().addAll(transactionTypes.values().stream().map(TransactionType::getTransactionType).toArray(String[]::new));
        transactionSelect.getSelectionModel().selectFirst(); // Set default to first item

        // Set default date to today
        datePicker.setValue(LocalDate.now());
        
        pageTitle.setText("Enter Transaction");
    }
    
    /**
     * Initializes the UI elements for the EDIT mode:
     * <ul>
     *   <li>Populates the account and transaction type choice boxes with available values,
     *       and selects the existing transaction's values</li>
     *   <li>Sets the date picker to the existing transaction's date</li>
     *   <li>Pre-fills the description and amounts</li>
     *   <li>Updates the page title</li>
     * </ul>
     */
    private void initializeEdit() {
    	Map<String, Account> accounts;
    	accounts = ((AccountDAO) accountDAO).getAccounts();
        accountSelect.getItems().addAll(accounts.values().stream().map(Account::getName).toArray(String[]::new));
        accountSelect.getSelectionModel().select(transaction.getAccount().getName()); // Set default to current account name

        Map<String, TransactionType> transactionTypes;
        transactionTypes = ((TransactionTypeDAO) transactionTypeDAO).getTransactionTypes();
        transactionSelect.getItems().addAll(transactionTypes.values().stream().map(TransactionType::getTransactionType).toArray(String[]::new));
        transactionSelect.getSelectionModel().select(transaction.getTransactionType().getTransactionType()); // Set default to current transaction type
        
        datePicker.setValue(transaction.getTransactionDate());
        
        transactionDescription.setText(transaction.getDescription());
        
        paymentAmount.setText(Double.toString(transaction.getPaymentAmount()));
        depositAmount.setText(Double.toString(transaction.getDepositAmount()));
        
        pageTitle.setText("Edit Transaction");
    } 

    /**
     * Handles the action triggered by the "Cancel" button.
     * Depending on the current {@link Format}, this method navigates:
     * <ul>
     *   <li>CREATE mode: back to the Home view</li>
     *   <li>EDIT mode: back to the ViewTransactions view</li>
     * </ul>
     *
     * @param event The action event triggered by the "Cancel" button.
     */
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
    
    /**
     * Handles the action triggered by the "Submit" button.
     * Validates the user input fields. If valid:
     * <ul>
     *   <li>Constructs a new {@link Transaction} object from the user input</li>
     *   <li>In CREATE mode, creates a new transaction in the data store</li>
     *   <li>In EDIT mode, updates the existing transaction in the data store</li>
     *   <li>Navigates back to the Home or ViewTransactions view depending on the mode</li>
     * </ul>
     *
     * @param event The action event triggered by the "Submit" button.
     */
    @FXML
    void onSubmitAction(ActionEvent event) {
    	if (validateFields()) {
    		Map<String, Account> accounts = ((AccountDAO) accountDAO).getAccounts();
            Account selectedAccount = accounts.values().stream()
                .filter(a -> a.getName().equals(accountSelect.getValue()))
                .findFirst()
                .orElse(null);

            Map<String, TransactionType> transactionTypes;
            transactionTypes = ((TransactionTypeDAO) transactionTypeDAO).getTransactionTypes();
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
    
    /**
     * Validates the user inputs from the UI fields.
     * Ensures that an account and transaction type are selected, a date is chosen,
     * a description is provided, and at least one amount (payment or deposit) is entered
     * with a valid decimal number format.
     *
     * @return true if all fields are valid; false otherwise.
     */
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
