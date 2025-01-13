// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

<<<<<<< HEAD
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import common.BLibData;
import common.Borrow;
import common.BorrowMessage;
import common.Subscriber;
import ocsf.client.AbstractClient;
=======
import ocsf.client.*;
import client.*;
import common.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
>>>>>>> origin/main

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
<<<<<<< HEAD
  	
  	//private final BlockingQueue<Object> responseQueue = new LinkedBlockingQueue<>();
=======
  	public Librarian librarian;
>>>>>>> origin/main


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
	  
//	    synchronized (responseQueue) {
//	        responseQueue.add(msg); // Add the response to the queue for sendAndWaitForResponse
//	        responseQueue.notifyAll(); // Notify any thread waiting for a response
//	    }

	  
		System.out.println("received message");

	  awaitResponse = false;
	  
<<<<<<< HEAD
	  if (msg instanceof Subscriber)
	  {
		  s1=(Subscriber) msg;
		  System.out.println(s1);
	  }
	  
	  if (msg instanceof String) {
	        UserManager.inb.setMessage(msg);  
		}
	  
	  
	  else
		  inb.setMessage(msg);
=======
	  if (msg instanceof Librarian)
		  librarian = (Librarian) msg;
	  if (msg == null)
		  inb.setMessage("received null");
	  inb.setMessage(msg);
	  
	  
>>>>>>> origin/main

	 
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
  
  
  
  

//  /**
//   * Sends a message to the server and waits for a response.
//   *
//   * @param message The message to send.
//   * @return The response from the server.
//   * @throws IOException If there's an issue communicating with the server.
//   */
//  public Object sendAndWaitForResponse(Object message) throws IOException {
//	  send(message);
//	  return inb.getObj();
//	  
//	  
//      awaitResponse = true;
//      sendToServer(message);
//
//      try {
//          // Wait for a response from the server
//          return responseQueue.take();
//      } catch (InterruptedException e) {
//          Thread.currentThread().interrupt();
//          throw new IOException("Interrupted while waiting for server response", e);
//      }
//  }
  
  
  
  
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
