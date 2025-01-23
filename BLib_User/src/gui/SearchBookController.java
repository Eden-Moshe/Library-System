package gui;

import java.io.IOException;
import java.util.ArrayList;

import client.UserManager;
import client.MyInbox;
import client.SubscriberUI;
import common.Book;
import common.SearchMessage;
import javafx.concurrent.Task;
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

public class SearchBookController extends BaseController{

    @FXML
    private Button btnSearch; // Search button
    
    @FXML
    private Button handleBack; // Back button

    @FXML
    private TextField txtBookName; // Text field for book name

    @FXML
    private TextField txtBookSubject; // Text field for book subject

    @FXML
    private TextField txtDescription; // Text field for book description

    private ArrayList<Book> searchResults;  // Variable to store search results

    // Getter for book name input
    private String getBookName() {
        return txtBookName.getText();
    }

    // Getter for book subject input
    private String getBookSubject() {
        return txtBookSubject.getText();
    }

    // Getter for description input
    private String getDescription() {
        return txtDescription.getText();
    }

    // Handle the back button click, closes the current window and opens the main menu
    public void handleBack(ActionEvent event) {
    	
    	SubscriberUI.mainController.goBack();
//        try {
//            ((Node) event.getSource()).getScene().getWindow().hide(); // Hide the current window
//            Stage primaryStage = new Stage();
//            Pane root = FXMLLoader.load(getClass().getResource("/gui/MainMenu.fxml"));
//            Scene scene = new Scene(root);
//            primaryStage.setTitle("Main Menu"); // Set title for the main menu
//            primaryStage.setScene(scene);
//            primaryStage.show(); // Show the main menu
//        } catch (IOException e) {
//            showAlert("Error", "Could not return to main menu: " + e.getMessage());
//        }
    }
    
    /**
     * Handles the search button click action, sends a search request
     * and processes the search results.
     */
    public void btnSearch(ActionEvent event) throws Exception {
        // Get values from the input fields
        String bookName = getBookName();
        String bookSubject = getBookSubject();
        String description = getDescription();
        UserManager UM = UserManager.getInstance();

        // Create a new search message with the entered values
        SearchMessage searchMessage = new SearchMessage();
        searchMessage.bookName = bookName;
        searchMessage.bookGenre = bookSubject;
        searchMessage.bookDescription = description;

        // Get an instance of UserManager and send the search request
        UserManager userManager = UserManager.getInstance();
        userManager.send(searchMessage);

        
        //23.01.25 victor change:
        //i changed the way the search results is called to conform to the newer method
        //the search results controller will now handle the response from the server.
        SubscriberUI.mainController.switchView("/gui/BookResults.fxml");
        
//        
//        
//        
//        MyInbox inbox = UM.inb;
//        Object response = inbox.getObj(); // Get the response from the server
//
//        // Check if the response is an ArrayList of Book objects
//        if (response instanceof ArrayList<?>) {
//            ArrayList<Book> searchResults = (ArrayList<Book>) response; // Cast the response to a list of books
//
//            if (!searchResults.isEmpty()) {
//                // Print search results for debugging purposes
//                System.out.println("Search Results:");
//                for (Book book : searchResults) {
//                    System.out.println("Book Name: " + book.getBookName());
//                    System.out.println("Genre: " + book.getBookGenre());
//                    System.out.println("Barcode: " + book.getBookBarcode());
//                    System.out.println("Location on Shelf: " + book.getPlaceOnShelf());
//                    System.out.println("Description: " + book.getBookDesc());
//                    System.out.println("Available: " + (book.isBookAvailable() ? "Yes" : "No"));
//                    System.out.println("Return Date: " + book.getReturnDate());
//                    System.out.println("----------------------------");
//                }
//                
//             
//                
//                
//                
////
////                // Load the new page to display search results
////                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/BookResults.fxml"));
////                Parent root = loader.load();
////
////                // Pass the search results to the next controller
////                BookResultsController controller = loader.getController();
////                controller.setBookResults(searchResults);
////
////                // Display the new page with search results
////                Stage primaryStage = new Stage();
////                primaryStage.setScene(new Scene(root));
////                primaryStage.setTitle("Search Results");
////                primaryStage.show();
//
//                // Hide the current window
//                //((Node) event.getSource()).getScene().getWindow().hide();
//            } else {
//                showAlert("No Results", "No books were found matching the search criteria.");
//            }
//        } else {
//            showAlert("Error", "Unexpected response received from the server.");
//        }
    }

    /**
     * Initializes the Search Book window.
     */
    public void start(Stage primaryStage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("SearchBookWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Search Book"); // Set window title
        primaryStage.setScene(scene);
        primaryStage.show(); // Show the window
    }

    // Show an alert with the specified title and content text
    private void showAlert(String title, String contentText) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait(); // Show the alert
    }

    // Exit the application (for testing purposes, you might want to replace this in production)
    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Exiting Search Tool");
        System.exit(0); // Terminate the application
    }
}
