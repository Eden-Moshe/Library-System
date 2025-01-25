package gui;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import client.SubscriberUI;
import client.UserManager;
import common.Book;
import common.CheckAvailabilityMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class CheckAvailabilityWindowController extends BaseController {
	@FXML
	private Button btnOrderBook= null;
	@FXML
	private Button btnBack= null;
	@FXML
    private TextField bookName;
	
	private String getbookName() {
		return bookName.getText();
	}
	
	
	public void btnCheckAvailability(ActionEvent event) throws Exception {
        String bookName = getbookName();
        UserManager UM = UserManager.getInstance();
        
        String subscriberId = UM.getCurrentSubscriberId();
        if (bookName == null || bookName.trim().isEmpty()) {
            showAlert("Error", "Please enter a book name.");
            return;
        }
        
        // Create message to send to server via UserManager
        CheckAvailabilityMessage message = new CheckAvailabilityMessage();
        message.bookName = bookName;
        
        // Send to UserManager and wait for response
        UM.send(message);
        Object response = UM.inb.getObj();
        
        handleAvailabilityResponse(response, event);
    }
	
	private void handleAvailabilityResponse(Object response, ActionEvent event) {
        if (response instanceof Boolean) {
            boolean isAvailable = (Boolean) response;
            if (isAvailable) {
                openOrderWindow(event);
            } else {
                showAlert("Not Available", "This book is not available for ordering at this time.");
            }
        } else {
            showAlert("Error", "Unexpected response from server");
        }
    }
	
	private void openOrderWindow(ActionEvent event) {
		SubscriberUI.mainController.switchView("/gui/OrderWindow.fxml");

//		
//	    try {
//	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/OrderWindow.fxml"));
//	        Parent root = loader.load();
//	        OrderWindowController controller = loader.getController();
//	        controller.setBookName(getbookName()); // Crucial line: Set the book name
//	        Stage stage = new Stage();
//	        stage.setScene(new Scene(root));
//	        stage.setTitle("Place Order");
//	        stage.show();
//
//	        ((Node) event.getSource()).getScene().getWindow().hide();
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	        showAlert("Error", "Could not open order window");
//	    }
	}
	
	public void getBackBtn(ActionEvent event) throws Exception {
		
		
		SubscriberUI.mainController.goBack();
		
		

    }
	
	//alert massage to present to user. 
	private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
