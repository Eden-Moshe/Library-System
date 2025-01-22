package common;

import java.io.Serializable;
import java.util.Date;

public class InboxMessage implements Serializable {

	public String message_id;
	
	
	public String librarian_id;
	public Librarian librarian;//can use either id or Librarian instance
	
	public String sender, message;
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean is_new;
	
}
