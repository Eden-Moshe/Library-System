package common;

import java.io.Serializable;
import java.util.Date;

public class AccountStatus implements Serializable {

	public Subscriber sub;
	public String sub_id;//can use either sub or sub_id
	
	public Date set_date;
	public Date end_date;
	
	public String status;
	
	
	public boolean is_current;
	
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Subscriber getSub() {
		return sub;
	}

	public void setSub(Subscriber sub) {
		this.sub = sub;
	}

	public String getSub_id() {
		return sub_id;
	}

	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}

	public Date getSet_date() {
		return set_date;
	}

	public void setSet_date(Date set_date) {
		this.set_date = set_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public boolean isIs_current() {
		return is_current;
	}

	public void setIs_current(boolean is_current) {
		this.is_current = is_current;
	}

	
	
}
