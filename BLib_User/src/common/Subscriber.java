package common;

import java.io.Serializable;

public class Subscriber implements Serializable {
<<<<<<< HEAD
	
	private String SID,name, pNumber, email, status;
	
=======
	private String SID, histID;
	private String name, pNumber, email, status, newPass;
	
>>>>>>> origin/main
	public Subscriber (String sid, String name, String pNumber, String email, String status)
	{
		this.SID=sid;
<<<<<<< HEAD
		this.name=name;
		this.pNumber=pNumber;
		this.email=email;
		this.status = status;
=======
		this.pNumber=pNumber;
		this.name=name;
		this.status = status;
		
>>>>>>> origin/main
		
	}
	public void setSID(String s) 
	{
        this.SID=s;
    }


    public String getSID() {
        return SID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPNumber() {
        return pNumber;
    }

    public void setPNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    // Getter for status
    public String getStatus() {
        return status;
    }
    
    // Setter for status
    public void setStatus(String newStatus) {
        if ("active".equalsIgnoreCase(newStatus) || "frozen".equalsIgnoreCase(newStatus)) {
            this.status = newStatus;
        } else {
            throw new IllegalArgumentException("Invalid status. Only 'active' or 'frozen' are allowed.");
        }
    }
    
    public void setTempPass(String pass) {
        this.newPass = pass;
    }
    public String getTemporaryPassword() {
		return newPass;
		
	}
    
    @Override
    public String toString() {

        return "subscriber_id" +" = " + SID +
        ", subscriber_name ='" + name + 
        ", subscriber_phone_number='" + pNumber +  
        "', subscriber_email='" + email +'\'' ;
    }

	

    
}

	
	


