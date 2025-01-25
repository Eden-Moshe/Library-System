package gui;

import client.SubscriberUI;
import client.UserManager;
import common.GetReturnDateMessage;
import common.OrderMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import java.util.Date;

public class OrderWindowController extends BaseController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnOrder;

    @FXML
    private Text bookNameText;

    @FXML
    private Text returnDateText;

    @FXML
    private Text statusMessage;

    private String bookName;
    private static String tempBookName; // Static variable to store the book name
    private Date returnDate;

    /**
     * Set the name of the book and update the UI to display it.
     * 
     * @param bookName The name of the book to be set.
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
        if (bookNameText != null) {
            bookNameText.setText("Book Name: " + bookName);
            // Fetch return date after setting the book name
            fetchReturnDate();
        } else {
            System.out.println("Something went wrong in OrderWindowController.");
        }
    }

    /**
     * Set a temporary book name for use before initializing the controller.
     * 
     * @param bookName The name of the book to be temporarily stored.
     */
    public static void setTempBookName(String bookName) {
        tempBookName = bookName;
    }

    /**
     * Fetch the expected return date for the book and update the UI.
     */
    private void fetchReturnDate() {
        UserManager userManager = UserManager.getInstance();

        // Create a message to request the return date
        GetReturnDateMessage dateMessage = new GetReturnDateMessage();
        dateMessage.bookName = bookName;

        // Send the message and get the response
        userManager.send(dateMessage);
        Object response = userManager.inb.getObj();

        if (response instanceof Date) {
            returnDate = (Date) response;
            returnDateText.setText("Expected Return Date: " + returnDate.toString());
        } else {
            returnDateText.setText("Expected Return Date: Not available");
        }
    }

    /**
     * Initialize the UI components and set the book name using the temporary value.
     */
    public void initialize() {
        statusMessage.setText("");  // Clear any previous status
        btnOrder.setDisable(false); // Enable the order button

        if (tempBookName != null) {
            setBookName(tempBookName); // Use the static value to set the book name
            tempBookName = null; // Clear the static value to avoid unintended reuse
        }
    }

    /**
     * Handle the order action when the user clicks the order button.
     * 
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void handleOrderAction(ActionEvent event) {
        try {
            UserManager UM = UserManager.getInstance();
            String subscriberId = UM.getCurrentSubscriberId();
            if (bookName == null || bookName.trim().isEmpty()) {
                showAlert("Error", "Please enter a book name.");
                return;
            }

            // Create and send the order message
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.bookName = bookName;
            orderMessage.subscriberId = Integer.parseInt(subscriberId);
            UM.send(orderMessage);

            // Get the response from the server
            Object response = UM.inb.getObj();

            if (response instanceof Boolean && (Boolean) response) {
                statusMessage.setText("Order placed successfully!");
                btnOrder.setDisable(true);
                showAlert("Success", "Order placed successfully!");
            } else {
                statusMessage.setText("Failed to place order. Please try again.");
                showAlert("Error", "Failed to place order. Please try again.");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid subscriber ID format.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Handle the action of going back to the previous screen.
     * 
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void handleBackAction(ActionEvent event) {
        SubscriberUI.mainController.goBack();
    }

//   
//    private void showAlert(String title, String content) {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
}
