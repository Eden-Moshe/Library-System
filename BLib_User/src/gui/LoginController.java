package gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import client.UserManager;
import client.SubscriberUI;
import common.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public  class LoginController   {
	
	@FXML
	private Button btnLogin = null;

	
    @FXML
    private TextField idtxt;

    @FXML
    private PasswordField passwordtxt;

    @FXML

	private String getID() {
		return idtxt.getText();
	}
	private String getPass() {
		return passwordtxt.getText();
	}

	
	public void btnLogin(ActionEvent event) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		
		String id=getID();
		String pass=getPass();
		
		UserManager UM = UserManager.getInstance();
		
		LoginMessage lm = new LoginMessage();
		lm.id=id;
		lm.password=pass;
		
		
		UM.setPass(pass);
		UM.send(lm);
		
		
		if (UM.s1.getSID()!=null)
		{
			
			//start main menu process
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/gui/MainMenu.fxml").openStream());
			//SubscriberFormController subscriberFormController = loader.getController();		
			//subscriberFormController.loadSubscriber(UM.s1);
			
			Scene scene = new Scene(root);			
			primaryStage.setTitle("Main Menu");

			primaryStage.setScene(scene);		
			primaryStage.show();
			
			
		}
		else
		{
			//IMPLEMENT SHOW WRONG ID/PASS MESSAGE INTO THE UI
		}
		
	}

	public void btnSearchBook(ActionEvent event) throws IOException {
	    // load the new screem
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SearchBook.fxml"));
	    Parent root = loader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Search for Books");
	    stage.setScene(new Scene(root));
	    stage.show();

	    ((Node) event.getSource()).getScene().getWindow().hide();
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
