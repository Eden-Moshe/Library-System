package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.*;
import server.DBController;

public class LibrarianController {
	private static final LibrarianController instance = new LibrarianController();
	private DBController db;
	private static String tName="librarian";
	private static String keyField="librarian_id";
	
	public static LibrarianController getInstance() {
		return instance;
	}
	private LibrarianController()
	{
		db=DBController.getInstance();
	}
	public String markRead(ArrayList<InboxMessage> messages)
	{
		int cnt=0;
		for (InboxMessage msg : messages)
		{
			db.editRow("librarian_messages", "message_id", msg.message_id, "is_new", "false");
			cnt++;
		}
		String ret = "marked " + cnt + " messages as read";
		
		return ret;
		
		
	}
	public ArrayList<InboxMessage> retrieveMessages(String id)
	{
		ArrayList<InboxMessage> ret = new ArrayList<>();
		InboxMessage inboxMessage;
		ResultSet rs = db.retrieveRow("librarian_messages", "librarian_id", id);
		try {
			while (rs.next())
			{

				
				inboxMessage = new InboxMessage();
				
				inboxMessage.librarian_id=id;
				inboxMessage.sender=rs.getString("sender");
				inboxMessage.message=rs.getString("message");
				inboxMessage.message_id=rs.getString("message_id");
				inboxMessage.is_new = rs.getBoolean("is_new");
				ret.add(inboxMessage);
					
				
				
			}
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		
		return null;
	}
	
	
}
