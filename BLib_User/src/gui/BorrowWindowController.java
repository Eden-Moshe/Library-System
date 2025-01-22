package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import client.UserManager;
import common.Book;
import common.Borrow;
import common.BorrowMessage;
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

public class BorrowWindowController {

    UserManager UM = UserManager.getInstance();
    
	private Borrow b;
    @FXML
    private Button btnBack = null;

    @FXML
    private Button btnBorrow = null;

    @FXML
    private TextField txtBorrowerId;

    @FXML
    private TextField txtBookBarcode;

    @FXML
    private DatePicker dpBorrowDate;

    @FXML
    private DatePicker dpReturnDate;
    
    
    @FXML
    private TextField txtResponse;
    
    @FXML
    private Button btnReset;
    
    //setting user ID in proper textbox
    String userID = UM.s1.getSID();
    
    
    //display User ID in Borrower ID textbox
	public void setUserID() {
		this.txtBorrowerId.setText(userID);	
	}
    
    //get book barcode from text inserted
    private String getBookBarcode() {
        return txtBookBarcode.getText();
    }

    
    // Get borrow date from DatePicker
    private Date getBorrowDate() {
        LocalDate borrowDateLocal = dpBorrowDate.getValue(); // Get the selected date from DatePicker
        if (borrowDateLocal != null) {
            return java.sql.Date.valueOf(borrowDateLocal); // Convert LocalDate to Date
        }
        return null; // Return null if no date is selected
    }

    // Get return date from DatePicker
    private Date getReturnDate() {
        LocalDate returnDateLocal = dpReturnDate.getValue(); // Get the selected date from DatePicker
        if (returnDateLocal != null) {
            return java.sql.Date.valueOf(returnDateLocal); // Convert LocalDate to Date
        }
        return null; // Return null if no date is selected
    }
    
    //display text returned from server
	public void setTextRespose(String msg) {
		this.txtResponse.setText(msg);	
	}
	

	//resets all fields when pressing 'reset'
	@FXML
	private void resetFields(ActionEvent event) {
	    txtBookBarcode.clear();
	    dpBorrowDate.setValue(null);  // Reset the DatePicker
	    dpReturnDate.setValue(null);  // Reset the DatePicker
	    txtResponse.clear();
	}
	
	//method that sends borrow message to server with user inputs
	public void sendBorrowRequest(ActionEvent event) throws SQLException, IOException {
	    //set Strings and date from user input
	    String borrowerId = userID;
	    String bookBarcode = getBookBarcode();
	    Date borrowDate = getBorrowDate();
	    Date returnDate = getReturnDate();
	    
	    //getting librarian id from UserManager instance of librarian
	    String Librarian_id = UM.librarian.getLibrarian_id();

	    //check no field is left empty
	    if (borrowerId.trim().isEmpty() || bookBarcode.trim().isEmpty() || borrowDate == null || returnDate == null) {
	        txtResponse.setText("All fields are required.");
	        return;
	    }
	    
	    // Check if the return date is more than 2 weeks from the borrow date
	    long diffInMillis = returnDate.getTime() - borrowDate.getTime();
	    long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);  // Convert milliseconds to days

	    if (diffInDays > 14) {
	        txtResponse.setText("Return date can be up to two weeks from borrow date.");
	        return;
	    }

	    // Create BorrowMessage with:
	    // 1. Subscriber id
	    // 2. Book's barcode
	    // 3. new instance of Borrow with wanted values
	    BorrowMessage fetchMsg = new BorrowMessage();
	    fetchMsg.s=UM.s1;
//	    fetchMsg.s = new Subscriber(borrowerId, null, null, null, null);
	    fetchMsg.b = new Book(bookBarcode, null, null, null, null, false, null);
	    fetchMsg.borrow = new Borrow(fetchMsg.s, borrowDate,returnDate);
	    fetchMsg.lib_id = Librarian_id;
	    
	    // Send the BorrowMessage
	    UM.send(fetchMsg);
	    
	    // Wait for the response 
	    // Get the message from MyInbox 
	    String response = UM.inb.getMessage();  

	    // Set the response text in the TextBox
	    txtResponse.setText(response);  // Display the server's response (e.g., "Borrow created" or error message)
	    
	}
	
	//method returns to previous page when pressing 'Back' button
    public void getBackBtn(ActionEvent event) throws Exception {
    	//goes back to Readers card where librarian pressed "Extend Borrow"
        try {
            // Close the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Load the previous screen (Main Menu)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ViewUserInfo.fxml"));
            Pane root = loader.load();

            // Set up the new stage
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Reader's Card");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load ViewUserInfo.fxml.");
       }
    }
}



