package application.controller;

import java.io.IOException;

import application.ScheduledTransaction;
import application.Transaction;
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


    private final TransactionDAO transactionDAO = new TransactionDAO();

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
    public void initialize() {
        // Set up table columns
    	accountCol.setCellValueFactory(cellData -> 
        	new SimpleStringProperty(cellData.getValue().getAccount().getName()));
	    transactionTypeCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty(cellData.getValue().getTransactionType().getTransactionType()));
	    transactionDateCol.setCellValueFactory(cellData -> 
	        new SimpleStringProperty(cellData.getValue().getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
	    frequencyCol.setCellValueFactory(cellData -> 
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
        List<Transaction> transactions = transactionDAO.getTransactions();
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
                EditTransactionController.initializeTransaction(rowData);
                try {
    	    		// Load the Home.fxml file
    	    		Parent homeView = FXMLLoader.load(getClass().getClassLoader().getResource("view/EditTransaction.fxml"));
    	    		
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
            });
            return row;
        });
    }
    
    @FXML
    void onSearch(ActionEvent event) {
        String searchInput = searchText.getText().trim(); // get search input
        List<Transaction> searchResults = transactionDAO.search(searchInput); // search transactions by description
        ObservableList<Transaction> observableResults = FXCollections.observableArrayList(searchResults); // convert results to observable list
        transactionTable.setItems(observableResults); // update table
        transactionTable.getSortOrder().clear();// clear sort order to view results correctly
    }
}
