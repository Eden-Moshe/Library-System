import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import common.Book;
import common.Borrow;
import common.Subscriber;
import server.DBController;

public class RequestController {
	private static final RequestController instance = new RequestController();
	private DBController db;
	
	private BorrowController borrowController;
	//can be fully implemented when ReservationController is made
	private ReservationController reservationController; // Assumes this controller exists
	
	private RequestController()
	{
		db=DBController.getInstance();
		
	}
	
	public static RequestController getInstance() {
		return instance;
	}

    // Constructor to initialize dependencies (like BorrowController and ReservationController)
    public RequestController(BorrowController borrowController, ReservationController reservationController) {
        this.borrowController = borrowController;
        this.reservationController = reservationController;
    }

    public String requestExtension(Borrow borrow, Book b) {
        Date returnDate = borrow.getReturnDate();
        Date currentDate = new Date();

        // Check if the extension request is being made one week or less before the return date
        if (isEligibleForExtension(returnDate, currentDate)) {
            // Check if a reservation exists for the book in the database
            if (isBookReservedInDatabase(b.getBookName())) {
                return "Extension request denied. The book is reserved.";
            } else {
                // Approve the extension, and update the borrow's return date
                extendBorrow(borrow);
                // Send notification to the librarian
                sendLibrarianNotification(borrow);
                return "Extension granted. The book is due for return on " + borrow.getReturnDate().toString() + ".";
            }
        } else {
            return "Extension request denied. The request is only valid one week before the return date.";
        }
    }

    // Helper method to check if the book is reserved (exists in the borrow table)
    private boolean isBookReservedInDatabase(String bookName) {
        String sql = "SELECT COUNT(*) FROM borrow WHERE book_name = ? AND return_date > CURRENT_DATE";
        
        try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
            stmt.setString(1, bookName);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // If count > 0, the book is currently reserved
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // No active borrow record, the book is not reserved
    }

    // Helper method to check if the extension request is within the allowed timeframe
    private boolean isEligibleForExtension(Date returnDate, Date currentDate) {
        long timeDiff = returnDate.getTime() - currentDate.getTime();
        long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
        return daysDiff <= 7; // Less than or equal to 7 days before the return date
    }

    // Helper method to extend the borrow period by some predefined days (e.g., 7 days)
    private void extendBorrow(Borrow borrow) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrow.getReturnDate());
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Add 7 days to the return date
        borrow.setReturnDate(calendar.getTime());
        
        // Update the borrow record in the database (you would implement this logic in your database layer)
        borrowController.updateBorrowReturnDate(borrow);
    }

    // Method to send a notification to the librarian about the extension
    private void sendLibrarianNotification(Book b, Subscriber s) {
        String message = "A borrow extension has been granted for the book '" + b.getBookName() + 
                         "' by subscriber " + s.getName() + ".";
        // Send notification (you would implement this logic based on your system's notification service)
        System.out.println("Librarian Notification: " + message);
    }
}