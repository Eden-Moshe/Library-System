package common;

import java.io.Serializable;

/**
 * Represents a message for ordering a book.
 * This class contains the book's name and the subscriber's ID who placed the order.
 */
public class OrderMessage implements Serializable {

    /** The name of the book being ordered. */
    public String bookName;

    /** The ID of the subscriber placing the order. */
    public int subscriberId;

    /**
     * Gets the name of the book being ordered.
     * 
     * @return the name of the book.
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Sets the name of the book being ordered.
     * 
     * @param bookName the name of the book to set.
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * Gets the ID of the subscriber placing the order.
     * 
     * @return the ID of the subscriber.
     */
    public int getSubscriberId() {
        return subscriberId;
    }

    /**
     * Sets the ID of the subscriber placing the order.
     * 
     * @param subscriberId the ID of the subscriber to set.
     */
    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }
}
