package common;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents the status of a subscriber's account. It tracks details such as 
 * the subscriber, status dates, and whether the status is currently active.
 * Implements Serializable to allow objects of this class to be serialized.
 */
public class AccountStatus implements Serializable {

    /** The Subscriber associated with this account status. */
    public Subscriber sub;

    /** The unique identifier of the subscriber. Can be used instead of the 'sub' object. */
    public String sub_id; //can use either sub or sub_id
    
    /** The date when the account status was set. */
    public Date set_date;
    
    /** The date when the account status ends. */
    public Date end_date;
    
    /** The current status of the subscriber (e.g., 'active', 'frozen'). */
    public String status;
    
    /** A flag indicating whether the status is current (active) or not. */
    public boolean is_current;

    /**
     * Gets the current status of the subscriber's account.
     * 
     * @return the current status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the subscriber's account.
     * 
     * @param status the new status to be set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the Subscriber associated with this account status.
     * 
     * @return the subscriber.
     */
    public Subscriber getSub() {
        return sub;
    }

    /**
     * Sets the Subscriber associated with this account status.
     * 
     * @param sub the subscriber to be associated.
     */
    public void setSub(Subscriber sub) {
        this.sub = sub;
    }

    /**
     * Gets the subscriber's unique identifier.
     * 
     * @return the subscriber ID.
     */
    public String getSub_id() {
        return sub_id;
    }

    /**
     * Sets the subscriber's unique identifier.
     * 
     * @param sub_id the new subscriber ID.
     */
    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    /**
     * Gets the date when the account status was set.
     * 
     * @return the date the status was set.
     */
    public Date getSet_date() {
        return set_date;
    }

    /**
     * Sets the date when the account status was set.
     * 
     * @param set_date the new set date.
     */
    public void setSet_date(Date set_date) {
        this.set_date = set_date;
    }

    /**
     * Gets the date when the account status ends.
     * 
     * @return the end date of the status.
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * Sets the date when the account status ends.
     * 
     * @param end_date the new end date.
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /**
     * Checks if the account status is current.
     * 
     * @return true if the status is current, false otherwise.
     */
    public boolean isIs_current() {
        return is_current;
    }

    /**
     * Sets whether the account status is current or not.
     * 
     * @param is_current true if the status is current, false otherwise.
     */
    public void setIs_current(boolean is_current) {
        this.is_current = is_current;
    }
}
