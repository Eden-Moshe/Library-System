package controllers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import common.Book;
import common.Borrow;
import common.Subscriber;
import server.DBController;

/**
 * Controller class for handling book borrowing and extension requests.
 */
public class RequestController {
    private static final RequestController instance = new RequestController();
    private DBController db;
    private static String tName = "borrow";
    private static String keyField = "book_barcode";
    private SubscriberController subscriberController;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private RequestController() {
        db = DBController.getInstance();
        subscriberController = SubscriberController.getInstance(); // Initialize subscriberController
    }

    /**
     * Retrieves the singleton instance of RequestController.
     * @return The singleton instance.
     */
    public static RequestController getInstance() {
        return instance;
    }

    /**
     * Fetches a Borrow record from the database.
     * @param sub_id The subscriber ID.
     * @param barcode The book barcode.
     * @param errorMessage A StringBuilder to capture error messages.
     * @return The Borrow object if found, otherwise null.
     */
    public Borrow fetchBorrow(String sub_id, String barcode, StringBuilder errorMessage) {
        if (sub_id == null || barcode == null) {
            System.out.println("Invalid input: sub_id or barcode is null");
            return null;
        }
        
        // Fetch subscriber
        Subscriber s = subscriberController.fetchSubscriber(sub_id);
        if (s == null) {
            errorMessage.append("Error: Subscriber not found for ID: " + sub_id);
            return null;
        }
        
        Borrow ret = null;
        ResultSet rs = db.retrieveRow(tName, keyField, barcode);
        try {
            if (rs.next()) {
                Date borrowDate = rs.getDate("borrow_date");
                Date returnDate = rs.getDate("return_date");
                
                // Create and return Borrow object
                ret = new Borrow(s, borrowDate, returnDate);
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

    /**
     * Checks if a borrow is eligible for an extension (must be within 7 days of return date).
     * @param currentReturnDate The current return date.
     * @return True if eligible for extension, false otherwise.
     */
    public boolean isEligibleForExtension(Date currentReturnDate) {
        Date today = new Date();
        long timeDiff = Math.abs(currentReturnDate.getTime() - today.getTime());
        long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
        return daysDiff <= 7;
    }

    /**
     * Extends the borrow return date if eligible.
     *
     * This method performs the following:
     * - Checks if the borrow and extended date are valid.
     * - Verifies that the book is not currently ordered by another subscriber.
     * - Ensures the new return date is later than the current return date.
     * - Updates the database with the new return date.
     * - Logs the extension request in the 'extensions' table.
     * - Sends a message to the librarian regarding the extension.
     *
     * @param borrow The Borrow object.
     * @param book The Book object.
     * @param extendedDate The new extended return date.
     * @return A success or error message.
     */
    public String extendBorrow(Borrow borrow, Book book, Date extendedDate) {
        String librarian_id = "-1";
        int borrow_num_pk = -1;
        if (borrow == null) {
            return "Borrow object is null. Cannot extend borrow.";
        }

        if (extendedDate == null) {
            return "Extended date is null. Cannot extend borrow.";
        }
        
        // Check if the book has an existing order in the 'order_book' table
        ResultSet rs = db.retrieveRow("order_book", "book_name", book.getBookName());
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

        // Validate that the new return date is after the original return date
        Date currentReturnDate = borrow.getReturnDate();
        if (extendedDate.before(currentReturnDate)) {
            return "Error: The return date can only be extended, not shortened. " +
                   "Current return date: " + currentReturnDate + ", " +
                   "Attempted new return date: " + extendedDate;
        }
        
        String bookBarcode = book.getBookBarcode();
        
        // Update return date in the borrow table
        try {
            java.sql.Date sqlDate = new java.sql.Date(extendedDate.getTime());
            db.editRow("borrow", "book_barcode", bookBarcode, "return_date", sqlDate.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Failed to edit return_date";
        }
        
        // Retrieve librarian ID from borrow table
        ResultSet rs1 = db.retrieveRow(tName, keyField, book.getBookBarcode());
        try {
            if (rs1.next()) {
                librarian_id = rs1.getString("lending_librarian");
                borrow_num_pk = rs1.getInt("borrow_number");
                if (librarian_id == null || librarian_id.isEmpty() || borrow_num_pk == -1) {
                    return "Error: Lending librarian ID or borrow_number is missing in the borrow record.";
                }
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: Failed to retrieve librarian ID from borrow table.";
        }
        
        // Log the extension in the 'extensions' table
        try {
            String fields[] = {"lending_librarian", "borrow_number", "day_of_extension", "new_return_date"};
            String values[] = {librarian_id, String.valueOf(borrow_num_pk),
                    new java.sql.Date(System.currentTimeMillis()).toString(),
                    new java.sql.Date(extendedDate.getTime()).toString()};
            db.insertRow("extensions", fields, values);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Failed to log the extension.";
        }
        
        borrow.setReturnDate(extendedDate);
        return "Request approved, date of return was updated accordingly.";
    }
}
