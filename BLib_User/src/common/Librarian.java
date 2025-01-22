package common;

import java.io.Serializable;

public class Librarian implements Serializable {

	private String name,librarian_id;
	
	
	public Librarian(String name) {
		this.name = name;
		//this.librarian_id = librarian_id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLibrarian_id() {
		return librarian_id;
	}

	public void setLibrarian_id(String librarian_id) {
		this.librarian_id = librarian_id;
	}




}
