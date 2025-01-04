package controllers;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

import common.Borrow;
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
	
	
	public boolean createBorrow() {
		
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
