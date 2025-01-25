package gui;

import java.io.IOException;

import client.*;
import common.*;
import static common.GenericMessage.Action.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public  class MainMenuController extends BaseController  {
	private SubscriberFormController sfc;
	
	
	@FXML
	private Button btnLogin = null;
	
	@FXML
	private Button btnCreateSubscriber = null;
	
	@FXML
	private TextField lblID;

	public void goBack(ActionEvent event)
	{
		UM.logOut();
		SubscriberUI.mainController.goBack();

		
	}
	public void btnLendBook(ActionEvent event) throws Exception {
	//	FXMLLoader loader = new FXMLLoader();
		
		
		SubscriberUI.mainController.switchView("/gui/BorrowForm.fxml");
		

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
		
		GenericMessage msg = new GenericMessage();
		
		if (UM.librarian != null)
		{
			//enter user id window
			System.out.println("viewHistory librarian");
		}
		else
		{
			
			System.out.println("viewHistory else");
			//get the subscriber that's using this client 
			msg.subscriber = UM.s1;
			
			msg.action=get_Borrow_History;
			
			UM.send(msg);
			
			SubscriberUI.mainController.switchView("/gui/BorrowHistory.fxml");
		
			
		}
		
		
		

	}
	public void btnLendBook() {
		
	}
	public void btnSearchBook(ActionEvent event) throws IOException {
		
		SubscriberUI.mainController.switchView("/gui/SearchBook.fxml");
		
	}

	public void btnReturnBook(ActionEvent event) throws Exception {
		
		SubscriberUI.mainController.switchView("/gui/ExtendBorrowForm.fxml");

		
	    // load the new screen
//	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/BookReturn.fxml"));
//	    
//	    Parent root = loader.load();
//	    Stage stage = new Stage();
//	    stage.setTitle("Return Book Page");
//	    stage.setScene(new Scene(root));
//	    stage.show();
//	    ((Node) event.getSource()).getScene().getWindow().hide();
	}
	
	public void btnExtendBorrow(ActionEvent event) throws Exception {
		
		
		SubscriberUI.mainController.switchView("/gui/ExtendBorrowForm.fxml");
		
		
		
//		FXMLLoader loader = new FXMLLoader();
//		
//		
//		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary windows
//		Stage primaryStage = new Stage();
//		Pane root = loader.load(getClass().getResource("/gui/ExtendBorrowForm.fxml").openStream());
//		//BorrowWindowController borrowWindowController = loader.getController();		
//		//borrowWindowController.loadBorrow(UM.b);
//	
//		
//		Scene scene = new Scene(root);			
//		scene.getStylesheets().add(getClass().getResource("/gui/ExtendBorrowForm.css").toExternalForm());
//		primaryStage.setTitle("Borrow Extension Managment Tool");
//		primaryStage.setScene(scene);		
//		primaryStage.show();
	}
		
	
	public void btnUserInfo(ActionEvent event) throws Exception {

		SubscriberUI.mainController.switchView("SubscriberInformationPersonal.fxml");
		
	}

	
	public void viewAccountStatus (ActionEvent event)
	{
		
		GenericMessage getStatus = new GenericMessage();
		getStatus.subscriber=UM.s1;
		getStatus.action = get_Account_Status_History;
		
		UM.send(getStatus);
		
		SubscriberUI.mainController.switchView("/gui/AccountStatusHistory.fxml");
		
	}
	public void start(Stage primaryStage) throws Exception {	

		UM = UserManager.getInstance();

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
	
	public void btnOrderBook(ActionEvent event) throws Exception{
		
		SubscriberUI.mainController.switchView("/gui/CheckAvailability.fxml");

		
		

	}
}
