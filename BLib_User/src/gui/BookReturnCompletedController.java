package gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;

/**
 * The {@code BookReturnCompletedController} class is responsible for handling interactions
 * on the "Book Return Completed" screen. This includes actions like navigating back to the main
 * menu or returning another book.
 */
public class BookReturnCompletedController {

    @FXML
    private Button btnBack; // Button to return to the main menu

    @FXML
    private Button btnReturnBook; // Button to return another book

    @FXML
    private ImageView logo; // ImageView for displaying the logo

    /**
     * Event handler for mouse entering the button. It changes the button style to a highlighted color.
     * 
     * @param event The mouse enter event.
     */
    public void onMouseEnteredButton(Event event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #a37a2c; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 200px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20;");
    }

    /**
     * Event handler for mouse exiting the button. It resets the button style to its default color.
     * 
     * @param event The mouse exit event.
     */
    public void onMouseExitedButton(Event event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #b59b53; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 200px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20;");
    }

    /**
     * Event handler for the "Return another book" button. It loads the book return screen again.
     * 
     * @param event The action event triggered by clicking the "Return another book" button.
     * @throws IOException If an error occurs while loading the next screen.
     */
    @FXML
    void btnReturnBook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/BookReturn.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Book Return Completed");
        stage.setScene(new Scene(root));
        stage.show();

        // Close the current window
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    /**
     * Event handler for the "Return to main menu" button. It navigates back to the main menu screen.
     * 
     * @param event The action event triggered by clicking the "Return to main menu" button.
     */
    @FXML
    void handleBack(ActionEvent event) {
        try {
            // Close the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Load the main menu screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainMenu.fxml"));
            Pane root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load MainMenu.fxml.");
        }
    }
}
