package controllers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import common.Book;
import common.Borrow;
import common.Subscriber;
import server.DBController;

public class RequestController {
	private static final RequestController instance = new RequestController();
	private DBController db;
	private static String tName="borrow";
	private static String keyField="book_barcode";
	private SubscriberController subscriberController;
	
	private RequestController()
	{
		db=DBController.getInstance();
		
	}
	
	public static RequestController getInstance() {
		return instance;
	}



    // fetch Borrow from table 
    public Borrow fetchBorrow(String sub_id, String barcode) {
        Subscriber s = subscriberController.fetchSubscriber(sub_id);
    	Borrow ret = null;
        //puts into rs both dates
        ResultSet rs = db.retrieveRow(tName, keyField, barcode); // Query the 'borrow' table
        try {
            if (rs.next()) {

                Date borrowDate = rs.getDate("borrow_date"); // borrow_date column
                Date returnDate = rs.getDate("return_date"); // return_date column

                // Create a Borrow object using the constructor and fetched subscriber and dates
                ret = new Borrow(s, borrowDate,returnDate);

                return ret;
            } else {
                System.out.println("No Borrow record found with book barcode: " + barcode);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve Borrow table data");
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to check if the extension request is within the allowed timeframe
    public boolean isEligibleForExtension(Date currentReturnDate) {
        Date today = new Date(); // Get today's date
        long timeDiff = Math.abs(currentReturnDate.getTime() - today.getTime());
        long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
        return daysDiff <= 7; // True if within 7 days, false otherwise
    }

    //method that extends borrow return date
    public void extendBorrow(Borrow borrow, Book book, Date extendedDate) {
        if (borrow == null) {
            System.out.println("Borrow object is null. Cannot extend borrow.");
            return;
        }

        if (extendedDate == null) {
            System.out.println("Extended date is null. Cannot extend borrow.");
            return;
        }

        Date currentReturnDate = borrow.getReturnDate();
        if (extendedDate.before(currentReturnDate)) {
            System.out.println("Extended date cannot be before the current return date.");
            return;
        }

        // Update the Borrow object's return date
        borrow.setReturnDate(extendedDate);

        // Update the return_date column in the database
        String bookBarcode = book.getBookBarcode();
        try {
            db.editRow("book_barcode", bookBarcode, "return_date", new java.sql.Date(extendedDate.getTime()).toString());
            System.out.println("Return date updated successfully for book barcode: " + bookBarcode);
        } catch (Exception e) {
            System.out.println("Failed to update return date in the database.");
            e.printStackTrace();
        }
    }

    // Method to send a notification to the librarian about the extension
    private void sendLibrarianNotification(Borrow b, Subscriber s) {
        String message = "A borrow extension has been granted for the book '" + b.bo.getBookName() + 
                         "' by subscriber " + s.getName() + ".";
        // Send notification (you would implement this logic based on your system's notification service)
        System.out.println("Librarian Notification: " + message);
    }
}