package gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField; 
import client.SubscriberUI;
import client.UserManager;
import common.SubMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LibrarianEnterUserIDWindow extends BaseController{
    @FXML
    private Button btnViewUserInfo;
    
    @FXML
    private Button btnReset;
    
    @FXML
    private Button btnBack;
    
    
    @FXML
    private TextField userIdField;
    
    //get borrower id from text inserted
    private String getUserId() {
        return userIdField.getText();
    }
    
    // Resets all fields when pressing 'reset'
    @FXML
    private void resetFields(ActionEvent event) {
    	userIdField.clear();
    }
    
    //display text returned from server
	public void setTextRespose(String msg) {
		this.userIdField.setText(msg);	
	}

    @FXML
    private void ViewUserInfo(ActionEvent event) {
        UserManager UM = UserManager.getInstance();
        String userID = getUserId();
        
        //creating a subscriber message with librarian's input
        SubMessage subMsg = new SubMessage();
	    subMsg.pKey=userID;
	    //alert this as a fetch message
	    subMsg.editBool = true;
	    
	    //sending user id to server and fetching him into next page
	    UM.send(subMsg);
	    
	    //go to Reader's card:
    	SubscriberUI.mainController.switchView("/gui/ViewUserInfo.fxml");
    	
 
    }
    
    

    @FXML
    private void getBackBtn(ActionEvent event) {
    	SubscriberUI.mainController.goBack();
    }
}
