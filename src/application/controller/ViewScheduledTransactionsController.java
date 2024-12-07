package application.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import application.Format;
import application.ScheduledTransaction;
import application.Transaction;
import application.dao.ScheduledTransactionDAO;
import application.dao.TransactionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewScheduledTransactionsController {

    @FXML
    private TableColumn<ScheduledTransaction, String> accountCol;

    @FXML
    private Button back;

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private TableColumn<ScheduledTransaction, Integer> dueDateCol;

    @FXML
    private TableColumn<ScheduledTransaction, String> frequencyCol;

    @FXML
    private TableColumn<ScheduledTransaction, Double> paymentAmountCol;

    @FXML
    private TableColumn<ScheduledTransaction, String> scheduleNameCol;

    @FXML
    private TableView<ScheduledTransaction> scheduledTransactionTable;

    @FXML
    private TableColumn<ScheduledTransaction, String> transactionTypeCol;
    
    @FXML
    private Button searchButton;

    @FXML
    private TextField searchText;

    private final ScheduledTransactionDAO scheduledTransactionDAO = new ScheduledTransactionDAO();
    
    @FXML
    public void initialize() {
        // Set up the columns to match Account class properties
        scheduleNameCol.setCellValueFactory(new PropertyValueFactory<>("scheduleName"));
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        paymentAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Custom cell value factory for nested properties since PropertyValueFactory can't find readable properties
        accountCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAccount().getName()));
        transactionTypeCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getType().getTransactionType()));

        // Load accounts from the CSV file
        ScheduledTransactionDAO acc = new ScheduledTransactionDAO();
        Map<String, ScheduledTransaction> scheduledTransactions = acc.getScheduledTransactions();
        if (scheduledTransactions != null) {
            ObservableList<ScheduledTransaction> accountList = FXCollections.observableArrayList(scheduledTransactions.values());
            scheduledTransactionTable.setItems(accountList);
        }
        // sort by opening date descending
        scheduledTransactionTable.getSortOrder().add(dueDateCol);
        
     // add click able rows
        scheduledTransactionTable.setRowFactory( tv -> {
            TableRow<ScheduledTransaction> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
            	ScheduledTransaction rowData = row.getItem();
            	if(rowData != null) {
	            	ScheduleTransactionController.initialize(rowData, Format.EDIT);
	            	try {
	    	    		// Load the Home.fxml file
	    	    		Parent editScheduledTransactionView = FXMLLoader.load(getClass().getClassLoader().getResource("view/ScheduleTransaction.fxml"));
	    	    		
	    	    		// Get the current stage
	    				Stage stage = (Stage) createAccountPane.getScene().getWindow();
	    				
	    				// Set the new scene
	    				stage.setScene(new Scene(editScheduledTransactionView));
	    				stage.setTitle("Edit Scheduled Transaction"); // Optional: Set the window title
	    				stage.show();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
            	}
            });
            return row;
        });
    }
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
    
    @FXML
    void onSearch(ActionEvent event) {
        String searchInput = searchText.getText().trim();// get search input
        List<ScheduledTransaction> searchResults = scheduledTransactionDAO.search(searchInput);// search scheduled transactions by name
        ObservableList<ScheduledTransaction> observableResults = FXCollections.observableArrayList(searchResults);// convert results to observable list
        scheduledTransactionTable.setItems(observableResults);// update table
        scheduledTransactionTable.getSortOrder().clear();// clear sort order to view results correctly
    }

}
