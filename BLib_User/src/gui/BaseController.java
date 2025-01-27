package gui;

import client.*;
import common.GenericMessage;
import common.GenericMessage.Action;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static common.GenericMessage.Action.*;
public class BaseController {

	protected UserManager UM = UserManager.getInstance();


	
	
	public void barcodeScan(String barcode)
	{
		//implement in each inherited class getText
	}
	
	
	 /**
     * Display an alert message to the user.
     * 
     * @param title   The title of the alert dialog.
     * @param content The content message displayed in the alert dialog.
     */
	protected void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        
    }
	
	
	public void onLoad() {
		//nothing by default, let inheritors implement this when needed. 
	}
}
