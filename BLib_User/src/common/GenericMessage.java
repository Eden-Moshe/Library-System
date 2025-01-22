package common;

import java.io.Serializable;

public class GenericMessage implements Serializable{

	public Action action;
	
	public enum Action{
		get_Borrow_History,
		get_Account_Status,
		get_Librarian_Messages,
		set_Librarian_Messages_Read,
	}
	public Subscriber subscriber;
	public Librarian librarian;
	public String fieldCol, fieldVal, message;
	public Object Obj;
}
