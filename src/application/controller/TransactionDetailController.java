package application.controller;

import application.Transaction;
import application.Account;
import application.TransactionType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the Transaction Detail view. This class displays detailed information
 * about a single transaction, including the associated account, transaction type, date, description,
 * and the amount (either payment or deposit). The user can navigate back to a report view that
 * displays filtered results based on the currently selected account and transaction type.
 */
public class TransactionDetailController {

    @FXML private Label accountLabel;
    @FXML private Label transactionTypeLabel;
    @FXML private Label transactionDateLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label paymentDepositLabel;
    @FXML private Label amountLabel;

    // Fields to store the selected Account and TransactionType
    private Account selectedAccount;
    private TransactionType selectedTransactionType;

    /**
     * Initializes the transaction details on the view. 
     * This method should be called after loading the FXML and before displaying the view.
     *
     * @param transaction The transaction whose details are to be displayed.
     * @param account The account associated with the transaction.
     * @param transactionType The type of the transaction.
     */
    public void initialize(Transaction transaction, Account account, TransactionType transactionType) {
        // Initialize the labels with the transaction details
        accountLabel.setText(transaction.getAccount().getName());
        transactionTypeLabel.setText(transaction.getTransactionType().getTransactionType());
        transactionDateLabel.setText(transaction.getTransactionDate().toString());
        descriptionLabel.setText(transaction.getDescription());
        paymentDepositLabel.setText(transaction.getPaymentAmount() > 0 ? "Payment" : "Deposit");
        amountLabel.setText(String.format("%.2f", transaction.getPaymentAmount() > 0 ?
                transaction.getPaymentAmount() :
                transaction.getDepositAmount()));

        // Store the selected account and transaction type
        this.selectedAccount = account;
        this.selectedTransactionType = transactionType;
    }

    /**
     * Handles the action triggered by the "Back" button.
     * Navigates back to the reports view (ViewReports.fxml), passing along the currently selected
     * account and transaction type so that the report can be re-displayed with the same filters.
     *
     * @param event The action event triggered by clicking the "Back" button.
     */
    @FXML
    void onBack(ActionEvent event) {
        try {
            // Load the ViewReports.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/ViewReports.fxml"));
            Parent reportView = loader.load();

            // Get the controller of the report page
            ViewReportsController reportController = loader.getController();

            // Pass the selected account and transaction type back to the report page
            reportController.setSelectedAccount(selectedAccount);
            reportController.setSelectedTransactionType(selectedTransactionType);

            // Get the current stage
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // Set the new scene with the updated report
            stage.setScene(new Scene(reportView));
            stage.setTitle("Report Results");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
