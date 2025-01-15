package server;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.*;

import controllers.BookController;
import controllers.BorrowController;
import controllers.RequestController;
import controllers.SubscriberController;
import gui.ConnectionEntryController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

//BLib server-side
public class server extends AbstractServer{
	
		public static DBController db;
		private ConnectionEntryController conEntry;
		private SubscriberController subscriberController;
		private BorrowController borrowController;
		private BookController bookController;
		private RequestController requestController;
		
		private ArrayList<ConnectionToClient> connectedSubscribers =  new ArrayList<>();
		private ArrayList<ConnectionToClient> connectedLibrarians =  new ArrayList<>();


	  /**
	   * The default port to listen on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	  
	  
	  /**
	   * Constructs an instance of the echo server.
	   *
	   * @param port The port number to connect on.
	   */
	  public server(int port) 
	  {
	    super(port);
  		db = DBController.getInstance();
  		subscriberController = SubscriberController.getInstance();
  		borrowController = BorrowController.getInstance();
  		bookController = BookController.getInstance();
  		requestController = RequestController.getInstance();
	  }

	
	
	private Object userLogin (ConnectionToClient client ,String id, String pass)
	{
		System.out.println("userlogin the fuck");
		ResultSet auth = db.retrieveRow("users","user_id", id);
		try {
			if (auth.next())
			{
				System.out.println("auth.next the fuck");

				if (!auth.getString("user_password").equals(pass))
					return null;
				System.out.println("auth.next pass is fine the fuck");
				if (auth.getString("user_role").equals("Librarian")) {
					System.out.println("Librarian the fuck");
					connectedLibrarians.add(client);
					System.out.println("Librarian connected the fuck");
					ResultSet ret = db.retrieveRow("librarian", "librarian_id", id);
					if (ret.next())
					{
						return new Librarian(ret.getString("librarian_name"));
						//changed here to also get librarian id cause i changed how constructor looks
						//return new Librarian(ret.getString("librarian_name"), ret.getString("librarian_id"));
					}
					
				}
				else {
					
					connectedSubscribers.add(client);
					return subscriberController.fetchSubscriber(id);
				}
			}
		} catch (SQLException e) {
			System.out.println("userLogin failed");
			e.printStackTrace();
		}
		
		return null;
		
	}
	@Override
	  /**
	   * This method handles any messages received from the client.
	   *
	   * @param msg The message received from the client.
	   * @param client The connection from which the message originated.
	 * @throws IOException 
	   */
	public void handleMessageFromClient (Object msg, ConnectionToClient client) 
	{
		SubMessage sm;
		LoginMessage lm;
		BorrowMessage borrowMessage;
		BookMessage bookMessage;
		RequestMessage reqMessage;
		
		try {
		
			if (msg instanceof LoginMessage)
			{
				lm = ((LoginMessage) msg);
				System.out.println("login the fuck " +  lm.id + " pass " + lm.password);
				Object newUser = userLogin(client,lm.id,lm.password);
				System.out.println("newuser the fuck");
				if (newUser == null) {
					client.sendToClient("wrong user id or password");
					System.out.println("wrong user id the fuck");
				
				}
				System.out.println("newuser the fuck " +  newUser.toString());
				client.sendToClient(newUser);
				System.out.println("newuser the fuck 2" +  newUser.toString());

				
//				if (!subscriberController.verifyPassword(lm.id, lm.password)) {
//					client.sendToClient("wrong user id or password");
//					return;//exiting the function to prevent it from completing DB operations on unverified user.
//				}
//				else
//				{
//					connectedSubscribers.add(client);
//					client.sendToClient(subscriberController.fetchSubscriber(lm.id));
//					
//				}
			}
			if (msg instanceof LibrarianMessage)
			{
				
			}
			if (msg instanceof SubMessage)
			{
				sm = ((SubMessage) msg);
				if (!subscriberController.verifyPassword(sm.pKey, sm.password)) {
					client.sendToClient("wrong user id or password");
					return;//exiting the function to prevent it from completing DB operations on unverified user.
				}
				//this is a fetch info request
				if(!sm.editBool) 
						client.sendToClient(subscriberController.fetchSubscriber(sm.pKey));
					
				//this is an edit request
				else
				{
					subscriberController.editSubscriber(sm.pKey,sm.fieldCol,sm.fieldVal);
					client.sendToClient(subscriberController.fetchSubscriber(sm.pKey));
				}
				
			}
			
			if (msg instanceof BorrowMessage) 
			{
			    borrowMessage = (BorrowMessage) msg;
			    
			    //fetch subscriber and check if its even in table send message accordingly  
			    Subscriber sub = subscriberController.fetchSubscriber(borrowMessage.s.getSID());
		        if (sub == null) {
		            client.sendToClient("No subscriber found with that ID.");
		            return; // Stop further processing
		        }
		        
			    //fetch book and check if its even in table send message accordingly 
		        Book bo = bookController.fetchBook(borrowMessage.b.getBookBarcode());
		        if (bo == null) {
		            client.sendToClient("No book found with that barcode.");
		            return; // Stop further processing
		        }

		        //assign sub to s instance in BorrowMessage which is Subscriber
			    borrowMessage.s = sub;
		        //assign bo to b instance in BorrowMessage which is Book
				borrowMessage.b = bo;
				
		        //check that book is available for borrowing 
				if (borrowMessage.b.isBookAvailable())
					//at this point both subscriber and book details are in s and b so now we create new Borrow
					client.sendToClient(borrowController.createBorrow(borrowMessage.s, borrowMessage.b, borrowMessage.borrow));
				else 
					client.sendToClient("Book is not available for borrowing, consider requesting an order or searching a different barcode for the same book.");
			}
			
			
			if (msg instanceof BookMessage) 
			{
				bookMessage = ((BookMessage)msg);
				//this is a fetch info request
				if(!bookMessage.editBool) 
						client.sendToClient(bookController.fetchBook(bookMessage.pKey));
				
			}
			
			if (msg instanceof RequestMessage) 
			{
				reqMessage = ((RequestMessage)msg);
				
			    // Use a StringBuilder to capture error messages
			    StringBuilder errorMessage = new StringBuilder();
			    //fetching Borrow from table with book barcode and creating new Borrow instance with subscriber id and dates
			    Borrow borrow = requestController.fetchBorrow(reqMessage.s.getSID(), reqMessage.b.getBookBarcode(), errorMessage);

			    if (borrow == null) {
			        client.sendToClient(errorMessage.toString()); // Send the error message to the client
			        return; // Stop further processing
			    }
				//fetching Borrow from table with book barcode and creating new Borrow instance with subscriber id and dates
				reqMessage.borrow = borrow;

				//fetching into b all book info
				reqMessage.b = bookController.fetchBook(reqMessage.b.getBookBarcode());
				//checking if original return date is not more than a week away
				if (requestController.isEligibleForExtension(reqMessage.borrow.getReturnDate())) {
					//update return date in database
					client.sendToClient(requestController.extendBorrow(reqMessage.borrow,reqMessage.b,reqMessage.returnDate));
				}
				
				else client.sendToClient("Extension request can made 7 days or less from return date, request is denied.");
				
				//need to add implementation if book already has an order
				
			}
		
		
		
		
		
		}catch (IOException e) {
			e.printStackTrace();
			
			
		}
		
		
		
	}
		
		
		
		
		
		
//	  	if(msg instanceof ArrayList) {
//	  		System.out.println("the server received the data successfully!");
//	  		try {
//	  			//passing off the relevant classes to handle requests
//	  			if (((ArrayList<String>) msg).get(0).contains("subscriber"))
//	  			{
//	  				if (((ArrayList<String>) msg).get(0).contains("info"))
//	  				{	
//	  					client.sendToClient(db.inputOutput("output", (ArrayList<String>) msg));
//	  				}
//	  				if (((ArrayList<String>) msg).get(0).contains("edit"))
//	  				{	
//	  					client.sendToClient(db.inputOutput("input", (ArrayList<String>) msg));
//	  				}
//	  				
//	  			}
//	  			
//	  			else
//	  				client.sendToClient(db.inputOutput("input",(ArrayList<String>) msg));
//	  				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	  		
//	  	}
//	  	else
//  			System.out.println("poorly formatted message, message discarded");
//	  	
//	  	if (msg instanceof String)
//	  		System.out.println("user " + client.toString() + " says: " + msg.toString());

