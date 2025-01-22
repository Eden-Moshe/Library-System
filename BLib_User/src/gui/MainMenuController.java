package gui;

<<<<<<< HEAD
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
=======
import java.io.IOException;
>>>>>>> d8cdc31a9b2b8b40d883f95101f676bc7acbc2b7

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


<<<<<<< HEAD
	public void btnLendBook() {
		
	}
	public void btnViewHistory() {
		
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
=======

	public void btnLendBook(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		
		UserManager UM = UserManager.getInstance();
		
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary windows
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/BorrowForm.fxml").openStream());
		//BorrowWindowController borrowWindowController = loader.getController();		
		//borrowWindowController.loadBorrow(UM.b);
	
		
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/BorrowForm.css").toExternalForm());
		primaryStage.setTitle("Borrow Managment Tool");
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
		

	public void btnCreateSubscriber(ActionEvent event) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		
		UserManager UM = UserManager.getInstance();
		

		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/CreateSubscriberForm.fxml").openStream());

		
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Subscriber Creation Tool");

		primaryStage.setScene(scene);		
		primaryStage.show();

	}
	
	public void btnViewHistory(ActionEvent event) throws Exception {


	}
	public void btnLendBook() {
		
	}
	public void btnSearchBook(ActionEvent event) throws IOException {
	    // load the new screen
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SearchBook.fxml"));
	    Parent root = loader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Search for Books");
	    stage.setScene(new Scene(root));
	    stage.show();

	    ((Node) event.getSource()).getScene().getWindow().hide();
	}

	public void btnReturnBook(ActionEvent event) throws Exception {
		
		
>>>>>>> d8cdc31a9b2b8b40d883f95101f676bc7acbc2b7
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
