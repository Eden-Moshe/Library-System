package server;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import common.*;
import controllers.BookReturnController;
import common.BookReturnMessage;
import controllers.*;
import gui.ConnectionEntryController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import static common.GenericMessage.Action.*;
import controllers.BookReturnController;
import common.BookReturnMessage;

/**
 * The main server class for the BLib library management system.
 * This class handles all client-server communications, user authentication,
 * and coordinates various controllers for different aspects of the library system.
 * Extends AbstractServer to provide basic server functionality.
 */
public class server extends AbstractServer{
	
		/** The database controller instance */
		public static DBController db;
	    /** Connection entry controller for managing client connections */
		public static ConnectionEntryController conEntry;
	    /** Various controllers for different system functionalities */
		private SubscriberController subscriberController;
		private BorrowController borrowController;
		private BookController bookController;
		private RequestController requestController;
		private SearchController searchController;
		private LibrarianController librarianController;
		private OrderController orderController;
		private BookReturnController bokRetCont;
		private TimedTasks timedTasks;
		private ReportController reportController;
		
	    /** Lists to track connected clients */
		private ArrayList<ConnectionToClient> connectedSubscribers =  new ArrayList<>();
		private ArrayList<ConnectionToClient> connectedLibrarians =  new ArrayList<>();
		
	    /** Maps to associate connections with user IDs */
		private HashMap<ConnectionToClient, String> clientToUserMap = new HashMap<>();
		private HashMap<ConnectionToClient, String> librarianClientMap = new HashMap<>();

	  /**
	   * The default port to listen on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	  
	  
	  /**
	     * Constructs a new server instance.
	     * Initializes all controllers and sets up daily tasks.
	     *
	     * @param port The port number to listen on
	     */
	  public server(int port) 
	  {
		  
	    super(port);
	    System.out.println("classpath is: " + System.getProperty("java.class.path"));
		System.out.println("started server");
  		db = DBController.getInstance();
  		subscriberController = SubscriberController.getInstance();
  		borrowController = BorrowController.getInstance();
  		bookController = BookController.getInstance();
  		requestController = RequestController.getInstance();
  		searchController = SearchController.getInstance();
  		librarianController = LibrarianController.getInstance();
		orderController = OrderController.getInstance();
		bokRetCont = BookReturnController.getInstance();
		reportController = ReportController.getInstance();
		
		timedTasks = new TimedTasks();
		dailyTasks();
		
		System.out.println("finished starting server");

		
	  }
	  /**
	     * Schedules and runs daily maintenance tasks.
	     * Currently includes sending reminders and checking for expired orders.
	     */
	  private void dailyTasks()
	  {
		  timedTasks.scheduleTask(() -> {
	            try {
	            	//add all the calls to functions that need to run every day
	            	
	                borrowController.sendReminders();
	                orderController.checkAndCancelExpiredOrders();
	            	
	                System.out.println("Daily tasks completed successfully.");
	            } catch (Exception e) {
	                System.err.println("Daily tasks failed: " + e.getMessage());
	            }
	        });
	  }
	  

  /**
     * Shuts down the server and closes database connections.
     */
	public void shutDown() {

		db.closeConnection();
		System.exit(0);
	}
	

	
	/**
     * Authenticates a user login attempt.
     * 
     * @param client The client connection attempting to log in
     * @param id The user ID
     * @param pass The password
     * @return Object representing the logged-in user (Librarian or Subscriber), or null if authentication fails
     */
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
						String ip = client.getInetAddress().getHostAddress();
						
						Librarian newLibrarian = new Librarian(ret.getString("librarian_name"));
						
						librarianClientMap.put(client, id);
						conEntry.addClient(id, ip);
						