	    //System.out.println("Message received: " + msg + " from " + client);
//	  }
	
	
	

	  /**
	   * updates client info
	   * @param client the connection connected to the client.
	   */
	  public void clientConnected(ConnectionToClient client) {
		  
		  System.out.println("CLIENT CONNECTED: " + client.toString());
		  
		  
		  try {
			ServerUI.conEntry.loadConnection(client.getInetAddress().toString(), "TEMP", "connected");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		  
		  
//		  
//		  	FXMLLoader loader = new FXMLLoader();
//
//			Stage primaryStage = new Stage();
//			Pane root=null;
//			try {
//				root = loader.load(getClass().getResource("/gui/Connection.fxml").openStream());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			conEntry = loader.getController();		
//			//conEntry.loadConnection(CIP,CCON,CSTATUS);
//			conEntry.loadConnection(client.getInetAddress().toString(), "connected", client.toString());
//			Scene scene = new Scene(root);			
//			primaryStage.setTitle("Client Managment Window");
//
//			primaryStage.setScene(scene);		
//			primaryStage.show();
//			System.out.println("SDGFFDSAF: " + client.toString() + "NET: " + client.getInetAddress().toString());

	  }

	  /**
	   * removes client from the UI
	   *
	   * @param client the connection with the client.
	   */
	  synchronized protected void clientDisconnected(
	    ConnectionToClient client) {
		  
		  connectedSubscribers.remove(client);
		  connectedLibrarians.remove(client);
		  
		  conEntry.removeConnection();

	  }
	
	
	/**
	   * This method overrides the one in the superclass.  Called
	   * when the server starts listening for connections.
	   */
	  protected void serverStarted()
	  {
	    System.out.println
	      ("Server listening for connections on port " + getPort());
	    
	    
	  }
	  
	  /**
	   * This method overrides the one in the superclass.  Called
	   * when the server stops listening for connections.
	   */
	  protected void serverStopped()
	  {
	    System.out.println
	      ("Server has stopped listening for connections.");
	  }
	  


}
