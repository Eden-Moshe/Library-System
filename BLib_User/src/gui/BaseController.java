package gui;

import client.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static common.GenericMessage.Action.*;
public class BaseController {

	protected UserManager UM = UserManager.getInstance();

//	protected MainController main;
//
//	public void setMainApp(MainController main) {
//		
//        this.main = main;
//        System.out.println("setMainApp main: " + main);
//    }
	
	
	public void barcodeScan(String barcode)
	{
		//implement in each inherited class getText
	}
	
	//good function for every controller class to have. 
//	protected void showAlert(String title, String content) {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
	public void onLoad() {
		//nothing by default, let inheritors implement this when needed. 
	}
}
