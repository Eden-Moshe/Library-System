package server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executors;

import common.BorrowRecord;
import common.DestRecord;















/**
 * A singleton database controller class that manages database connections and operations
 * for a library management system. This class handles all database interactions including
 * connection management, and specialized queries for book borrowing and
 * destruction records.
 * 
 * The controller maintains type-specific field sets for integers, dates, and booleans
 * to ensure proper data type handling during database operations.
 */

public class DBController {
    /** Singleton instance of the DBController. */
	private static final DBController instance = new DBController();
    /** Database connection object. */
	private Connection con;
    /** Set of field names that contain integer values in the database. */
	private static Set<String> intFields;
    /** Set of field names that contain date values in the database. */
	private static Set<String> dateFields;
    /** Set of field names that contain boolean values in the database. */
	private static Set<String> boolFields;
	
	/**
     * Returns the singleton instance of DBController.
     *
     * @return The singleton instance of DBController
     */
	public static DBController getInstance () {
		return instance;
	}
	
	/**
     * Private constructor to prevent instantiation.
     * Initializes field sets and establishes database connection.
     */
	private DBController() {
		intFields=new HashSet<>(Arrays.asList());
		dateFields=new HashSet<>(Arrays.asList());
		boolFields=new HashSet<>(Arrays.asList());
		connectToDB();
		initializeFields();
	}
	
