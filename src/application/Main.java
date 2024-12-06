package application;

import application.dao.ScheduledTransactionDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.List;


public class Main extends Application {
	private final ScheduledTransactionDAO scheduledTransactionDAO = new ScheduledTransactionDAO();
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getClassLoader().getResource("view/Home.fxml"));

			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());

			// Check for transactions due today
			List<ScheduledTransaction> dueToday = scheduledTransactionDAO.getTransactionsDueToday();
			if (!dueToday.isEmpty()) {
				showAlert(dueToday);
			}

			primaryStage.setScene(scene);
			primaryStage.setTitle("Home"); // Set the window title
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void showAlert(List<ScheduledTransaction> dueToday) {
		StringBuilder message = new StringBuilder("The following transactions are due today:\n");
		for (ScheduledTransaction transaction : dueToday) {
			message.append("- ").append(transaction.getScheduleName())
					.append(": $").append(transaction.getAmount())
					.append("\n");
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Scheduled Transactions Due");
		alert.setHeaderText("Transactions Due Today");
		alert.setContentText(message.toString());
		alert.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
