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
        // Create the conditions for the query based on user input
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        // Add conditions for each field
        if (bookName != null && !bookName.isEmpty()) {
            fields.add("book_name");
            values.add(bookName);
        }
        if (bookGenre != null && !bookGenre.isEmpty()) {
            fields.add("book_genre");
            values.add(bookGenre);
        }
        if (bookDescription != null && !bookDescription.isEmpty()) {
            fields.add("book_description");
            values.add(bookDescription);
        }

        // If no conditions are specified, return an empty list
        if (fields.isEmpty()) {
            return Collections.emptyList();
        }

        // Get the DBController instance
        DBController dbController = DBController.getInstance();

        // Perform the query with the provided fields and values
        ResultSet resultSet = dbController.retrieveRowsWithConditions("book", 
                                                                    fields.toArray(new String[0]), 
                                                                    values.toArray(new String[0]));
        List<Book> bookList = new ArrayList<>();

        try {
            while (resultSet != null && resultSet.next()) {
                // Create a Book object for each result
                Book book = new Book(
                    resultSet.getString("book_name"),
                    resultSet.getString("book_genre"),
                    resultSet.getString("barcode"),
                    resultSet.getString("shelf_location"),
                    resultSet.getString("book_description"),
                    resultSet.getBoolean("book_available"),
                    resultSet.getDate("return_date")
                );
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of books
        return bookList;
    }


}