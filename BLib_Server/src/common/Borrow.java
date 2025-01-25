/**
 * The {@code Borrow} class represents a borrowing transaction in a library system.
 * It contains details about the subscriber, the book being borrowed, the borrowing date,
 * the expected return date, the borrow number, and the librarian handling the transaction.
 * 
 * This class implements {@code Serializable} to support object serialization.
 */
package common;

import java.io.Serializable;
import java.util.Date;

public class Borrow implements Serializable {
    /**
     * The subscriber involved in the borrowing transaction.
     */
    public Subscriber s;

    /**
     * The book being borrowed.
     */
    public Book bo;

    /**
     * The date when the book was borrowed.
     */
    private Date borrowDate;

    /**
     * The expected date for returning the book.
     */
    private Date returnDate;

    /**
     * The unique number associated with the borrowing transaction.
     */
    private int borrow_num;

    /**
     * The librarian handling the borrowing transaction.
     */
    public Librarian l;

    /**
     * Constructs a new {@code Borrow} instance with the specified subscriber, borrow date, and return date.
     * 
     * @param s          the subscriber involved in the transaction
     * @param borrowDate the date the book was borrowed
     * @param returnDate the expected return date of the book
     */
    public Borrow(Subscriber s, Date borrowDate, Date returnDate) {
        this.s = s;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    /**
     * Gets the name of the book being borrowed.
     * 
     * @return the name of the book
     */
    public String getBookName() {
        return bo.getBookName();
    }

    /**
     * Gets the unique number associated with the borrowing transaction.
     * 
     * @return the borrow number
     */
    public int getBorrow_num() {
        return borrow_num;
    }

    /**
     * Sets the unique number associated with the borrowing transaction.
     * 
     * @param borrow_num the new borrow number
     */
    public void setBorrow_num(int borrow_num) {
        this.borrow_num = borrow_num;
    }

    /**
     * Gets the date when the book was borrowed.
     * 
     * @return the borrow date
     */
    public Date getBorrowDate() {
        return borrowDate;
    }

    /**
     * Sets the date when the book was borrowed.
     * 
     * @param borrowDate the new borrow date
     */
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * Gets the expected return date of the book.
     * 
     * @return the return date
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the expected return date of the book.
     * 
     * @param returnDate the new return date
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
