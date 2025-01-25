package gui;

import client.*;
import common.LoginMessage;
import common.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The LoginController class handles the login functionality for users (subscribers and librarians),
 * validates the provided credentials, and navigates to the appropriate main menu after successful login.
 */
public class LoginController extends BaseController {

    @FXML
    private Button btnLogin = null;

    @FXML
    private TextField idtxt;

    @FXML
    private PasswordField passwordtxt;

    /**
     * Retrieves the user ID from the input field.
     *
     * @return The entered user ID.
     */
    private String getID() {
        return idtxt.getText();
    }

    /**
     * Retrieves the password from the input field.
     *
     * @return The entered password.
     */
    private String getPass() {
        return passwordtxt.getText();
    }

    /**
     * Handles the login process when the "Login" button is clicked.
     * It validates the user's credentials and switches to the appropriate main menu (subscriber or librarian).
     *
     * @param event The event triggered by the button click.
     * @throws Exception If an error occurs during the login process.
     */
    public void btnLogin(ActionEvent event) throws Exception {

        // Clearing previous user data if any
        // UM.logOut();

        // Login process
        UM.login();

        String id = getID();
        String pass = getPass();

        // Create and send login message to the server
        LoginMessage lm = new LoginMessage();
        lm.id = id;
        lm.password = pass;

        UM.setPass(pass);
        UM.send(lm);

        // Check for invalid response (null message as a temporary check)
        if (UM.inb.getMessage().contains("null"))
            return;

        // Handle server response and navigate to the appropriate main menu
        Object fromServer = UM.inb.getObj();
        if (fromServer instanceof Subscriber) {
            UM.s1 = (Subscriber) fromServer;

            // Switch to the subscriber main menu
            SubscriberUI.mainController.switchView("/gui/SubscriberMainMenu.fxml");

        } else if (UM.librarian != null) {

            // Switch to the librarian main menu
            SubscriberUI.mainController.switchView("/gui/LibrarianMainMenu.fxml");

        } else {
            // Display an alert for invalid login
            System.out.println("Wrong ID or Password");
            showAlert("Wrong ID or Password", "Wrong ID or Password");
        }
    }

    /**
     * Allows the user to search for books without logging in.
     * This method is triggered when the "Search Without Login" button is clicked.
     *
     * @param event The event triggered by the button click.
     */
    public void searchNoLogin(ActionEvent event) {
        UM.login();
        SubscriberUI.mainController.switchView("/gui/SearchBook.fxml");
    }

    /**
     * Starts the login window stage.
     * This method is used to launch the login window as a standalone application.
     *
     * @param primaryStage The primary stage for the login window.
     * @throws Exception If an error occurs while loading the FXML file.
     */
    public void start(Stage primaryStage) throws Exception {

        // Load the login window FXML
        Parent root = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));

        // Create the scene and set the primary stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login Window");
        primaryStage.setScene(scene);

        // Show the login window
        primaryStage.show();
    }

    /**
     * Exits the application when the "Exit" button is clicked.
     *
     * @param event The event triggered by the button click.
     * @throws Exception If an error occurs during the exit process.
     */
    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Exiting Library Tool");
        System.exit(0);
    }
}
