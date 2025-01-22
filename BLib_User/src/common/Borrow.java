package common;

import java.io.Serializable;
import java.util.Date;

public class Borrow implements Serializable{
	//class Borrow holds the following values to store as borrow occurs
	public Subscriber s;
	public Book bo;
    private Date borrowDate;
    private Date returnDate;
    private int borrow_num;
	public Librarian l;
    


	// Constructor that initializes borrowDate and calculates returnDate
    public Borrow(Subscriber s, Date borrowDate, Date returnDate) {
        this.s = s;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
    
    public String getBookName()
    {
    	return bo.getBookName();
    }
    
    //getter for borrow number
    public int getBorrow_num() {
		return borrow_num;
	}

    //setter for borrow number
	public void setBorrow_num(int borrow_num) {
		this.borrow_num = borrow_num;
	}
    
	//getter for borrow date
    public Date getBorrowDate() {
        return borrowDate;
    }
    //setter for borrow date
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
    //getter for return date
    public Date getReturnDate() {
        return returnDate;
    }
    //setter for return dated
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }


}
