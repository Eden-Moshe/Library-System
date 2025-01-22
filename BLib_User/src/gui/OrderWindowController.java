package gui;

import java.io.IOException;
import client.UserManager;
import common.GetReturnDateMessage;
import common.OrderMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private Date returnDate;
    public void setBookName(String bookName) {
        this.bookName = bookName;
        if (bookNameText != null) {
            bookNameText.setText("Book Name: " + bookName);
            // Fetch return date after setting book name
            fetchReturnDate();
        } else {
        	System.out.println("somthing wrong in OWC");
        }
    }
    
    private void fetchReturnDate() {
        UserManager userManager = UserManager.getInstance();
        
        // Create a message to request the return date
        GetReturnDateMessage dateMessage = new GetReturnDateMessage();
        dateMessage.bookName = bookName;
        
        // Send message and get response
        userManager.send(dateMessage);
        Object response = userManager.inb.getObj();
        
        if (response instanceof Date) {
            returnDate = (Date) response;
            returnDateText.setText("Expected Return Date: " + returnDate.toString());
        } else {
            returnDateText.setText("Expected Return Date: Not available");
        }
    }
    
    public void initialize() {
        // Only initialize UI components, don't fetch data here
        statusMessage.setText("");  // Clear any previous status
        btnOrder.setDisable(false); // Enable order button
    }
    
    @FXML
    private void handleOrderAction(ActionEvent event) {
        try {
            UserManager UM = UserManager.getInstance();
            String subscriberId = UM.getCurrentSubscriberId();
            if (bookName == null || bookName.trim().isEmpty()) {
                showAlert("Error", "Please enter a book name.");
                return;
            }
            
            // Create and send order message
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.bookName=bookName;
            orderMessage.subscriberId=Integer.parseInt(subscriberId);
            //orderMessage.bookName=bookName;
            //orderMessage.subscriberId=Integer.parseInt(subscriberId);
            UM.send(orderMessage);
            
            // Get response from server
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
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleBackAction(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainMenu.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load MainMenu.fxml");
        }
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