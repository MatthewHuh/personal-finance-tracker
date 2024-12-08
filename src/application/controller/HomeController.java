package application.controller;


import java.time.LocalDate;
import java.util.Map;

import application.Account;
import application.Format;
import application.dao.AccountDAO;
import application.dao.DAOInt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private TableView<Account> accountTable;
	
	@FXML
    private TableColumn<Account, Double> accountBalanceCol;

    @FXML
    private TableColumn<Account, LocalDate> accountDateCol;

    @FXML
    private TableColumn<Account, String> accountNameCol;

    @FXML
    private Button createNewAccountButton;

    @FXML
    private Button defineNewTransactionButton;

    @FXML
    private Button enterTransactionButton;

    @FXML
    private Button scheduleTransactionButton;
    
    @FXML
    private Button viewTransactionsButton;
    
    @FXML
    private Button viewScheduledTransactionsButton;

    @FXML
    private Button viewReportsButton;

    @FXML
    public void initialize() {
        // Set up the columns to match Account class properties
        accountNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        accountDateCol.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
        accountBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Load accounts from the CSV file
        DAOInt<Account> acc = new AccountDAO();
        Map<String, Account> accounts = ((AccountDAO) acc).getAccounts();
        if (accounts != null) {
            ObservableList<Account> accountList = FXCollections.observableArrayList(accounts.values());
            accountTable.setItems(accountList);
        }
        // sort by opening date descending
        accountTable.getSortOrder().add(accountDateCol);
    }
    
    @FXML
    void onCreateNewAccount(ActionEvent event) {
        try {
            // Load the CreateAccount.fxml file
            Parent createAccountView = FXMLLoader.load(getClass().getClassLoader().getResource("view/CreateAccount.fxml"));

            // Get the current stage
            Stage stage = (Stage) createNewAccountButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(createAccountView));
            stage.setTitle("Create New Account"); // Set the window title
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for any errors
        }
    }

    @FXML
    void onDefineNewTransaction(ActionEvent event) {
    	 try {
             // Load the DefineTransactionType.fxml file
             Parent defineTransactionTypeView = FXMLLoader.load(getClass().getClassLoader().getResource("view/DefineTransactionType.fxml"));

             // Get the current stage
             Stage stage = (Stage) defineNewTransactionButton.getScene().getWindow();

             // Set the new scene
             stage.setScene(new Scene(defineTransactionTypeView));
             stage.setTitle("Define New Transaction Type"); // Set the window title
             stage.show();
         } catch (Exception e) {
             e.printStackTrace(); // Print stack trace for any errors
         }
    }

    @FXML
    void onEnterTransaction(ActionEvent event) {
    	try {
            // Load the CreateTransaction.fxml file
    		TransactionController.initialize(Format.CREATE);
            Parent createTransactionView = FXMLLoader.load(getClass().getClassLoader().getResource("view/Transaction.fxml"));
            
            // Get the current stage
            Stage stage = (Stage) enterTransactionButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(createTransactionView));
            stage.setTitle("Create New Transaction"); // Set the window title
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for any errors
        }
    }

    @FXML
    void onScheduleTransaction(ActionEvent event) {
    	try {
            // Load the CreateTransaction.fxml file
    		ScheduleTransactionController.initialize(Format.CREATE);
            Parent scheduleTransactionView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ScheduleTransaction.fxml"));

            // Get the current stage
            Stage stage = (Stage) scheduleTransactionButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(scheduleTransactionView));
            stage.setTitle("Schedule New Transaction"); // Set the window title
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for any errors
        }
    }
    

    @FXML
    void onViewTransaction(ActionEvent event) {
    	try {
            // Load the CreateTransaction.fxml file
            Parent viewTransactionView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewTransactions.fxml"));

            // Get the current stage
            Stage stage = (Stage) viewTransactionsButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(viewTransactionView));
            stage.setTitle("View Transactions"); // Set the window title
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for any errors
        }
    }

    @FXML
    void onViewScheduledTransaction(ActionEvent event) {
    	try {
            // Load the CreateTransaction.fxml file
            Parent viewScheduledTransactionView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewScheduledTransactions.fxml"));

            // Get the current stage
            Stage stage = (Stage) viewScheduledTransactionsButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(viewScheduledTransactionView));
            stage.setTitle("View Scheduled Transactions"); // Set the window title
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for any errors
        }
    }

    @FXML
    void onViewReports(ActionEvent event) {
    	try {
            // Load the CreateTransaction.fxml file
            Parent viewReports = FXMLLoader.load(getClass().getClassLoader().getResource("view/ViewReports.fxml"));

            // Get the current stage
            Stage stage = (Stage) viewReportsButton.getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(viewReports));
            stage.setTitle("View Reports"); // Set the window title
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for any errors
        }
    }

}
