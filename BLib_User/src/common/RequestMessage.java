package common;

import java.io.Serializable;
import java.util.Date;

public class RequestMessage implements Serializable {
	public Subscriber s;
	public Book b;
	public Borrow borrow;
	public Date returnDate;
}