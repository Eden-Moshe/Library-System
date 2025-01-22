package server;

import java.io.IOException;
<<<<<<< HEAD

import common.BookMessage;
import common.BorrowMessage;
import common.LoginMessage;
import common.RequestMessage;
import common.SubMessage;
import common.SearchMessage;
import controllers.BookController;
import controllers.BorrowController;
import controllers.SearchController;
//import controllers.RequestController;
import controllers.SubscriberController;
=======
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import common.*;

import controllers.*;
>>>>>>> d8cdc31a9b2b8b40d883f95101f676bc7acbc2b7
import gui.ConnectionEntryController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import static common.GenericMessage.Action.*;


//BLib server-side
public class server extends AbstractServer{
	
		public static DBController db;
		private ConnectionEntryController conEntry;
		private SubscriberController subscriberController;
		private BorrowController borrowController;
		private BookController bookController;
<<<<<<< HEAD
		private SearchController searchController;
		//private RequestController requestController;
=======
		private RequestController requestController;
		private SearchController searchController;
		private LibrarianController librarianController;
		
		private ArrayList<ConnectionToClient> connectedSubscribers =  new ArrayList<>();
		private ArrayList<ConnectionToClient> connectedLibrarians =  new ArrayList<>();


>>>>>>> d8cdc31a9b2b8b40d883f95101f676bc7acbc2b7
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
<<<<<<< HEAD
  		searchController = SearchController.getInstance();
  		//requestController = RequestController.getInstance();
	  }

=======
  		requestController = RequestController.getInstance();
  		searchController = SearchController.getInstance();
  		librarianController = LibrarianController.getInstance();
	  }

	  
	  
	  //test this
	  public class DailyTaskRunner {

		    public static void scheduleDailyTask(Runnable task, int hour, int minute) {
		        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		        Runnable dailyTask = () -> {
		            System.out.println("Executing task at: " + java.time.LocalDateTime.now());
		            task.run();
		        };

		        long initialDelay = calculateInitialDelay(hour, minute);
		        long period = 24 * 60 * 60; // 24 hours in seconds

		        scheduler.scheduleAtFixedRate(dailyTask, initialDelay, period, TimeUnit.SECONDS);
		    }

		    private static long calculateInitialDelay(int hour, int minute) {
		        LocalTime now = LocalTime.now();
		        LocalTime targetTime = LocalTime.of(hour, minute);

		        if (now.isAfter(targetTime)) {
		            targetTime = targetTime.plusHours(24); // Schedule for the next day
		        }

		        return Duration.between(now, targetTime).getSeconds();
		    }

		    public static void main(String[] args) {
		        scheduleDailyTask(() -> {
		            System.out.println("Running the scheduled daily task!");
		            // Your function logic here
		        }, 8, 0); // Schedule the task to run daily at 08:00 AM
		    }
		}

	
	
	private Object userLogin (ConnectionToClient client ,String id, String pass)
	{
		ResultSet auth = db.retrieveRow("users","user_id", id);
		try {
			if (auth.next())
			{

				if (!auth.getString("user_password").equals(pass))
					return null;
				if (auth.getString("user_role").equals("Librarian")) {
					connectedLibrarians.add(client);
					ResultSet ret = db.retrieveRow("librarian", "librarian_id", id);
					if (ret.next())
					{
						Librarian newLibrarian = new Librarian(ret.getString("librarian_name"));
						newLibrarian.setLibrarian_id(id);
						return newLibrarian;
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
>>>>>>> d8cdc31a9b2b8b40d883f95101f676bc7acbc2b7
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
		SearchMessage searchMessage;
<<<<<<< HEAD
=======

		GenericMessage genericMsg;
		
>>>>>>> d8cdc31a9b2b8b40d883f95101f676bc7acbc2b7
		try {
		
			if (msg instanceof GenericMessage)
			{
				genericMsg =  (GenericMessage)msg;
				
				if (genericMsg.action == get_Borrow_History)
				{
					client.sendToClient(borrowController.borrowList(genericMsg.subscriber));
				}
				
				if (genericMsg.action == get_Account_Status)
				{
					client.sendToClient(subscriberController.getAccountStatus(genericMsg.subscriber));
				}
				if (genericMsg.action == get_Librarian_Messages)
				{
					String id = genericMsg.librarian.getLibrarian_id();
					ArrayList<InboxMessage> im = null;
					try {
						  im = librarianController.retrieveMessages(id);
						  System.out.println("Fuck");
						  client.sendToClient(im);
						} catch (Exception e) {
						  e.printStackTrace(); // This will show you if there's an exception
						  System.out.println("Fuck2");
							client.sendToClient(im);
						}
					
				}
				if (genericMsg.action == set_Librarian_Messages_Read)
				{
					client.sendToClient(librarianController.markRead((ArrayList<InboxMessage>)genericMsg.Obj));
				}
				
			}
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
<<<<<<< HEAD

=======
>>>>>>> d8cdc31a9b2b8b40d883f95101f676bc7acbc2b7
			// Check if the received message is an instance of SearchMessage
			if (msg instanceof SearchMessage) {
			    // Cast the message to SearchMessage type
			    searchMessage = (SearchMessage) msg;

			    // Perform the search using the SearchController with parameters from the message
			    // and send the result back to the client
			    client.sendToClient(searchController.performSearch(searchMessage.bookName, searchMessage.bookGenre, searchMessage.bookDescription));
			}
<<<<<<< HEAD
=======
		
		
		
		
>>>>>>> d8cdc31a9b2b8b40d883f95101f676bc7acbc2b7
		
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
