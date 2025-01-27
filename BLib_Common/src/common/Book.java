/**
 * The {@code Book} class represents a book in a library system.
 * It includes information about the book's barcode, name, genre, description,
 * shelf location, availability status, and return date.
 * 
 * This class implements {@code Serializable} to support object serialization.
 */
package common;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {
    /**
     * The barcode of the book.
     */
    private String barcode;

    /**
     * The name of the book.
     */
    private String bookName;

    /**
     * The genre of the book.
     */
    private String bookGenre;

    /**
     * A description of the book.
     */
    private String bookDesc;

    /**
     * The location of the book on the shelf.
     */
    private String shelf_location;

    /**
     * Indicates whether the book is available.
     */
    private boolean book_available;

    /**
     * The return date of the book, if applicable.
     */
    private Date returnDate;

    /**
     * Constructs a new {@code Book} instance with the specified details.
     *
     * @param barcode        the barcode of the book
     * @param bookName       the name of the book
     * @param bookGenre      the genre of the book
     * @param bookDesc       a description of the book
     * @param shelf_location the location of the book on the shelf
     * @param book_available the availability status of the book
     * @param returnDate     the return date of the book
     */
    public Book(String barcode, String bookName, String bookGenre, String bookDesc,
                String shelf_location, boolean book_available, Date returnDate) {
        this.barcode = barcode;
        this.bookName = bookName;
        this.bookGenre = bookGenre;
        this.bookDesc = bookDesc;
        this.shelf_location = shelf_location;
        this.book_available = book_available;
        this.returnDate = returnDate;
    }

    /**
     * Gets the barcode of the book.
     *
     * @return the barcode of the book
     */
    public String getBookBarcode() {
        return barcode;
    }

    /**
     * Sets the barcode of the book.
     *
     * @param barcode the new barcode of the book
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * Gets the name of the book.
     *
     * @return the name of the book
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Sets the name of the book.
     *
     * @param bookName the new name of the book
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * Gets the genre of the book.
     *
     * @return the genre of the book
     */
    public String getBookGenre() {
        return bookGenre;
    }

    /**
     * Sets the genre of the book.
     *
     * @param bookGenre the new genre of the book
     */
    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    /**
     * Gets the description of the book.
     *
     * @return the description of the book
     */
    public String getBookDesc() {
        return bookDesc;
    }

    /**
     * Sets the description of the book.
     *
     * @param bookDesc the new description of the book
     */
    public void setBookDecs(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    /**
     * Gets the shelf location of the book.
     *
     * @return the shelf location of the book
     */
    public String getPlaceOnShelf() {
        return shelf_location;
    }

    /**
     * Sets the shelf location of the book.
     *
     * @param placeOnShelf the new shelf location of the book
     */
    public void setPlaceOnShelf(String placeOnShelf) {
        this.shelf_location = placeOnShelf;
    }

    /**
     * Checks whether the book is available.
     *
     * @return {@code true} if the book is available, {@code false} otherwise
     */
    public boolean isBookAvailable() {
        return book_available;
    }

    /**
     * Sets the availability status of the book.
     *
     * @param book_available the new availability status of the book
     */
    public void setBookAvailable(boolean book_available) {
        this.book_available = book_available;
    }

    /**
     * Gets the return date of the book.
     *
     * @return the return date of the book
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date of the book.
     *
     * @param returnDate the new return date of the book
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
