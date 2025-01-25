package common;

import java.io.Serializable;

/**
 * Represents an inbox message for a librarian.
 * This class is serializable and holds the information about a message received by a librarian, including the sender, the message content, 
 * and whether the message is new.
 */
public class InboxMessage implements Serializable {

    /** The unique identifier for the message. */
    public String message_id;

    /** The ID of the librarian who is receiving the message. */
    public String librarian_id;

    /** The librarian instance associated with the message. Either the librarian_id or the librarian instance can be used. */
    public Librarian librarian;

    /** The sender of the message. */
    public String sender;

    /** The content of the message. */
    public String message;

    /** Flag indicating whether the message is new or not. */
    public boolean is_new;

    /**
     * Gets the sender of the message.
     * 
     * @return the sender of the message.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender of the message.
     * 
     * @param sender the sender to set for the message.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the content of the message.
     * 
     * @return the content of the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the content of the message.
     * 
     * @param message the content to set for the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Checks if the message is new.
     * 
     * @return true if the message is new, false otherwise.
     */
    public boolean isIs_new() {
        return is_new;
    }

    /**
     * Sets the new status of the message.
     * 
     * @param is_new true if the message is new, false otherwise.
     */
    public void setIs_new(boolean is_new) {
        this.is_new = is_new;
    }
}
