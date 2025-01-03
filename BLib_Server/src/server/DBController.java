package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.sql.*;

public class DBController {
	private static final DBController instance = new DBController();
	private boolean init=false;
	private Connection con;
	//intFields is the list of all fields that have int values in the database
	private static Set<String> intFields = new HashSet<>(Arrays.asList("subscriber_id"));
	
	public static DBController getInstance () {
		return instance;
	}
	
	private DBController() {
		connectToDB();
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
	
	/**
	   * Parses and inputs data into table
	   * data needs to be in the form of "columName:columnData"
	   * @param data to input to table
	   */
//	private void dataInput(ArrayList<String> msg) {
//		/*String subscriber_id = null ;
//		String subscriber_name = null ;
//		String detailed_subscription_history = null ;
//		String subscriber_phone_number = null;
//		String subscriber_email = null;
//		*/
//		
//		
//		
//		//list of Subscriber table columns. needed for validation
//        Set<String> validSubColumns = new HashSet<>(Arrays.asList("subscriber_id", "subscriber_name", "detailed_subscription_history", "subscriber_phone_number", "subscriber_email"));
//    	
//        
//        //primary key name and value
//        String[] pKey = msg.get(1).split(":");
////        if(!validSubColumns.contains(pKey[0]))
////        	return;
//        
//        //strings to hold the column we want to change and the data to change.
//        String[] input= new String[2];
//        Iterator<String> iterator = msg.iterator();
//        while (iterator.hasNext()) {
//        		input = iterator.next().split(":");
//        		if (validSubColumns.contains(input[0]))
//        		{
//        			savetoDB(pKey[0],pKey[1],input[0],input[1]);
//        			
//        		}
//        	
//        	}
//    
//	
//	
//	}/**
//	   *
//	   * function saves to DB or returns fields from DB
//	   * 
//	   * @param 0 to save ArrayList to DB, 1 to return ArrayList fields for single user
//	   * @param data to input to table
//	   */
//	public ArrayList inputOutput(String operation, ArrayList<String> f)
//	{
//		if (operation.equals("input"))
//		{
//			dataInput(f);
//			return null;
//		}
////		else if (operation.equals("output"))
////		{
////			return retrieveUserData(f);
////		}
//		else
//			return null;
//		
//		
//	}
//	
//	private ArrayList retrieveUserData(ArrayList<String> f)
//	{
//		//Statement stmt;
//	 	ArrayList<String> data = new ArrayList<String>();
//		String t[];
//		try
//		{
//			//stmt = con.createStatement();
//			PreparedStatement stmt = con.prepareStatement("SELECT * FROM subscriber WHERE subscriber_id = ?");
//			
//			t=f.get(1).split(":");
//			
//			stmt.setInt(1, Integer.parseInt(t[1]));
//			ResultSet rs = stmt.executeQuery();
//
//			
//			data.add("subscriber:info");
//
//			rs.next();
//			for (int i = 1; i < 6; i++) {
//				data.add(rs.getString(i));
//				
//			}
//			System.out.println(data);
//			stmt.close();
//			return data;
//		}
//		catch(SQLException e) {
//			System.out.println("SQL Exception error : " + e.getMessage());
//			e.printStackTrace();
//			return null;
//		}
//		
//		
//		
//		
//	}
	
	
	
	//consideration for changes:
	//create a string array for keyfields and keys and concat a statement that can have as many ANDS as i want
	//for example, i want a user, i can have an array of 1 with sub_id and keynum
	//and if i want a book that has "harry" in the name and "fantasy" in the topic,
	//the array can be of length 2 with fields [name,topic] and [harry,fantasy] as fields.
	//a for loop would then create a statement that looks like this:
	//"SELECT * FROM " + table + "WHERE keyfield[0] = ? AND keyfield[1] = ?"
	
	
	private void setStmt(PreparedStatement stmt, int index,String field ,String value) throws SQLException
	{
		if (intFields.contains(field))
			stmt.setInt(index, Integer.parseInt(value));
		else
			stmt.setString(index, value);	
	}
	
	public ResultSet retrieveRow(String table, String pKeyField, String key)
	{
		
		try
		{
			//stmt = con.createStatement();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM " +table +" WHERE " + pKeyField + " = ?");
			
			System.out.println(stmt.toString());
			//stmt.setString(1, key);
			setStmt(stmt,1,pKeyField,key);
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
		
	public void editRow(String keyName, String keyVal, String col, String data) {
		
		
		PreparedStatement stmt = null;
		int rows=0;
		
		//integer value handling

		
		try {
			stmt = con.prepareStatement("UPDATE subscriber SET " + col +" = ? WHERE " + keyName + " =  ?;");
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
	
	public void insertRow(String table, String[] Fields, String[] Vals) {
		int rows;
		PreparedStatement stmt = null;
		//INSERT INTO subscriber (subscriber_id, subscriber_name, detailed_subscription_history, subscriber_phone_number, subscriber_email) VALUES  (5, 'nofar', 5, 2253150559,'eSemailS@fake.com')
		StringBuilder query = new StringBuilder ("INSERT INTO ");
		query.append(table);
		//query.append(" ");
		if (Fields!=null)
		{
			query.append(" (");
			for (int i = 0; i < Fields.length-1; i++) {
				query.append(Fields[i]);
				query.append(", ");
			}
			query.append(Fields[Fields.length-1]);
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
	    for (int i = 0; i < Vals.length-1; i++) {
	        query.append("?, ");
	    }
	    	query.append("?)");

	    	try {
				stmt = con.prepareStatement(query.toString());
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
