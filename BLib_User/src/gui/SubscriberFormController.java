package gui;

import static common.GenericMessage.Action.*;
import client.SubscriberUI;
import client.UserManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import common.*;

/**
 * Controller for the Subscriber Form. This class manages the subscriber's
 * information display, updates, and password change functionality.
 */
public class SubscriberFormController extends BaseController {
    
    private Subscriber s; // Subscriber object to hold the current subscriber's information
    
    @FXML
    private Button btnGoBack; // Button to go back to the previous screen
    
    @FXML
    private Button btnViewAccountStatusHistory; // Button to view account status history
    
    @FXML
    private TextField txtUserID; // Text field for the user's ID
    
    @FXML
    private TextField txtUserName; // Text field for the user's name
    
    @FXML
    private TextField txtPassword; // Text field for the user's password
    
    @FXML
    private TextField txtPhoneNumber; // Text field for the user's phone number
    
    @FXML
    private TextField txtEmail; // Text field for the user's email
    
    @FXML
    private TextField txtStatus; // Text field for the user's account status
    
    ObservableList<String> list;

    /**
     * Loads the subscriber's information into the form when the controller is initialized.
     */
    public void onLoad() {
        loadSubscriber(UM.s1);
    }

    /**
     * Handles the "Go Back" button action. Navigates the user back to the previous screen.
     * 
     * @param event The action event triggered by the "Go Back" button.
     */
    public void goBack(ActionEvent event) {
        SubscriberUI.mainController.goBack();
    }

    /**
     * Loads the subscriber's information into the corresponding text fields.
     * 
     * @param s1 The Subscriber object containing the subscriber's information.
     */
    public void loadSubscriber(Subscriber s1) {
        this.s = s1;
        this.txtEmail.setText(s.getEmail());
        this.txtUserName.setText(s.getName());
        this.txtPhoneNumber.setText(s.getPNumber());
        this.txtUserID.setText(s.getSID());
        this.txtStatus.setText(s.getStatus());
    }

    /**
     * Updates the subscriber's password based on the input provided.
     * 
     * @param event The action event triggered by the password update action.
     * @throws Exception If an error occurs while updating the password.
     */
    public void updatePassword(ActionEvent event) throws Exception {
        UserManager UM = UserManager.getInstance();
        
        // Create a message to request a password change
        GenericMessage changePass = new GenericMessage();
        changePass.action = set_New_Password;
        changePass.subscriber = UM.s1;
        changePass.fieldVal = txtPassword.getText();
        
        // Send the message to the server to update the password
        UM.send(changePass);
    }

    /**
     * Updates the subscriber's email and phone number based on the inputs provided.
     * 
     * @param event The action event triggered by the update information action.
     * @throws Exception If an error occurs while updating the subscriber's information.
     */
    public void updateInformation(ActionEvent event) throws Exception {
        UserManager UM = UserManager.getInstance();
        SubMessage sm = new SubMessage();
        sm.editBool = true;
        sm.pKey = s.getSID();

        if (s != null) {
            // Update the existing subscriber's email and phone number
            String newEmail = txtEmail.getText();
            String newPhoneNumber = txtPhoneNumber.getText();

            if (newEmail != null && !newEmail.trim().isEmpty() && !newEmail.equals(s.getEmail())) {
                sm.fieldCol = "subscriber_email";
                sm.fieldVal = newEmail;
                UM.send(sm);
            } else {
	            showAlert("warning", "email field cannot be empty");
            }

            if (newPhoneNumber != null && !newPhoneNumber.trim().isEmpty() && !newPhoneNumber.equals(s.getPNumber())) {
                sm.fieldCol = "subscriber_phone_number";
                sm.fieldVal = newPhoneNumber;
                UM.send(sm);
            } else {
	            showAlert("warning", "phone number field cannot be empty");
            }
        } else {
            showAlert("warning", "could not update subscriber");
        }
    }
}
