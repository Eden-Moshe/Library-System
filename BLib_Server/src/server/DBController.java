package server;

<<<<<<< HEAD
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
=======
>>>>>>> origin/main
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
<<<<<<< HEAD
import java.util.Set;
=======

import java.util.Set;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
>>>>>>> origin/main

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
			con = DriverManager.getConnection("jdbc:mysql://localhost/blib?serverTimezone=IST","root","eden1234");
		} catch (SQLException e) {
			System.out.print("cannot connect to the DB : " + e.getMessage());
			return false;
		}
		
		return true;
	}
//	private void logError(String error, Throwable t)
//	{
//		  //File errorLog = new File("DBController Error Log.txt");
//		File errorLog = new File(System.getProperty("user.home") + File.separator + "DBController_Error_Log.txt");
//
//		  try {
//			errorLog.createNewFile();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  Date currentDate = new Date(); // Get the current date
//		  String toLog = currentDate.toString() + "\n" + error + "\n" + t.getMessage() + "\n";
//		  //writing to file
//		  try {
//			FileOutputStream fos = new FileOutputStream(errorLog, true);
//			BufferedOutputStream bos = new BufferedOutputStream(fos);
//			
//			//byte [] mba  = toLog.getBytes();
//			
//			//bos receives toLog bytes from concatted  String
//			//bos.write(mba, 0 , mba.length);
//			bos.write(toLog.getBytes());
//			//shutting down streams
//			bos.flush();
//			fos.close();
//			bos.close();
//	}catch (Exception e) {//(FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	  
//		  
//		  System.out.println("logError will now print the logged error's stacktrace:\n");
//		  t.printStackTrace();
//		  
//		  
//	}
	
	
	
//	
//	private void initializeFields() {
//	    try {
//	        DatabaseMetaData metaData = con.getMetaData();
//	        ResultSet tables = metaData.getTables(null, "blib", "%", new String[]{"TABLE"});
//	        while (tables.next()) {
//	            String tableName = tables.getString("TABLE_NAME");
//	            //TO-REMOVE
//	            //System.out.println("Found table: " + tableName);
//	            
//	            // Fetch a single row from the table
//	            String query = "SELECT * FROM " + tableName + " LIMIT 1";
//	            try (PreparedStatement stmt = con.prepareStatement(query);
//	                 ResultSet rs = stmt.executeQuery()) {
//
//	                // Process metadata for the result set
//	                ResultSetMetaData rsMetaData = rs.getMetaData();
//	                int columnCount = rsMetaData.getColumnCount();
//
//	                for (int i = 1; i <= columnCount; i++) {
//	                    String columnName = rsMetaData.getColumnName(i);
//
//	                    // Check column type dynamically if the table has data
////	                    if (rs.next()) {
////	                        Object value = rs.getObject(i);
////	                        System.out.println(tableName + "\tinitializing fields, values: " + value.getClass());
////	                        if (value instanceof Integer) {
////	                            intFields.add(columnName);
////	                        }
////	                        if (value instanceof Date) {
////	                            dateFields.add(columnName);
////	                        }
////	                        if (value instanceof Boolean) {
////	                            dateFields.add(columnName);
////	                        }
////	                    }
//	                    
//	                    if (rs.next()) {
//	                        for (int j = 1; j <= columnCount; j++) {
//	                            Object value = rs.getObject(j);
//	                            System.out.println("Processing column " + columnName + " with value type " + value.getClass());
//	                            if (value instanceof Integer) {
//	                                intFields.add(columnName);
//	                            } else if (value instanceof Date) {
//	                                dateFields.add(columnName);
//	                            } else if (value instanceof Boolean) {
//	                                boolFields.add(columnName);
//	                            }
//	                        }
//	                    }
//	                    
//	                    
//	                }
//	            } catch (SQLException e) {
//	            	//TO-FIX BUG: it's fetching a lot of useless tables and doesn't find them in blib
//	                //System.out.println("Error querying table " + tableName + ": " + e.getMessage());
//	            }
//	        }
//	    } catch (SQLException e) {
//	        System.out.println("Error intializing int fields " + e.getMessage());
//	    }
//	}
//	

	
	
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
	    query.append(" WHERE ");
	    for (int i = 0; i < fields.length; i++) {
	        query.append(fields[i]);
	        query.append(" LIKE ?");
	        if (i < fields.length - 1) {
	            query.append(" AND ");
	        }
	    }
	    
	    try {
	        PreparedStatement stmt = con.prepareStatement(query.toString());
	        for (int i = 0; i < values.length; i++) {
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
	
		System.out.println(intFields);
		System.out.println(dateFields);
		System.out.println(boolFields);
		
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




	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.print("cannot close connection with the DB");
		}
	}
	
	
	

}
