package client;

public class MyInbox {
	private String awaiting_message;
	private Object objMsg;
	
	public void setMessage(Object msg)
	{
		if (msg instanceof String)
			awaiting_message=(String)msg;
		else
			objMsg=msg;
		
	}
	public Object getObj()
	{
		Object temp=objMsg;
		objMsg=null;
		return temp;
	}
	public String getMessage()
	{
		String temp=awaiting_message;
		awaiting_message=null;
		return temp;
	}
	
}
