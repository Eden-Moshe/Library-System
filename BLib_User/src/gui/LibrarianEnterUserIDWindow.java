package gui;

import java.awt.Button;
import javafx.scene.control.TextField; 
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LibrarianEnterUserIDWindow extends BaseController{
    @FXML
    private Button btnViewUserInfo;
    
    @FXML
    private Button btnReset;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private TextField txtResponse;
    
    @FXML
    private TextField txtUserID;
    
    //get borrower id from text inserted
    private String getUserId() {
        return txtUserID.getText();
    }
    
    // Resets all fields when pressing 'reset'
    @FXML
    private void resetFields(ActionEvent event) {
        txtUserID.clear();
    }
    
    //display text returned from server
	public void setTextRespose(String msg) {
		this.txtUserID.setText(msg);	
	}

    @FXML
    private void ViewUserInfo(ActionEvent event) {
        String userID = getUserId();
        
	    //check that field is not empty
	    if (userID.trim().isEmpty()) {
	        txtResponse.setText("this field must be filled.");
	        return;
	    }
	    
	   //implement check if that ID exists, if so send it to next page
	    
	    //go to Reader's card:
        try {
            // Close the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Load the previous screen (Main Menu)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ViewUserInfo.fxml"));
            Pane root = loader.load();

            // Set up the new stage
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Reader's Card");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load ViewUserInfo.fxml.");
       }
    }
    
    

    @FXML
    private void getBackBtn(ActionEvent event) {
        try {
            // Close the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Load the previous screen (Main Menu)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LibrarianMainMenu.fxml"));
            Pane root = loader.load();

            // Set up the new stage
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Librarian Main Menu");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load LibrarianMainMenu.fxml.");
       }
    }
}
