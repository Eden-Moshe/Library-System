package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Book;
import common.Subscriber;
import server.DBController;
import controllers.SubscriberController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class OrderController {
	private static final OrderController instance = new OrderController();
	private DBController db;
	private String bookName;
	private Book selectedBook;
	private String userId;
	// private UserManager userManager;
	public void saveUserId(String userId) {
		this.userId=userId;
	}
	public String getbookName() {
		return this.bookName;
	}
	public static OrderController getInstance() {
		return instance;
	}

	private OrderController() {
		db = DBController.getInstance();

	}
	public String getUserId() {
		return this.userId;
	}

	/**
	 * Check if a book can be ordered by checking existing orders and copies
	 * 
	 * @param bookName The name of the book to check
	 * @return true if the book can be ordered, false otherwise
	 */
	public boolean canOrderBook(String bookName) {
		try {
			this.bookName=bookName;
			// Count available copies of the book
			String[] fields = { "book_name" };
			String[] values = { bookName };
			ResultSet rs = db.retrieveRowsWithConditions("book", fields, values);
			int availableCopies = 0;
			int unavailableCopies = 0;
			if (rs != null && rs.next()) {
				if (rs.getBoolean("book_available")){
					availableCopies++;
				} else {
					unavailableCopies++;
				}
			}
			
			// If the book is available, ordering is not allowed
	        if (availableCopies > 0) {
	            return false;
	        }
	        
			// Count the number of orders that are still in "waiting" status
			String[] orderFields = { "book_name", "order_status" };
			String[] orderValues = { bookName, "waiting" };
			ResultSet rsOrders = db.retrieveRowsWithConditions("order_book", orderFields, orderValues);
			int currentOrders = 0;
			while (rsOrders != null && rsOrders.next()) {
				currentOrders++;
			}
			
			System.out.println("can be orderd:"+ (unavailableCopies > currentOrders)); 
			// Return if the number of active orders is less than the available copies
			return unavailableCopies > currentOrders;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Place a new order for a book
	 * 
	 * @param bookName     The name of the book
	 * @param subscriberId The ID of the subscriber
	 * @return true if order was placed successfully, false otherwise
	 */
	public boolean placeBookOrder(String bookName, int subscriberId) {
		try {
			if(!isActive(subscriberId)) {
				return false; 
			}
			// Get nearest return date for unavailable books
			String[] fields = { "book_name", "book_available" };
			String[] values = { bookName, "false" };
			ResultSet rs = db.retrieveRowsWithConditions("book", fields, values);

			Date nearestReturn = null;
			if (rs != null && rs.next()) {
				//nearestReturn = rs.getDate("return_date");
				nearestReturn=getNearestReturnDate(bookName);
			}

			// Insert the order
			String[] orderFields = { "book_name", "nearest_book_return", "subscriber_id", "order_status" };
			String[] orderValues = { bookName, nearestReturn != null ? nearestReturn.toString() : null,
					String.valueOf(subscriberId), "waiting" };

			db.insertRow("order_book", orderFields, orderValues);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Get total number of copies available minus waiting orders
	 */
	public int getTotalCopies(String bookName) {
		int availableCopies = 0;
		int waitingOrders = 0;

		try {
			// Get available copies
			String[] fields = { "book_name", "book_available" };
			String[] values = { bookName, "1" };
			ResultSet rsBooks = db.retrieveRowsWithConditions("book", fields, values);

			while (rsBooks != null && rsBooks.next()) {
				availableCopies++;
			}

			// Get waiting orders
			String[] orderFields = { "book_name", "order_status" };
			String[] orderValues = { bookName, "waiting" };
			ResultSet rsOrders = db.retrieveRowsWithConditions("order_book", orderFields, orderValues);

			while (rsOrders != null && rsOrders.next()) {
				waitingOrders++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return availableCopies - waitingOrders;
	}

	/**
	 * Add a new order with specific return date
	 */
	public boolean addOrder(String bookName, int subscriberId, Date returnDate) {
		String[] fields = { "book_name", "subscriber_id", "order_status", "nearest_book_return" };
		String[] values = { bookName, String.valueOf(subscriberId), "waiting",
				returnDate != null ? returnDate.toString() : null };

		db.insertRow("order_book", fields, values);
		return true;
	}

	/**
	 * Get current number of waiting orders for a book
	 */
	public int getCurrentOrders(String bookName) {
		int waitingOrders = 0;

		try {
			String[] orderFields = { "book_name", "order_status" };
			String[] orderValues = { bookName, "waiting" };
			ResultSet rsOrders = db.retrieveRowsWithConditions("order_book", orderFields, orderValues);

			while (rsOrders != null && rsOrders.next()) {
				waitingOrders++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return waitingOrders;
	}

	/**
	 * Get the nearest return date for a book
	 */
	public Date getNearestReturnDate(String bookName) {
		List<Book> books = performSearchByBookName(bookName);

		if (books.isEmpty()) {
			System.out.println("No books found with the name: " + bookName);
			return null;
		}

		Date nearestReturnDate = null;

		for (Book book : books) {
			if (!book.isBookAvailable()) {
				Date returnDate = book.getReturnDate();
				if (returnDate != null && (nearestReturnDate == null || returnDate.before(nearestReturnDate))) {
					nearestReturnDate = returnDate;
				}
			}
		}

		System.out.println("Nearest return date found: " + nearestReturnDate);
		return nearestReturnDate;
	}
	
	/**
     * Perform a search for books based on the BookName.
     *
     * @param bookName The name of the book to search for.
     * @return A list of books matching the search criteria.
     */
public List<Book> performSearchByBookName(String bookName) {
    // Setting the fields and values for the search query
    String[] fields = {"book_name"};
    String[] values = {bookName};

    // Get the result set using retrieveRowsWithConditions
    ResultSet resultSet = db.retrieveRowsWithConditions("book", fields, values);

    // List to store the books found
    List<Book> bookList = new ArrayList<>();

    try {
        while (resultSet != null && resultSet.next()) {
            // Make sure we get each field from its correct column
            String barcode = resultSet.getString("barcode");
            String name = resultSet.getString("book_name");
            String genre = resultSet.getString("book_genre");
            String description = resultSet.getString("book_description");
            String location = resultSet.getString("shelf_location");
            boolean available = resultSet.getBoolean("book_available");
            Date returnDate = resultSet.getDate("return_date");
            // Print values for debugging
            System.out.println("Retrieved from DB - Return Date: " + returnDate);
            
            Book book = new Book(
                barcode,        // Barcode first
                name,          // Book name second
                genre,         // Genre third
                description,   // Description fourth
                location,      // Shelf location fifth
                available,     // Availability sixth
                returnDate    // Return date last
            );
            bookList.add(book);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Debug print the results
    for (Book book : bookList) {
        System.out.println("Book in list - Return Date: " + book.getReturnDate());
    }

    return bookList;
}
// Check if the subscriber is active
public boolean isActive(int subscriberId) {
    try {
    	ResultSet rsSubscriber = db.retrieveRow("subscriber", "subscriber_id", String.valueOf(subscriberId));
    	 // First check if we have any results
        if (rsSubscriber == null) {
            System.out.println("No subscriber found with ID: " + subscriberId);
            return false;
        }
     // Move to first row and check if we have data
        if (rsSubscriber.next()) {
            String status = rsSubscriber.getString("subscriber_status");
            if (!"active".equalsIgnoreCase(status)) {
                System.out.println("Subscriber is not active. Status: " + status);
                return false;
            }
            return true;
        } else {
            System.out.println("No subscriber found with ID: " + subscriberId);
            return false;
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("SQL Exception in isActive: " + e.getMessage());
        return false;
    }
}
}
	
