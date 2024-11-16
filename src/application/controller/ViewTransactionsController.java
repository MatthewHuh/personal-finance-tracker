package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewTransactionsController {

    @FXML
    private TableColumn<?, ?> accountCol;

    @FXML
    private TableColumn<?, ?> amountCol;

    @FXML
    private Button back;

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private TableColumn<?, ?> frequencyCol;

    @FXML
    private TableColumn<?, ?> paymentDepositCol;

    @FXML
    private TableColumn<?, ?> transactionDateCol;

    @FXML
    private TableView<?> transactionDescriptionTable;

    @FXML
    private TableColumn<?, ?> transactionTypeCol;

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
