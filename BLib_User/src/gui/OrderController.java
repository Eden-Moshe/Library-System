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
public class CheckControllerAvailability {
	@FXML
	private Button btnOrderBook= null;
	@FXML
    private TextField bookName;
	
	private String getbookName() {
		return bookName.getText();
	}
	public void btnCheckAvailability(ActionEvent event) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		
		String bookName=getbookName();
		OrderMessage om= new OrderMessage();
		om.bookName=bookName;
		//UserManager UM = UserManager.getInstance();
		
		//LoginMessage lm = new LoginMessage();
		//lm.id=id;
		//lm.password=pass;
		
		
		//UM.setPass(pass);
		//UM.send(lm);
		
		/*
		 * if (UM.s1.getSID()!=null)
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
		 */
	}		
}
