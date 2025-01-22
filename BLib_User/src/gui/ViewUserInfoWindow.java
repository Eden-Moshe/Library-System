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


public class ViewUserInfoWindow extends BaseController {

    UserManager UM = UserManager.getInstance();
    Subscriber sub = (Subscriber) UM.inb.getObj();
    
    @FXML
    public void initialize() {
        loadResponse();  // Call loadResponse when the controller is initialized
    }
    
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
    
    
    //method loads all info into text boxes
    public void loadResponse() {
        this.idResponse.setText(sub.getSID());
        this.passResponse.setText("");
        this.phoneResponse.setText(sub.getPNumber());
        this.mailResponse.setText(sub.getEmail());
        this.statusResponse.setText(sub.getStatus());
    }
	
    //set all fields with fetched subscriber info
    //id,pass,phone,mail,status
    
    
    
    
    //TODO: load user info
    
    @FXML
    private void checkBookStatus(ActionEvent event) {
        // TODO: Implement logic to check book status (lost/destroyed)
    }
    
    @FXML
    private void extendBorrow(ActionEvent event) {
    	//goes to Extend Borrow Page
    	
    	SubscriberUI.mainController.switchView("/gui/ExtendBorrowForm.fxml");

    }

    
    @FXML
    private void viewAccountStatusHistory(ActionEvent event) {
    	//goes to Account Status History Page
    	SubscriberUI.mainController.switchView("/gui/AccountStatusHistory.fxml");

    	
    	

    }
    
    
    @FXML
    private void viewBorrowHistory(ActionEvent event) {
    	
    	
    	//goes to Borrow History Page
    	GenericMessage borrowMessage = new GenericMessage();
    	borrowMessage.action = get_Borrow_History;
    	borrowMessage.subscriber = null; // waiting for implementation!!
    	SubscriberUI.mainController.switchView("/gui/BorrowHistory.fxml");
    	
    	

        
        
    }
	

	
    @FXML
    private void getBackBtn(ActionEvent event) {
    	//goes back one page
    	
    	SubscriberUI.mainController.goBack();

    	
    	

    }
}
