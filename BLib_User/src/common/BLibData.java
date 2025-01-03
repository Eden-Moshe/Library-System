package common;

public class BLibData {
	private Subscriber s;
	private Object obj;//the object we want to receive;
	private int val; //the primary key of the data we want to work with
	private boolean editRequest;//when requesting to edit information this will be true and false for requesting info.
	
	//class booleans
	public boolean sub;//these booleans will flag true for the class with which we want to work with 
	
	public BLibData () {
		s=null;
		sub=false;
		obj=null;
		val=-1;
	}
	
	//after sending a request to server reset all the class booleans
	public void resetBooleans() {
		sub=false;
	}
	
	
	
	public void setToEdit () {
		editRequest=true;
	}
	public void setToGetInfo()
	{
		editRequest=false;
	}
	
	//set and get for primary key of the data we want to work with
	public void setKey(int val) {
		this.val=val;
		
	}
	public int getKey() {
		return val;
		
	}
	
	
	//set and get for the object we want to edit/get info for.
	public void setObj(Object obj) {
		this.obj=obj;
		
	}
	public Object getObj() {
		return obj;
	}
	
	
	
	
	
	//set and get for subscriber
	public void setSub (Subscriber dat) {
		s=dat;
	}
	public Subscriber getSub ()
	{
		return s;
	}
	
	
	

}
