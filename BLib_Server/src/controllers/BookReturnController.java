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

/**
 * The {@code BookReturnController} class handles the logic for processing the return of books.
 * It interacts with the database to update the relevant tables and manage user statuses.
 * 
 * This class implements the Singleton design pattern, ensuring only one instance of the controller is used throughout the application.
 */
public class BookReturnController {
    private static final BookReturnController instance = new BookReturnController();
    private DBController db;

    /**
     * Retrieves the singleton instance of the {@code BookReturnController}.
     * 
     * @return The singleton instance of {@code BookReturnController}.
     */
    public static BookReturnController getInstance() {
        return instance;
    }

    /**
     * Private constructor for the {@code BookReturnController} class.
     * Initializes the DBController instance.
     */
    private BookReturnController() {
        db = DBController.getInstance();
    }

    /**
     * Processes the return of a book by updating the relevant fields in the database.
     * This method updates the return date in the "borrow" table, marks the book as available in the "book" table,
     * and checks whether the return is overdue. If the return is overdue by more than 6 days,
     * it freezes the borrower's account for 30 days.
     * 
     * @param borrowerId The ID of the borrower returning the book.
     * @param bookBarcode The barcode of the book being returned.
     * @param borrowNum The borrow number associated with the book return.
     * @return A string message indicating the result of the book return operation. 
     *         It can indicate a successful return or an account freeze if overdue.
     */
    public String createNewBookReturn(String borrowerId, String bookBarcode, String borrowNum) {
        // Update the actual_returned_date in the borrow table
        db.editRow("borrow", "borrow_number", borrowNum, "actual_returned_date", LocalDate.now().toString());

        // Set the book's availability to true in the book table
        db.editRow("book", "barcode", bookBarcode, "book_available", "true");

        // Retrieve the borrow record to check the return date
        ResultSet rs = db.retrieveRow("borrow", "borrow_number", borrowNum);
        try {
            if (rs.next()) {
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
                    String[] values = {borrowerId, LocalDate.now().toString(), LocalDate.now().plusDays(30).toString(), "true"};
                    db.insertRow("user_status_registry", fields, values);

                    // Freeze the subscriber's account
                    db.editRow("subscriber", "subscriber_id", borrowerId, "subscriber_status", "frozen");

                    return "The book was returned, and account is frozen.";
                }
            } else {
                throw new SQLException(String.format("Error: No Borrow record found with borrow_number %s: ", borrowNum));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Problem with the database.";
        }

        return "The book was returned.";
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
