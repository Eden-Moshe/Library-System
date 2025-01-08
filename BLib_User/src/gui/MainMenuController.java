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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public  class MainMenuController   {
	private SubscriberFormController sfc;	
	private static int itemIndex = 3;
	
	@FXML
	private Button btnLogin = null;
	
	@FXML
	private Button btnSend = null;
	
	@FXML
	private TextField lblID;
	private TextField lblPassword;


	public void btnLendBook() {
		
	}
	public void btnViewHistory() {
		
	}
	public void btnSearchBook() {
		
	}
	public void btnReturnBook() {
		
	}
		
	
	public void btnUserInfo(ActionEvent event) throws Exception {

		
		FXMLLoader loader = new FXMLLoader();
		
		UserManager UM = UserManager.getInstance();

//		
//		String id=UM.s1.getSID();
//		String pass=UM.get
//		
//		UserManager UM = UserManager.getInstance();
//		
//		LoginMessage lm = new LoginMessage();
//
//		
//		
//		UM.setPass(pass);
//		UM.send(lm);
		
		
		if (UM.s1.getSID()!=null)
		{
			//start main menu process
			
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/gui/SubscriberForm.fxml").openStream());
			SubscriberFormController subscriberFormController = loader.getController();		
			subscriberFormController.loadSubscriber(UM.s1);
			
			
				
			
			Scene scene = new Scene(root);			
			scene.getStylesheets().add(getClass().getResource("/gui/SubscriberForm.css").toExternalForm());
			primaryStage.setTitle("Subscriber Managment Tool");

			primaryStage.setScene(scene);		
			primaryStage.show();
			
			
		}
		else
		{
			//IMPLEMENT SHOW WRONG ID/PASS MESSAGE INTO THE UI
		}
		
	}

	
	
	public void start(Stage primaryStage) throws Exception {	

		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
				
		Scene scene = new Scene(root);
		primaryStage.setTitle("Subscriber Entry Tool");
		primaryStage.setScene(scene);
		
		primaryStage.show();	 	   
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);
	}
	
	public void loadSubscriber(Subscriber s1) {
		this.sfc.loadSubscriber(s1);
	}	
	
	public  void display(String message) {
		System.out.println("message");
		
	}
	
}
