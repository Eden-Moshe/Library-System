package common;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a request message containing information about a subscriber,
 * a book, a borrow record, and a return date.
 * This class is serializable to allow object transmission over a network.
 */
public class RequestMessage implements Serializable {

    /**
     * The subscriber associated with the request.
     */
    public Subscriber s;

    /**
     * The book associated with the request.
     */
    public Book b;

    /**
     * The borrow record associated with the request.
     */
    public Borrow borrow;

    /**
     * The return date for the borrowed book.
     */
    public Date returnDate;
}