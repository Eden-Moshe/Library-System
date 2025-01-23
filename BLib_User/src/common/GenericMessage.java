package common;

import java.io.Serializable;

public class GenericMessage implements Serializable{

	public Action action;
	
	public enum Action{
		get_Borrow_History,
		get_Account_Status_History,
		get_Librarian_Messages,
		set_Librarian_Messages_Read,
		set_New_Password
	}
	public Subscriber subscriber;
	public Librarian librarian;
	public String fieldCol, fieldVal, message;
	public Object Obj;
}
