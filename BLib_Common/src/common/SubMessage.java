package common;

import java.io.Serializable;

/**
 * Represents a message related to a Subscriber.
 * This class is used to carry information such as the subscriber's details,
 * whether an edit is requested, and specific fields to be updated.
 */
public class SubMessage implements Serializable {

    /** The primary key associated with the subscriber. */
    public String pKey;

    /** Flag indicating whether the subscriber details are to be edited. */
    public boolean editBool;

    /** The subscriber instance containing the details of the subscriber. */
    public Subscriber s;

    /** The password of the subscriber (used for authentication). */
    public String password;

    /** The name of the field to be updated. */
    public String fieldCol;

    /** The new value to be set for the specified field. */
    public String fieldVal;

    /**
     * Gets the primary key associated with the subscriber.
     * 
     * @return the primary key of the subscriber.
     */
    public String getPKey() {
        return pKey;
    }

    /**
     * Sets the primary key associated with the subscriber.
     * 
     * @param pKey the primary key to set.
     */
    public void setPKey(String pKey) {
        this.pKey = pKey;
    }

    /**
     * Gets the flag indicating whether the subscriber details are to be edited.
     * 
     * @return true if the subscriber details are to be edited; false otherwise.
     */
    public boolean isEditBool() {
        return editBool;
    }

    /**
     * Sets the flag indicating whether the subscriber details are to be edited.
     * 
     * @param editBool the flag to set.
     */
    public void setEditBool(boolean editBool) {
        this.editBool = editBool;
    }

    /**
     * Gets the subscriber instance containing the subscriber's details.
     * 
     * @return the subscriber instance.
     */
    public Subscriber getS() {
        return s;
    }

    /**
     * Sets the subscriber instance containing the subscriber's details.
     * 
     * @param s the subscriber instance to set.
     */
    public void setS(Subscriber s) {
        this.s = s;
    }

    /**
     * Gets the password of the subscriber.
     * 
     * @return the password of the subscriber.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the subscriber.
     * 
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the name of the field to be updated for the subscriber.
     * 
     * @return the name of the field to update.
     */
    public String getFieldCol() {
        return fieldCol;
    }

    /**
     * Sets the name of the field to be updated for the subscriber.
     * 
     * @param fieldCol the name of the field to update.
     */
    public void setFieldCol(String fieldCol) {
        this.fieldCol = fieldCol;
    }

    /**
     * Gets the new value to be set for the specified field of the subscriber.
     * 
     * @return the new value for the field.
     */
    public String getFieldVal() {
        return fieldVal;
    }

    /**
     * Sets the new value for the specified field of the subscriber.
     * 
     * @param fieldVal the new value for the field.
     */
    public void setFieldVal(String fieldVal) {
        this.fieldVal = fieldVal;
    }
}
