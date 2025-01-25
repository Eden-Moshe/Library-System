package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.SubscriberUI;
import client.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import common.*;

/**
 * The CreateSubscriberFormController class manages the user interface for creating 
 * new subscribers. It handles the user inputs, validates them, prevents double-clicks 
 * on the save button, and sends a request to the server to create a new subscriber.
 */
public class CreateSubscriberFormController extends BaseController implements Initializable {
    
    private Subscriber s;

    @FXML
    private Label lblSID;
    @FXML
    private Label lblName;
    @FXML
    private Label lblHistoryID;
    @FXML
    private Label lblPhoneNum;
    @FXML
    private Label lblEmail;

    @FXML
    private TextField txtSID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPhoneNum;
    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnclose;

    @FXML
    private TextField idText;

    @FXML
    private Button saveButton;

    private boolean saveClicked = false;

    /**
     * Handles the action for exiting the form.
     * This method is triggered when the exit button is clicked.
     * 
     * @param event The event that triggered the action.
     */
    public void getExitBtn(ActionEvent event) throws Exception {
        saveClicked = false;
        SubscriberUI.mainController.goBack();
    }

    /**
     * Initializes the form window for creating a new subscriber.
     * It loads the CreateSubscriberFrom.fxml file, sets the scene, and displays the window.
     * 
     * @param primaryStage The main stage where the form will be displayed.
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/CreateSubscriberFrom.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/CreateSubscriberFrom.css").toExternalForm());
        primaryStage.setTitle("Subscriber Management Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the save action to create a new subscriber.
     * This method sends the subscriber's data to the server, receives the response,
     * and updates the form with the new subscriber's ID and temporary password.
     * 
     * @param event The event that triggered the action.
     */
    public void save(ActionEvent event) throws Exception {
        System.out.println("savebutton in createform");
        UserManager UM = UserManager.getInstance();

        String name = txtName.getText();
        String phoneNum = txtPhoneNum.getText();
        String email = txtEmail.getText();

        // Prevent double-clicking the save button
        if (saveClicked) {
            System.out.println("doubleclick prevention text in createform SID.getText = " + txtSID.getText() + "|STOP");
            // Warn the user that no new user can be created or disable the button
            return;
        }

        // Check if any required field is empty
        if (email == null || name == null || phoneNum == null) {
            // Warn the user that fields cannot be empty.
            return;
        }

        System.out.println("createform post null and notnull-doubleclick-prevention");

        // Create a new Subscriber object and populate it with the input values
        Subscriber newSub = new Subscriber();
        newSub.setEmail(email);
        newSub.setName(name);
        newSub.setPNumber(phoneNum);

        // Send a request to the server to create the new subscriber
        LibrarianMessage LM = new LibrarianMessage();
        LM.funcRequest = "Create Subscriber";
        LM.sub = newSub;
        UM.send(LM);

        // Get the response from the server (new subscriber information)
        newSub = (Subscriber) UM.inb.getObj();

        System.out.println("createform post send");

        // Update the form with the new subscriber's ID and temporary password
        this.txtSID.setText(newSub.getSID());
        this.txtPassword.setText(newSub.getTemporaryPassword());

        System.out.println("createform post set text");

        // Mark that the save action has been completed
        saveClicked = true;
    }

    /**
     * Initializes the form when it is loaded.
     * This method is a placeholder for future initialization logic.
     * 
     * @param arg0 The location of the FXML file.
     * @param arg1 The resources for the FXML file.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Placeholder for future initialization logic.
    }
}
