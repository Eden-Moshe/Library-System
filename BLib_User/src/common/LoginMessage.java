package common;

import java.io.Serializable;

/**
 * Represents a message containing login information for a user.
 * This class is used to transmit a user's ID and password during a login process.
 */
public class LoginMessage implements Serializable {

    /** The ID of the user attempting to log in. */
    public String id;

    /** The password of the user attempting to log in. */
    public String password;

    /**
     * Gets the ID of the user attempting to log in.
     * 
     * @return the ID of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the user attempting to log in.
     * 
     * @param id the ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the password of the user attempting to log in.
     * 
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user attempting to log in.
     * 
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
