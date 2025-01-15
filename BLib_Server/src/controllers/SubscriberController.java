package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public Subscriber fetchSubscriber(String pKey)
	{
		
		Subscriber ret=null;
		ResultSet rs = db.retrieveRow(tName, keyField, pKey);
		try {
			rs.next();
			System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3)+rs.getString(4)+rs.getString(5)+rs);
			
			ret = new Subscriber (rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
			System.out.println(ret);
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
        	System.out.println("editSubscriber wrong field was inputted");
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
		System.out.println("add subscriber");
		String[] fields = {"user_password"};
		String tempPassword = generateTemporaryPassword();
		String[] values = {tempPassword};
		
		db.insertRow("users", fields,values);
		String lastUserNum = Integer.toString(db.countRows("users", null, null));
		
		System.out.println("add subscriber post insertrow");
		
		newSub.setSID(lastUserNum);
		newSub.setTempPass(tempPassword);
		newSub.setStatus("active");
		
		System.out.println("add subscriber lastUserNum = " + lastUserNum);

		
		ResultSet testCreation = db.retrieveRow("users", "user_id", lastUserNum);
		
		System.out.println("add subscriber post retrieveRow");

		try {
			if (testCreation.next())
			{
				System.out.println("if (testCreation.next())");
				if (!testCreation.getString("user_password").equals(tempPassword))
				{
					//the last user isn't the one we inserted. perhaps a collision happened.
					//recursively call addSubscriber until it happens. should not happen more than once or twice.
					System.out.println("failed to create new subscriber. likely collision. trying again");
					//uncomment this after testing.
					//return addSubscriber(newSub);
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

	
	
	//currently unimplemented, this will check to make sure a user can't edit or fetch data that doesn't belong to them.
	public boolean verifyPassword(String userID, String pass)
	{
		return true;
	}
}
