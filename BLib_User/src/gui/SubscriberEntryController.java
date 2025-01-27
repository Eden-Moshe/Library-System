package gui;

import client.UserManager;
import common.SubMessage;
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


public  class SubscriberEntryController  extends BaseController {
	private SubscriberFormController sfc;	
	private static int itemIndex = 3;
	
	@FXML
	private Button btnExit = null;
	
	@FXML
	private Button btnSend = null;
	
	@FXML
	private TextField idtxt;
	
	private String getID() {
		return idtxt.getText();
	}
	

	
	public void Send(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		
		String id=getID();
		
		UserManager UM = UserManager.getInstance();

		
		SubMessage getSub = new SubMessage();
		getSub.editBool=false;
		getSub.pKey=id;
		
		UM.send(getSub);
		
		
		
		if(id.trim().isEmpty() || UM.s1.getSID()==null)
			{
	
				System.out.println("You must enter a valid id number" +id);	
			}
		else
		{
			
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

	}

	
	
	public void start(Stage primaryStage) throws Exception {	

		Parent root = FXMLLoader.load(getClass().getResource("SubscriberEntry.fxml"));
				
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("SubscriberEntry.css").toExternalForm());
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
