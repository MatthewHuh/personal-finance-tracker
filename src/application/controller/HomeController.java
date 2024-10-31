package application.controller;


import application.Account;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import application.DataAccessLayer;
import java.util.List;

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
    private Button searchTransactionButton;

    @FXML
    private Button viewReportsButton;

    @FXML
    public void initialize() {
        // Set up the columns to match Account class properties
        accountNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        accountDateCol.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
        accountBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Load accounts from the CSV file
        List<Account> accounts = DataAccessLayer.loadAccounts();
        if (accounts != null) {
            ObservableList<Account> accountList = FXCollections.observableArrayList(accounts);
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
            Parent createTransactionView = FXMLLoader.load(getClass().getClassLoader().getResource("view/CreateTransaction.fxml"));

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

    }

    @FXML
    void onSearchTransaction(ActionEvent event) {

    }

    @FXML
    void onViewReports(ActionEvent event) {

    }

}
