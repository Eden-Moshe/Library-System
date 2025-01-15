package common;

import java.io.Serializable;
import java.util.Date;

public class BorrowMessage implements Serializable {
    
	public boolean editBool;
	public Subscriber s;
	public Borrow borrow;
	public Book b;
	
}