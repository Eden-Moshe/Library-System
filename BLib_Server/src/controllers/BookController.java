package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.Book;
import server.DBController;

/**
 * Controller class responsible for managing book-related operations.
 */
public class BookController {
    
    /**
     * Singleton instance of BookController.
     */
    private static final BookController instance = new BookController();
    
    /**
     * Database controller instance for handling database operations.
     */
    private DBController db;
    
    /**
     * Table name for book records in the database.
     */
    private static String tName = "book";
    
    /**
     * Primary key field name in the book table.
     */
    private static String keyField = "barcode";
    
    /**
     * Retrieves the singleton instance of BookController.
     * 
     * @return the singleton instance of BookController
     */
    public static BookController getInstance() {
        return instance;
    }
    
    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the database controller instance.
     */
    private BookController() {
        db = DBController.getInstance();
    }
    
    /**
     * Fetches a book from the database using its primary key.
     * 
     * @param pKey the barcode of the book to retrieve
     * @return the Book instance if found, otherwise null
     */
    public Book fetchBook(String pKey) {
        Book ret = null;
        ResultSet rs = db.retrieveRow(tName, keyField, pKey);
        try {
            if (rs.next()) {
                // Populate the Book details
                ret = new Book(
                    rs.getString("barcode"),           // barcode column
                    rs.getString("book_name"),        // book_name column
                    rs.getString("book_genre"),       // book_genre column
                    rs.getString("book_description"), // book_description column
                    rs.getString("shelf_location"),   // shelf_location column
                    rs.getBoolean("book_available"),  // book_available column (boolean)
                    rs.getDate("return_date")         // return_date column (Date)
                );
                System.out.println(ret);
                return ret;
            } else {
                System.out.println("No Book found with barcode: " + pKey);
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve Book table data");
            e.printStackTrace();
            return null;
        }
    }
}
