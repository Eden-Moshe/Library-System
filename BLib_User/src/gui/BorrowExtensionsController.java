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


public  class BorrowExtensionsController extends BaseController   {
	
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
	     */
	    @FXML
	    private void viewExtensions() {
	        // TODO: Implement the logic for viewing borrow extensions.
	    }

	    /**
	     * Handles the action for navigating back.
	     * This method is triggered when the back button is clicked.
	     */
	    @FXML
	    private void goBack() {
	        SubscriberUI.mainController.goBack();
	    }
	
	
	@Override
	public void onLoad() {
		Object fromServer = UM.inb.getObj();
		if (fromServer == null)
		{
			//implement error message
		}
		ArrayList<Borrow> borrowHistory = (ArrayList<Borrow>) fromServer;
		
		
		System.out.println("setting columns");
		

    	if (borrowHistory == null || borrowHistory.isEmpty()) {
            System.out.println("No messages to display.");
            
            // Clear TableView if there are no messages
            tableView.setItems(FXCollections.observableArrayList());
            return;
        }
    	
		
		
		//iterate over borrowHistory to view it in the window
		//make sure it is chronlogically ordered by date of borrow
		
		 //  Set the cell value factories to match the fields in Borrow
		bookBorrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
		bookReturnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
		
		bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        //  Convert ArrayList to an ObservableList

        ObservableList<Borrow> observableMessages = FXCollections.observableArrayList(borrowHistory);

        //  Load the data into the TableView
        tableView.setItems(observableMessages);
		
	}
	
	
	
//	
//	
//	public void start(Stage primaryStage) throws Exception {	
//
//		Parent root = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));
//				
//		Scene scene = new Scene(root);
//		primaryStage.setTitle("Login Window");
//		primaryStage.setScene(scene);
//		
//		primaryStage.show();	 	   
//	}
	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Library Tool");
		System.exit(0);
	}
	
}
