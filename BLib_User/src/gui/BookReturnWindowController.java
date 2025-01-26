package gui;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import client.MyInbox;
import client.SubscriberUI;
import client.UserManager;
import common.Book;
import common.Borrow;
import common.BorrowMessage;
import common.BookReturnMessage;
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
import javafx.scene.Parent;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller class for managing the Book Return window.
 * <p>
 * This controller handles the actions and events for the book return process.
 * It includes functionality for returning a book, resetting fields, and navigating back to the main menu.
 * </p>
 */
public class BookReturnWindowController extends BaseController {
    
    /**
     * Button that navigates the user back to the previous screen (likely the main menu).
     * The user can return to the main menu without making any changes.
     */
    @FXML
    private Button btnBack;

    /**
     * Button that triggers the book return process.
     * Once clicked, it initiates the logic to return the book and update the UI accordingly.
     */
    @FXML
    private Button btnBookReturn;

    /**
     * Button that resets all form fields.
     * This clears the user's input and resets the interface to its default state.
     */
    @FXML
    private Button btnreset;

    /**
     * TextField for the user to input the Borrow Number.
     * This is a reference number that identifies the borrowing transaction.
     */
    @FXML
    private TextField txtBorrowNumber;

    /**
     * TextField for the user to input the Borrower ID.
     * The ID is used to associate the user with their borrowed books in the system.
     */
    @FXML
    private TextField txtBorrowerId;

    /**
     * TextField to display a response or error message after a user action (such as returning a book).
     */
    @FXML
    private TextField txtResponse;

    /**
     * TextField for the user to input the Book Barcode.
     * This is used to uniquely identify the book being returned.
     */
    @FXML
    private TextField txtbookBarcode;
    
    /**
     * The logo image displayed at the top of the Book Return window.
     * It is used to visually identify the library system.
     */
    @FXML
    private ImageView logo; 

    /**
     * This method is called when the 'Reset' button is clicked.
     * It resets the fields (Borrower ID, Borrow Number, Book Barcode, etc.) to their initial state.
     */
    @FXML
    public void resetFields(ActionEvent event) {
        txtBorrowerId.clear();
        txtBorrowNumber.clear();
        txtbookBarcode.clear();
        txtResponse.clear();
    }

    /**
     * Retrieves the text entered in the Borrower ID field.
     * <p>
     * This method returns the value that the user has input in the Borrower ID TextField.
     * It is typically used to fetch the Borrower ID when performing operations like validating or processing a book return.
     * </p>
     * 
     * @return The text from the Borrower ID TextField as a String.
     */
    public String getBorrowerId() {
        return txtBorrowerId.getText();
    }
    
    /**
     * Retrieves the text entered in the Borrow Number field.
     * <p>
     * This method returns the value that the user has input in the Borrow Number TextField.
     * It is typically used to fetch the Borrow Number when performing operations like validating or processing a book return.
     * </p>
     * 
     * @return The text from the Borrow Number TextField as a String.
     */
    public String getBorrowNumber() {
        return txtBorrowNumber.getText();
    }

    /**
     * Retrieves the text entered in the Book Barcode field.
     * <p>
     * This method returns the value that the user has input in the Book Barcode TextField.
     * It is typically used to fetch the Book Barcode when performing operations like validating or processing a book return.
     * </p>
     * 
     * @return The text from the Book Barcode TextField as a String.
     */
    public String getbookBarcode() {
        return txtbookBarcode.getText();
    }

    /**
     * Sets a response message in the Response TextField.
     * <p>
     * This method updates the TextField used to display response messages (such as success or error messages) to the user.
     * It is useful for providing feedback after a book return operation.
     * </p>
     * 
     * @param msg The message to be displayed in the Response TextField.
     */
    public void setTextRespose(String msg) {
        this.txtResponse.setText(msg);    
    }
    
    /**
     * Event handler for mouse enter event on buttons.
     * This provides visual feedback, such as changing the button's background color on hover.
     */
    // Event handler for mouse entering the button
    @FXML
    public void onMouseEnteredButton(Event event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #a37a2c; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 150px; -fx-min-width: 150px; -fx-max-width: 150px; -fx-font-family: Arial; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20;");
    }
    
