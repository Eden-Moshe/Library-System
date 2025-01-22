package gui;

import java.util.ArrayList;

import client.*;
import common.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static common.GenericMessage.Action.*;


public class LibrarianInboxController extends BaseController {

    // Singleton manager that holds the messages
    UserManager UM = UserManager.getInstance();
    ArrayList<InboxMessage> messages;
    ArrayList<InboxMessage> currentlyDisplayed;

    private boolean markAsReadToggle=false;
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

    public void markAsRead(ActionEvent event) {
    	//this means we are viewing past messages and need to do nothing cus they are already marked as read
    	if (btnOtherMessages.getText().contains("current"))
    		return;
    	
    	GenericMessage toMarkRead = new GenericMessage();
    	toMarkRead.Obj = currentlyDisplayed;
    	toMarkRead.action = set_Librarian_Messages_Read;
    	toMarkRead.librarian = UM.librarian;
    	
    	UM.send(toMarkRead);
    	
    	//removing all the messages because they are now set as read.
    	currentlyDisplayed = new ArrayList<>();;
    	displayMessages();
    	
    }
    public void viewOtherMessages(ActionEvent event) {
    	
        ArrayList<InboxMessage> temp = new ArrayList<InboxMessage>();

        
        //switching UI elements to suit the message type (past/current) being viewed
        btnMarkAsRead.setVisible(markAsReadToggle);
        if (markAsReadToggle)
        	lblMessageType.setText("New Messages");
        else
        	lblMessageType.setText("Past Messages");
        
        markAsReadToggle = !markAsReadToggle;
    	
    	
        for (InboxMessage msg :  messages)
        {
        	if (!currentlyDisplayed.contains(msg))
        		temp.add(msg);
        	
        }
    	
    	currentlyDisplayed = temp;
    	displayMessages();
    	
    	
    	if (btnOtherMessages.getText().contains("past"))
    		btnOtherMessages.setText("view current messages");
    	else
    		btnOtherMessages.setText("view past messages");
    }
    
    public void goBack(ActionEvent event) {
    	//returning to main menu
    	//SubscriberUI.mainController.switchView("/gui/LibrarianMenuNew.fxml");
    	SubscriberUI.mainController.goBack();
    }
    @Override
    public void onLoad() {
        // 1. Fetch the data from the server’s message handler
        Object fromServer = UM.inb.getObj();
        if (fromServer == null) {
            System.out.println("Error: No data received from server.");
            return;
        }

        // 2. Cast the received object to the expected ArrayList type
        messages = (ArrayList<InboxMessage>) fromServer;

        
        currentlyDisplayed=new ArrayList<InboxMessage>();
        for (InboxMessage msg :  messages)
        {
        	if (msg.is_new) {
        		currentlyDisplayed.add(msg);
        		System.out.println("message added: "+ msg.getMessage());
        	}
        	
        }
        
        
        
        //display unread messages
        displayMessages();
       
    }
    
    
    private void displayMessages() {
    	
    	
    	if (currentlyDisplayed == null || currentlyDisplayed.isEmpty()) {
            System.out.println("No messages to display.");
            
            // Clear the TableView if there are no messages
            tableView.setItems(FXCollections.observableArrayList());
            return;
        }
    	
    	 //  Set the cell value factories to match the fields in InboxMessage
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        //  Convert ArrayList to an ObservableList

        ObservableList<InboxMessage> observableMessages = FXCollections.observableArrayList(currentlyDisplayed);

        //  Load the data into the TableView
        tableView.setItems(observableMessages);
    	
    }
    

    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("exit Library Tool");
        System.exit(0);
    }
}

