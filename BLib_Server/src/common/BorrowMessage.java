package common;

import java.io.Serializable;
import java.util.Date;

public class BorrowMessage implements Serializable {
    
	public Date borrowDate;
    public Date returnDate;
	public boolean editBool;
	public Subscriber s;
	public Book b;
	
}
