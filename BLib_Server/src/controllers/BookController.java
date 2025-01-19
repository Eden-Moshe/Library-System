package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.Book;
import common.Subscriber;
import server.DBController;

public class BookController {
	private static final BookController instance = new BookController();
	private DBController db;
	private static String tName="book";
	private static String keyField="barcode";
	
	public static BookController getInstance() {
		return instance;
	}
	
	private BookController()
	{
		db=DBController.getInstance();
		
	}
	
	//the methods used retrieveRow in DBController to fetch row details into Book instance
	public Book fetchBook(String pKey) {
	    Book ret = null;
	    ResultSet rs = db.retrieveRow(tName, keyField, pKey);
	    try {
	        if (rs.next()) {
	            // Populate the Book details
	            ret = new Book (						            
	            		rs.getString("barcode"),               	  // barcode column
			            rs.getString("book_name"),                // book_name column
		                rs.getString("book_genre"),               // book_genre column
		                rs.getString("book_description"),	      // book description column
		                rs.getString("shelf_location"),  		  // place_on_shelf column
		                rs.getBoolean("book_available"),          // book_exists column (boolean)
		                rs.getDate("return_date")                 // return_date column (Date)
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
