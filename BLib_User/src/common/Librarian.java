/**
 * The {@code Librarian} class represents a librarian in a library system.
 * It contains details such as the librarian's name and ID.
 * 
 * This class implements {@code Serializable} to support object serialization.
 */
package common;

import java.io.Serializable;

public class Librarian implements Serializable {

    /**
     * The name of the librarian.
     */
    private String name;

    /**
     * The unique identifier for the librarian.
     */
    private String librarian_id;

    /**
     * Constructs a new {@code Librarian} instance with the specified name.
     * 
     * @param name the name of the librarian
     */
    public Librarian(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the librarian.
     * 
     * @return the librarian's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the librarian.
     * 
     * @param name the new name of the librarian
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier of the librarian.
     * 
     * @return the librarian's ID
     */
    public String getLibrarian_id() {
        return librarian_id;
    }

    /**
     * Sets the unique identifier of the librarian.
     * 
     * @param librarian_id the new ID of the librarian
     */
    public void setLibrarian_id(String librarian_id) {
        this.librarian_id = librarian_id;
    }
}
