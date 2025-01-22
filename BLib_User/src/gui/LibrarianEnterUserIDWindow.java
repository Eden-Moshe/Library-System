package gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField; 
import java.io.IOException;

import client.SubscriberUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LibrarianEnterUserIDWindow extends BaseController{
    @FXML
    private Button btnViewUserInfo;
    
    @FXML
    private Button btnReset;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private TextField txtResponse;
    
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
        String userID = getUserId();
        
	    //check that field is not empty
	    if (userID.trim().isEmpty()) {
	        txtResponse.setText("this field must be filled.");
	        return;
	    }
	    
	    
	   //implement check if that ID exists, if so send it to next page
	    
	    //go to Reader's card:
    	SubscriberUI.mainController.switchView("/gui/ViewUserInfo.fxml");

 
    }
    
    

    @FXML
    private void getBackBtn(ActionEvent event) {
    	SubscriberUI.mainController.goBack();
    }
}