	/**
     * Gets the current database connection.
     *
     * @return The current Connection object
     */
	public Connection getConnection() {
	    return con;
	}

	
	/**
     * Establishes a connection to the MySQL database.
     *
     * @return true if connection is successful, false otherwise
     */
	private boolean connectToDB() {
		System.out.println("connec to db");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print("the class Driver is not found");
			return false ;
		}
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/blib?serverTimezone=Asia/Jerusalem","root","Aa123456");
		} catch (SQLException e) {
			System.out.print("cannot connect to the DB : " + e.getMessage());
			return false;
		}
		System.out.println("finish connec to db");

		return true;
	}
	
	/**
     * Initializes the field type sets with appropriate column names from various tables.
     * This method sets up which fields should be treated as integers, dates, or booleans.
     */
	private void initializeFields() {

	    // Table: users
	    intFields.add("user_id");


	    // Table: librarian
	    intFields.add("librarian_id");

	    // Table: subscriber
	    intFields.add("subscriber_id");


	    // Table: user_status_registry
	    dateFields.add("status_set_date");
	    dateFields.add("status_end_date");
	    boolFields.add("status_is_current");

	    // Table: book

	    boolFields.add("book_available");
	    dateFields.add("return_date");

	    // Table: borrow
	    intFields.add("borrow_number");
	    intFields.add("lending_librarian");
	    dateFields.add("borrow_date");
	    dateFields.add("actual_returned_date");

	    // Table: extensions
	    dateFields.add("day_of_extension");
	    dateFields.add("new_return_date");

	    // Table: order_book
	    dateFields.add("nearest_book_return");
	    intFields.add("subscriber_id");
	    
	    
	    // Table : librarian_messages
	    boolFields.add("is_new");
	    
	    

	  
	}
	
	
	/**
     * Retrieves database rows based on specified conditions.
     *
     * @param tablename The name of the table to query
     * @param fields Array of field names to match against
     * @param values Array of values to match with corresponding fields
     * @return ResultSet containing matching rows, or null if query fails
     */
	public ResultSet retrieveRowsWithConditions(String tablename, String[] fields, String[] values) {

	    StringBuilder query = new StringBuilder("SELECT * FROM ");
	    query.append(tablename);
	    
	    int fieldLen=0,valuesLen=0;
	    
	    if (fields!=null)
	    {
	    	query.append(" WHERE ");
	    	fieldLen=fields.length;
	    }
	    if (values!=null)
	    	valuesLen=values.length;
	    
	    for (int i = 0; i < fieldLen; i++) {
	        query.append(fields[i]);
	        if (values[i]==null)
	        	query.append(" IS ?");
	        else
	        	query.append(" LIKE ?");
	        
	        if (i < fieldLen - 1) {
	            query.append(" AND ");
	        }
	    }
	    
	    try {
	        PreparedStatement stmt = con.prepareStatement(query.toString());
	        for (int i = 0; i < valuesLen; i++) {
	            //stmt.setString(i + 1, "%" + values[i] + "%");
	            setStmt(stmt,i+1,fields[i],"%" + values[i] + "%");
	        }
	        return stmt.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("SQLException in retrieveRowsWithConditions" + e.getMessage());

	        return null;
	    }
	    
	}
	

	
	 /**
     * Sets a prepared statement parameter with appropriate type conversion.
     *
     * @param stmt The PreparedStatement to set parameters for
     * @param index The parameter index
     * @param field The field name to determine the type
     * @param value The value to set
     * @throws SQLException if there's an error setting the parameter
     */
	private void setStmt(PreparedStatement stmt, int index,String field ,String value) throws SQLException
	{

		
		if (intFields.contains(field))
		{
			int intValue=-1;
			try {
				intValue = Integer.parseInt(value);
			}catch(NumberFormatException e)
			{
				//do nothing.
			}
			
			stmt.setInt(index,intValue);
		}	

	    // Handling date fields
		else if (dateFields.contains(field)) {
		    if (value == null) {
		        stmt.setNull(index, java.sql.Types.DATE);
		    } else if (value.contains("null")) {
		        stmt.setNull(index, java.sql.Types.DATE);
		    } else {
		        try {
		            java.sql.Date sqlDate;
	                String cleanValue = value.replaceAll("%", "").trim();
		            if (cleanValue.matches("\\d{4}-\\d{2}-\\d{2}")) {

		                // Handle yyyy-MM-dd format
		                sqlDate = java.sql.Date.valueOf(cleanValue);

		            } else {
		                // Handle Date.toString() format (e.g., "Wed Dec 31 00:00:00 PST 1969")
		                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		                java.util.Date parsed = format.parse(cleanValue);
		                sqlDate = new java.sql.Date(parsed.getTime());
		            }
		            stmt.setDate(index, sqlDate);
		        } catch (IllegalArgumentException | ParseException e) {

		            throw new SQLException("Invalid date format for field: " + field + " with value: " + value);
		        }
		    }
		}
		else if (boolFields.contains(field))
			stmt.setBoolean(index, value.contains("true"));//if value = "true" will set true.
		else
			stmt.setString(index,value);	
	}
	
	/**
     * Retrieves a single row based on a field value.
     *
     * @param table The table to query
     * @param field The field to match against
     * @param val The value to match
     * @return ResultSet containing the matching row, or null if query fails
     */
	public ResultSet retrieveRow(String table, String field, String val)
	{
		
		try
		{
			//stmt = con.createStatement();
			PreparedStatement stmt;
			if (val==null)
				 stmt = con.prepareStatement("SELECT * FROM " +table +" WHERE " + field + " IS ?");
			else
				 stmt = con.prepareStatement("SELECT * FROM " +table +" WHERE " + field + " = ?");
			
			
			setStmt(stmt,1,field,val);
			
			ResultSet rs = stmt.executeQuery();

			return rs;

		}
		catch(SQLException e) {
			System.out.println("SQL Exception error : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
			
	}
	/**
     * Updates a single row in the specified table.
     *
     * @param tableName The name of the table to update
     * @param keyName The primary key field name
     * @param keyVal The primary key value to match
     * @param col The column to update
     * @param data The new value to set
     */
	public void editRow(String tableName, String keyName, String keyVal, String col, String data) {
		
		
		PreparedStatement stmt = null;
		int rows=0;
		
		//integer value handling

		
		try {
			stmt = con.prepareStatement("UPDATE " + tableName + " SET " + col +" = ? WHERE " + keyName + " =  ?;");
			
			setStmt(stmt,1,col,data);
			setStmt(stmt,2,keyName,keyVal);
			
			rows = stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e) {
			System.out.println("SQL Exception at savetoDB error: " + e.getMessage());
		}
		
	}
	
	
	/**
     * Inserts a new row into the specified table.
     *
     * @param table The table to insert into
     * @param fields Array of field names
     * @param vals Array of values corresponding to the fields
     */
	public void insertRow(String table, String[] fields, String[] vals) {
		int rows;
		PreparedStatement stmt = null;
		StringBuilder query = new StringBuilder ("INSERT INTO ");
		query.append(table);
		//query.append(" ");
		if (fields!=null)
		{
			query.append(" (");
			for (int i = 0; i < fields.length-1; i++) {
				query.append(fields[i]);
				query.append(", ");
			}
			query.append(fields[fields.length-1]);
			query.append(")");
			
		}
		
		
		query.append(" VALUES (");
	    for (int i = 0; i < vals.length-1; i++) {
	        query.append("?, ");
	    }
	    	query.append("?)");
	    	

	    
	    	try {
				stmt = con.prepareStatement(query.toString());
				for (int i = 0; i < vals.length; i++) {
					setStmt(stmt,i+1,fields[i],vals[i]);
				}
				rows=stmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("DBController.insertRows failed");
				e.printStackTrace();
			}
	}


	
	/**
     * Counts rows in a table that match specified conditions.
     *
     * @param tableName The name of the table to count from
     * @param fields Array of field names to match against (can be null)
     * @param values Array of values to match with corresponding fields
     * @return The count of matching rows, or -1 if query fails
     */
	public int countRows(String tableName, String[] fields, String[] values) {
        String newID;
        boolean keyExists;
        int rows=-1;
        
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM ");
	    query.append(tableName);
        
        if (fields!=null)
        {
        	
        	query.append(" WHERE ");
        	
        	for (int i = 0; i < fields.length; i++) {
    	        query.append(fields[i]);
    	        query.append(" LIKE ?");
    	        if (i < fields.length - 1) {
    	            query.append(" AND ");
    	        }
    	    }
        	
        }
        
        PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query.toString());
		    // Set the value for the placeholder (e.g., "%active%" for LIKE query)
		    stmt.setString(1, "%" + values[0] + "%");  // Assuming you're looking for the first value in the array (values[0])
			ResultSet rs = stmt.executeQuery();
	        rs.next(); // Move to the first and only result
	        rows = rs.getInt(1); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       

        return rows;
    
	}
	
	/**
     * Counts total number of rows in a table.
     *
     * @param tableName The name of the table to count
     * @return Total number of rows in the table, or -1 if query fails
     */
	public int tableCount(String tableName)
	{
		
        int rows=-1;
        
        String query = "SELECT COUNT(*) FROM " + tableName;
        
       
        PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query.toString());
			ResultSet rs = stmt.executeQuery();
	        rs.next(); // Move to the first and only result
	        rows = rs.getInt(1); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       

        return rows;
	}
	
	
	 /**
     * Retrieves all borrow records from the database.
     *
     * @return List of BorrowRecord objects containing borrow history
     */	
	public List<BorrowRecord> getBorrowRecords() {
	    List<BorrowRecord> records = new ArrayList<>();
	    String query = "SELECT borrow_date, return_date, actual_returned_date FROM borrow";

	    try (PreparedStatement stmt = con.prepareStatement(query); 
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            // Handling borrow_date
	            LocalDate borrowDate = rs.getDate("borrow_date") != null
	                ? rs.getDate("borrow_date").toLocalDate()
	                : null;
	                
	            // Handling return_date
	            LocalDate returnDate = rs.getDate("return_date") != null
	                ? rs.getDate("return_date").toLocalDate()
	                : null;
	                
	            // Handling actual_return_date (can be NULL)
	            LocalDate actualReturnDate = rs.getDate("actual_returned_date") != null
	                ? rs.getDate("actual_returned_date").toLocalDate()
	                : null;

	            // Add the borrow record to the list
	            records.add(new BorrowRecord(borrowDate, returnDate, actualReturnDate));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Log the exception or handle it appropriately
	        // Optionally, rethrow or return an empty list to ensure the application continues
	    }

	    return records;
	}
	
	
	/**
     * Retrieves all destroyed book records from the database.
     *
     * @return List of DestRecord objects containing destroyed book information
     */
	public List<DestRecord> getDestRecords() {
	    List<DestRecord> records = new ArrayList<>();
	    String query = "SELECT book_barcode, borrower_id FROM destroyed_books";

	    try (PreparedStatement stmt = con.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            // Fetch the book barcode and borrower ID from the result set
	            String bookBarcode = rs.getString("book_barcode");
	            String borrowerId = rs.getString("borrower_id");

	            // Add the destroyed book record to the list
	            records.add(new DestRecord(bookBarcode, borrowerId));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Log the exception or handle it appropriately
	        // Optionally, rethrow or return an empty list to ensure the application continues
	    }

	    return records;
	}
	
	
	
	public void deleteRow(String table, String primaryKeyColumn, String primaryKeyValue) {
	    int rows;
	    PreparedStatement stmt = null;
	    StringBuilder query = new StringBuilder("DELETE FROM ");
	    query.append(table);
	    query.append(" WHERE ");
	    query.append(primaryKeyColumn);
	    query.append(" = ?");

	    try {
	        stmt = con.prepareStatement(query.toString());
	        stmt.setString(1, primaryKeyValue); // Assuming the primary key value is a string, modify this if it's a different data type
	        rows = stmt.executeUpdate();
	        System.out.println("Rows deleted: " + rows);
	    } catch (SQLException e) {
	        System.out.println("DBController.deleteRow failed");
	        e.printStackTrace();
	    }
	}




	public void closeConnection() {
		try {
	            //con.close();
	        
		} catch (Exception e) {
			
			//System.out.print("cannot close connection with the DB ");
		}
	}
	
	
	

}
