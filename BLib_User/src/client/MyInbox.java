package client;

public class MyInbox {
	private String awaiting_message;
	
	public void setMessage(String msg)
	{
		awaiting_message=msg;
	}
	
	public String getMessage()
	{
		String temp=awaiting_message;
		awaiting_message=null;
		return temp;
	}
}