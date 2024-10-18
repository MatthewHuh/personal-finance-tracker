package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateAccountController {

    @FXML
    private Label accountNameErrorMsg;

    @FXML
    private TextField accountNameText;

    @FXML
    private Label openingBalanceErrorMsg;

    @FXML
    private TextField openingBalanceText;

    @FXML
    private Label openingDateErrorMsg;

    @FXML
    private DatePicker openingDatePicker;

    @FXML
    void onCancelAction(ActionEvent event) {
    	
    }

    @FXML
    void onDateAction(ActionEvent event) {

    }

    @FXML
    void onSubmitAction(ActionEvent event) {

    }

}
