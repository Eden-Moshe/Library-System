package gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import client.SubscriberUI;
import client.UserManager;
import common.SubMessage;
import common.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller class for handling user interactions in the Librarian's Enter User ID window.
 * This includes viewing user information, resetting fields, and navigating back.
 */
public class LibrarianEnterUserIDWindow extends BaseController {
    
    @FXML
    private Button btnViewUserInfo;
    
    @FXML
    private Button btnReset;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private TextField userIdField;
    
    @FXML
    private TextField txtresponse;
    
    /**
     * Retrieves the user ID from the text input field.
     * 
     * @return the user ID as a string
     */
    private String getUserId() {
        return userIdField.getText();
    }
    
    /**
     * Resets all fields (user ID and response text) when the 'reset' button is pressed.
     * 
     * @param event the ActionEvent triggered by the reset button
     */
    @FXML
    private void resetFields(ActionEvent event) {
        userIdField.clear();
        txtresponse.clear();
    }
    
    /**
     * Sets the response message in the response text field.
     * 
     * @param msg the response message to display
     */
    public void setTextRespose(String msg) {
        this.txtresponse.setText(msg);    
    }

    /**
     * Handles the "View User Info" button click. 
     * It checks that the user ID is provided, sends a request to the server, 
     * and fetches the subscriber information. If the subscriber doesn't exist, 
     * an error message is shown. Otherwise, it navigates to the "View User Info" page.
     * 
     * @param event the ActionEvent triggered by the "View User Info" button
     */
    @FXML
    private void ViewUserInfo(ActionEvent event) {
        String userID = getUserId();
        
        // Check no field is left empty
        if (userID.trim().isEmpty()) {
            txtresponse.setText("ID field is required.");
            return;
        }
        
        // Creating a subscriber message with librarian's input
        SubMessage subMsg = new SubMessage();
        subMsg.pKey = userID;
        // Alert this as a fetch message
        subMsg.editBool = false;
        
        // Sending user ID to server and fetching him into next page
        UM.send(subMsg);
        
        // Check that subscriber exists
        Subscriber sub = (Subscriber) UM.inb.getObj();
        UM.inb.handledSubscriber(sub);
        if (sub == null) {
            txtresponse.setText("The subscriber ID does not exist.");
            return;
        }
        
        // Go to Reader's card:
        SubscriberUI.mainController.switchView("/gui/ViewUserInfo.fxml");
    }
    
    /**
     * Handles the back button functionality. Navigates back to the previous screen.
     * 
     * @param event the ActionEvent triggered by the back button
     */
    @FXML
    private void getBackBtn(ActionEvent event) {
        SubscriberUI.mainController.goBack();
    }
}
