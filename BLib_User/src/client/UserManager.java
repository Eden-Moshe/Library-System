// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;

import common.BLibData;
import common.Borrow;
import common.Librarian;
import common.Subscriber;
import ocsf.client.AbstractClient;

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
	public Borrow b;
  	public BLibData udata;
  	public MyInbox inb=new MyInbox();
  	private String myPass;
  	public Librarian librarian;



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
  
  public String getPass()
  {
	  return myPass;
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
	  
		System.out.println("received message");

	  awaitResponse = false;
	  

	  if (msg instanceof Librarian)
		  librarian = (Librarian) msg;
	  if (msg == null)
		  inb.setMessage("received null");
	  inb.setMessage(msg);
	  
	  

	 
  }
  
 
  
  
 
  
  
  
  
  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  
//  public void send(Object message)  
//  {
//	    try {
//	        System.out.println("Preparing to send message: " + message);
//	        awaitResponse = true;
//	        sendToServer(message);
//	        System.out.println("Message sent successfully.");
//
//	        while (awaitResponse) {
//	            try {
//	                Thread.sleep(100);
//	            } catch (InterruptedException e) {
//	                System.err.println("Thread interrupted while waiting for response: " + e.getMessage());
//	                e.printStackTrace();
//	            }
//	        }
//	    } catch (IOException e) {
//	        System.err.println("IOException encountered: " + e.getMessage());
//	        e.printStackTrace();
//	        System.out.println("Could not send message to server: Terminating client." + e);
//	        quit();
//	    }
//    
//  }
  
  
  
  
//original send
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
