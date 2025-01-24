package controllers;

import java.util.List;

import common.BorrowRecord;
import common.ReportMessage;
import server.DBController;

public class ReportController {
	private static final ReportController instance = new ReportController();
	private DBController db;
	
	
	public static ReportController getInstance() {
		return instance;
	}
	
	private ReportController()
	{
		db=DBController.getInstance();
		
	}
	
	public List<BorrowRecord> borrowReport() {
	    // Fetch the borrow records
	    List<BorrowRecord> borrowRecords = db.getBorrowRecords();
	    return borrowRecords; 
	}
	
	public void statusReport(ReportMessage report) {

        // Define the fields to count based on the subscriber_status
        String[] fields = {"subscriber_status"};

        // Count for "Active"
        String[] valuesActive = {"active"};
        int activeCount = db.countRows("subscriber", fields, valuesActive);
        report.setActiveCount(activeCount);

        // Count for "Frozen"
        String[] valuesFrozen = {"frozen"};
        int frozenCount = db.countRows("subscriber", fields, valuesFrozen);
        report.setFrozenCount(frozenCount); 
        
	}
	
	
}
