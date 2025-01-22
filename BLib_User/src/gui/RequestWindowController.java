package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RequestWindowController {

    @FXML
    private Button btnBack = null;

    @FXML
    private Button btnRequestExtension = null;

    @FXML
    private TextField txtBorrowerId;

    @FXML
    private TextField txtBookBarcode;

    @FXML
    private TextField txtExtendedReturnDate;

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

    private Date getRequestDate() {
        String requestDateText = txtExtendedReturnDate.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(requestDateText); // Parse the String into a Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }

    public void loadResponse(String msg) {
        this.txtResponse.setText(msg);
    }

    @FXML
    private void resetFields(ActionEvent event) {
        txtBorrowerId.clear();
        txtBookBarcode.clear();
        txtExtendedReturnDate.clear();
        txtResponse.clear();
    }

    public void sendExtensionRequest(ActionEvent event) throws SQLException, IOException {
        UserManager UM = UserManager.getInstance();
        String borrowerId = getBorrowerId();
        String bookBarcode = getBookBarcode();
        Date requestDate = getRequestDate();

        if (borrowerId.trim().isEmpty() || bookBarcode.trim().isEmpty() || requestDate == null) {
            System.out.println("All fields are required.");
            return;
        }
        
        

        // Creating a RequestMessage to send for extension
        RequestMessage requestMessage = new RequestMessage();
	    requestMessage.s = new Subscriber(borrowerId, null, null, null, null);
	    requestMessage.b = new Book(bookBarcode, null, null, null, null, false, null);
        requestMessage.returnDate = requestDate;

        UM.send(requestMessage); // Send the message to the server

	    // Wait for the response 
	    // Get the message from MyInbox (set it in handleMessageFromServer)
	    String response = UM.inb.getMessage();  

	    // Set the response text in the TextBox
	    txtResponse.setText(response);  // Display the server's response (e.g., "Extension sent" or error message)

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
