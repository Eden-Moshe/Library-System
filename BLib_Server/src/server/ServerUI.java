package server;

import javafx.application.Application;
import javafx.stage.Stage;


import java.util.Vector;

import gui.ConnectionEntryController;
import gui.ServerPortFrameController;


import gui.ServerPortFrameController;
import server.server;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	
	public static ConnectionEntryController conEntry;
	
	
	//public static Vector<Student> students=new Vector<Student>();
	
	
	public static void main( String args[] ) throws Exception
	   {   

		 launch(args);
	  } // end main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		ServerPortFrameController sPort = new ServerPortFrameController(); // create StudentFrame
		sPort.start(primaryStage);
	}
	
	public static void runServer(String p)
	{
		 int port = 0; //Port to listen on

	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - could not convert port to int");
	        }
	    	
	        server sv = new server(port);
	        
	        try 
	        {
	          sv.listen(); //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	}
	

}
