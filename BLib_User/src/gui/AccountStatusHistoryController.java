package gui;

import java.util.ArrayList;

import client.*;
import common.*;
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


public  class AccountStatusHistoryController extends BaseController   {
	
	UserManager UM = UserManager.getInstance();


	
	
	@Override
	public void onLoad() {
		Object fromServer = UM.inb.getObj();
		if (fromServer == null)
		{
			//implement error message
		}
		ArrayList<AccountStatus> accountStatus = (ArrayList<AccountStatus>) fromServer;
		
		
		//iterate over accountStatus to view it in the window
		//make sure it is chronlogically ordered by date of status
		
		
		
	}
	
	
//	
//	
//	public void start(Stage primaryStage) throws Exception {	
//
//		Parent root = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
//				
//		Scene scene = new Scene(root);
//		primaryStage.setTitle("Login Window");
//		primaryStage.setScene(scene);
//		
//		primaryStage.show();	 	   
//	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Library Tool");
		System.exit(0);
	}
	
}
