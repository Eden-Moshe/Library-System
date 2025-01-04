package common;

import java.util.Date;

public class Borrow {
	private Subscriber s;
    private Date borrowDate;
    private Date returnDate;



    // Constructor
    public Borrow(Subscriber s, Date borrowDate, Date returnDate) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.s=s;

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


//    @Override
//    public String toString() {
//        return "Borrow [borrowerId=" + borrowerId + ", borrowerName=" + borrowerName +
//                ", borrowDate=" + borrowDate + ", returnDate=" + returnDate +
//                ", borrowerStatus=" + borrowerStatus + ", borrowerPhoneNumber=" + borrowerPhoneNumber +
//                ", borrowerEmail=" + borrowerEmail + ", bookName=" + bookName + "]";
//    }
}
