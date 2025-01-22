package common;

import java.io.Serializable;
import java.util.Date;


public class Book implements Serializable {
	private String bookName,bookGenre,barcode,shelf_location, bookDesc;
	private boolean book_available;
	private Date returnDate;
	
    // Constructor
    public Book(String bookName, String bookGenre, String barcode, String shelf_location, String bookDesc, boolean book_available, Date returnDate) {
        this.bookName = bookName;
        this.bookGenre = bookGenre;
        this.barcode=  barcode;
        this.bookDesc=bookDesc;
        this.shelf_location = shelf_location;
        this.book_available = book_available;
        this.returnDate = returnDate;
    }

    // Getter for bookName
    public String getBookDesc() {
        return bookDesc;
    }

    
    // Getter for bookName
    public String getBookName() {
        return bookName;
    }


    // Getter for bookSubject
    public String getBookGenre() {
        return bookGenre;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    
    // Getter for barcode
    public String getBookBarcode() {
        return barcode;
    }

    // Getter and Setter for placeOnShelf
    public String getPlaceOnShelf() {
        return shelf_location;
    }

    public void setPlaceOnShelf(String placeOnShelf) {
        this.shelf_location = placeOnShelf;
    }

    // Getter and Setter for bookExists
    public boolean isBookAvailable() {
        return book_available;
    }

    public void setBookAvailable(boolean book_available) {
        this.book_available = book_available;
    }

    // Getter and Setter for ReturnDate
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date ReturnDate) {
        this.returnDate = ReturnDate;
    }
}