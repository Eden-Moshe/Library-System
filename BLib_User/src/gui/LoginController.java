package gui;

import client.*;
import common.LoginMessage;
import common.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public  class LoginController extends BaseController   {
	
	
	
	@FXML
	private Button btnLogin = null;

	
    @FXML
    private TextField idtxt;

    @FXML
    private PasswordField passwordtxt;


	private String getID() {
		return idtxt.getText();
	}
	private String getPass() {
		return passwordtxt.getText();
	}
	
	public void btnLogin(ActionEvent event) throws Exception {

		//clearing previous user if any
		//UM.logOut();
		
		
		UM.login();
		
		
		String id=getID();
		String pass=getPass();
		
		
		LoginMessage lm = new LoginMessage();
		lm.id=id;
		lm.password=pass;
		
		
		UM.setPass(pass);
		UM.send(lm);
		
		
		
		//login and show subscriber menu
		Object fromServer = UM.inb.getObj();
		if (fromServer instanceof Subscriber)
		{
			UM.s1=(Subscriber) fromServer;
			
	        SubscriberUI.mainController.switchView("/gui/SubscriberMainMenu.fxml");

		}
		else if (UM.librarian != null)
		{
			
	        SubscriberUI.mainController.switchView("/gui/LibrarianMainMenu.fxml");

		}
		else
		{
			showAlert("Wrong ID or Password","Wrong ID or Password");
		}
		
	}

	
	public void searchNoLogin(ActionEvent event) 
	{
		UM.login();
		SubscriberUI.mainController.switchView("/gui/SearchBook.fxml");
		
	}
	
	public void start(Stage primaryStage) throws Exception {	

		Parent root = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
				
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login Window");
		primaryStage.setScene(scene);
		
		primaryStage.show();	 	   
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Library Tool");
		System.exit(0);
	}
	
}
