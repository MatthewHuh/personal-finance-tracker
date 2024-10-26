package application.controller;

import java.time.LocalDate;

import application.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


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

    }

    @FXML
    void onEnterTransaction(ActionEvent event) {

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
