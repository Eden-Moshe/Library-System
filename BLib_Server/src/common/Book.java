package common;

import java.util.Date;

public class Book {
	private String bookName,bookSubject,barcode;
	private int placeOnShelf;
	private boolean bookExists;
	private Date closestReturnDate;
	
    // Constructor
    public Book(String bookName, String bookSubject, String barcode, int placeOnShelf, boolean bookExists, Date closestReturnDate) {
        this.bookName = bookName;
        this.bookSubject = bookSubject;
        this.barcode=  barcode;
        this.placeOnShelf = placeOnShelf;
        this.bookExists = bookExists;
        this.closestReturnDate = closestReturnDate;
    }

    // Getter for bookName
    public String getBookName() {
        return bookName;
    }


    // Getter for bookSubject
    public String getBookSubject() {
        return bookSubject;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    
    // Getter for barcode
    public String getBookBarcode() {
        return barcode;
    }

    // Getter and Setter for placeOnShelf
    public int getPlaceOnShelf() {
        return placeOnShelf;
    }

    public void setPlaceOnShelf(int placeOnShelf) {
        this.placeOnShelf = placeOnShelf;
    }

    // Getter and Setter for bookExists
    public boolean isBookExists() {
        return bookExists;
    }

    public void setBookExists(boolean bookExists) {
        this.bookExists = bookExists;
    }

    // Getter and Setter for closestReturnDate
    public Date getClosestReturnDate() {
        return closestReturnDate;
    }

    public void setClosestReturnDate(Date closestReturnDate) {
        this.closestReturnDate = closestReturnDate;
    }
}
