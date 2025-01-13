package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import common.*;
import server.DBController;

//probably needs to be a singleton
public class SubscriberController {
	private static final SubscriberController instance = new SubscriberController();
	private DBController db;
	private static String tName="subscriber";
	private static String keyField="subscriber_id";
	
	public static SubscriberController getInstance() {
		return instance;
	}
	private SubscriberController()
	{
		db=DBController.getInstance();
	}
	
	public Subscriber fetchSubscriber(String pKey) {
	    Subscriber ret = null;
	    ResultSet rs = db.retrieveRow(tName, keyField, pKey);
	    try {
	        if (rs.next()) {
	            // Populate the Subscriber details
	            ret = new Subscriber(
	                rs.getString("subscriber_id"),                 // subscriber_id column
	                rs.getString("subscriber_name"),               // subscriber_name column
	                rs.getString("subscriber_phone_number"),       // subscriber_phone_number column
	                rs.getString("subscriber_email"),              // subscriber_email column
	                rs.getString("subscriber_status")              // subscriber_status column
	            );
	            System.out.println(ret);
	            return ret;
	        } else {
	            System.out.println("No Subscriber found with key: " + pKey);
	            return null;
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to retrieve Subscriber table data");
	        e.printStackTrace();
	        return null;
	    }
	}

	
//	public Subscriber fetchSubscriber(String pKey)
//	{
//		
//		Subscriber ret=null;
//		ResultSet rs = db.retrieveRow(tName, keyField, pKey);
//		try {
//			rs.next();
//			System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getString(5)+rs.getString(6));
//			
//			ret = new Subscriber (rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
//			System.out.println(ret);
//			return ret;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Failed to retrieve Subscriber table data");
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	public void editSubscriber(String pKey, String field, String val)
	{
		//list of valid editing Subscriber table columns. needed for validation
        Set<String> validCol = new HashSet<>(Arrays.asList("subscriber_name", "detailed_subscription_history", "subscriber_phone_number", "subscriber_email"));
        if (!validCol.contains(field))
        {	
        	System.out.println("editSubscriber wrong field was inputted");
        	return;
        }
        
        db.editRow(keyField, pKey, field, val);
        
	}
	public void addSubscriber()
	{
		//needs to add subscriber to database
	}

	
	
	//currently unimplemented, this will check to make sure a user can't edit or fetch data that doesn't belong to them.
	public boolean verifyPassword(String userID, String pass)
	{
		return true;
	}
}
