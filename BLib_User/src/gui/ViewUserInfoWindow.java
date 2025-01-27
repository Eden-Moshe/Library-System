package gui;

import client.SubscriberUI;
import client.UserManager;
import common.GenericMessage;
import common.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static common.GenericMessage.Action.*;

/**
 * Controller class for the "View User Info" window in the application.
 * This class is responsible for handling user interface elements such as buttons and text fields.
 * It loads user information into the view and provides navigation to other pages.
 */
public class ViewUserInfoWindow extends BaseController {

    // Instance of UserManager for managing user-related operations
    UserManager UM = UserManager.getInstance();
    Subscriber sub;

    @FXML
    private Button btnBack; // Button to navigate back to the previous page
    
    @FXML
    private Button btnExtendBorrow; // Button to navigate to the "Extend Borrow" page
    
    @FXML
    private Button btnViewAccountStatusHistory; // Button to view the account status history
    
    @FXML
    private Button btnViewBorrowHistory; // Button to view the borrow history
    
    @FXML
    private TextField idResponse; // Text field to display the subscriber's ID
    
    @FXML
    private TextField passResponse; // Text field to display the subscriber's password (not used)
    
    @FXML
    private TextField phoneResponse; // Text field to display the subscriber's phone number
    
    @FXML
    private TextField mailResponse; // Text field to display the subscriber's email address
    
    @FXML
    private TextField statusResponse; // Text field to display the subscriber's account status
    
    
    /**
     * Initializes the view by loading the current subscriber's information.
     * This method is called when the view is loaded.
     */
    public void onLoad() {
        sub = UM.inb.getHandledSubscriber();
        loadResponse();
    }

    /**
     * Loads the current subscriber's information into the text fields.
     * This includes subscriber ID, phone number, email, and status.
     */
    public void loadResponse() {
        this.idResponse.setText(sub.getSID());
        this.passResponse.setText(""); // Password is not displayed
        this.phoneResponse.setText(sub.getPNumber());
        this.mailResponse.setText(sub.getEmail());
        this.statusResponse.setText(sub.getStatus());
    }

    /**
     * Handles the event when the user clicks the "Extend Borrow" button.
     * This method navigates to the "Extend Borrow" page.
     * 
     * @param event the event triggered by clicking the button
     */
    @FXML
    private void extendBorrow(ActionEvent event) {
        // Navigates to the "Extend Borrow" page
        SubscriberUI.mainController.switchView("/gui/ExtendBorrowForm.fxml");
    }

    /**
     * Handles the event when the user clicks the "View Account Status History" button.
     * This method navigates to the "Account Status History" page.
     * 
     * @param event the event triggered by clicking the button
     */
    @FXML
    private void viewAccountStatusHistory(ActionEvent event) {
        // Navigates to the "Account Status History" page
    	GenericMessage getStatus = new GenericMessage();
		getStatus.subscriber=sub;
		getStatus.action = get_Account_Status_History;
		
		UM.send(getStatus);
        SubscriberUI.mainController.switchView("/gui/AccountStatusHistory.fxml");
    }

    /**
     * Handles the event when the user clicks the "View Borrow History" button.
     * This method sends a request to load the borrow history and navigates to the "Borrow History" page.
     * 
     * @param event the event triggered by clicking the button
     */
    @FXML
    private void viewBorrowHistory(ActionEvent event) {
        // Sends a request to load the borrow history and navigates to the "Borrow History" page
        GenericMessage borrowMessage = new GenericMessage();
        borrowMessage.action = get_Borrow_History;
        borrowMessage.subscriber = sub; // Sending the current subscriber's information
        UM.send(borrowMessage);
        SubscriberUI.mainController.switchView("/gui/BorrowHistory.fxml");
    }

    /**
     * Handles the event when the user clicks the "Back" button.
     * This method navigates back to the previous page.
     * 
     * @param event the event triggered by clicking the button
     */
    @FXML
    private void getBackBtn(ActionEvent event) {
        // Navigates back to the previous page
        SubscriberUI.mainController.goBack();
    }
}
