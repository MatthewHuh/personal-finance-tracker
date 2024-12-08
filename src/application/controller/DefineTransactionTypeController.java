package application.controller;

import java.io.IOException;
import application.TransactionType;
import application.dao.DAOInt;
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

/**
 * Controller class that handles the definition of new transaction types.
 * Allows the user to specify a unique transaction type and persists
 * it in the underlying data layer.
 */
public class DefineTransactionTypeController {

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private Label transactionTypeErrorMsg;

    @FXML
    private TextField transactionTypeText;
    
    private DAOInt<TransactionType> dao;
    
    
    /**
     * Initializes the controller after its root element has been completely processed.
     * This method sets up the data access object (DAO) needed for transaction type persistence.
     */
    public void initialize() {
    	// initialize dao
		dao = new TransactionTypeDAO();
    }   

    /**
     * Handles the action triggered by the "Cancel" button.
     * When invoked, this method navigates back to the Home view.
     *
     * @param event The action event triggered by the cancel button.
     */
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

    /**
     * Handles the action triggered by the "Submit" button.
     * Validates user input for the transaction type field. If valid, a new 
     * {@link TransactionType} object is created and persisted. Upon successful 
     * creation, navigates back to the Home view.
     *
     * @param event The action event triggered by the submit button.
     */
    @FXML
    void onSubmitAction(ActionEvent event) {
    	// check if input is valid
    	boolean inputValidate = true;
    	// check if transaction type was entered
    	if(transactionTypeText.getText().equals("")) { 
    		transactionTypeErrorMsg.setText("Please enter the transaction type");
    		inputValidate = false;
    	} // check if transaction type is unique
    	else if(((TransactionTypeDAO) dao).getTransactionTypes().get(transactionTypeText.getText()) != null) {
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
