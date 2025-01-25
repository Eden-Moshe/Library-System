/**
 * The {@code BorrowRecord} class represents a record of a borrowing transaction.
 * It contains details about the borrowing date, the expected return date, 
 * and the actual return date of the borrowed item.
 * 
 * This class implements {@code Serializable} to support object serialization.
 */
package common;

import java.io.Serializable;
import java.time.LocalDate;

public class BorrowRecord implements Serializable {

    /**
     * The date when the item was borrowed.
     */
    private LocalDate borrowDate;

    /**
     * The expected date for returning the borrowed item.
     */
    private LocalDate returnDate;

    /**
     * The actual date the item was returned.
     */
    private LocalDate actualReturnDate;

    /**
     * Constructs a new {@code BorrowRecord} instance with the specified dates.
     * 
     * @param borrowDate       the date when the item was borrowed
     * @param returnDate       the expected return date of the item
     * @param actualReturnDate the actual return date of the item
     */
    public BorrowRecord(LocalDate borrowDate, LocalDate returnDate, LocalDate actualReturnDate) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
    }

    /**
     * Gets the date when the item was borrowed.
     * 
     * @return the borrowing date
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /**
     * Gets the expected return date of the item.
     * 
     * @return the expected return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Gets the actual return date of the item.
     * 
     * @return the actual return date
     */
    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }
}
