package gui;

import java.util.ArrayList;
import java.util.Date;

import client.*;
import client.UserManager;
import common.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The BorrowExtensionsController class manages the user interface for 
 * displaying borrowed books and their return dates. It allows users to 
 * view borrow extensions and navigate back to previous screens.
 */
public class BorrowExtensionsController extends BaseController {

    // Instance of UserManager to access user data
    UserManager UM = UserManager.getInstance();

    @FXML
    private TableView<Borrow> tableView;

    @FXML
    private TableColumn<Book, String> bookNameColumn;

    @FXML
    private TableColumn<Borrow, Date> bookBorrowDateColumn;

    @FXML
    private TableColumn<Borrow, Date> bookReturnDateColumn;

    @FXML
    private Button btnExtensions;

    @FXML
    private Button btnBack;

    @FXML
    private Label label;

    /**
     * Handles the action for viewing borrow extensions. 
     * This method is triggered when the "View Borrow Extensions" button is clicked.
     * Currently, this is a placeholder method and should be implemented with 
     * logic to handle borrow extensions in the future.
     */
    @FXML
    private void viewExtensions() {
        // TODO: Implement the logic for viewing borrow extensions.
    }

    /**
     * Handles the action for navigating back to the previous screen.
     * This method is triggered when the "Back" button is clicked.
     */
    @FXML
    private void goBack() {
        SubscriberUI.mainController.goBack();
    }

    /**
     * This method is invoked when the screen is loaded. It retrieves the borrow 
     * history from the server and populates the TableView with the data.
     * The table displays the book names, borrow dates, and return dates.
     */
    @Override
    public void onLoad() {
        Object fromServer = UM.inb.getObj();
        
        // Handle the case where no data is received from the server
        if (fromServer == null) {
            // TODO: Implement error message handling when no data is received
            return;
        }

        // Cast the received object to ArrayList<Borrow> 
        ArrayList<Borrow> borrowHistory = (ArrayList<Borrow>) fromServer;

        // Check if the borrow history is empty
        if (borrowHistory == null || borrowHistory.isEmpty()) {
            // Clear TableView if there are no borrow records
            tableView.setItems(FXCollections.observableArrayList());
            return;
        }

        // Set the cell value factories for each column to match the fields in Borrow
        bookBorrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        bookReturnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        // Convert the ArrayList to an ObservableList for TableView
        ObservableList<Borrow> observableMessages = FXCollections.observableArrayList(borrowHistory);

        // Load the data into the TableView
        tableView.setItems(observableMessages);
    }

//	
//	public void start(Stage primaryStage) throws Exception {	
     * This method is triggered when the "Exit" button is clicked.
     * 
     * @param event The event that triggered the action.
     * @throws Exception if there is an error during the exit process.
//
    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("exit Library Tool");
        System.exit(0);
    }
}
