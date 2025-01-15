package server;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import java.util.Set;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DBController {
	private static final DBController instance = new DBController();
	private Connection con;
	//intFields is the list of all fields that have int values in the database
	//private static Set<String> intFields = new HashSet<>(Arrays.asList());
	private static Set<String> intFields;
	private static Set<String> dateFields;
	private static Set<String> boolFields;
	public static DBController getInstance () {
		return instance;
	}
	
	private DBController() {
		intFields=new HashSet<>(Arrays.asList());
		dateFields=new HashSet<>(Arrays.asList());
		boolFields=new HashSet<>(Arrays.asList());
		connectToDB();
		initializeFields();
	}
	
	private boolean connectToDB() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print("the class Driver is not found");
			return false ;
		}
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/blib?serverTimezone=IST","root","Aa123456");
		} catch (SQLException e) {
			System.out.print("cannot connect to the DB : " + e.getMessage());
			return false;
		}
		
		return true;
	}


	
	
	private void initializeFields() {

	    // Table: users
	    intFields.add("user_id");


	    // Table: librarian
	    intFields.add("librarian_id");

	    // Table: subscriber
	    intFields.add("subscriber_id");


	    // Table: user_status_registry
	    intFields.add("user_id");
	    dateFields.add("status_set_date");
	    dateFields.add("status_end_date");
	    boolFields.add("status_is_current");

	    // Table: book

	    boolFields.add("book_available");
	    dateFields.add("return_date");

	    // Table: borrow
	    intFields.add("borrow_number");
	    intFields.add("lending_librarian");
	    intFields.add("subscriber_id");
	    dateFields.add("borrow_date");
	    dateFields.add("return_date");
	    dateFields.add("actual_returned_date");

	    // Table: extensions
	    intFields.add("lending_librarian");
	    intFields.add("borrow_number");
	    dateFields.add("day_of_extension");
	    dateFields.add("new_return_date");

	    // Table: order_book
	    dateFields.add("nearest_book_return");
	    intFields.add("subscriber_id");

	    // Print the categorized fields for verification
	    System.out.println("Integer Fields: " + intFields);
	    System.out.println("Date Fields: " + dateFields);
	    System.out.println("Boolean Fields: " + boolFields);
	}
	
	
	
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

	        //logError("SQLException in retrieveRowsWithConditions", e);
	        return null;
	    }
	    
	}
	
	//consideration for changes:
	//create a string array for keyfields and keys and concat a statement that can have as many ANDS as i want
	//for example, i want a user, i can have an array of 1 with sub_id and keynum
	//and if i want a book that has "harry" in the name and "fantasy" in the topic,
	//the array can be of length 2 with fields [name,topic] and [harry,fantasy] as fields.
	//a for loop would then create a statement that looks like this:
	//"SELECT * FROM " + table + "WHERE keyfield[0] = ? AND keyfield[1] = ?"
	

	private void setStmt(PreparedStatement stmt, int index,String field ,String value) throws SQLException
	{
		//LocalDate s = new LocalDate(index, index, index);
		//Date s = new Date();
		//s.parse(value);
	
		
		if (intFields.contains(field))
			stmt.setInt(index, Integer.parseInt(value));
		else if (dateFields.contains(field))
		{
			Date d=null;
			try {
				d = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(value);
			} catch (ParseException e) {
				System.out.println("error converting string to date");
				e.printStackTrace();
			}
			stmt.setDate(index, (java.sql.Date) d);
		}
		else if (boolFields.contains(field))
			stmt.setBoolean(index, value.contains("true"));//if value = "true" will set true.
		else
			stmt.setString(index, value);	
	}
	
	public ResultSet retrieveRow(String table, String field, String val)
	{
		
		try
		{
			//stmt = con.createStatement();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM " +table +" WHERE " + field + " = ?");
			
			System.out.println(stmt.toString());
			//stmt.setString(1, key);
			setStmt(stmt,1,field,val);
			//stmt.setInt(1, Integer.parseInt(val));
			System.out.println(stmt.toString());
			ResultSet rs = stmt.executeQuery();

			return rs;

		}
		catch(SQLException e) {
			System.out.println("SQL Exception error : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		
		
		
	}
		
	public void editRow(String tableName, String keyName, String keyVal, String col, String data) {
		
		
		PreparedStatement stmt = null;
		int rows=0;
		
		//integer value handling

		
		try {
			stmt = con.prepareStatement("UPDATE " + tableName + " SET " + col +" = ? WHERE " + keyName + " =  ?;");
			//handling strings and integers differently
	
			//stmt.setString(1, data);
			//stmt.setString(2, keyVal);
			setStmt(stmt,1,col,data);
			setStmt(stmt,2,keyName,keyVal);
			
			rows = stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e) {
			System.out.println("SQL Exception at savetoDB error: " + e.getMessage());
		}
		
	}
	
	public void insertRow(String table, String[] fields, String[] vals) {
		int rows;
		PreparedStatement stmt = null;
		//INSERT INTO subscriber (subscriber_id, subscriber_name, detailed_subscription_history, subscriber_phone_number, subscriber_email) VALUES  (5, 'nofar', 5, 2253150559,'eSemailS@fake.com')
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
		
//		query.append(" VALUES (");
//		for (int i = 0; i < Vals.length-1; i++) {
//			query.append(Vals[i]);
//			query.append(", ");
//		}
//		query.append(Vals[Vals.length-1]);
//		query.append(")");
		
		
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


	
	//function counts the amount of rows that satisfy the conditions of the fields=values. 
	//fields can be null to retrieve the table size
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
			ResultSet rs = stmt.executeQuery();
	        rs.next(); // Move to the first and only result
	        rows = rs.getInt(1); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       

        return rows;
    
	}


	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.print("cannot close connection with the DB");
		}
	}
	
	
	

}
