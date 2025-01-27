package common;

import java.io.Serializable;
import java.util.Date;

public class Extensions implements Serializable {

	public String librarian_name, book_name;
	public Date day_of_extension, new_return_date;
	
	
	public String getLibrarian_name() {
		
		return librarian_name;
	}
	public void setLibrarian_name(String librarian_name) {
		this.librarian_name = librarian_name;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public Date getDay_of_extension() {
		return day_of_extension;
	}
	public void setDay_of_extension(Date day_of_extension) {
		this.day_of_extension = day_of_extension;
	}
	public Date getNew_return_date() {
		return new_return_date;
	}
	public void setNew_return_date(Date new_return_date) {
		this.new_return_date = new_return_date;
	}
	
	
	
	
}