						newLibrarian.setLibrarian_id(id);
						return newLibrarian;

					}
					
				}
				else {
					conEntry.addClient(id, client.getInetAddress().getHostAddress());
					connectedSubscribers.add(client);
	                clientToUserMap.put(client, id); // Map client to user ID
					return subscriberController.fetchSubscriber(id);
				}
			}
		} catch (SQLException e) {
			System.out.println("userLogin failed");
			e.printStackTrace();
		}
		
		return null;
		
	}
	 /**
     * Handles messages received from clients.
     * Processes various types of messages and routes them to appropriate controllers.
     *
     * @param msg The message received from the client
     * @param client The client connection that sent the message
     */
    @Override
	public void handleMessageFromClient (Object msg, ConnectionToClient client) 
	{
		System.out.println("handle message from client");

		SubMessage sm;
		LoginMessage lm = null;
		BorrowMessage borrowMessage;
		BookMessage bookMessage;
		RequestMessage reqMessage;
		SearchMessage searchMessage;
		CheckAvailabilityMessage checkAvailabilityMessage;
		GenericMessage genericMsg;
		GetReturnDateMessage getReturnDateMessage;
		OrderMessage orderMessage;
		BookReturnMessage bokRet;
		ReportMessage reportMsg;
		DestroyedMessage destMsg;
		
		try {
			if (msg instanceof GenericMessage)
			{
				genericMsg =  (GenericMessage)msg;
				//authenticating subscriber;
				if (connectedSubscribers.contains(client))
				{
					//if client fails authentication
					if (!genericMsg.userID.equals(clientToUserMap.get(client))) {
						client.sendToClient("user ID has been tampered with. ID does not match client");
						return;
					}
				}
				
				if (genericMsg.action == get_Borrow_History)
				{
					client.sendToClient(borrowController.borrowList(genericMsg.subscriber));
				}
				
				if (genericMsg.action == get_Account_Status_History)
				{
					client.sendToClient(subscriberController.getAccountStatus(genericMsg.subscriber));
				}
				if (genericMsg.action == get_Librarian_Messages)
				{
					String id = genericMsg.librarian.getLibrarian_id();
					ArrayList<InboxMessage> im = null;
					try {
						  im = librarianController.retrieveMessages(id);
						  client.sendToClient(im);
						} catch (Exception e) {
						  e.printStackTrace(); // This will show you if there's an exception
							client.sendToClient(im);
						}
					
				}
				if (genericMsg.action == set_Librarian_Messages_Read)
				{
					client.sendToClient(librarianController.markRead((ArrayList<InboxMessage>)genericMsg.Obj));
				}
				if (genericMsg.action == set_New_Password)
				{
					subscriberController.editPassword(genericMsg.subscriber.getSID(), genericMsg.fieldVal);
					client.sendToClient("password changed succesfully");
				}
				
			}
			if (msg instanceof LoginMessage)
			{
				lm = ((LoginMessage) msg);
				Object newUser = userLogin(client,lm.id,lm.password);
				if (newUser == null) {
					client.sendToClient("wrong user id or password");
				
				}
				client.sendToClient(newUser);


			}
			
			if (msg instanceof LibrarianMessage && connectedLibrarians.contains(client))
			{
				LibrarianMessage LM = (LibrarianMessage) msg;
				if (LM.funcRequest.contains("Create Subscriber"))
				{
					client.sendToClient(subscriberController.addSubscriber(LM.sub));
					
				}
				
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

			if (msg instanceof BookReturnMessage) 
			{
				    bokRet=((BookReturnMessage)msg);
				    
				    //fetch book and check if its even in table send message accordingly 
		                    Book bo = bookController.fetchBook(bokRet.bookBarcode);
		                    if (bo == null) {
		                         	bokRet.allOfCon=false;
		                                client.sendToClient("No book found with that barcode.");
		                                return; // Stop further processing
		                                    }
		                    //check borrow number belongs to actual borrow
		                    ResultSet rs = db.retrieveRow("book", "barcode", bokRet.bookBarcode);
		                    try {
		        	   	 if (rs.next())
		        			{
		        	   		 
		        	   		 if (rs.getBoolean("book_available"))
		        	   		 {
		        	   			client.sendToClient("this book is not borrowed.");
		                         return; // Stop further processing	
		        	   		 }
		        	   		 

		        			}
		        			bokRet.allOfCon=true;
		        			client.sendToClient(bokRetCont.createNewBookReturn(bokRet.bookBarcode));
		            	       	      
		                    
		        	  	  
		                    }      
		                    catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
									   }				
			}
			
			if (msg instanceof DestroyedMessage) {
				destMsg = (DestroyedMessage) msg;
				//fetch destroyed books from database
				if (destMsg.fetch) {
					client.sendToClient(bokRetCont.fetchDest());
				}
				String barcode = destMsg.barcode;
				client.sendToClient(bokRetCont.destroyBook(barcode));
				
			}
			

			
			if (msg instanceof BorrowMessage) 
			{
			    borrowMessage = (BorrowMessage) msg;
			    //lines 212-217 are not necessary, subscriber is already inside borrowMessage.s
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
					client.sendToClient(borrowController.createBorrow(borrowMessage.s, borrowMessage.b, borrowMessage.borrow, borrowMessage.lib_id));
				//case where book is not available to borrow
				//check if that subscriber id has an order waiting to be fulfilled
				else 
					client.sendToClient(borrowController.CompareOrderID(borrowMessage.s, borrowMessage.b, borrowMessage.borrow, borrowMessage.lib_id));
					
			}
			if (msg instanceof CheckAvailabilityMessage) {
	            CheckAvailabilityMessage checkMsg = (CheckAvailabilityMessage) msg;
	            //OrderController.getInstance().saveUserId(lm.id);
	            boolean isAvailable = OrderController.getInstance().canOrderBook(checkMsg.bookName);
	            client.sendToClient(isAvailable);
	        }
			if (msg instanceof GetReturnDateMessage) {
			    GetReturnDateMessage dateMsg = (GetReturnDateMessage) msg;
			    Date returnDate = OrderController.getInstance().getNearestReturnDate(dateMsg.bookName);
			    client.sendToClient(returnDate);
			}
			
			if (msg instanceof OrderMessage) {
				orderMessage = (OrderMessage) msg;
				//orderMessage.bookName=orderController.getbookName();
				//orderMessage.subscriberId=Integer.parseInt(orderController.getUserId());
				
			    // Validate message details
			    if (orderMessage.bookName == null || orderMessage.bookName.isEmpty()) {
			        client.sendToClient("Book name cannot be null or empty.");
			        return;
			    }

				/*
				 * if (orderMsg.subscriberId == null) {
				 * client.sendToClient("Subscriber ID cannot be null."); return; }
				 */
			    
			    //OrderController.getInstance().saveUserId(lm.id);
			    boolean success = orderController.placeBookOrder(orderMessage.bookName, orderMessage.subscriberId);
			    client.sendToClient(success);
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
					client.sendToClient(requestController.extendBorrow(reqMessage.s,reqMessage.borrow,reqMessage.b,reqMessage.returnDate));
				}
				
				else client.sendToClient("Extension request can made 7 days or less from return date, request is denied.");
				
				
			}
			// Check if the received message is an instance of SearchMessage
			if (msg instanceof SearchMessage) {
			    // Cast the message to SearchMessage type
			    searchMessage = (SearchMessage) msg;

			    // Perform the search using the SearchController with parameters from the message
			    // and send the result back to the client
			    client.sendToClient(searchController.performSearch(searchMessage.bookName, searchMessage.bookGenre, searchMessage.bookDescription));
			}
			
			if (msg instanceof ReportMessage) {
	            reportMsg = (ReportMessage) msg;
	            //report Message is for borrowing report
	            if (reportMsg.statusReport) {
	            	reportController.statusReport(reportMsg);
	            	//returning count of active and frozen to client
	            	String returnMsg = String.format("active = %d frozen = %d", reportMsg.getActiveCount(),reportMsg.getFrozenCount());
	            	client.sendToClient(returnMsg);
	            }
	            if (reportMsg.borrowReport)	{
		            //report message is for status report
		            // Call borrowReport() and get the list of borrow records
		            List<BorrowRecord> borrowRecords = reportController.borrowReport();
		            client.sendToClient(borrowRecords);
	            }
	            
	        }
		
		
		
		
		
		}catch (IOException e) {
			e.printStackTrace();
			
			
		}
		
		
		
	}
		
		
		
		
	
	

    /**
     * Called when a new client connects to the server.
     *
     * @param client The client that connected
     */
    @Override
	  public void clientConnected(ConnectionToClient client) {
		  
		  System.out.println("CLIENT CONNECTED: " + client.toString());
		  
		  
		
		}
		  
		  
		  
		 

	  /**
	   * removes client from the UI
	   *
	   * @param client the connection with the client.
	   */
	   protected synchronized void clientDisconnected(ConnectionToClient client) {
		  

		  
		  if (connectedLibrarians.contains(client))
			  conEntry.removeClient(librarianClientMap.get(client));
		  else
			  conEntry.removeClient(clientToUserMap.get(client));
		  connectedSubscribers.remove(client);
		  connectedLibrarians.remove(client);
		  
		  clientToUserMap.remove(client);
		  
		  

	  }
	
	
	/**
	   * This method overrides the one in the superclass.  Called
	   * when the server starts listening for connections.
	   */
	  protected void serverStarted()
	  {
	    System.out.println("Server listening for connections on port " + getPort());
	    
	    
	  }
	  
	  /**
	   * This method overrides the one in the superclass.  Called
	   * when the server stops listening for connections.
	   */
	  protected void serverStopped()
	  {
	    System.out.println("Server has stopped listening for connections.");
	  }
	  


}
