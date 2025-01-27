package gui;

import java.io.IOException;
import java.util.ArrayList;

import client.UserManager;
import client.SubscriberUI;
import common.Book;
import common.SearchMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controller for searching books. This class handles the user interface logic 
 * for the book search form, including capturing user input, sending search 
 * requests, and processing the search results.
 */
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

    /**
     * Getter for book name input.
     * 
     * @return The book name entered by the user.
     */
    private String getBookName() {
        return txtBookName.getText();
    }

    /**
     * Getter for book subject input.
     * 
     * @return The book subject entered by the user.
     */
    private String getBookSubject() {
        return txtBookSubject.getText();
    }

    /**
     * Getter for description input.
     * 
     * @return The book description entered by the user.
     */
    private String getDescription() {
        return txtDescription.getText();
    }

    /**
     * Handle the back button click. Closes the current window and opens the main menu.
     * 
     * @param event The action event triggered by the back button.
     */
    public void handleBack(ActionEvent event) {
        SubscriberUI.mainController.goBack();
    }
    
    /**
     * Handles the search button click action. It collects the user's search input,
     * sends a search request, and processes the response. The search results are 
     * displayed on a new page.
     * 
     * @param event The action event triggered by the search button.
     * @throws Exception If an error occurs during the search process.
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

        //the search results controller will now handle the response from the server.
        SubscriberUI.mainController.switchView("/gui/BookResults.fxml");
        

    }

    /**
     * Initializes the Search Book window, setting up the stage, scene, 
     * and showing the window to the user.
     * 
     * @param primaryStage The main window stage.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public void start(Stage primaryStage) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource("SearchBookWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Search Book"); // Set window title
        primaryStage.setScene(scene);
        primaryStage.show(); // Show the window
    }

    /**
     * Exits the application. This method is intended for testing purposes.
     * 
     * @param event The action event triggered by the exit button.
     * @throws Exception If an error occurs during the exit process.
     */
    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Exiting Search Tool");
        System.exit(0); // Terminate the application
    }
}
