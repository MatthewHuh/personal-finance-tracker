package application.controller;

import java.io.IOException;
import application.TransactionType;
import application.dao.TransactionTypeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DefineTransactionTypeController {

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private Label transactionTypeErrorMsg;

    @FXML
    private TextField transactionTypeText;
    
    private TransactionTypeDAO dao;
    
    public void initialize() {
    	// initialize dao
		dao = new TransactionTypeDAO();
    }   

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
    	boolean inputValidate = true;
    	if(transactionTypeText.getText().equals("")) {
    		transactionTypeErrorMsg.setText("Please enter the transaction type");
    		inputValidate = false;
    	}
    	else if(dao.search(transactionTypeText.getText()) != null) {
    		transactionTypeErrorMsg.setText("Transaction type already exists. Please enter a unique type");
    		inputValidate = false;
    	}
    	else {
    		transactionTypeErrorMsg.setText("");
    	}
    	if(inputValidate) {
	    	try {
	    		// create new TransactionType object
	    		TransactionType type = new TransactionType(transactionTypeText.getText());
	    		
	    		// write to db
	    		dao.create(type);
	    		
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
}
