package common;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Borrow implements Serializable{
	public Subscriber s;
	public Book bo;
    private Date borrowDate;
    private Date returnDate;
    //add librarian id
    //private Librarian librarian_id



    // Constructor that initializes borrowDate and calculates returnDate
    public Borrow(Subscriber s, Date borrowDate, Date returnDate) {
        this.s = s;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
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
