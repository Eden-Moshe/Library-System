package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.sql.*;

public class DBController {
	
	public Connection con;
	
	public DBController() {
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
	private void dataInput(ArrayList<String> msg) {
		/*String subscriber_id = null ;
		String subscriber_name = null ;
		String detailed_subscription_history = null ;
		String subscriber_phone_number = null;
		String subscriber_email = null;
		*/
		
		
		
		//list of Subscriber table columns. needed for validation
        Set<String> validSubColumns = new HashSet<>(Arrays.asList("subscriber_id", "subscriber_name", "detailed_subscription_history", "subscriber_phone_number", "subscriber_email"));
    	
        
        //primary key name and value
        String[] pKey = msg.get(1).split(":");
//        if(!validSubColumns.contains(pKey[0]))
//        	return;
        
        //strings to hold the column we want to change and the data to change.
        String[] input= new String[2];
        Iterator<String> iterator = msg.iterator();
        while (iterator.hasNext()) {
        		input = iterator.next().split(":");
        		if (validSubColumns.contains(input[0]))
        		{
        			savetoDB(pKey[0],pKey[1],input[0],input[1]);
        			
        		}
        	
        	}
    
	
	
	}/**
	   *
	   * function saves to DB or returns fields from DB
	   * 
	   * @param 0 to save ArrayList to DB, 1 to return ArrayList fields for single user
	   * @param data to input to table
	   */
	public ArrayList inputOutput(String operation, ArrayList<String> f)
	{
		if (operation.equals("input"))
		{
			dataInput(f);
			return null;
		}
		else if (operation.equals("output"))
		{
			return retrieveUserData(f);
		}
		else
			return null;
		
		
	}
	
	private ArrayList retrieveUserData(ArrayList<String> f)
	{
		//Statement stmt;
	 	ArrayList<String> data = new ArrayList<String>();
		String t[];
		try
		{
			//stmt = con.createStatement();
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM subscriber WHERE subscriber_id = ?");
			
			t=f.get(1).split(":");
			
			stmt.setInt(1, Integer.parseInt(t[1]));
			ResultSet rs = stmt.executeQuery();

			
			data.add("subscriber:info");

			rs.next();
			for (int i = 1; i < 6; i++) {
				data.add(rs.getString(i));
				
			}
			System.out.println(data);
			stmt.close();
			return data;
		}
		catch(SQLException e) {
			System.out.println("SQL Exception error : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
		
		
		
	}
		
	private void savetoDB(String keyName, String keyVal, String col, String data) {
		
		
		PreparedStatement stmt = null;
		int rows=0,numData=-1, key=-1;
		boolean flag=false;
		key=Integer.parseInt(keyVal);
		//integer value handling
		if (col.equals("subscriber_id") | data.equals("detailed_subscription_history"))
			{
				numData = Integer.parseInt(data);
				flag=true;
			}
		
		
		try {
			stmt = con.prepareStatement("UPDATE subscriber SET " + col +" = ? WHERE " + keyName + " =  ?;");
			//handling strings and integers differently
			if (flag)
				stmt.setInt(1, numData);
			else
				stmt.setString(1, data);
			stmt.setInt(2, key);
			rows = stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException e) {
			System.out.println("SQL Exception at savetoDB error: " + e.getMessage());
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
