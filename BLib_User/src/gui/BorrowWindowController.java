package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import client.MyInbox;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BorrowWindowController {

	
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
    private TextField txtBorrowDate;

    @FXML
    private TextField txtReturnDate;
    
    
    @FXML
    private TextField txtResponse;
    
    @FXML
    private Button btnReset;

    private String getBorrowerId() {
        return txtBorrowerId.getText();
    }

    private String getBookBarcode() {
        return txtBookBarcode.getText();
    }

    
    

    
    private Date getBorrowDate() {
        String borrowDateText = txtBorrowDate.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Use the format that matches your input
        try {
            return dateFormat.parse(borrowDateText); // Parse the String into a Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }
    


    private Date getReturnDate() {
        String returnDateText = txtReturnDate.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Use the format that matches your input
        try {
            return dateFormat.parse(returnDateText); // Parse the String into a Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }
    
    
	public void setTextRespose(String msg) {
		this.txtResponse.setText(msg);
		
	}
	

	
	@FXML
	private void resetFields(ActionEvent event) {
	    txtBorrowerId.clear();
	    txtBookBarcode.clear();
	    txtBorrowDate.clear();
	    txtReturnDate.clear();
	    txtResponse.clear();
	}
	
	
	public void sendBorrowRequest(ActionEvent event) throws SQLException, IOException {
	    UserManager UM = UserManager.getInstance();
	    String borrowerId = getBorrowerId();
	    String bookBarcode = getBookBarcode();
	    Date borrowDate = getBorrowDate();
	    Date returnDate = getReturnDate();

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
	    fetchMsg.s = new Subscriber(borrowerId, null, null, null, null);
	    fetchMsg.b = new Book(bookBarcode, null, null, null, null, false, null);
	    fetchMsg.borrow = new Borrow(fetchMsg.s, borrowDate,returnDate);
	    // Send the BorrowMessage
	    UM.send(fetchMsg);
	    
	    // Wait for the response 
	    // Get the message from MyInbox (set it in handleMessageFromServer)
	    String response = UserManager.inb.getMessage();  

	    // Set the response text in the TextBox
	    txtResponse.setText(response);  // Display the server's response (e.g., "Borrow created" or error message)
	    
	    
	}

    public void getBackBtn(ActionEvent event) throws Exception {
        try {
            // Close the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Load the previous screen (Main Menu)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainMenu.fxml"));
            Pane root = loader.load();

            // Set up the new stage
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load MainMenu.fxml.");
       }
    }
}



