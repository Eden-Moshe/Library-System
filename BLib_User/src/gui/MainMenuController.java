package gui;

import client.UserManager;
import common.Borrow;
import common.Subscriber;
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


public  class MainMenuController   {
	private SubscriberFormController sfc;
	private BorrowWindowController bwc;
	private static int itemIndex = 3;
	
	@FXML
	private Button btnLogin = null;
	
	@FXML
	private Button btnSend = null;
	
	@FXML
	private TextField lblID;
	private TextField lblPassword;


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
	
	public void btnViewHistory(ActionEvent event) throws Exception {
		
	}
	public void btnSearchBook(ActionEvent event) throws Exception {
		
	}
	public void btnReturnBook(ActionEvent event) throws Exception {
		
	}
	
	public void btnExtendBorrow(ActionEvent event) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		
		UserManager UM = UserManager.getInstance();
		
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary windows
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/ExtendBorrowForm.fxml").openStream());
		//BorrowWindowController borrowWindowController = loader.getController();		
		//borrowWindowController.loadBorrow(UM.b);
	
		
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/ExtendBorrowForm.css").toExternalForm());
		primaryStage.setTitle("Borrow Managment Tool");
		primaryStage.setScene(scene);		
		primaryStage.show();
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
