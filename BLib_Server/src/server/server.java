package server;

import java.io.IOException;

import common.BookMessage;
import common.BorrowMessage;
import common.LoginMessage;
import common.RequestMessage;
import common.SubMessage;
import controllers.BookController;
import controllers.BorrowController;
//import controllers.RequestController;
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
		//private RequestController requestController;
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
  		//requestController = RequestController.getInstance();
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
				if (!subscriberController.verifyPassword(lm.id, lm.password)) {
					client.sendToClient("wrong user id or password");
					return;//exiting the function to prevent it from completing DB operations on unverified user.
				}
				else
					client.sendToClient(subscriberController.fetchSubscriber(lm.id));
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
				borrowMessage = ((BorrowMessage)msg);
				client.sendToClient(borrowController.createBorrow(borrowMessage.s,borrowMessage.b,borrowMessage.borrow));
				
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
				//this is a fetch info request
				//client.sendToClient(requestController.requestExtension(reqMessage.borrow,reqMessage.b));
				
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
