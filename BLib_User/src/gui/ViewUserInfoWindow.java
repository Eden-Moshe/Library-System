package gui;

import java.awt.Button;
import java.io.IOException;

import client.SubscriberUI;
import common.GenericMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import static common.GenericMessage.Action.*;


public class ViewUserInfoWindow {

    
    @FXML
    private Button btnBack;
    
    
    @FXML
    private Button btnExtendBorrow;
    
    @FXML
    private Button btnCheckBook;
    
    @FXML
    private Button btnViewAccountStatusHistory;
    
    @FXML
    private Button btnViewBorrowHistory;
	
    
    
    @FXML
    private void checkBookStatus(ActionEvent event) {
        // TODO: Implement logic to check book status (lost/destroyed)
    }
    
    @FXML
    private void extendBorrow(ActionEvent event) {
    	//goes to Extend Borrow Page
    	
    	SubscriberUI.mainController.switchView("/gui/ExtendBorrowForm.fxml");
//    	
//        try {
//            // Close the current window
//            ((Node) event.getSource()).getScene().getWindow().hide();
//
//            // Load the next screen (Extend Borrow)
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ExtendBorrowForm.fxml"));
//            Pane root = loader.load();
//            //alerting the next page Librarian entered
//            RequestWindowController requestWindowController = new RequestWindowController();
//            requestWindowController.setUserRole("Librarian");  // Set the user role
//            
//            //implement the same from Subscriber Main Menu
//            //RequestWindowController requestWindowController = new RequestWindowController();
//            //requestWindowController.setUserRole("Reader");  // Set the user role
//
//            // Set up the new stage
//            Stage stage = new Stage();
//            Scene scene = new Scene(root);
//            stage.setTitle("Extend Borrow");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to load ExtendBorrowForm.fxml.");
//       }
    }

    
    @FXML
    private void viewAccountStatusHistory(ActionEvent event) {
    	//goes to Account Status History Page
    	SubscriberUI.mainController.switchView("/gui/AccountStatusHistory.fxml");

    	
    	
//        try {
//            // Close the current window
//            ((Node) event.getSource()).getScene().getWindow().hide();
//
//            // Load the next screen (Account Status History)
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/AccountStatusHistory.fxml"));
//            Pane root = loader.load();
//
//            // Set up the new stage
//            Stage stage = new Stage();
//            Scene scene = new Scene(root);
//            stage.setTitle("Account Status History");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to load AccountStatusHistory.fxml.");
//       }
    }
    
    @FXML
    private void viewBorrowHistory(ActionEvent event) {
    	
    	
    	//goes to Borrow History Page
    	GenericMessage borrowMessage = new GenericMessage();
    	borrowMessage.action = get_Borrow_History;
    	borrowMessage.subscriber = null; // waiting for implementation!!
    	SubscriberUI.mainController.switchView("/gui/BorrowHistory.fxml");
    	
    	
    	
//        try {
//            // Close the current window
//            ((Node) event.getSource()).getScene().getWindow().hide();
//
//            // Load the next screen (Borrow History)
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/BorrowHistory.fxml"));
//            Pane root = loader.load();
//
//            // Set up the new stage
//            Stage stage = new Stage();
//            Scene scene = new Scene(root);
//            stage.setTitle("Borrow History");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to load BorrowHistory.fxml.");
//       }
//        
        
        
    }
	

	
    @FXML
    private void getBackBtn(ActionEvent event) {
    	//goes back one page
    	
    	SubscriberUI.mainController.goBack();

    	
    	
    	
//        try {
//            // Close the current window
//            ((Node) event.getSource()).getScene().getWindow().hide();
//
//            // Load the previous screen (Librarian Enters User ID)
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LibrarianEnterUserID.fxml"));
//            Pane root = loader.load();
//
//            // Set up the new stage
//            Stage stage = new Stage();
//            Scene scene = new Scene(root);
//            stage.setTitle("Librarian Enter User ID");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to load LibrarianEnterUserID.fxml.");
//       }
    }
}
