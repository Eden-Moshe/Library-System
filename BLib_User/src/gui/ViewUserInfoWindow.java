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
    
    // Subscriber object representing the current user
    //Subscriber sub = (Subscriber) UM.inb.getObj();
    Subscriber sub;
    /**
     * Initializes the controller and loads the user data into the UI fields.
     * This method is called when the controller is initialized.
     */
//    @FXML
//    public void initialize() {
//        loadResponse();  // Call loadResponse when the controller is initialized
//    }
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnExtendBorrow;
    
    @FXML
    private Button btnCheckBook;
    
    @FXML
    private Button btnViewAccountStatusHistory;
    
    @FXML
    private Button btnViewBorrowHistory;
    
    @FXML
    private TextField idResponse;
    
    @FXML
    private TextField passResponse;
    
    @FXML
    private TextField phoneResponse;
    
    @FXML
    private TextField mailResponse;
    
    @FXML
    private TextField statusResponse;
    
    
    public void onLoad()
    {
    	
    	sub = UM.inb.getHandledSubscriber();
    	loadResponse();
    }
    /**
     * Loads the current subscriber's information into the text fields.
     * This includes subscriber ID, phone number, email, and status.
     */
    public void loadResponse() {
        this.idResponse.setText(sub.getSID());
        this.passResponse.setText("");
        this.phoneResponse.setText(sub.getPNumber());
        this.mailResponse.setText(sub.getEmail());
        this.statusResponse.setText(sub.getStatus());
    }
    
    /**
     * Handles the event when the user clicks the "Check Book Status" button.
     * This method will be implemented to check the status of a book (e.g., lost or destroyed).
     * 
     * @param event the event triggered by clicking the button
     */
    @FXML
    private void checkBookStatus(ActionEvent event) {
        // TODO: Implement logic to check book status (lost/destroyed)
    }
    
    /**
     * Handles the event when the user clicks the "Extend Borrow" button.
     * This method navigates to the "Extend Borrow" page.
     * 
     * @param event the event triggered by clicking the button
     */
    @FXML
    private void extendBorrow(ActionEvent event) {
        // Goes to Extend Borrow Page
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
        // Goes to Account Status History Page
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
        // Goes to Borrow History Page
        GenericMessage borrowMessage = new GenericMessage();
        borrowMessage.action = get_Borrow_History;
        borrowMessage.subscriber = sub; // waiting for implementation!!
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
        // Goes back one page
        SubscriberUI.mainController.goBack();
    }
}
