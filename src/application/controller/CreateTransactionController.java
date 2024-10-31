package application.controller;

import java.io.IOException;

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

public class CreateTransactionController {

    @FXML
    private Label AccountErrorMsg;

    @FXML
    private ChoiceBox<?> accountSelect;

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
    private ChoiceBox<?> transactionSelect;

    @FXML
    private Label transactionTypeErrorMsg;

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
