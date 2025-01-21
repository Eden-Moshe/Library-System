package common;

import java.io.Serializable;

public class GenericMessage implements Serializable{

	public Action action;
	
	public enum Action{
		get_Borrow_History,
		get_Account_Status;
	}
	public Subscriber sub;
	public Librarian lib;
	public String fieldCol, fieldVal, message;
	public Object Obj;
}
