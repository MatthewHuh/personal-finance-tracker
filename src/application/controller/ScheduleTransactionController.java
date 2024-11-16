package application.controller;

import java.io.IOException;

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

public class ScheduleTransactionController {

    @FXML
    private Label AccountErrorMsg;

    @FXML
    private ChoiceBox<String> accountSelect;

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private Label depositAmountErrorMsg;

    @FXML
    private TextField dueDate;

    @FXML
    private ChoiceBox<String> frequencySelect;

    @FXML
    private TextField paymentAmount;

    @FXML
    private Label paymentAmountErrorMsg;

    @FXML
    private TextField scheduleName;

    @FXML
    private Label transactionDateErrorMsg;

    @FXML
    private Label transactionDescErrorMsg;

    @FXML
    private Label transactionTypeErrorMsg;

    @FXML
    private ChoiceBox<String> typeSelect;

    @FXML
    void onCancelAction(ActionEvent event) {
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

    @FXML
    void onSubmitAction(ActionEvent event) {
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

