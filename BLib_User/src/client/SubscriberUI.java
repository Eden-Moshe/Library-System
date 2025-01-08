package client;

import common.*;
import gui.LoginController;
import gui.MainMenuController;
import gui.SubscriberEntryController;
import javafx.application.Application;
import javafx.stage.Stage;

public class SubscriberUI extends Application {
	public static UserManager UM; //only one instance
	public static Stage primaryStage ;
	private static String host="localhost";
	private static int port=5555;
	public static void main(String args[] ) throws Exception
	   { 
		
		// server IP
		try
		{
			host = args[0];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			host = "localhost";
		}
		
		// port
		try
		{
			port = Integer.parseInt(args[1]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			port = 5555;
		}
		
		
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		UM = UM.getInstance(host,port);
		SubscriberUI.primaryStage = primaryStage;				  		
		//SubscriberEntryController aFrame = new SubscriberEntryController(); // create SubscriberEntry
		LoginController LC = new LoginController();
		
		
		LC.start(primaryStage);
		
		
	}
}
