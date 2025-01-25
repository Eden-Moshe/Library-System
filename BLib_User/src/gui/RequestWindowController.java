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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Controller class for handling user interactions in the Request Window.
 * This includes handling extension requests, resetting form fields, and navigating back.
 */
public class RequestWindowController extends BaseController {
    
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
    
    /**
     * Retrieves the borrower ID from the text input field.
     * 
     * @return the borrower ID as a string
     */
    private String getBorrowerId() {
        return txtBorrowerId.getText();
    }

    /**
     * Retrieves the book barcode from the text input field.
     * 
     * @return the book barcode as a string
     */
    private String getBookBarcode() {
        return txtBookBarcode.getText();
    }

    /**
     * Retrieves the request date from the DatePicker.
     * Converts the LocalDate to java.sql.Date for the server.
     * 
     * @return the request date as a java.sql.Date, or null if no date is selected
     */
    private Date getRequestDate() {
        LocalDate localDate = datePickerRequestDate.getValue();
        if (localDate == null) {
            return null;
        }
        return java.sql.Date.valueOf(localDate);  // Convert LocalDate to java.sql.Date
    }

    /**
     * Loads and sets the response message in the response text field.
     * 
     * @param msg the response message to display
     */
    public void loadResponse(String msg) {
        this.txtResponse.setText(msg);
    }
    
    /**
     * Resets all fields (borrower ID, book barcode, request date, and response) when the 'reset' button is pressed.
     * 
     * @param event the ActionEvent triggered by the reset button
     */
    @FXML
    private void resetFields(ActionEvent event) {
        txtBorrowerId.clear();
        txtBookBarcode.clear();
        datePickerRequestDate.setValue(null);  // Reset the DatePicker
        txtResponse.clear();
    }
    
    /**
     * Sends the extension request to the server based on user inputs. 
     * It checks that all fields are filled, constructs a RequestMessage, and waits for the server response.
     * 
     * @param event the ActionEvent triggered by the request extension button
     * @throws SQLException if an SQL error occurs while processing the request
     * @throws IOException if an error occurs while sending the request or receiving the response
     */
    public void sendExtensionRequest(ActionEvent event) throws SQLException, IOException {
        UserManager UM = UserManager.getInstance();
        String borrowerId = getBorrowerId();
        String bookBarcode = getBookBarcode();
        Date requestDate = getRequestDate();
        
        // Check no field is left empty
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
    
    /**
     * Handles the back button functionality. 
     * Navigates back to the previous screen.
     * 
     * Reader side goes back to Subscriber Main Menu, 
     * Librarian goes back to Reader's Card.
     * 
     * @param event the ActionEvent triggered by the back button
     * @throws Exception if navigation fails
     */
    @FXML
    private void getBackBtn(ActionEvent event) throws Exception {
        SubscriberUI.mainController.goBack();
    }
}
