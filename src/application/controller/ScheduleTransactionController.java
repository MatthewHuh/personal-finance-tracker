package application.controller;

import java.io.IOException;
import java.util.Map;

import application.Account;
import application.Format;
import application.ScheduledTransaction;
import application.TransactionType;
import application.dao.AccountDAO;
import application.dao.DAOInt;
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

/**
 * Controller class for scheduling transactions.
 * This class allows the user to create or edit scheduled transactions,
 * including specifying the account, transaction type, frequency, due date, and amount.
 * The controller supports two modes:
 * <ul>
 *   <li>{@link Format#CREATE}: creating a new scheduled transaction</li>
 *   <li>{@link Format#EDIT}: editing an existing scheduled transaction</li>
 * </ul>
 */
public class ScheduleTransactionController {

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
    
    @FXML
    private Label pageTitle;

    private static ScheduledTransaction scheduledTransaction;
    private static Format format;
    private final DAOInt<ScheduledTransaction> scheduledTransactionDAO = new ScheduledTransactionDAO();
    private final DAOInt<Account> accountDAO = new AccountDAO();
    private final DAOInt<TransactionType> transactionTypeDAO = new TransactionTypeDAO();
    
    /**
     * Initializes the static properties of this controller before the scene is displayed.
     * Sets the page to "CREATE" mode and clears any previously held {@link ScheduledTransaction}.
     *
     * @param theFormat The format to set, usually {@link Format#CREATE}.
     */
    public static void initialize(Format theFormat) {
    	scheduledTransaction = null;
    	format = theFormat;
    }
    
    /**
     * Initializes the static properties of this controller with an existing {@link ScheduledTransaction}.
     * Sets the controller to "EDIT" mode for the given scheduled transaction.
     *
     * @param theSchedule The existing scheduled transaction to edit.
     * @param theFormat   The format to set, usually {@link Format#EDIT}.
     */
    public static void initialize(ScheduledTransaction theSchedule, Format theFormat) {
		scheduledTransaction = theSchedule;
		format = theFormat;
    }
    
    /**
     * Initializes the controller after its root element has been completely processed.
     * Depending on the {@link Format} (CREATE or EDIT), this method initializes the fields
     * and choice boxes with appropriate default values and data from existing transactions.
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
     * Initializes the controller for the CREATE mode.
     * Populates the account, transaction type, and frequency choice boxes with available values.
     * Sets default selections and updates the page title.
     */
    private void initializeCreate() {
    	// Populate account and transaction type ChoiceBoxes
    	Map<String, Account> accounts;
		accounts = ((AccountDAO) accountDAO).getAccounts();
        accountSelect.getItems().addAll(accounts.values().stream().map(Account::getName).toArray(String[]::new));
        accountSelect.getSelectionModel().selectFirst(); // Set default to first item
        
        Map<String, TransactionType> transactionTypes;
        transactionTypes = ((TransactionTypeDAO) transactionTypeDAO).getTransactionTypes();
        typeSelect.getItems().addAll(transactionTypes.values().stream().map(TransactionType::getTransactionType).toArray(String[]::new));
        typeSelect.getSelectionModel().selectFirst(); // Set default to first item
        
        frequencySelect.getItems().add("Monthly");
        frequencySelect.getSelectionModel().selectFirst(); // Set default to first item
        
        pageTitle.setText("Schedule Transaction");
    }
    
    /**
     * Initializes the controller for the EDIT mode.
     * Populates the account, transaction type, and frequency choice boxes and sets their values
     * to those of the {@link #scheduledTransaction} being edited.
     * Also pre-fills the schedule name, due date, and payment amount fields.
     */
    private void initializeEdit() {
    	// Populate account and transaction type ChoiceBoxes
    	Map<String, Account> accounts;
    	accounts = ((AccountDAO) accountDAO).getAccounts();
        accountSelect.getItems().addAll(accounts.values().stream().map(Account::getName).toArray(String[]::new));
        accountSelect.getSelectionModel().select(scheduledTransaction.getAccount().getName()); // Set default to first item

        Map<String, TransactionType> transactionTypes;
        transactionTypes = ((TransactionTypeDAO) transactionTypeDAO).getTransactionTypes();
        typeSelect.getItems().addAll(transactionTypes.values().stream().map(TransactionType::getTransactionType).toArray(String[]::new));
        typeSelect.getSelectionModel().select(scheduledTransaction.getType().getTransactionType()); // Set default to first item
        
        frequencySelect.getItems().add("Monthly");
        frequencySelect.getSelectionModel().selectFirst(); // Set default to first item
    	
    	scheduleName.setText(scheduledTransaction.getScheduleName());
    	dueDate.setText(Integer.toString(scheduledTransaction.getDueDate()));
    	paymentAmount.setText(Double.toString(scheduledTransaction.getAmount()));
    	
    	pageTitle.setText("Edit Scheduled Transaction");
    }
    
