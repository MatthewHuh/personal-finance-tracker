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

    }

}
