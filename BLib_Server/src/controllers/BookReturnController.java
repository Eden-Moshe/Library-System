package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import common.Book;
import common.Borrow;
import common.BorrowRecord;
import common.DestRecord;
import server.DBController;
import controllers.OrderController;

/**
 * The {@code BookReturnController} class is responsible for processing the return of books. 
 * It interacts with the database to update relevant tables, such as the book's availability status and the borrow record. 
 * This class also manages user account statuses based on the timeliness of book returns.
 * 
 * <p>This class implements the Singleton design pattern, ensuring that only one instance of the controller is used throughout the application.</p>
 */
public class BookReturnController {
    private static final BookReturnController instance = new BookReturnController();
    private DBController db;
    private OrderController orderController= OrderController.getInstance();
    /**
     * Retrieves the singleton instance of the {@code BookReturnController}.
     * 
     * @return The singleton instance of the {@code BookReturnController}.
     */
    public static BookReturnController getInstance() {
        return instance;
    }

    /**
     * Private constructor for the {@code BookReturnController} class.
     * Initializes the {@code DBController} instance for database interactions.
     */
    private BookReturnController() {
        db = DBController.getInstance();
    }

    /**
     * Processes a book return by updating the borrow record and setting the book's availability to true.
     * 
     * @param bookBarcode The barcode of the returned book.
     * @param borrowNum The borrow number associated with the returned book.
     * @return A message indicating the success of the book return operation.
     */
    public String createNewBookReturn(String bookBarcode) {
    	
    	String fields[] = {"book_barcode","actual_returned_date"};
    	String values[] = {bookBarcode, null};
    	
    	String borrow_number="";
    	
    	ResultSet rs = db.retrieveRowsWithConditions("borrow", fields, values);
    	
    	try {
			if (rs.next())
			{
				borrow_number = rs.getString("borrow_number");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	orderController.notifyUserForReturnedBook(bookBarcode);
    	
    	// Update the actual_returned_date in the borrow table
        db.editRow("borrow", "borrow_number", borrow_number, "actual_returned_date", LocalDate.now().toString());

        // Set the book's availability to true in the book table
        db.editRow("book", "barcode", bookBarcode, "book_available", "true");
        // Set the book's return_date to null in the book table
        db.editRow("book", "barcode", bookBarcode, "return_date", "null");
        
        
    	
        //old
//        // Update the actual_returned_date in the borrow table
//        db.editRow("borrow", "borrow_number", borrowNum, "actual_returned_date", LocalDate.now().toString());
//
//        // Set the book's availability to true in the book table
//        db.editRow("book", "barcode", bookBarcode, "book_available", "true");

        return "The book was returned.";
    }

    /**
     * Checks if any book is overdue and processes the user's account if necessary. 
     * If a book is overdue by more than 6 days, the user's account is frozen for 30 days.
     */
    public void bookIsNotReturnedYet() {
        // Retrieve the borrow record to check the return date
        ResultSet rs = db.retrieveRow("borrow", "actual_returned_date", null);
        try {
            while (rs.next()) {
                Date returnDate = rs.getDate("return_date"); // Get the return date from the borrow record
                LocalDate localDate = LocalDate.now(); // Get today's date
                LocalDateTime localDateTime = localDate.atStartOfDay(); // Convert to LocalDateTime
                Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); // Convert LocalDateTime to Date

                // Calculate the difference in days between the actual return date and the expected return date
                long diffInMillis = currentDate.getTime() - returnDate.getTime();
                long diffInDays = diffInMillis / (1000 * 60 * 60 * 24); // Convert milliseconds to days

                // If the return is overdue by more than 6 days, freeze the account
                if (diffInDays > 6) {
                    // Create a new entry in the user_status_registry table for freezing the account
                    String[] fields = {"user_id", "status_set_date", "status_end_date", "status_is_current"};
                    String[] values = {rs.getNString(4), LocalDate.now().toString(), LocalDate.now().plusDays(30).toString(), "true"};
                    db.insertRow("user_status_registry", fields, values);

                    // Freeze the subscriber's account
                    db.editRow("subscriber", "subscriber_id", rs.getNString(4), "subscriber_status", "frozen");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Removes a book from the database and adds its details to the "destroyed_books" table.
     * 
     * This method deletes a book from the "book" table based on the given barcode, 
     * and then inserts a record into the "destroyed_books" table with the provided 
     * barcode and borrower ID.
     * 
     * @param id the ID of the borrower who destroyed the book
     * @param barcode the barcode of the destroyed book
     * @return a message indicating whether the operation was successful or not
     */
    public String destroyBook(String id, String barcode) {
        
        // Delete the book from the database
        db.deleteRow("book", "barcode", barcode);
        
        // Add the destroyed book details to the "destroyed_books" table
        String[] fields = {"book_barcode", "borrower_id"};
        String[] values = {barcode, id};
        db.insertRow("destroyed_books", fields, values);
        
        return "Book was successfully removed from database";
    }

    /**
     * Fetches all records of destroyed books from the "destroyed_books" table.
     * 
     * This method retrieves a list of records that contains the details (barcode and borrower ID)
     * of all destroyed books stored in the "destroyed_books" table.
     * 
     * @return a List of {@link DestRecord} objects representing the destroyed books
     */
    public List<DestRecord> fetchDest() {
        return db.getDestRecords();
    }

    
}
