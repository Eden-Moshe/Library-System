// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import client.*;
import common.BLibData;
import common.Subscriber;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */


public final class UserManager extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
	private static UserManager instance=null;
	public static boolean awaitResponse = false;
	public Subscriber  s1;
  	public BLibData udata;
  	public static MyInbox inb=new MyInbox();
  	private String myPass;


  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  private UserManager(String host, int port) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
	udata = new BLibData();
    openConnection();
  }
  public void setPass(String p)
  {
	  myPass = p;
  }
  
  
  public static UserManager getInstance(String host, int port) throws IOException {
	    if (instance == null) {
	        instance = new UserManager(host, port);
	    }
	    return instance;
	}
  
  public static UserManager getInstance() {
//	    if (instance == null) {
//	        throw new IllegalStateException("UserManager is not initialized. Call getInstance(String host, int port) first.");
//	    }
	    return instance;
	}

  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	  


	  
	  
	  awaitResponse = false;
	  
	  if (msg instanceof Subscriber)
	  {
		  s1=(Subscriber) msg;
		  System.out.println(s1);
	  }

	  else
		  inb.setMessage(msg);
	  
	  

	 
  }
  
 
  
  
 
  
  
  
  
  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  
  public void send(Object message)  
  {
    try
    {
    	//openConnection();//in order to send more than one message
       	awaitResponse = true;
    	sendToServer(message);
		// wait for response
		while (awaitResponse) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    
    catch(IOException e)
    {
    	e.printStackTrace();
      System.out.println("Could not send message to server: Terminating client."+ e);
      quit();
    }
    
  }
  
//  public void handleSavingStudentInfo(Student newStudent , Student oldStudent) {
//	  //SaveStudentInfo ssi = new SaveStudentInfo(newStudent , oldStudent) ;
//	  try {
//		openConnection();
//		sendToServer(ssi);
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//  }

  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
