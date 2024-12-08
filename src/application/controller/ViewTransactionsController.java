package application.controller;

import java.io.IOException;

import application.Format;
import application.Transaction;
import application.dao.DAOInt;
import application.dao.TransactionDAO;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller class for viewing and managing existing transactions.
 * This class displays transactions in a table where each row can be clicked to
 * edit the corresponding transaction. Users can also search transactions by their description.
 * Clicking the "Back" button returns to the Home view.
 */
public class ViewTransactionsController {
	
	@FXML
    private TableColumn<Transaction, String> accountCol;

    @FXML
    private TableColumn<Transaction, Double> amountCol;

    @FXML
    private TableColumn<Transaction, String> descriptionCol;
    
    @FXML
    private TableColumn<Transaction, String> paymentDepositCol;
    
    @FXML
    private TableColumn<Transaction, String> transactionDateCol;
    
    @FXML
    private TableColumn<Transaction, String> frequencyCol;
    
    @FXML
    private TableView<Transaction> transactionTable;
    
    @FXML
    private TableColumn<Transaction, String> transactionTypeCol;
    
    @FXML
    private Button back;

    @FXML
    private BorderPane createAccountPane;
    
    @FXML
    private Button searchButton;

    @FXML
    private TextField searchText;


    private final DAOInt<Transaction> transactionDAO = new TransactionDAO();
    
    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up the table columns, loads transactions, applies default sorting, and enables row-click editing.
     */
    @FXML
    public void initialize() {
        // Set up table columns
    	accountCol.setCellValueFactory(cellData -> 
        	new SimpleStringProperty(cellData.getValue().getAccount().getName()));
	    transactionTypeCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty(cellData.getValue().getTransactionType().getTransactionType()));
	    transactionDateCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty(cellData.getValue().getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
	    descriptionCol.setCellValueFactory(cellData ->
	        new SimpleStringProperty(cellData.getValue().getDescription()));
	    paymentDepositCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty(cellData.getValue().getPaymentAmount() > 0 ? "Payment" : "Deposit"));
	    amountCol.setCellValueFactory(cellData -> 
	    cellData.getValue().getPaymentAmount() > 0 
	        ? new SimpleObjectProperty<>(cellData.getValue().getPaymentAmount()) 
	        : new SimpleObjectProperty<>(cellData.getValue().getDepositAmount()));

		amountCol.setCellFactory(column -> new TableCell<>() {
		    @Override
		    protected void updateItem(Double item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty || item == null) {
		            setText(null);
		        } else {
		            setText(String.format("%.2f", item));
		        }
		    }
		});
		
        // Load transactions from DAO and populate TableView
        List<Transaction> transactions = ((TransactionDAO) transactionDAO).getTransactions();
        if (transactionTable != null) {
        	ObservableList<Transaction> observableTransactions = FXCollections.observableArrayList(transactions);
            transactionTable.setItems(observableTransactions);
        }
        // sort by transactionDate descending
        transactionTable.getSortOrder().add(transactionDateCol);
        
        // add click able rows
        transactionTable.setRowFactory( tv -> {
            TableRow<Transaction> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
            	Transaction rowData = row.getItem();
            	if (rowData != null) {
	                TransactionController.initialize(rowData, Format.EDIT);
	                try {
	    	    		// Load the Home.fxml file
	    	    		Parent editTransactionView = FXMLLoader.load(getClass().getClassLoader().getResource("view/Transaction.fxml"));
	    	    		
	    	    		// Get the current stage
	    				Stage stage = (Stage) createAccountPane.getScene().getWindow();
	    				
	    				// Set the new scene
	    				stage.setScene(new Scene(editTransactionView));
	    				stage.setTitle("Edit Transaction"); // Optional: Set the window title
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
    
    /**
     * Handles the action triggered by the "Back" button.
     * Navigates back to the Home view.
     *
     * @param event The action event triggered by the "Back" button.
     */
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
    
    /**
     * Handles the action triggered by the "Search" button.
     * Searches the transactions by the description entered in the search field,
     * updates the table to show only matching results.
     *
     * @param event The action event triggered by the "Search" button.
     */
    @FXML
    void onSearch(ActionEvent event) {
        String searchInput = searchText.getText().trim(); // get search input
        List<Transaction> searchResults = ((TransactionDAO) transactionDAO).search(searchInput); // search transactions by description
        ObservableList<Transaction> observableResults = FXCollections.observableArrayList(searchResults); // convert results to observable list
        transactionTable.setItems(observableResults); // update table
        transactionTable.getSortOrder().clear();// clear sort order to view results correctly
    }
}
