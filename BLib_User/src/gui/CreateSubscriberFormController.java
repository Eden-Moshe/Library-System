package gui;

import java.net.URL;
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
	private Button btnclose=null;
	
	
	
	@FXML
	private TextField idText;
	
	@FXML
	private Button saveButton;
	
	
	private boolean saveClicked=false;
	
	public void getExitBtn(ActionEvent event) throws Exception {
		saveClicked=false;
		
		SubscriberUI.mainController.goBack();
	}
	

	
	public void save(ActionEvent event) throws Exception {
		
		UserManager UM = UserManager.getInstance();
		//String pass = UM.getPass();
		String name, phoneNum, email;
		
		name = txtName.getText();
		phoneNum = txtPhoneNum.getText();
		email = txtEmail.getText();
		
		//prevent double-clicking save, no new user can be created until the window is closed and opened again. 
		if (saveClicked)
		{
			//warn user that no new user can be created. or replace button with non-clickable button
			showAlert("Warning","clicked twice\ngo back to create a new user");
			return;
			
		}
		
		if (email == null || name == null || phoneNum == null) 
        {
        	//warn user fields cann
			showAlert("Warning","one or more fields are empty");

			
        }
		Subscriber newSub = new Subscriber();
		newSub.setEmail(email);
		newSub.setName(name);
		newSub.setPNumber(phoneNum);
		
		//sending a request to create a new subscriber to the server
		LibrarianMessage LM = new LibrarianMessage();
		LM.funcRequest = "Create Subscriber";
		LM.sub=newSub;
		UM.send(LM);
		//Subscriber newSub = (Subscriber) UM.inb.getObj();
		
		newSub = (Subscriber) UM.inb.getObj();
		
		
		//displaying subscriber id and their temporary password
		this.txtSID.setText(newSub.getSID());
		this.txtPassword.setText(newSub.getTemporaryPassword());
		
		
		saveClicked=true;
		

		
		
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		//setFacultyComboBox();		
	}
	
}
