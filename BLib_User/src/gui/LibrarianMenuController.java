package gui;

import java.io.IOException;
import client.*;
import common.*;
import static common.GenericMessage.Action.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    private Button btnBack;

    @FXML
    private Button btnUserInfo, btnLendBook, btnSearchBook, btnExtendBorrow, btnMessage, btnViewReports;
    
    @FXML
    private ImageView logo; // This line fixes the missing 'logo' field issue
	
	@FXML
	private TextField lblID;
	private TextField lblPassword;

	
	public void btnLendBook(ActionEvent event) throws Exception {

		SubscriberUI.mainController.switchView("/gui/BorrowForm.fxml");
	}
	
	public void btnViewReports(ActionEvent event) throws Exception {

		SubscriberUI.mainController.switchView("/gui/ReportsWindow.fxml");
	}
		
	
	public void btnInbox(ActionEvent event) throws Exception {
	
	GenericMessage msg = new GenericMessage();
	
	msg.action = get_Librarian_Messages;
	
	msg.librarian = UM.librarian;

	UM.send(msg);
	
	SubscriberUI.mainController.switchView("/gui/LibrarianInbox.fxml");

	}
	
	public void btnViewHistory(ActionEvent event) throws Exception {

	}
	
	public void btnSearchBook(ActionEvent event) throws IOException {
		
		
		SubscriberUI.mainController.switchView("/gui/SearchBook.fxml");

	}

	public void btnReturnBook(ActionEvent event) throws Exception {
		
		SubscriberUI.mainController.switchView("/gui/BookReturn.fxml");

	}
	
	public void btnExtendBorrow(ActionEvent event) throws Exception {
		SubscriberUI.mainController.switchView("/gui/ExtendBorrowForm.fxml");
	}
		
	
	public void btnUserInfo(ActionEvent event) throws Exception {
		SubscriberUI.mainController.switchView("/gui/LibrarianEnterUserID.fxml");
	}
	
	public void createSubscriber(ActionEvent event) {
		SubscriberUI.mainController.switchView("/gui/CreateSubscriberForm.fxml");
	}
	
	@FXML
	private void viewDestroyedBooks(ActionEvent event) {
		//fetch data from table "destroyed_books"
		DestroyedMessage msg = new DestroyedMessage();
		msg.fetch=true;
		UM.send(msg);
		SubscriberUI.mainController.switchView("/gui/DestroyedBooks.fxml");
	}
	
	public void ViewMessageInbox(ActionEvent event) throws Exception {

		GenericMessage msg = new GenericMessage();
		
		msg.action = get_Librarian_Messages;
		
		msg.librarian = UM.librarian;

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
