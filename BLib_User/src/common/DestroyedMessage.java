package common;

import java.io.Serializable;

/**
 * Represents a message for a destroyed book.
 * This class is serializable and is used to transmit information about a destroyed book's ID and barcode.
 * The message also includes a fetch flag, indicating whether to retrieve the destroyed book's details.
 */
public class DestroyedMessage implements Serializable {

    /** The ID of the book that was destroyed. */
    public String id;

    /** The barcode of the book that was destroyed. */
    public String barcode;

    /** A flag to determine if the details of the destroyed book should be fetched. */
    public boolean fetch;

    /**
     * Gets the ID of the destroyed book.
     * 
     * @return the ID of the destroyed book.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the destroyed book.
     * 
     * @param id the ID of the destroyed book to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the barcode of the destroyed book.
     * 
     * @return the barcode of the destroyed book.
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the barcode of the destroyed book.
     * 
     * @param barcode the barcode of the destroyed book to set.
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Returns the fetch flag that indicates if the destroyed book's details should be retrieved.
     * 
     * @return true if the details should be fetched, false otherwise.
     */
    public boolean isFetch() {
        return fetch;
    }

    /**
     * Sets the fetch flag for whether the destroyed book's details should be retrieved.
     * 
     * @param fetch true if the details should be fetched, false otherwise.
     */
    public void setFetch(boolean fetch) {
        this.fetch = fetch;
    }
}
