package common;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Borrow {
	private Subscriber s;
    private Date borrowDate;
    private Date returnDate;



    // Constructor that initializes borrowDate and calculates returnDate
    public Borrow(Subscriber s, Date borrowDate) {
        this.s = s;
        this.borrowDate = borrowDate;
        this.returnDate = calculateReturnDate(borrowDate); // Automatically set returnDate
    }
    
    // Method to calculate return date 14 days from borrow date
    private Date calculateReturnDate(Date borrowDate) {
        LocalDate localBorrowDate = borrowDate.toInstant()
                                              .atZone(ZoneId.systemDefault())
                                              .toLocalDate();
        LocalDate localReturnDate = localBorrowDate.plusDays(14);
        return Date.from(localReturnDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
        this.returnDate = calculateReturnDate(borrowDate); // Update returnDate if borrowDate changes
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
