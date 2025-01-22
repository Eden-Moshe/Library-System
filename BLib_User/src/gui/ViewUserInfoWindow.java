package gui;

import java.io.IOException;

import client.SubscriberUI;
import common.GenericMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static common.GenericMessage.Action.*;


public class ViewUserInfoWindow extends BaseController {

    
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
