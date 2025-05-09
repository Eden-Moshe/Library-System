package gui;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.ServerUI;

public class ServerPortFrameController  {
	//private SubscriberFormController sfc;	
	//public ConnectionEntryController conEntry;
	String temp="";
	
	@FXML
	private Button btnExit = null;
	@FXML
	private Button btnDone = null;
	@FXML
	private Label lbllist;
	
	@FXML
	private TextField portxt;
	ObservableList<String> list;
	
	private String getport() {
		return portxt.getText();			
	}
	
	public void Done(ActionEvent event) throws Exception {
		String p;
		
		p=getport();
		if(p.trim().isEmpty()) {
			System.out.println("You must enter a port number");
					
		}
		else
		{
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			
			ServerUI.runServer(p);
			

			
			
			//open connected hosts window
			

			Pane root = loader.load(getClass().getResource("Connection.fxml").openStream());
		
			ServerUI.conEntry = loader.getController();		
			//conEntry.loadConnection(CIP,CCON,CSTATUS);
			//ServerUI.conEntry.loadConnection("","","");
			Scene scene = new Scene(root);			
			primaryStage.setTitle("Client Managment Window");

			primaryStage.setScene(scene);		
			primaryStage.show();
			
			
		}
	}

	public void start(Stage primaryStage) throws Exception {	
//		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
//				
//		Scene scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
//		primaryStage.setTitle("Client");
//		primaryStage.setScene(scene);
//		
//		primaryStage.show();		
		
		
		
		FXMLLoader loader = new FXMLLoader();

		
		ServerUI.runServer("5555");
		
		Pane root = loader.load(getClass().getResource("/gui/Connection.fxml").openStream());
		
		ServerUI.conEntry = loader.getController();		
		//conEntry.loadConnection(CIP,CCON,CSTATUS);
		//ServerUI.conEntry.loadConnection("","","");
		Scene scene = new Scene(root);			
		primaryStage.setTitle("Client Managment Window");

		primaryStage.setScene(scene);		
		primaryStage.show();
		
		
		
		
	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool");
		System.exit(0);			
	}

}