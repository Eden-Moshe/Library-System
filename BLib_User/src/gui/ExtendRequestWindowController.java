package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import client.SubscriberUI;
import client.UserManager;
import common.Book;
import common.RequestMessage;
import common.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExtendRequestWindowController extends BaseController{
	
	
	//setting role of user as Librarian
    private String userRole; // "Reader" or "Librarian"

    public void setUserRole(String role) {
        this.userRole = role;
    }

    @FXML
    private Button btnBack = null;

    @FXML
    private Button btnRequestExtension = null;

    @FXML
    private TextField txtBorrowerId;

    @FXML
    private TextField txtBookBarcode;

    @FXML
    private DatePicker datePickerRequestDate;

    @FXML
    private TextField txtResponse;

    @FXML
    private Button btnReset;
    
    //get borrower id from text inserted
    private String getBorrowerId() {
        return txtBorrowerId.getText();
    }
    //get book barcode from text inserted
    private String getBookBarcode() {
        return txtBookBarcode.getText();
    }

    // Method to get the request date from the DatePicker
    private Date getRequestDate() {
        LocalDate localDate = datePickerRequestDate.getValue();
        if (localDate == null) {
            return null;
        }
        return java.sql.Date.valueOf(localDate);  // Convert LocalDate to java.sql.Date
    }

    public void loadResponse(String msg) {
        this.txtResponse.setText(msg);
    }
    
	//resets all fields when pressing 'reset'
    @FXML
    private void resetFields(ActionEvent event) {
        //txtBorrowerId.clear();
        txtBookBarcode.clear();
        datePickerRequestDate.setValue(null);  // Reset the DatePicker
        txtResponse.clear();
    }
    
	//method that sends extension request message to server with user inputs
    public void sendExtensionRequest(ActionEvent event) throws SQLException, IOException {
        UserManager UM = UserManager.getInstance();
        //implement that user id is inserted automatically in filed ID 
        //if (UM.s1 == null) ---> librarian entered
        //subscriber entered
        String borrowerId = getBorrowerId();
        String bookBarcode = getBookBarcode();
        Date requestDate = getRequestDate();
        
	    //check no field is left empty
        if (borrowerId.trim().isEmpty() || bookBarcode.trim().isEmpty() || requestDate == null) {
        	 txtResponse.setText("All fields are required.");
            return;
        }
        
        

        // Creating a RequestMessage to send for extension with:
	    // 1. Subscriber id
	    // 2. Book's barcode
	    // 3. new return date
        RequestMessage requestMessage = new RequestMessage();
	    requestMessage.s = new Subscriber(borrowerId, null, null, null, null);
	    requestMessage.b = new Book(bookBarcode, null, null, null, null, false, null);
        requestMessage.returnDate = requestDate;
        
        // Send the message to the server
        UM.send(requestMessage); 

	    // Wait for the response 
	    // Get the message from MyInbox
	    String response = UM.inb.getMessage();  

	    // Set the response text in the TextBox
	    txtResponse.setText(response);

    }
    
	//method returns to previous page when pressing 'Back' button
    //Reader side goes back to Subscriber Main Menu
    //Librarian goes back to Reader's Card
    public void getBackBtn(ActionEvent event) throws Exception {
    	
    	
    	SubscriberUI.mainController.goBack();
    	
    	
//        try {
//            // Close the current window
//            ((Node) event.getSource()).getScene().getWindow().hide();
//
//            FXMLLoader loader;
//            String pageTitle;
//
//            // Check the user role and load the correct page
//            if ("Reader".equalsIgnoreCase(userRole)) {
//                // Reader should go back to Subscriber Main Menu
//                loader = new FXMLLoader(getClass().getResource("/gui/SubscriberMainMenu.fxml"));
//                pageTitle = "Subscriber Main Menu";
//            } else if ("Librarian".equalsIgnoreCase(userRole)) {
//                // Librarian should go back to ViewUserInfo
//                loader = new FXMLLoader(getClass().getResource("/gui/ViewUserInfo.fxml"));
//                pageTitle = "View User Info";
//            } else {
//                // Default behavior if the role is unrecognized
//                throw new IllegalArgumentException("Unknown user role: " + userRole);
//            }
//
//            // Load the appropriate page
//            Pane root = loader.load();
//
//            // Set up the new stage
//            Stage stage = new Stage();
//            Scene scene = new Scene(root);
//            stage.setTitle(pageTitle);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to load the respective page.");
//        }
    }
}
