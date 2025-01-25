package common;

import java.io.Serializable;

/**
 * Represents a Subscriber entity with details such as SID, name, phone number, email, and status.
 * This class is used to store and manage information about a subscriber in the system.
 */
public class Subscriber implements Serializable {

    /** The unique identifier for the subscriber. */
    private String SID;

    /** The name of the subscriber. */
    private String name;

    /** The phone number of the subscriber. */
    private String pNumber;

    /** The email address of the subscriber. */
    private String email;

    /** The status of the subscriber (e.g., active, inactive). */
    private String status;

    /** The temporary password for the subscriber. */
    private String newPass;

    /**
     * Default constructor for the Subscriber class.
     */
    public Subscriber() {
        // Default constructor
    }

    /**
     * Constructor to initialize a Subscriber object with the specified details.
     *
     * @param sid    The unique ID of the subscriber.
     * @param name   The name of the subscriber.
     * @param pNumber The phone number of the subscriber.
     * @param email  The email address of the subscriber.
     * @param status The status of the subscriber.
     */
    public Subscriber(String sid, String name, String pNumber, String email, String status) {
        this.SID = sid;
        this.name = name;
        this.pNumber = pNumber;
        this.email = email;
        this.status = status;
    }

    /**
     * Sets the unique ID for the subscriber.
     *
     * @param s The unique ID to set for the subscriber.
     */
    public void setSID(String s) {
        this.SID = s;
    }

    /**
     * Gets the unique ID of the subscriber.
     *
     * @return the unique ID of the subscriber.
     */
    public String getSID() {
        return SID;
    }

    /**
     * Gets the name of the subscriber.
     *
     * @return the name of the subscriber.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the subscriber.
     *
     * @param name The name to set for the subscriber.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the subscriber.
     *
     * @return the phone number of the subscriber.
     */
    public String getPNumber() {
        return pNumber;
    }

    /**
     * Sets the phone number of the subscriber.
     *
     * @param pNumber The phone number to set for the subscriber.
     */
    public void setPNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    /**
     * Gets the email address of the subscriber.
     *
     * @return the email address of the subscriber.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the subscriber.
     *
     * @param email The email to set for the subscriber.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the status of the subscriber (e.g., active, inactive).
     *
     * @return the status of the subscriber.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the subscriber.
     *
     * @param newStatus The new status to set for the subscriber.
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    }

    /**
     * Sets a temporary password for the subscriber.
     *
     * @param pass The temporary password to set.
     */
    public void setTempPass(String pass) {
        this.newPass = pass;
    }

    /**
     * Gets the temporary password for the subscriber.
     *
     * @return the temporary password of the subscriber.
     */
    public String getTemporaryPassword() {
        return newPass;
    }

    /**
     * Returns a string representation of the subscriber's details.
     *
     * @return a string containing the subscriber's ID, name, phone number, and email.
     */
    @Override
    public String toString() {
        return "subscriber_id = " + SID +
               ", subscriber_name ='" + name +
               "', subscriber_phone_number='" + pNumber +  
               "', subscriber_email='" + email + "'";
    }
}
