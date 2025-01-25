package client;

import java.io.IOException;

import gui.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
// het shaked
public class SubscriberUI extends Application {
	public static UserManager UM; //only one instance
	public static Stage primaryStage ;
	private static String host="localhost";
	private static int port=5555;
	public static MainController mainController;
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
		
		
		try {
			UM = UM.getInstance(host,port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

		mainController = new MainController();
		mainController.start(primaryStage);
		
		
		
		
		
	}
}
