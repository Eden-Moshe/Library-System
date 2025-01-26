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
//BLib server-side
public class server extends AbstractServer{
	
		public static DBController db;
		//private ConnectionEntryController conEntry;
		public static ConnectionEntryController conEntry;
		private SubscriberController subscriberController;
		private BorrowController borrowController;
		private BookController bookController;
		private RequestController requestController;
		private SearchController searchController;
		private LibrarianController librarianController;
		private OrderController orderController;
		private BookReturnController bokRetCont;
	
		private ReportController reportController;
		
		private ArrayList<ConnectionToClient> connectedSubscribers =  new ArrayList<>();
		private ArrayList<ConnectionToClient> connectedLibrarians =  new ArrayList<>();
		
		//variable to hold subscribers IDs to their connection
		//to prevent unauthorized access to other subscribers
		private HashMap<ConnectionToClient, String> clientToUserMap = new HashMap<>();
		private HashMap<ConnectionToClient, String> librarianClientMap = new HashMap<>();

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
  		searchController = SearchController.getInstance();
  		librarianController = LibrarianController.getInstance();
		orderController = OrderController.getInstance();
		bokRetCont = BookReturnController.getInstance();
		
		reportController = ReportController.getInstance();
		
	  }
	  
	  
//	  public void setUIController(ConnectionEntryController connectionController)
//	  {
//		  conEntry = connectionController;
//	  }

	  
	  
//	  //test this
//	  public class DailyTaskRunner {
//
//		    public static void scheduleDailyTask(Runnable task, int hour, int minute) {
//		        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//		        Runnable dailyTask = () -> {
//		            System.out.println("Executing task at: " + java.time.LocalDateTime.now());
//		            task.run();
//		        };
//
//		        long initialDelay = calculateInitialDelay(hour, minute);
//		        long period = 24 * 60 * 60; // 24 hours in seconds
//
//		        scheduler.scheduleAtFixedRate(dailyTask, initialDelay, period, TimeUnit.SECONDS);
//		    }
//
//		    private static long calculateInitialDelay(int hour, int minute) {
//		        LocalTime now = LocalTime.now();
//		        LocalTime targetTime = LocalTime.of(hour, minute);
//
//		        if (now.isAfter(targetTime)) {
//		            targetTime = targetTime.plusHours(24); // Schedule for the next day
//		        }
//
//		        return Duration.between(now, targetTime).getSeconds();
//		    }
//
//		    public static void main(String[] args) {
//		        scheduleDailyTask(() -> {
//		            System.out.println("Running the scheduled daily task!");
//		            // Your function logic here
//		        }, 8, 0); // Schedule the task to run daily at 08:00 AM
//		    }
//		}
	  
	  
	
	public class DailyTaskRunner {
	    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	    private static LocalDate lastFetchDate = null; // Store last fetch date in memory
	
	    public static void scheduleDailyTask(Runnable dailyTask, Runnable monthlyTask, int hour, int minute) {
	        Runnable combinedTask = () -> {
	            System.out.println("Executing daily task at: " + LocalDateTime.now());
	            dailyTask.run();
	
	            if (shouldRunMonthlyTask()) {
	                System.out.println("Executing monthly task...");
	                monthlyTask.run();
	                lastFetchDate = LocalDate.now(); // Update last fetch date
	            }
	        };
	
	        long initialDelay = calculateInitialDelay(hour, minute);
	        long period = 24 * 60 * 60; // 24 hours in seconds
	
	        scheduler.scheduleAtFixedRate(combinedTask, initialDelay, period, TimeUnit.SECONDS);
	    }
	
	    private static boolean shouldRunMonthlyTask() {
	        LocalDate today = LocalDate.now();
	        return lastFetchDate == null || lastFetchDate.getMonthValue() != today.getMonthValue();
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
	        scheduleDailyTask(
	            () -> System.out.println("Running the scheduled daily task!"), // Daily task
	            () -> System.out.println("Running the scheduled monthly task!"), // Monthly task
	            8, 0 // Run at 08:00 AM
	        );
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
			if (msg instanceof LibrarianMessage && connectedLibrarians.contains(client))
			{
				System.out.println("if (msg instanceof LibrarianMessage && connectedLibrarians.contains(client))");
				LibrarianMessage LM = (LibrarianMessage) msg;
				if (LM.funcRequest.contains("Create Subscriber"))
				{
					System.out.println("if (LM.funcRequest.contains(Create Subscriber))");
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
				    //fetch subscriber and check if its even in table send message accordingly  
				    Subscriber sub = subscriberController.fetchSubscriber(bokRet.borrowerId);
		                    if (sub == null) {
		           		 bokRet.allOfCon=false;
		          		 client.sendToClient("No subscriber found with that ID.");
		          	         return; // Stop further processing
		      				     }
				    //fetch book and check if its even in table send message accordingly 
		                    Book bo = bookController.fetchBook(bokRet.bookBarcode);
		                    if (bo == null) {
		                         	bokRet.allOfCon=false;
		                                client.sendToClient("No book found with that barcode.");
		                                return; // Stop further processing
		                                    }
		                    //check borrow number belongs to actual borrow
		                    ResultSet rs = db.retrieveRow("borrow", "borrow_number", bokRet.borrowNum);
		                    try {
		        	   	 if (rs.next())
		        			{
		        			//rs.getString(1)== borrow number   rs.getString(2)==bookbarcode  rs.getString(4)==borrowerid
		        			if(!(bokRet.borrowNum.equalsIgnoreCase(rs.getString(1)) && bokRet.borrowerId.equalsIgnoreCase(rs.getString(4)) &&
		        					bokRet.bookBarcode.equalsIgnoreCase(rs.getString(2)) )) {
		        				if(!(bokRet.borrowNum.equalsIgnoreCase(rs.getString(1)))) {
			    		           		 bokRet.allOfCon=false;
			    		           		 client.sendToClient("This is not the right borrow num");
			    		          		 return; // Stop further processing		
		        			          }
		        				if(!(bokRet.borrowerId.equalsIgnoreCase(rs.getString(4)))) {
			    		                         bokRet.allOfCon=false;
			    		                         client.sendToClient("This is not the right borrower ID");
			    		                         return; // Stop further processing		
		        				}
		        				if(!(bokRet.bookBarcode.equalsIgnoreCase(rs.getString(2)))) {
			    		                         bokRet.allOfCon=false;
			    		                         client.sendToClient("This is not the right book barcode");
			    		                         return; // Stop further processing		
		        				}	 
		        			}
		        			bokRet.allOfCon=true;
		        			client.sendToClient(bokRetCont.createNewBookReturn(bokRet.bookBarcode,bokRet.borrowNum));
		            	       	      }
		        	  	  else {
		        			 client.sendToClient("No borrow found with that borrow number.");
		        			 bokRet.allOfCon=false;
		        		       }
		                    }      
		                    catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
									   }				
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
				else 
					client.sendToClient("Book is not available for borrowing, consider requesting an order or searching a different barcode for the same book.");
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
					client.sendToClient(requestController.extendBorrow(reqMessage.borrow,reqMessage.b,reqMessage.returnDate));
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
	   * updates client info
	   * @param client the connection connected to the client.
	   */
	  public void clientConnected(ConnectionToClient client) {
		  
		  System.out.println("CLIENT CONNECTED: " + client.toString());
		  
		  
		
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

//	  }

	  /**
	   * removes client from the UI
	   *
	   * @param client the connection with the client.
	   */
	   protected synchronized void clientDisconnected(ConnectionToClient client) {
		  
			System.out.println("client disconnected");

		  
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
