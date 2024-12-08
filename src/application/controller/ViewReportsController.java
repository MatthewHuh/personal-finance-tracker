package application.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import application.Account;
import application.Transaction;
import application.TransactionType;
import application.dao.AccountDAO;
import application.dao.DAOInt;
import application.dao.TransactionTypeDAO;
import application.dao.TransactionDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class ViewReportsController {

    @FXML
    private Button back;

    @FXML
    private TableColumn<Transaction, String> dynamicColumn;

    @FXML
    private TableColumn<Transaction, String> transactionDateCol;

    @FXML
    private TableColumn<Transaction, String> descriptionCol;

    @FXML
    private TableColumn<Transaction, String> paymentDepositCol;

    @FXML
    private TableColumn<Transaction, Double> amountCol;

    @FXML
    private TableColumn<Transaction, Double> frequencyCol;

    @FXML
    private BorderPane createAccountPane;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private ChoiceBox<TransactionType> typeSelect;
    
    @FXML
    private ChoiceBox<Account> accountSelect;

    private DAOInt<Account> accountDAO = new AccountDAO();
    private DAOInt<TransactionType> transactionTypeDAO = new TransactionTypeDAO();
    private DAOInt<Transaction> transactionDAO = new TransactionDAO();
    private Account selectedAccount;
    private TransactionType selectedTransactionType;

    @FXML
    public void initialize() {
        // Add options into Account ChoiceBox
        ObservableList<Account> accounts = FXCollections.observableArrayList(((AccountDAO) accountDAO).getAccounts().values());
        accountSelect.setItems(accounts);

        // Add options into TransactionType ChoiceBox
        ObservableList<TransactionType> transactionTypes = FXCollections.observableArrayList(((TransactionTypeDAO) transactionTypeDAO).getTransactionTypes().values());
        typeSelect.setItems(transactionTypes);

        // Set up the columns to display data
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


        // Set up first column to be a dynamic column that shows the account name/ transaction type based on selection
        dynamicColumn.setCellValueFactory(cellData -> {
            // If account is selected, show transaction type in the dynamic column
            if (accountSelect.getSelectionModel().getSelectedItem() != null &&
                    typeSelect.getSelectionModel().getSelectedItem() == null) {
                return new SimpleStringProperty(cellData.getValue().getTransactionType().getTransactionType());
            }
            // If transaction type is selected, show account name in the dynamic column
            else if (typeSelect.getSelectionModel().getSelectedItem() != null &&
                    accountSelect.getSelectionModel().getSelectedItem() == null) {
                return new SimpleStringProperty(cellData.getValue().getAccount().getName());
            }
            return new SimpleStringProperty("");
        });

        // Wait for selection in the account choice box
        accountSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Store the selected account
                selectedAccount = newValue;

                // Change dynamic column text
                dynamicColumn.setText("Transaction Type");

                // Reset the Transaction Type ChoiceBox since Account was selected
                typeSelect.getSelectionModel().clearSelection();

                // Get the transactions based on selected account
                ObservableList<Transaction> filteredTransactions = FXCollections.observableArrayList(((TransactionDAO) transactionDAO).getTransactionsByAccount(newValue));

                // Sort the transactions by transaction date in descending order
                filteredTransactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));

                // Set the transactions to the table
                transactionTable.setItems(filteredTransactions);

                // refresh the table to avoid any errors
                transactionTable.refresh();
            }
        });

        // Wait for selection in the transaction type choice box
        typeSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Store selected transaction type
                selectedTransactionType = newValue;
                // Change dynamic column text
                dynamicColumn.setText("Account Name");

                // Reset the Account ChoiceBox since Transaction Type was selected
                accountSelect.getSelectionModel().clearSelection();

                // Get the transactions based on selected transaction type
                ObservableList<Transaction> filteredTransactions = FXCollections.observableArrayList(((TransactionDAO) transactionDAO).getTransactionsByType(newValue));

                // Sort the transactions by transaction date in descending order
                filteredTransactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));

                // Set the transactions to the table
                transactionTable.setItems(filteredTransactions);

                // refresh the table to avoid any errors
                transactionTable.refresh();
            }
        });

        // Add click-able rows to open the detailed view
        transactionTable.setRowFactory( tv -> {
            TableRow<Transaction> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Transaction rowData = row.getItem();
                if (rowData != null) {
                    openTransactionDetailPage(rowData);
                }
            });
            return row;
        });
    }
    // Open the Transaction Detail page which is read only
    private void openTransactionDetailPage(Transaction transaction) {
        try {
            Account selectedAccount = accountSelect.getSelectionModel().getSelectedItem();
            TransactionType selectedTransactionType = typeSelect.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/TransactionDetail.fxml"));
            Parent detailView = loader.load();

            TransactionDetailController controller = loader.getController();
            controller.initialize(transaction, selectedAccount, selectedTransactionType);

            Stage stage = (Stage) transactionTable.getScene().getWindow();

            stage.setScene(new Scene(detailView));
            stage.setTitle("Transaction Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    // Method to load transactions based on previous choice box selection
    private void loadTransactions() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        if (selectedAccount != null) {
            transactions.addAll(((TransactionDAO) transactionDAO).getTransactionsByAccount(selectedAccount));
        } else if (selectedTransactionType != null) {
            transactions.addAll(((TransactionDAO) transactionDAO).getTransactionsByType(selectedTransactionType));
        }

        transactions.sort((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()));

        transactionTable.setItems(transactions);

        transactionTable.refresh();
    }
    // Method to update selected account and transaction type from TransactionDetailController
    public void setSelectedAccount(Account account) {
        this.selectedAccount = account;
        accountSelect.getSelectionModel().select(account);  // Update the choice box selection
        loadTransactions();  // Load the transactions based on the selected account
    }

    public void setSelectedTransactionType(TransactionType transactionType) {
        this.selectedTransactionType = transactionType;
        typeSelect.getSelectionModel().select(transactionType);  // Update the choice box selection
        loadTransactions();  // Load the transactions based on the selected transaction type
    }
}
