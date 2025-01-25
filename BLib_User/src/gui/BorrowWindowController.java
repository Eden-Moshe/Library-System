package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import client.SubscriberUI;
import client.UserManager;
import common.Book;
import common.Borrow;
import common.BorrowMessage;
import common.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Controller class for handling the Borrow Window.
 * This class manages user input for borrowing books and communicates with the server.
 */
public class BorrowWindowController extends BaseController {

    UserManager UM = UserManager.getInstance();
    
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
    
    /**
     * Retrieves the user ID entered in the text field.
     * @return the user ID as a String.
     */
    public String getUserID() {
        return txtBorrowerId.getText();
    }
    
    /**
     * Retrieves the book barcode entered in the text field.
     * @return the book barcode as a String.
     */
    private String getBookBarcode() {
        return txtBookBarcode.getText();
    }

    /**
     * Retrieves the borrow date selected in the DatePicker.
     * @return the borrow date as a Date object or null if not selected.
     */
    private Date getBorrowDate() {
        LocalDate borrowDateLocal = dpBorrowDate.getValue();
        if (borrowDateLocal != null) {
            return java.sql.Date.valueOf(borrowDateLocal);
        }
        return null;
    }

    /**
     * Retrieves the return date selected in the DatePicker.
     * @return the return date as a Date object or null if not selected.
     */
    private Date getReturnDate() {
        LocalDate returnDateLocal = dpReturnDate.getValue();
        if (returnDateLocal != null) {
            return java.sql.Date.valueOf(returnDateLocal);
        }
        return null;
    }
    
    /**
     * Displays the server response message in the response text field.
     * @param msg the message to display.
     */
    public void setTextRespose(String msg) {
        this.txtResponse.setText(msg);    
    }
    
    /**
     * Resets all input fields when the 'Reset' button is clicked.
     * @param event the action event triggered by clicking the button.
     */
    @FXML
    private void resetFields(ActionEvent event) {
        txtBorrowerId.clear();
        txtBookBarcode.clear();
        dpBorrowDate.setValue(null);
        dpReturnDate.setValue(null);
        txtResponse.clear();
    }
    
    /**
     * Sends a borrow request to the server using user input.
     * @param event the action event triggered by clicking the 'Borrow' button.
     * @throws SQLException if a database error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public void sendBorrowRequest(ActionEvent event) throws SQLException, IOException {
        // Set Strings and date from user input
        String borrowerId = getUserID();
        String bookBarcode = getBookBarcode();
        Date borrowDate = getBorrowDate();
        Date returnDate = getReturnDate();
        
        // Getting librarian ID from UserManager instance of librarian
        String Librarian_id = UM.librarian.getLibrarian_id();

        // Check no field is left empty
        if (borrowerId.trim().isEmpty() || bookBarcode.trim().isEmpty() || borrowDate == null || returnDate == null) {
            txtResponse.setText("All fields are required.");
            return;
        }
        
        // Check if the return date is more than 2 weeks from the borrow date
        long diffInMillis = returnDate.getTime() - borrowDate.getTime();
        long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
        
        if (diffInDays > 14) {
            txtResponse.setText("Return date can be up to two weeks from borrow date.");
            return;
        }

        // Create BorrowMessage with subscriber ID, book barcode, and new borrow instance
        BorrowMessage fetchMsg = new BorrowMessage();
        fetchMsg.s = new Subscriber(borrowerId, null, null, null, null);
        fetchMsg.b = new Book(bookBarcode, null, null, null, null, false, null);
        fetchMsg.borrow = new Borrow(fetchMsg.s, borrowDate, returnDate);
        fetchMsg.lib_id = Librarian_id;
        
        // Send the BorrowMessage
        UM.send(fetchMsg);
        
        // Wait for the response and display the server's response
        String response = UM.inb.getMessage();  
        txtResponse.setText(response);
    }
    
    /**
     * Returns to the previous page when the 'Back' button is clicked.
     * @param event the action event triggered by clicking the button.
     * @throws Exception if navigation fails.
     */
    public void getBackBtn(ActionEvent event) throws Exception {
        SubscriberUI.mainController.goBack();
    }
}