    /**
     * Event handler for mouse exit event on buttons.
     * This restores the original button background color when the mouse exits.
     */
    // Event handler for mouse exiting the button
    @FXML
    public void onMouseExitedButton(Event event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #b59b53; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 150px; -fx-min-width: 150px; -fx-max-width: 150px; -fx-font-family: Arial; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 10 20;");
    }
    
    /**
     * Event handler for mouse enter event on text fields.
     * This can be used to provide visual feedback (e.g., change of background color).
     */
    // Event handler for mouse entering the text field
    @FXML
    public void onMouseEnteredTextField(Event event) {
        TextField textField = (TextField) event.getSource();
        textField.setStyle("-fx-background-color: #3a4d51; -fx-text-fill: white; -fx-font-size: 14px;");
    }
    
    /**
     * Event handler for mouse exit event on text fields.
     * This can be used to reset visual feedback (e.g., revert background color).
     */
    // Event handler for mouse exiting the text field
    @FXML
    public void onMouseExitedTextField(Event event) {
        TextField textField = (TextField) event.getSource();
        textField.setStyle("-fx-background-color: #4b636e; -fx-text-fill: white; -fx-font-size: 14px;");
    }
    /**
     * This method is called when the 'Return Book' button is clicked.
     * It processes the book return logic by validating the inputs and performing necessary actions.
     */
	public void btnBookReturn(ActionEvent event) throws SQLException, IOException {
		BookReturnMessage fetchMsg = new BookReturnMessage();
	    while(fetchMsg.allOfCon==false) {
		    UserManager UM = UserManager.getInstance();
		   // String borrowerId = getBorrowerId();
		    //String borrowNumber = getBorrowNumber();
		    String bookBarcode = getbookBarcode();
		    if (bookBarcode.trim().isEmpty()) 
		    {   txtResponse.setText("book Barcode is required.");
		        return;
		    }    
		    // Create BookReturnMessage with:
		    // 1. borrowerId
		    // 2. borrowNumber
		    // 3. Book's barcode
	    	//fetchMsg.borrowerId = borrowerId;
	    	//fetchMsg.borrowNum=borrowNumber;
	    	fetchMsg.bookBarcode = bookBarcode;
	    	// Send the BookReturnMessage
	    	UM.send(fetchMsg);
	    	// Wait for the response 
	    	// Get the message from MyInbox (set it in handleMessageFromServer)
	    	String response = UM.inb.getMessage();    
	    	// Set the response text in the TextBox
	    	txtResponse.setText(response);  // Display the server's response (e.g., "The book was returned." or error message)
	    	if(response.equalsIgnoreCase("The book was returned."))fetchMsg.allOfCon=true;
	    	else {return;}
	    }
	    
	    
	   //SubscriberUI.mainController.switchView("/gui/ReturnBookOption.fxml");
	    
	    
//	    // Load the new page to display return results
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ReturnBookOption.fxml"));
//        Parent root = loader.load();
//        // Display the new page with return results
//        Stage primaryStage = new Stage();
//        primaryStage.setScene(new Scene(root));
//        primaryStage.setTitle("book return completed");
//        primaryStage.show();
//        // Hide the current window
//        ((Node) event.getSource()).getScene().getWindow().hide();
	}
	
    /**
     * This method is called when the 'Back' button is clicked.
     * It navigates the user back to the main menu or previous screen.
     */
	
	
	@Override
    public void barcodeScan(String barcode){
		txtbookBarcode.setText(barcode);
    	
    }
	public void getBackBtn(ActionEvent event) throws Exception {
		
		SubscriberUI.mainController.goBack();
		
		
	}
//	        try {
//	            // Close the current window
//	            ((Node) event.getSource()).getScene().getWindow().hide();
//	            // Load the previous screen (Main Menu)
//	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainMenu.fxml"));
//	            Pane root = loader.load();
//	            // Set up the new stage
//	            Stage stage = new Stage();
//	            Scene scene = new Scene(root);
//	            stage.setTitle("Main Menu");
//	            stage.setScene(scene);
//	            stage.show();
//	            } 
//	        catch (IOException e) {
//	            e.printStackTrace();
//	            System.out.println("Failed to load MainMenu.fxml.");
//	                              }
//	}
}
