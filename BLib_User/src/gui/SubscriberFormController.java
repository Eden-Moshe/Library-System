package gui;

import java.net.URL;
import static common.GenericMessage.Action.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.SubscriberUI;
import client.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import common.*;

public class SubscriberFormController extends BaseController {
	private Subscriber s;
	 @FXML
	    private Button btnGoBack;

	    @FXML
	    private Button btnViewAccountStatusHistory;

	    @FXML
	    private TextField txtUserID;
	    
	    @FXML
	    private TextField txtUserName;

	    @FXML
	    private TextField txtPassword;

	    @FXML
	    private TextField txtPhoneNumber;

	    @FXML
	    private TextField txtEmail;

	    @FXML
	    private TextField txtStatus;
	    
	
	ObservableList<String> list;
	
	public void onLoad()
	{
		loadSubscriber(UM.s1);
	}
		
	public void goBack(ActionEvent event)
	{
		SubscriberUI.mainController.goBack();
	}
	public void loadSubscriber(Subscriber s1) {
		this.s=s1;
		this.txtEmail.setText(s.getEmail());
		this.txtUserName.setText(s.getName());		
		this.txtPhoneNumber.setText(s.getPNumber()); 
		this.txtUserID.setText(s.getSID());
		this.txtStatus.setText(s.getStatus());
		
		
	}
	
	
	public void updatePassword(ActionEvent event) throws Exception {
		UserManager UM = UserManager.getInstance();
		
		GenericMessage changePass = new GenericMessage();
		changePass.action = set_New_Password;
		changePass.subscriber = UM.s1;
		changePass.fieldVal = txtPassword.getText();
		
		UM.send(changePass);
		
		
		
		
	}
	
	
	
	public void updateInformation(ActionEvent event) throws Exception {
		UserManager UM = UserManager.getInstance();
		SubMessage sm=new SubMessage();
        sm.editBool=true;
        sm.pKey=s.getSID();
        
		if (s != null) {
	        // Update the existing subscriber's email and phone number
	        String newEmail = txtEmail.getText();
	        String newPhoneNumber = txtPhoneNumber.getText();

	        if (newEmail != null && !newEmail.trim().isEmpty() && newEmail!=s.getEmail()) 
	        {
	        	sm.fieldCol="subscriber_email";
	        	sm.fieldVal=newEmail;
	            UM.send(sm);
	        }
	        else {
	            showAlert("warning", "email field cannot be empty");
	        }

	        if (newPhoneNumber != null && !newPhoneNumber.trim().isEmpty() && newPhoneNumber != s.getPNumber()) {
	            sm.fieldCol="subscriber_phone_number";
	            sm.fieldVal=newPhoneNumber;
	            UM.send(sm);
	        } else {
	            showAlert("warning", "phone number field cannot be empty");
	        }

			
	        
    	} 
		else 
	    {
            showAlert("warning", "could not update subscriber");
	    }
		
	}
	

	
}