    /**
     * Navigates to the appropriate home view based on the current {@link Format}.
     * If CREATE mode, navigates to Home.fxml. If EDIT mode, navigates to ViewScheduledTransactions.fxml.
     */
    private void exit() {
    	// check which page to navigate to based on current page format
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
        		Parent homeView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewScheduledTransactions.fxml"));
        		
        		// Get the current stage
    			Stage stage = (Stage) createAccountPane.getScene().getWindow();
    			
    			// Set the new scene
    			stage.setScene(new Scene(homeView));
    			stage.setTitle("View Scheduled Transactions"); // Optional: Set the window title
    			stage.show();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
    }

    /**
     * Handles the action triggered by the "Cancel" button.
     * Navigates back to the appropriate home or view page without saving changes.
     *
     * @param event The action event triggered by the "Cancel" button.
     */
    @FXML
    void onCancelAction(ActionEvent event) {
    	exit();
    }

    /**
     * Handles the action triggered by the "Submit" button.
     * Validates the user input and if valid, either creates a new scheduled transaction or updates
     * an existing one, then navigates back to the appropriate home or view page.
     *
     * @param event The action event triggered by the "Submit" button.
     */
    @FXML
    void onSubmitAction(ActionEvent event) {
    	// check if inputs are valid
    	if(validate()) {
    		// create ScheduledTransaction Object
    		String name = scheduleName.getText();
    		Account account = ((AccountDAO) accountDAO).getAccounts().get(accountSelect.getValue());
    		TransactionType type = ((TransactionTypeDAO) transactionTypeDAO).getTransactionTypes().get(typeSelect.getValue());
    		String frequency = frequencySelect.getValue();
    		int due = Integer.parseInt(dueDate.getText());
    		double amount = Double.parseDouble(paymentAmount.getText());
    		
    		ScheduledTransaction newScheduledTransaction = new ScheduledTransaction(name, account, type, frequency, due, amount);
    		
    		// create or update in CSV file
    		if(format.equals(Format.CREATE)) {
    			scheduledTransactionDAO.create(newScheduledTransaction);
        	}
        	else if(format.equals(Format.EDIT)) {
        		scheduledTransactionDAO.update(scheduledTransaction, newScheduledTransaction);
        	}
    		
	    	exit();
    	}
    }
    
    /**
     * Determines which validation method to call based on the current {@link Format}.
     *
     * @return true if the input is valid; false otherwise.
     */
    private boolean validate() {
    	if(format.equals(Format.CREATE)) {
    		return validateCreate();
    	}
    	else if(format.equals(Format.EDIT)) {
    		return validateEdit();
    	}
    	return false;
    }
    
    /**
     * Validates the user input fields for creating a new scheduled transaction.
     * Checks schedule name uniqueness, account selection, transaction type selection, frequency, due date, and amount.
     *
     * @return true if all inputs are valid; false otherwise.
     */
    private boolean validateCreate() {
    	// boolean to verify valid inputs
    	boolean inputValidate = true;
    	
    	// check if account name input
    	if(scheduleName.getText().equals("")) {
    		nameErrorMsg.setText("Please enter the schedule name");
    		inputValidate = false;
    	} 
    	else if(((ScheduledTransactionDAO) scheduledTransactionDAO).getScheduledTransactions().get(scheduleName.getText()) != null) {
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
    
    /**
     * Validates the user input fields for editing an existing scheduled transaction.
     * Ensures the edited schedule name is not taken by another transaction, and checks
     * account selection, transaction type selection, frequency, due date, and amount.
     *
     * @return true if all inputs are valid; false otherwise.
     */
    private boolean validateEdit() {
    	// boolean to verify valid inputs
    	boolean inputValidate = true;
    	
    	// check if account name input
    	if(scheduleName.getText().equals("")) {
    		nameErrorMsg.setText("Please enter the schedule name");
    		inputValidate = false;
    	} 
    	else if(!scheduledTransaction.getScheduleName().equals(scheduleName.getText()) && 
    			((ScheduledTransactionDAO) scheduledTransactionDAO).getScheduledTransactions().get(scheduleName.getText()) != null) {
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
