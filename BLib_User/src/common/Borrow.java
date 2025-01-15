package common;

import java.io.Serializable;
import java.util.Date;

public class Borrow implements Serializable{
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
    
    public int getBorrow_num() {
		return borrow_num;
	}


	public void setBorrow_num(int borrow_num) {
		this.borrow_num = borrow_num;
	}
    

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }


}
