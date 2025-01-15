package gui;

import client.UserManager;
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
		
		System.out.println("pre null instance");
		//received null. something went wrong. temp code
		if (UM.inb.getMessage().contains("null"))
			return;
		
		System.out.println("pre sub instance");
		//login and show subscriber menu
		Object fromServer = UM.inb.getObj();
		if (fromServer instanceof Subscriber)
		{
			System.out.println("sub instance");
			UM.s1=(Subscriber) fromServer;
			//UM.setSub((Subscriber)UM.inb.getObj());
			//System.out.println("sub is" + UM.s1);
			System.out.println("sub is" + UM.s1);
			
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
		else {
		if (UM.librarian != null)
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
		}
		//else
		//{
			//IMPLEMENT SHOW WRONG ID/PASS MESSAGE INTO THE UI
		//}
		
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
