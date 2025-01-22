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
		subscriberController = SubscriberController.getInstance(); // Initialize subscriberController
	}
	
	public static RequestController getInstance() {
		return instance;
	}



    // fetch Borrow from table 
    public Borrow fetchBorrow(String sub_id, String barcode, StringBuilder errorMessage) {
        if (sub_id == null || barcode == null) {
            System.out.println("Invalid input: sub_id or barcode is null");
            return null;
        }
        //fetch subscriber into s
        Subscriber s = subscriberController.fetchSubscriber(sub_id);
        if (s == null) {
            errorMessage.append("Error: Subscriber not found for ID: " + sub_id);
            return null;
        }
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
                errorMessage.append("Error: No Borrow record found with book barcode: " + barcode);
                return null;
            }
        } catch (SQLException e) {
            errorMessage.append("Error: Failed to retrieve Borrow table data.");
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
    public String extendBorrow(Borrow borrow, Book book, Date extendedDate) {
    	String librarian_id = "-1";
    	int borrow_num_pk = -1;
        if (borrow == null) {
            return("Borrow object is null. Cannot extend borrow.");
            
        }

        if (extendedDate == null) {
            return("Extended date is null. Cannot extend borrow.");
        }
        //check order_book table and see if book already has an existing order
        //if so deny request
        //else continue to set up the request

        ResultSet rs = db.retrieveRow("order_book", "book_name",book.getBookName());
        try {
            if (rs != null && rs.next()) {
                String order_status = rs.getString("order_status");
                if ("waiting".equals(order_status)) {
                    return "Book already has an existing order, request of extension denied.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: Failed to retrieve Borrow table data.";
        }

        Date currentReturnDate = borrow.getReturnDate();
        // check that new return date occurs after original return date
        if (extendedDate.before(currentReturnDate)) {
        	return ("Error: The return date can only be extended, not shortened. " +
                    "Current return date: " + currentReturnDate + ", " +
                    "Attempted new return date: " + extendedDate);
        }
        
        String bookBarcode = book.getBookBarcode();
        
        //edit specific row in borrow table with new return date
        try {
            java.sql.Date sqlDate = new java.sql.Date(extendedDate.getTime());
            db.editRow("borrow","book_barcode", bookBarcode, "return_date", sqlDate.toString());
        } catch (Exception e) {
            System.out.println("Failed to update return date in the database.");
            e.printStackTrace();
            return "Error: Failed to edit return_date";
        }
        
        //retrieve id of librarian from book table
        ResultSet rs1 = db.retrieveRow(tName, keyField, book.getBookBarcode()); // Query the 'borrow' table
        try {
            if (rs1.next()) {
                librarian_id = rs1.getString("lending_librarian"); // getting lending librarian id from borrow table
                borrow_num_pk = rs1.getInt("borrow_number");
                if (librarian_id == null || librarian_id.isEmpty() || borrow_num_pk == -1) {
                    return "Error: Lending librarian ID or borrow_number is missing in the borrow record.";
                }
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: Failed to retrieve librarian id from borrow table.";
        }
        //insert new row to extension table
        try {
        	String fields[] = {"lending_librarian", "borrow_number", "day_of_extension", "new_return_date"};
        	String values[] = {librarian_id, String.valueOf(borrow_num_pk),
                    new java.sql.Date(System.currentTimeMillis()).toString(),
                    new java.sql.Date(extendedDate.getTime()).toString()
        	};

            // Insert into the extensions table
            db.insertRow("extensions",fields, values);
        } catch (Exception e) {
            System.out.println("Failed to insert into the extensions table.");
            e.printStackTrace();
            return "Error: Failed to log the extension in the extensions table for book barcode: " + bookBarcode;
        }
        
        String msg = String.format("User ID: %s extended return date of book %s from %s to %s",
                borrow.s.getSID(), book.getBookBarcode(), borrow.getReturnDate().toString(), extendedDate.toString());
        String fields[] = {"librarian_id", "sender", "message"};
    	String values[] = {librarian_id, "server", msg};
    	
    	db.insertRow("librarian_message", fields, values);
        
	    //implement message sent to librarian inbox in this format:
        
        //UM.librarian.send(msg);
        
        // Update the Borrow object's return date
        borrow.setReturnDate(extendedDate);
        return ("Request approved, date of return was updated accordingly.");
    }

    // Method to send a notification to the librarian about the extension
    private void sendLibrarianNotification(Borrow b, Subscriber s) {
        String message = "A borrow extension has been granted for the book '" + b.bo.getBookName() + 
                         "' by subscriber " + s.getName() + ".";
        // Send notification (you would implement this logic based on your system's notification service)
        System.out.println("Librarian Notification: " + message);
    }
}