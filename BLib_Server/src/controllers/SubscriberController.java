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

/**
 * The SubscriberController class is responsible for managing subscriber-related operations. 
 * This includes adding, fetching, and editing subscribers, retrieving account status, 
 * and managing subscriptions.
 */
public class SubscriberController {

    /** Singleton instance of SubscriberController. */
    private static final SubscriberController instance = new SubscriberController();

    /** Database controller instance for interacting with the database. */
    private DBController db;

    /** Table name for subscribers. */
    private static String tName = "subscriber";

    /** Key field for subscriber identification. */
    private static String keyField = "subscriber_id";
	
    /**
     * Returns the singleton instance of the SubscriberController.
     *
     * @return The singleton instance of SubscriberController.
     */
    public static SubscriberController getInstance() {
        return instance;
    }

    /**
     * Private constructor to initialize the DBController instance.
     */
    private SubscriberController() {
        db = DBController.getInstance();
    }

    /**
     * Retrieves the account status for a given subscriber.
     *
     * @param sub The subscriber whose account status is to be fetched.
     * @return A list of AccountStatus objects representing the subscriber's account status history.
     */
    public ArrayList<AccountStatus> getAccountStatus(Subscriber sub) {
        ArrayList<AccountStatus> ret = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        AccountStatus as;
        ResultSet rs = db.retrieveRow("user_status_registry", "user_id", sub.getSID());
        try {
            while (rs.next()) {
                as = new AccountStatus();
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
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Fetches a subscriber based on their unique identifier (primary key).
     *
     * @param pKey The subscriber's unique identifier.
     * @return The Subscriber object if found, otherwise null.
     */
    public Subscriber fetchSubscriber(String pKey) {
        Subscriber ret = null;
        ResultSet rs = db.retrieveRow(tName, keyField, pKey);
        try {
            if (rs.next()) {
                ret = new Subscriber(
                        rs.getString("subscriber_id"),
                        rs.getString("subscriber_name"),
                        rs.getString("subscriber_phone_number"),
                        rs.getString("subscriber_email"),
                        rs.getString("subscriber_status")
                );
                return ret;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

	
    public void editSubscriber(String pKey, String field, String val) {
        Set<String> validCol = new HashSet<>(Arrays.asList(
                "subscriber_name", "detailed_subscription_history", "subscriber_phone_number", "subscriber_email"
        ));
        if (!validCol.contains(field)) {

            return;
        }
        db.editRow("subscriber", keyField, pKey, field, val);
    }

    /**
     * Generates a temporary password consisting of 5 alphanumeric characters.
     *
     * @return A random 5-character alphanumeric temporary password.
     */
    private String generateTemporaryPassword() {
        int length = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }

    /**
     * Adds a new subscriber to the system.
     *
     * @param newSub The Subscriber object containing the details of the new subscriber.
     * @return The added Subscriber object if successful, otherwise null.
     */
    public Subscriber addSubscriber(Subscriber newSub) {
        String[] fields = {"user_password"};
        String tempPassword = generateTemporaryPassword();
        String[] values = {tempPassword};
        db.insertRow("users", fields, values);
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
					return newSub;
				}
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Edits the password of a subscriber in the database.
     *
     * @param id The subscriber's unique identifier.
     * @param pass The new password to be set.
     */
    public void editPassword(String id, String pass) {
        db.editRow("users", "user_id", id, "user_password", pass);
    }

    /**
     * Verifies if the given password matches the stored password for the specified user ID.
     *
     * @param userID The user ID to verify the password for.
     * @param pass The password to verify.
     * @return True if the password is correct, false otherwise.
     */
    public boolean verifyPassword(String userID, String pass) {
        return true;  // Placeholder for password verification logic.
    }
}
