package common;

import java.io.Serializable;

public class BorrowMessage implements Serializable {
    
	public boolean editBool;
	public Subscriber s;
	public Borrow borrow;
	public Book b;
	public String lib_id;
	
}