package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import common.Book;
import common.Borrow;
import common.Subscriber;
import server.DBController;

/**
 * Controller class responsible for managing borrow-related operations.
 */
public class BorrowController {
    /**
     * Singleton instance of BorrowController.
     */
    private static final BorrowController instance = new BorrowController();
    
    /**
     * Instance of BookController to fetch book details.
     */
    private static BookController bookController = BookController.getInstance();
    
    /**
     * Database controller instance for handling database operations.
     */
    private DBController db;
    
    /**
     * Table name for borrow records in the database.
     */
    private static String tName = "borrow";
    
    /**
     * Retrieves the singleton instance of BorrowController.
     * 
     * @return the singleton instance of BorrowController
     */
    public static BorrowController getInstance() {
        return instance;
    }
    
    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the database controller instance.
     */
    private BorrowController() {
        db = DBController.getInstance();
    }
    
    /**
     * Retrieves a list of borrow records for a given subscriber.
     * 
     * @param sub the subscriber whose borrow records are retrieved
     * @return an ArrayList of Borrow instances
     */
    public ArrayList<Borrow> borrowList(Subscriber sub) {
        Borrow bor;
        String tempStr;
        Date borrowDate;
        Date returnDate;
        ArrayList<Borrow> ret = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        
        ResultSet rs = db.retrieveRow(tName, "subscriber_id", sub.getSID());
        try {
            while (rs.next()) {
                tempStr = rs.getString("borrow_date");
                borrowDate = dateFormat.parse(tempStr);
                
                tempStr = rs.getString("return_date");
                returnDate = dateFormat.parse(tempStr);
                
                bor = new Borrow(sub, borrowDate, returnDate);
                bor.bo = bookController.fetchBook(rs.getString("book_barcode"));
                ret.add(bor);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Creates a new borrow record in the database.
     * 
     * @param s the subscriber borrowing the book
     * @param b the book being borrowed
     * @param borrow the borrow instance containing borrow details
     * @param lib_id the librarian ID processing the borrow request
     * @return a message indicating the success or failure of the borrow operation
     */
    public String createBorrow(Subscriber s, Book b, Borrow borrow, String lib_id) {
        if (canBorrow(s)) {
            String[] fields = {"book_barcode", "lending_librarian", "subscriber_id", "borrow_date", "return_date", "actual_returned_date"};
            String[] values = {
                b.getBookBarcode(),
                lib_id,
                s.getSID(),
                new java.sql.Date(borrow.getBorrowDate().getTime()).toString(),
                new java.sql.Date(borrow.getReturnDate().getTime()).toString(),
                null,
            };
            db.insertRow("borrow", fields, values);
            db.editRow("book", "barcode", b.getBookBarcode(), "book_available", "false");
	        db.editRow("book", "barcode", b.getBookBarcode(), "return_date", borrow.getReturnDate().toString());
	      
	        
	        return "Borrow request sent successfully."; 
        } else {
            return "Account status is frozen. Cannot create borrow.";
        }
    }
    
     
    
    
    /**
     * Checks if a subscriber is eligible to borrow books.
     * 
     * @param s the subscriber to check
     * @return true if the subscriber is active, false otherwise
     */
    public boolean canBorrow(Subscriber s) {
        return "active".equalsIgnoreCase(s.getStatus());
    }
    
    
    
    /**
     * Sends reminders to book borrowers who have not returned books one day before their due date.
     * 
     * Performs the following steps:
     * - Calculates the date one day before the current date
     * - Retrieves borrowers with unreturned books from the database
     * - Sends email and SMS reminders to each borrower
     * 
     */
    public void sendReminders()
    {
    	Date date = new Date();
    	Calendar calendar = Calendar.getInstance();
    	
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date oneDayBefore = calendar.getTime();
        String [] fields = {"return_date", "actual_returned_date"};
    	String [] values = {oneDayBefore.toString(), null};
    	
    	ResultSet borrows = db.retrieveRowsWithConditions("borrow", fields, values);
    
    	
    	String subscriber_id;
    	String message = "hello BLib subscriber,\nThis is an automated message reminding you to return book: ";
    	String borrow_barcode="";
    	try {
			while (borrows.next())
			{
				try {
					borrow_barcode = borrows.getString("book_barcode");
					subscriber_id = borrows.getString("subscriber_id");
					Subscriber subscriber = SubscriberController.getInstance().fetchSubscriber(subscriber_id);
					sendEmail(subscriber.getEmail(),"book return reminder from BLib", message + borrow_barcode);
					sendSMS(subscriber.getPNumber(),message + borrow_barcode);
					
				} catch (SQLException e) {
					System.out.println("failed to send reminder to borrower of borrow_num: " + borrows.getString("borrow_number"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
   
    /**
     * Sends an email notification to the subscriber.
     * 
     * @param toEmail the recipient's email address
     * @param subject the email subject
     * @param body the email content
     */
    private void sendEmail(String toEmail, String subject, String body) {
        System.out.println("Sending email to " + toEmail + " with subject: " + subject);
        System.out.println("Message: " + body);
        
        //TODO: implement call to function that implements sending e-mails.
    }
    
    /**
     * Sends an SMS notification to the subscriber.
     * 
     * @param phoneNumber the recipient's phone number
     * @param message the SMS content
     */
    private void sendSMS(String phoneNumber, String message) {
        System.out.println("Sending SMS to " + phoneNumber + " with message: " + message);
        //TODO: implement call to function that implements sending SMS messages.

    }
    
}
