package controllers;

import java.util.List;

import common.BorrowRecord;
import common.ReportMessage;
import server.DBController;

/**
 * Controller class responsible for generating reports related to borrowing and subscriber status.
 */
public class ReportController {
    /**
     * Singleton instance of ReportController.
     */
    private static final ReportController instance = new ReportController();
    
    /**
     * Database controller instance for handling database operations.
     */
    private DBController db;
    
    /**
     * Retrieves the singleton instance of ReportController.
     * 
     * @return the singleton instance of ReportController
     */
    public static ReportController getInstance() {
        return instance;
    }
    
    /**
     * Private constructor to enforce singleton pattern.
     * Initializes the database controller instance.
     */
    private ReportController() {
        db = DBController.getInstance();
    }
    
    /**
     * Generates a report of borrow records.
     * 
     * @return a list of BorrowRecord instances representing all borrow transactions
     */
    public List<BorrowRecord> borrowReport() {
        // Fetch the borrow records
        return db.getBorrowRecords();
    }
    
    /**
     * Generates a status report by counting active and frozen subscribers.
     * 
     * @param report the ReportMessage instance to store the counts of active and frozen subscribers
     */
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
