package client;

import common.Subscriber;

/**
 * The MyInbox class represents a message inbox for a subscriber. 
 * It stores messages either as strings or as objects, and tracks the subscriber to whom the messages are assigned.
 */
public class MyInbox {

    /** Default message when no new message is received. */
    private String immediate_message = "no new message";

    /** Object to store a message that is not a string. */
    private Object objMsg;

    /** Array to store multiple messages (if needed). */
    private Object arr[];

    /** Subscriber associated with this inbox. */
    private Subscriber handleSubscriber;

    /**
     * Sets a new message to the inbox.
     * If the message is a String, it updates the `immediate_message`. 
     * If the message is any other object, it assigns it to `objMsg`.
     *
     * @param msg The message to set. Can be either a String or an Object.
     */
    public void setMessage(Object msg) {
        if (msg instanceof String) {
            immediate_message = (String) msg;
        } else {
            objMsg = msg;
        }
    }

    /**
     * Associates a subscriber with this inbox. 
     * This can be used to track which subscriber is handling the inbox.
     *
     * @param handle The Subscriber object to associate with the inbox.
     */
    public void handledSubscriber(Subscriber handle) {
        handleSubscriber = handle;
    }

    /**
     * Gets the subscriber associated with this inbox.
     *
     * @return The Subscriber object assigned to this inbox.
     */
    public Subscriber getHandledSubscriber() {
        return handleSubscriber;
    }

    /**
     * Retrieves the stored object message. 
     * This is a one-time use method as the object is not stored after it is retrieved.
     *
     * @return The object message stored in the inbox.
     */
    public Object getObj() {
        return objMsg;
    }

    /**
     * Retrieves and clears the immediate message from the inbox.
     * After the message is retrieved, the `immediate_message` is set to null.
     *
     * @return The immediate message stored in the inbox.
     */
    public String getMessage() {
        String temp = immediate_message;
        immediate_message = null;
        return temp;
    }

}
