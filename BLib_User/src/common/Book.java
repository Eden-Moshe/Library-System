package common;

import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {
	private String barcode, bookName,bookGenre,bookDesc,shelf_location;
	private boolean book_available;
	private Date returnDate;
	
    // Constructor
    public Book(String barcode, String bookName, String bookGenre,String bookDesc, String shelf_location, boolean book_available, Date returnDate) {
        this.barcode=  barcode;
    	this.bookName = bookName;
        this.bookGenre = bookGenre;
        this.bookDesc=bookDesc;
        this.shelf_location = shelf_location;
        this.book_available = book_available;
        this.returnDate = returnDate;
    }
    
    // Getter for barcode
    public String getBookBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    // Getter for bookName
    public String getBookName() {
        return bookName;
    }
    
    
    // Setter for bookName
    public void setBookName(String bookName) {
        this.bookName=bookName;
    }
    

    // Getter for bookGenre
    public String getBookGenre() {
        return bookGenre;
    }
    
    // Setter for bookGenre
    public void setBookGenre(String bookGenre) {
        this.bookGenre=bookGenre;
    }
    
    

    // Getter for bookDesc
    public String getBookDesc() {
        return bookDesc;
    }

    
    // Setter for bookDesc
    public void setBookDecs(String bookDesc) {
        this.bookDesc=bookDesc;
    }
    

    // Getter for placeOnShelf
    public String getPlaceOnShelf() {
        return shelf_location;
    }
    
    // Setter for placeOnShelf
    public void setPlaceOnShelf(String placeOnShelf) {
        this.shelf_location = placeOnShelf;
    }

    // Getter for bookAvailable
    public boolean isBookAvailable() {
        return book_available;
    }

    // Setter for bookAvailable
    public void setBookAvailable(boolean book_available) {
        this.book_available = book_available;
    }

    // Getter for returnDate
    public Date getReturnDate() {
        return returnDate;
    }

    // Setter for returnDate
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}