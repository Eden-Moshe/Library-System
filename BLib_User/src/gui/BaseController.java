package gui;

import client.*;
import static common.GenericMessage.Action.*;
public class BaseController {

	protected UserManager UM = UserManager.getInstance();

	protected MainController main;

	public void setMainApp(MainController main) {
		
        this.main = main;
        System.out.println("setMainApp main: " + main);
    }
	
	public void onLoad() {
		//nothing by default, let inheritors implement this when needed. 
	}
}
