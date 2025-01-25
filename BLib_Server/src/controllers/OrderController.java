package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import common.Book;
import server.DBController;


public class OrderController {
	private static final OrderController instance = new OrderController();
	private DBController db;
	private String bookName;
	private String userId;
	
	/**
     * Save the user ID
     * 
     * @param userId The ID of the user to save
     */
	public void saveUserId(String userId) {
		this.userId = userId;
	}

	/**
     * Get the name of the currently selected book
     * 
     * @return The name of the book
     */
	public String getbookName() {
		return this.bookName;
	}
	
	/**
     * Get the singleton instance of OrderController
     * 
     * @return The instance of OrderController
     */
	public static OrderController getInstance() {
		return instance;
	}
	
	/**
     * Constructor for OrderController
     * Initializes the database controller instance
     */
	private OrderController() {
		db = DBController.getInstance();

	}
	
	/**
     * Get the currently saved user ID
     * 
     * @return The user ID
     */
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
			this.bookName = bookName;
			// Count available copies of the book
			String[] fields = { "book_name" };
			String[] values = { bookName };
			ResultSet rs = db.retrieveRowsWithConditions("book", fields, values);
			int availableCopies = 0;
			int unavailableCopies = 0;

			// Iterate through the ResultSet to count available and unavailable copies
			while (rs != null && rs.next()) {
				if (rs.getBoolean("book_available")) {
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

			System.out.println("can be orderd:" + (unavailableCopies > currentOrders));
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
			if (!isActive(subscriberId)) {
				return false;
			}
			// Get nearest return date for unavailable books
			String[] fields = { "book_name", "book_available" };
			String[] values = { bookName, "false" };
			ResultSet rs = db.retrieveRowsWithConditions("book", fields, values);

			Date nearestReturn = null;
			if (rs != null && rs.next()) {
				// nearestReturn = rs.getDate("return_date");
				nearestReturn = getNearestReturnDate(bookName);
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
     * Get the total number of copies available minus waiting orders
     * 
     * @param bookName The name of the book
     * @return The total number of copies available for order
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
     * Add a new order with a specific return date
     * 
     * @param bookName     The name of the book
     * @param subscriberId The ID of the subscriber
     * @param returnDate   The return date of the book
     * @return true if the order was added successfully
     */
	public boolean addOrder(String bookName, int subscriberId, Date returnDate) {
		String[] fields = { "book_name", "subscriber_id", "order_status", "nearest_book_return" };
		String[] values = { bookName, String.valueOf(subscriberId), "waiting",
				returnDate != null ? returnDate.toString() : null };

		db.insertRow("order_book", fields, values);
		return true;
	}

	/**
     * Get the current number of waiting orders for a book
     * 
     * @param bookName The name of the book
     * @return The number of waiting orders
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
     * 
     * @param bookName The name of the book
     * @return The nearest return date, or null if no date is found
     */
	public Date getNearestReturnDate(String bookName) {
	    List<Book> books = performSearchByBookName(bookName);

	    if (books.isEmpty()) {
	        System.out.println("No books found with the name: " + bookName);
	        return null;
	    }

	    Date nearestReturnDate = null;

	    // First, get all unavailable books with return dates
	    List<Date> possibleReturnDates = books.stream()
	        .filter(book -> !book.isBookAvailable() && book.getReturnDate() != null)
	        .map(Book::getReturnDate)
	        .sorted()
	        .collect(Collectors.toList());

	    // Now check each date to see if it has any waiting orders
	    for (Date returnDate : possibleReturnDates) {
	        // Direct SQL query to check for waiting orders on this specific date
	        try {
	            ResultSet rsOrders = db.retrieveRow("order_book", "nearest_book_return", returnDate.toString());
	            
	            // If no orders found, or orders found are not for this specific book and status
	            boolean dateAvailable = true;
	            while (rsOrders != null && rsOrders.next()) {
	                if (rsOrders.getString("book_name").equals(bookName) && 
	                    rsOrders.getString("order_status").equals("waiting")) {
	                    dateAvailable = false;
	                    break;
	                }
	            }

	            if (dateAvailable) {
	                nearestReturnDate = returnDate;
	                break;  // Take the first (earliest) available date
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
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
		String[] fields = { "book_name" };
		String[] values = { bookName };

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

				Book book = new Book(barcode, // Barcode first
						name, // Book name second
						genre, // Genre third
						description, // Description fourth
						location, // Shelf location fifth
						available, // Availability sixth
						returnDate // Return date last
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
	
	/**
     * Check if a subscriber is active (not frozen)
     * 
     * @param subscriberId The ID of the subscriber
     * @return true if the subscriber is active, false otherwise
     */
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
