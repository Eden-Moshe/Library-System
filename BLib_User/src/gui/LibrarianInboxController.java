package gui;

import java.util.ArrayList;

import client.*;
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

import static common.GenericMessage.Action.*;

/**
 * The LibrarianInboxController class manages the inbox for the librarian,
 * allowing them to view messages, mark them as read, and switch between new and past messages.
 */
public class LibrarianInboxController extends BaseController {

    // Singleton manager that holds the messages
    UserManager UM = UserManager.getInstance();
    private ArrayList<InboxMessage> messages;
    private ArrayList<InboxMessage> currentlyDisplayed;
    private boolean markAsReadToggle = false;

    @FXML
    private Label lblMessageType;

    @FXML
    private Button btnOtherMessages;

    @FXML
    private Button btnMarkAsRead;

    @FXML
    private TableView<InboxMessage> tableView;

    @FXML
    private TableColumn<InboxMessage, String> senderColumn;

    @FXML
    private TableColumn<InboxMessage, String> messageColumn;

    /**
     * Marks the currently displayed messages as read.
     * This method is triggered when the "Mark as Read" button is clicked.
     * It sends the "set_Librarian_Messages_Read" action to the server and updates the displayed messages.
     *
     * @param event The event that triggered the action.
     */
    public void markAsRead(ActionEvent event) {
        // Do nothing if we are viewing the current messages
        if (btnOtherMessages.getText().contains("current"))
            return;

        // Create a message to mark the messages as read
        GenericMessage toMarkRead = new GenericMessage();
        toMarkRead.Obj = currentlyDisplayed;
        toMarkRead.action = set_Librarian_Messages_Read;
        toMarkRead.librarian = UM.librarian;

        // Send the action to the server
        UM.send(toMarkRead);

        // Remove all the messages from the display as they are now marked as read
        currentlyDisplayed = new ArrayList<>();
        displayMessages();
    }

    /**
     * Toggles between displaying new and past messages.
     * This method is triggered when the "View Other Messages" button is clicked.
     * It updates the UI elements to suit the current message type being viewed (new or past).
     *
     * @param event The event that triggered the action.
     */
    public void viewOtherMessages(ActionEvent event) {
        ArrayList<InboxMessage> temp = new ArrayList<>();

        // Switch UI elements based on the message type being viewed (past/current)
        btnMarkAsRead.setVisible(markAsReadToggle);
        if (markAsReadToggle)
            lblMessageType.setText("New Messages");
        else
            lblMessageType.setText("Past Messages");

        // Toggle the state of the button to switch between new and past messages
        markAsReadToggle = !markAsReadToggle;

        // Add messages that are not already displayed
        for (InboxMessage msg : messages) {
            if (!currentlyDisplayed.contains(msg))
                temp.add(msg);
        }

        currentlyDisplayed = temp;
        displayMessages();

        // Update the button text to reflect the current state
        if (btnOtherMessages.getText().contains("past"))
            btnOtherMessages.setText("View Current Messages");
        else
            btnOtherMessages.setText("View Past Messages");
    }

    /**
     * Navigates back to the previous screen.
     * This method is triggered when the back button is clicked.
     *
     * @param event The event that triggered the action.
     */
    public void goBack(ActionEvent event) {
        SubscriberUI.mainController.goBack();
    }

    /**
     * Loads the inbox messages from the server when the view is loaded.
     * This method retrieves messages from the server and sets up the UI to display them.
     */
    @Override
    public void onLoad() {
        // Fetch the data from the server’s message handler
        Object fromServer = UM.inb.getObj();
        if (fromServer == null) {
            System.out.println("Error: No data received from server.");
            return;
        }

        // Cast the received object to the expected ArrayList type
        messages = (ArrayList<InboxMessage>) fromServer;

        currentlyDisplayed = new ArrayList<>();
        for (InboxMessage msg : messages) {
            if (msg.is_new) {
                currentlyDisplayed.add(msg);
                System.out.println("Message added: " + msg.getMessage());
            }
        }

        // Display unread messages
        displayMessages();
    }

    /**
     * Displays the messages in the TableView.
     * This method updates the TableView with the currently displayed messages.
     */
    private void displayMessages() {
        if (currentlyDisplayed == null || currentlyDisplayed.isEmpty()) {
            System.out.println("No messages to display.");

            // Clear the TableView if there are no messages
            tableView.setItems(FXCollections.observableArrayList());
            return;
        }

        // Set the cell value factories to match the fields in InboxMessage
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        // Convert the ArrayList to an ObservableList
        ObservableList<InboxMessage> observableMessages = FXCollections.observableArrayList(currentlyDisplayed);

        // Load the data into the TableView
        tableView.setItems(observableMessages);
    }

    /**
     * Exits the library tool application.
     * This method is triggered when the exit button is clicked.
     *
     * @param event The event that triggered the action.
     */
    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Exiting Library Tool");
        System.exit(0);
    }
}
