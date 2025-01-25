package common;

import java.io.Serializable;

/**
 * Represents a message for searching a book.
 * This class contains the book's name, genre, and description for the search query.
 */
public class SearchMessage implements Serializable {

    /** The name of the book to search for. */
    public String bookName;

    /** The genre of the book to search for. */
    public String bookGenre;

    /** A brief description of the book to search for. */
    public String bookDescription;

    /**
     * Gets the name of the book to search for.
     * 
     * @return the name of the book.
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Sets the name of the book to search for.
     * 
     * @param bookName the name of the book to set.
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * Gets the genre of the book to search for.
     * 
     * @return the genre of the book.
     */
    public String getBookGenre() {
        return bookGenre;
    }

    /**
     * Sets the genre of the book to search for.
     * 
     * @param bookGenre the genre of the book to set.
     */
    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    /**
     * Gets the description of the book to search for.
     * 
     * @return the description of the book.
     */
    public String getBookDescription() {
        return bookDescription;
    }

    /**
     * Sets the description of the book to search for.
     * 
     * @param bookDescription the description of the book to set.
     */
    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
}
