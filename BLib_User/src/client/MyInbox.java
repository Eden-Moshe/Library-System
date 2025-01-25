package client;

import common.Subscriber;

public class MyInbox {
	private String immediate_message="no new message";
	private Object objMsg;
	private Object arr[];
	private Subscriber handleSubscriber;
	
	public void setMessage(Object msg)
	{
		if (msg instanceof String)
			immediate_message=(String)msg;
		else
			objMsg=msg;
		
	}
	
	public void handledSubscriber(Subscriber handle)
	{
		handleSubscriber=handle;
	}
	public Subscriber getHandledSubscriber()
	{
		return handleSubscriber;
	}
	
	//one time use
	public Object getObj()
	{
		return objMsg;
	}
	public String getMessage()
	{
		String temp=immediate_message;
		immediate_message=null;
		return temp;
	}
	
}
