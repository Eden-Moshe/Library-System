package controllers;

import java.sql.*;
import server.DBController;
import common.Book;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SearchController {
    private static final SearchController instance = new SearchController();
    private DBController db;
    private static String tName = "book"; // Table name

    // Singleton pattern to return the single instance of SearchController
    public static SearchController getInstance() {
        return instance;
    }

    // Private constructor that initializes the DBController instance
    private SearchController() {
        db = DBController.getInstance();
    }

    /**
     * Perform a search for books based on the given criteria.
     *
     * @param bookName The name of the book to search for.
     * @param bookGenre The genre of the book to search for.
     * @param bookDescription The description of the book to search for.
     * @return A list of books matching the search criteria.
     */
    public List<Book> performSearch(String bookName, String bookGenre, String bookDescription) {
    	
        Connection con = db.getConnection();
        
        // Constructing the SQL query based on which fields are selected
        String query = "SELECT * FROM book WHERE ";
        
        // Creating a list of conditions to check
        ArrayList<String> conditions = new ArrayList<>();
        
        // If bookName is provided, add the condition for book_name
        if (bookName != null && !bookName.isEmpty()) {
            conditions.add("book_name LIKE ?");
        }
        
        // If bookGenre is provided, add the condition for book_genre
        if (bookGenre != null && !bookGenre.isEmpty()) {
            conditions.add("book_genre LIKE ?");
        }
        
        // If bookDescription is provided, add the condition for book_description
        if (bookDescription != null && !bookDescription.isEmpty()) {
            conditions.add("book_description LIKE ?");
        }
        
        // If no conditions are specified, it's an invalid search
        if (conditions.isEmpty()) {
            System.out.println("Please enter at least one search term (book name, genre, or description).");
            return Collections.emptyList();
        }
        
        // Joining the conditions with an OR to make the query
        query += String.join(" OR ", conditions);
        
        // List to store all books that match the criteria
        List<Book> bookList = new ArrayList<>();
        
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            int index = 1;
            
            // Setting the values for each condition
            if (bookName != null && !bookName.isEmpty()) {
                stmt.setString(index++, "%" + bookName + "%");
            }
            if (bookGenre != null && !bookGenre.isEmpty()) {
                stmt.setString(index++, "%" + bookGenre + "%");
            }
            if (bookDescription != null && !bookDescription.isEmpty()) {
                stmt.setString(index++, "%" + bookDescription + "%");
            }
            
            // Executing the query
            ResultSet result = stmt.executeQuery();
            
            
            
            
            
            /// me fucking around trying to convert the function to use DBController. 
//            
//        	List<Book> bookList = new ArrayList<>();
//        	try {
//        	
//        	String []fields = { "book_description"};
//        	String []values = {bookDescription};
//        	//ResultSet result = db.retrieveRowsWithConditions(bookDescription, fields, values)
//        	ResultSet result = db.retrieveRowsWithConditions(tName, fields, values);
//                // Creating Book objects from the results and adding them to the list
//                while (result.next()) {
//                    Book book = new Book(
//                        result.getString("book_name"),          // Book Name
//                        result.getString("book_genre"),         // Book Genre
//                        result.getString("barcode"),            // Barcode
//                        result.getString("shelf_location"),     // Shelf Location
//                        result.getString("book_description"),   // Book Description
//                        result.getBoolean("book_available"),    // Book Availability
//                        result.getDate("return_date")   // Closest Return Date
//                    );
//                    
//                    // Adding the book to the list
//                    bookList.add(book);
//                }
//        	
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            
//            
//            
            
            
            
            
            
            
            // Creating Book objects from the results and adding them to the list
            while (result.next()) {
                Book book = new Book(
                    result.getString("book_name"),          // Book Name
                    result.getString("book_genre"),         // Book Genre
                    result.getString("barcode"),            // Barcode
                    result.getString("shelf_location"),     // Shelf Location
                    result.getString("book_description"),   // Book Description
                    result.getBoolean("book_available"),    // Book Availability
                    result.getDate("return_date")   // Closest Return Date
                );
                
                // Adding the book to the list
                bookList.add(book);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Returning the list of books that match the query
        return bookList;
    }
}
