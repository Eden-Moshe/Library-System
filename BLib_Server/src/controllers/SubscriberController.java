package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
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
	public ArrayList<AccountStatus> getAccountStatus(Subscriber sub)
	{
		ArrayList<AccountStatus> ret = new ArrayList<>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		AccountStatus as;
		ResultSet rs = db.retrieveRow("user_status_registry", "user_id", sub.getSID());
		try {
			while (rs.next())
			{
				as = new AccountStatus ();
				
				String endDateStr = rs.getString("status_end_date");
				if (endDateStr != null) {
				    as.end_date = dateFormat.parse(endDateStr);
				} else {
				    as.end_date = null; 
				}
				
				as.set_date = dateFormat.parse(rs.getString("status_set_date"));
				as.is_current = rs.getBoolean("status_is_current");
				as.status = rs.getString("subscriber_status");
				
				ret.add(as);
				
				
			}
			
			return ret;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		return null;
		
		
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
	            return ret;
	        } else {
	            return null;
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to retrieve Subscriber table data");
	        e.printStackTrace();
	        return null;
	    }
	}


	
	public void editSubscriber(String pKey, String field, String val)
	{
		//list of valid editing Subscriber table columns. needed for validation
        Set<String> validCol = new HashSet<>(Arrays.asList("subscriber_name", "detailed_subscription_history", "subscriber_phone_number", "subscriber_email"));
        if (!validCol.contains(field))
        {	
        	return;
        }
        
        db.editRow("subscriber",keyField, pKey, field, val);
        
	}
	
	//this requires a change in the SQL. the primary key can't be an auto_increment key.
	
	private String generateTemporaryPassword() {
		
		int length = 5; // Desired string length
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
	}

	public Subscriber addSubscriber(Subscriber newSub)
	{
		String[] fields = {"user_password"};
		String tempPassword = generateTemporaryPassword();
		String[] values = {tempPassword};
		
		db.insertRow("users", fields,values);
		String lastUserNum = Integer.toString(db.tableCount("users"));
		
		
		newSub.setSID(lastUserNum);
		newSub.setTempPass(tempPassword);
		newSub.setStatus("active");
		

		
		ResultSet testCreation = db.retrieveRow("users", "user_id", lastUserNum);
		

		try {
			if (testCreation.next())
			{
				if (!testCreation.getString("user_password").equals(tempPassword))
				{
					//the last user isn't the one we inserted. perhaps a collision happened.
					return null;
				}
				else
				{
					//adding subscriber to the subscriber table.
					String [] subFields = {
						    "subscriber_id",
						    "subscriber_name",
						    "subscriber_phone_number",
						    "subscriber_email",
						    "subscriber_status"
						};
					String [] subValues = { 
							  newSub.getSID(),
							    newSub.getName(),
							    newSub.getPNumber(),
							    newSub.getEmail(),
							    newSub.getStatus()
							};
					db.insertRow(tName, subFields, subValues);
					System.out.println("addsubscriber before returning value newSub = "+newSub.toString());
					return newSub;
				}
			}
			System.out.println("testCreationfailed");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		return null;
		
		
		
	}
	
	public void editPassword(String id, String pass)
	{
		db.editRow("users", "user_id", id , "user_password", pass);
	}

	
	
	//currently unimplemented, this will check to make sure a user can't edit or fetch data that doesn't belong to them.
	public boolean verifyPassword(String userID, String pass)
	{
		return true;
	}
}
