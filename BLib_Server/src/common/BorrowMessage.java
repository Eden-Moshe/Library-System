package common;

import java.io.Serializable;

public class BorrowMessage implements Serializable {
    //Borrow Message class hold values to transfer between server and client side with needed information as required
	public boolean editBool;
	public Subscriber s;
	public Borrow borrow;
	public Book b;
	public String lib_id;
	
}