package common;

import java.io.Serializable;

/**
 * Represents a message related to a book, used for tracking and managing book-related information.
 * Implements Serializable to allow objects of this class to be serialized for saving or transferring.
 */
public class BookMessage implements Serializable {

    /** The primary key for identifying the book message. */
    public String pKey;

    /** The name associated with the book message. */
    public String name;

    /** The topic or category related to the book message. */
    public String topic;

    /** A flag indicating whether the book message is editable. */
    public boolean editBool;
}
