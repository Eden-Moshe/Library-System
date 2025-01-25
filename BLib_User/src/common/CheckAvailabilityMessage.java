package common;

import java.io.Serializable;

/**
 * Represents a message for checking the availability of a book.
 * This class is serializable and is used to transmit information about a book's name.
 */
public class CheckAvailabilityMessage implements Serializable {

    /** The name of the book whose availability is being checked. */
    public String bookName;

    /**
     * Gets the name of the book whose availability is being checked.
     * 
     * @return the name of the book.
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Sets the name of the book whose availability is being checked.
     * 
     * @param bookName the name of the book to be set.
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
