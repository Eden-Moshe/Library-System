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
	private static String keyField="book_id";
	
	public static BookController getInstance() {
		return instance;
	}
	
	private BookController()
	{
		db=DBController.getInstance();
		
	}
	
	
	public Book fetchBook(String pKey)
	{
		
		Book ret=null;
		ResultSet rs = db.retrieveRow(tName, keyField, pKey);
		try {
			rs.next();
			//ADD APROPRIATE FIELDS OF BOOK TABLE
			//ret = new Book (rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to retrieve Book table data");
			e.printStackTrace();
			return null;
		}
	}
	
	
}
