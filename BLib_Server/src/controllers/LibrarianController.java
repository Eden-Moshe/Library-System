package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.*;
import server.DBController;

/**
 * The LibrarianController class manages librarian-related operations such as retrieving messages 
 * and marking them as read. It communicates with the database using DBController to perform operations
 * related to the librarian's messages and other functionalities.
 */
public class LibrarianController {

    /** Singleton instance of the LibrarianController. */
    private static final LibrarianController instance = new LibrarianController();

    /** Database controller instance to interact with the database. */
    private DBController db;

    /** Table name for librarian messages. */
    private static String tName = "librarian";

    /** Key field for identifying librarian records. */
    private static String keyField = "librarian_id";

    /**
     * Returns the singleton instance of the LibrarianController.
     *
     * @return The instance of LibrarianController.
     */
    public static LibrarianController getInstance() {
        return instance;
    }

    /**
     * Private constructor for the LibrarianController. Initializes the DBController instance.
     */
    private LibrarianController() {
        db = DBController.getInstance();
    }

    /**
     * Marks a list of inbox messages as read in the database.
     *
     * @param messages The list of inbox messages to be marked as read.
     * @return A message indicating how many messages were marked as read.
     */
    public String markRead(ArrayList<InboxMessage> messages) {
        int cnt = 0;
        for (InboxMessage msg : messages) {
            // Updating the 'is_new' status to false for each message
            db.editRow("librarian_messages", "message_id", msg.message_id, "is_new", "false");
            cnt++;
        }
        // Return confirmation message
        String ret = "marked " + cnt + " messages as read";
        return ret;
    }

    /**
     * Retrieves the messages for a specific librarian based on their ID.
     *
     * @param id The librarian's unique ID.
     * @return A list of InboxMessage objects containing the librarian's messages.
     */
    public ArrayList<InboxMessage> retrieveMessages(String id) {
        ArrayList<InboxMessage> ret = new ArrayList<>();
        InboxMessage inboxMessage;
        // Retrieve messages from the database for the specified librarian ID
        ResultSet rs = db.retrieveRow("librarian_messages", "librarian_id", id);
        try {
            while (rs.next()) {

                // Create new InboxMessage object and populate it with the database values
                inboxMessage = new InboxMessage();
                inboxMessage.librarian_id = id;
                inboxMessage.sender = rs.getString("sender");
                inboxMessage.message = rs.getString("message");
                inboxMessage.message_id = rs.getString("message_id");
                inboxMessage.is_new = rs.getBoolean("is_new");

                // Add the message to the result list
                ret.add(inboxMessage);
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
