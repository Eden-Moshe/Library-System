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


public  class LibrarianMenuController extends BaseController  {
	private SubscriberFormController sfc;
	private BorrowWindowController bwc;
	private static int itemIndex = 3;
	
	
	@FXML
	private Button btnLogin = null;
	
	@FXML
	private Button btnCreateSubscriber = null;
	
	@FXML
	private TextField lblID;
	private TextField lblPassword;

	
	public void btnLendBook(ActionEvent event) throws Exception {

		SubscriberUI.mainController.switchView("/gui/BorrowForm.fxml");
	}
		

	public void btnCreateSubscriber(ActionEvent event) throws Exception {
		
		
		SubscriberUI.mainController.switchView("/gui/CreateSubscriberForm.fxml");

//		FXMLLoader loader = new FXMLLoader();
//		
//		UserManager UM = UserManager.getInstance();
//		
//
//		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
//		Stage primaryStage = new Stage();
//		Pane root = loader.load(getClass().getResource("/gui/CreateSubscriberForm.fxml").openStream());
//
//		
//		Scene scene = new Scene(root);			
//		primaryStage.setTitle("Subscriber Creation Tool");
//
//		primaryStage.setScene(scene);		
//		primaryStage.show();

	}
	
	
	
	public void btnInbox(ActionEvent event) throws Exception {
	
	GenericMessage msg = new GenericMessage();
	
	msg.action = get_Librarian_Messages;
	
	msg.librarian = UM.librarian;
	System.out.println("librarian id = " + UM.librarian.getLibrarian_id());
	 System.out.println("librarian id = " + msg.librarian.getLibrarian_id());

	UM.send(msg);
	
	SubscriberUI.mainController.switchView("/gui/LibrarianInbox.fxml");
	
	
	

	}
	
	public void btnViewHistory(ActionEvent event) throws Exception {
		
	
		
		

	}
	
	public void btnSearchBook(ActionEvent event) throws IOException {
		
		
		SubscriberUI.mainController.switchView("/gui/SearchBook.fxml");
		
		
//	    // load the new screen
//	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SearchBook.fxml"));
//	    Parent root = loader.load();
//	    Stage stage = new Stage();
//	    stage.setTitle("Search for Books");
//	    stage.setScene(new Scene(root));
//	    stage.show();
//
//	    ((Node) event.getSource()).getScene().getWindow().hide();
	}

	public void btnReturnBook(ActionEvent event) throws Exception {
		
		
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
//		primaryStage.setTitle("Borrow Managment Tool");
//		primaryStage.setScene(scene);		
//		primaryStage.show();
	}
		
	
	public void btnUserInfo(ActionEvent event) throws Exception {

		
		
		SubscriberUI.mainController.switchView("/gui/LibrarianEnterUserID.fxml");

		

	}
	
	public void ViewMessageInbox(ActionEvent event) throws Exception {
		
		
		GenericMessage msg = new GenericMessage();
		
		msg.action = get_Librarian_Messages;
		
		msg.librarian = UM.librarian;
		System.out.println("librarian id = " + UM.librarian.getLibrarian_id());
		 System.out.println("librarian id = " + msg.librarian.getLibrarian_id());

		UM.send(msg);
		
		SubscriberUI.mainController.switchView("/gui/LibrarianInbox.fxml");
		
	
	}
	public void goBack(ActionEvent event) throws Exception {
		
		UM.logOut();
		SubscriberUI.mainController.goBack();
		
	
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
	
	
}
