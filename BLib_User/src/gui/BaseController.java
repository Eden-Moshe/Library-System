package gui;

import client.*;

public class BaseController {

	protected MainController main;

	public void setMainApp(MainController main) {
		
        this.main = main;
        System.out.println("setMainApp main: " + main);
    }
	
	public void onLoad() {
		//nothing by default, let inheritors implement this when needed. 
	}
}
