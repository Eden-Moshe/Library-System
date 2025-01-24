package common;

import java.io.Serializable;
import java.time.LocalDate;

public class BorrowRecord implements Serializable {
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate actualReturnDate;

    public BorrowRecord(LocalDate borrowDate, LocalDate returnDate, LocalDate actualReturnDate) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public LocalDate getActualReturnDate() { return actualReturnDate; }
}