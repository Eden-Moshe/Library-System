package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.Book;
import common.Borrow;
import common.Subscriber;
import server.DBController;

public class BorrowController {
	private static final BorrowController instance = new BorrowController();
	private DBController db;
	private static String tName="borrow";
	private static String keyField="borrower_id";
	
	public static BorrowController getInstance() {
		return instance;
	}
	
	private BorrowController()
	{
		db=DBController.getInstance();
		
	}
	
	
    public boolean createBorrow(Subscriber s, Book b, Borrow borrow) {
        if (checkAccount(s)) {
            String sql = "INSERT INTO borrow (borrower_id, borrower_name, borrow_date, return_date, " +
                         "borrower_status, borrower_phone_number, borrower_email, book_name) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmt = db.getConnection().prepareStatement(sql)) {
                // Bind parameters
                stmt.setString(1, s.getId());
                stmt.setString(2, s.getName());
                stmt.setDate(3, java.sql.Date.valueOf(borrow.getBorrowDate()));
                stmt.setDate(4, java.sql.Date.valueOf(borrow.getReturnDate()));
                stmt.setString(5, s.getStatus());
                stmt.setString(6, s.getPhoneNumber());
                stmt.setString(7, s.getEmail());
                stmt.setString(8, b.getName());

                // Execute query and check success
                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0; // Return true if at least one row was inserted
            } catch (SQLException e) {
                e.printStackTrace(); // Log the exception for debugging
                return false; // Return false if there was an error
            }
        } else {
            System.out.println("Account status is frozen. Cannot create borrow record.");
            return false; // Return false if the account status is frozen
        }
    }
	
	
	private boolean checkAccount(Subscriber s) {
		//add status to Subscriber class
		//return s.getStatus()
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//    public boolean isEligible() {
//    	
//        return "eligible".equals(this.borrowerStatus);
//    }
//
//    // Check if the borrower's loan is overdue
//    public boolean isOverdue() {
//        Date today = new Date();
//        return today.after(this.returnDate);
//    }
	

	
}
