package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import common.Book;
import common.Borrow;
import common.Subscriber;
import server.DBController;

public class BorrowController {
	private static final BorrowController instance = new BorrowController();
	private DBController db;
	private static String tName="borrow";
	private static String keyField="book_barcode";
	
	public static BorrowController getInstance() {
		return instance;
	}
	
	private BorrowController()
	{
		db=DBController.getInstance();
		
	}
	
	
	public String createBorrow(Subscriber s, Book b, Borrow borrow) {
	    // Checking if account is active
	    if (canBorrow(s)) {
	        // Defining fields and values for the insert
	        String[] fields = {"book_barcode", "lending_librarian", "subscriber_id",
	        		           "borrow_date", "return_date", "actual_returned_date"};
	        
	        // Convert the borrow date and return date to appropriate format
	        String[] values = {
	            b.getBookBarcode(),
	            //need to implement librarian id
	            //lib.getName();
	            "1",
	            s.getSID(),
	            new java.sql.Date(borrow.getBorrowDate().getTime()).toString(),
	            new java.sql.Date(borrow.getReturnDate().getTime()).toString(),
	            null,
	        };

	        // Call insertRow method with the table, fields, and values
	        db.insertRow("borrow", fields, values);

	        return "Borrow request sent successfully."; 
	    } else {
	        return "Account status is frozen. Cannot create borrow.";

	    }
	}
	
    
    // Method to check if subscriber status is active
    public boolean canBorrow(Subscriber s) {
        return "active".equalsIgnoreCase(s.getStatus());
    }
    
    // method checks if book exists in library
	public boolean bookExists(Book b) {
	    String key = b.getBookBarcode(); // The barcode value to query

	    // Retrieve the row corresponding to the book
	    ResultSet rs = db.retrieveRow("book", "barcode", key);

	    try {
	        if (rs != null && rs.next()) { // Check if a result was returned
	            boolean bookAvailable = rs.getBoolean("book_available"); // Fetch the book_available field

	            // If book_available is false, return false
	            if (!bookAvailable) {
	                return false;
	            }

	            // If book_available is true, return true
	            return true;
	        } else {
	            // No row found for the given barcode
	            System.out.println("No book found with the given barcode: " + key);
	            return false;
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL error while checking book availability: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
    
    
    
    // Method to send return reminder to the subscriber
    public void sendReturnReminder(Borrow borrow, Subscriber subscriber) {
        // Step 1: Calculate the reminder date (one day before the return date)
        Date returnDate = borrow.getReturnDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(returnDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1); // Subtract one day from return date
        Date reminderDate = calendar.getTime();

        // Step 2: Check if the current date matches the reminder date
        Date currentDate = new Date(); // Get the current date

        if (isSameDay(currentDate, reminderDate)) {
            // Step 3: Send reminder to the subscriber via email, SMS, and system message
            String message = "Reminder: The book you borrowed is due for return tomorrow. Please return it by " + borrow.getReturnDate().toString() + ".";

            // Send Email
            sendEmail(subscriber.getEmail(), "Book Return Reminder", message);

            // Send SMS (You would integrate an SMS API here)
            sendSMS(subscriber.getPNumber(), message);

            // Send System Message (This could be a notification or logging the message)
            sendSystemMessage(subscriber.getSID(), message);
        }
    }

    // Helper method to compare two dates (ignores time part)
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    // Method to send an email (you will need an actual email service, e.g., JavaMail API)
    private void sendEmail(String toEmail, String subject, String body) {
        // Code to send email (using an email API/service like JavaMail)
        System.out.println("Sending email to " + toEmail + " with subject: " + subject);
        System.out.println("Message: " + body);
    }

    // Method to send an SMS (you would need an SMS API here, like Twilio)
    private void sendSMS(String phoneNumber, String message) {
        // Code to send SMS (using an SMS API like Twilio)
        System.out.println("Sending SMS to " + phoneNumber + " with message: " + message);
    }

    // Method to send a system message (This could be a log or a UI notification)
    private void sendSystemMessage(String subscriberId, String message) {
        // Code to send system message (logging, or showing a notification on the UI)
        System.out.println("System message for subscriber " + subscriberId + ": " + message);
    }
    
	

	
}
