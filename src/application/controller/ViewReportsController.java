package application.controller;

import java.io.IOException;

import application.Account;
import application.Transaction;
import application.TransactionType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewReportsController {

    @FXML
    private Button back;

    @FXML
    private TableColumn<Transaction, String> col1;

    @FXML
    private TableColumn<Transaction, String> col2;

    @FXML
    private TableColumn<Transaction, String> col3;

    @FXML
    private TableColumn<Transaction, Double> col4;

    @FXML
    private TableColumn<Transaction, Double> col5;

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private ChoiceBox<TransactionType> typeSelect;
    
    @FXML
    private ChoiceBox<Account> accountSelect;

    @FXML
    void onBack(ActionEvent event) {
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

}
