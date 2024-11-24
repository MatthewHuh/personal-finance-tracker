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

    }

}