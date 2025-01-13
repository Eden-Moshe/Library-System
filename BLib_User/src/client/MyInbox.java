package client;

public class MyInbox {
	private String immediate_message="no new message";
	private Object objMsg;
	private Object arr[];
	public void setMessage(Object msg)
	{
		if (msg instanceof String)
			immediate_message=(String)msg;
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
		String temp=immediate_message;
		immediate_message=null;
		return temp;
	}
	
}
