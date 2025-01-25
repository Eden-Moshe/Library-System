package common;

import java.io.Serializable;

/**
 * Represents a record of a destroyed book in the system.
 * This class stores the ID and barcode of the destroyed book.
 * It is serializable to allow transferring objects over the network or saving them to a file.
 */
public class DestRecord implements Serializable {

    /** The ID of the destroyed book. */
    private String id;
    
    /** The barcode of the destroyed book. */
    private String barcode;

    /**
     * Constructs a new DestRecord with the specified ID and barcode.
     * 
     * @param id the ID of the destroyed book
     * @param barcode the barcode of the destroyed book
     */
    public DestRecord(String id, String barcode) {
        this.id = id;
        this.barcode = barcode;
    }

    /**
     * Gets the ID of the destroyed book.
     * 
     * @return the ID of the destroyed book
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the destroyed book.
     * 
     * @param id the ID to set for the destroyed book
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the barcode of the destroyed book.
     * 
     * @return the barcode of the destroyed book
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the barcode of the destroyed book.
     * 
     * @param barcode the barcode to set for the destroyed book
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Gets the ID property of the destroyed book for UI binding.
     * 
     * @return the ID of the destroyed book
     */
    public String getIdProperty() {
        return id;
    }

    /**
     * Gets the barcode property of the destroyed book for UI binding.
     * 
     * @return the barcode of the destroyed book
     */
    public String getBarcodeProperty() {
        return barcode;
    }
}
