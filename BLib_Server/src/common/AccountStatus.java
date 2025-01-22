package common;

import java.io.Serializable;
import java.util.Date;

public class AccountStatus implements Serializable {

	public Subscriber sub;
	public String sub_id;//can use either sub or sub_id
	
	public Date set_date;
	public Date end_date;
	
	public boolean is_current;
	
}
