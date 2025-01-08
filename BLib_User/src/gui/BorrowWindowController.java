package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import client.UserManager;
import common.BorrowMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BorrowWindowController {

    @FXML
    private Button btnExit = null;

    @FXML
    private Button btnBorrow = null;

    @FXML
    private TextField txtBorrowerId;

    @FXML
    private TextField txtBookBarcode;

    @FXML
    private TextField txtBorrowDate;

    @FXML
    private TextField txtReturnDate;
    
    @FXML
    private TextField txtResponse;
    
    @FXML
    private Button btnReset;

    private String getBorrowerId() {
        return txtBorrowerId.getText();
    }

    private String getBookBarcode() {
        return txtBookBarcode.getText();
    }

    private Date getBorrowDate() {
        String borrowDateText = txtBorrowDate.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Use the format that matches your input
        try {
            return dateFormat.parse(borrowDateText); // Parse the String into a Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }

    private Date getReturnDate() {
        String returnDateText = txtReturnDate.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Use the format that matches your input
        try {
            return dateFormat.parse(returnDateText); // Parse the String into a Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }
    

    
	public void loadBorrow(String msg) {
		this.txtResponse.setText(msg);
		
	}
	
	@FXML
	private void resetFields(ActionEvent event) {
	    txtBorrowerId.clear();
	    txtBookBarcode.clear();
	    txtBorrowDate.clear();
	    txtReturnDate.clear();
	    txtResponse.clear();
	}
	
    public void sendBorrowRequest(ActionEvent event) throws SQLException, IOException {
		FXMLLoader loader = new FXMLLoader();
		UserManager UM = UserManager.getInstance();
    	String borrowerId = getBorrowerId();
        String bookBarcode = getBookBarcode();
        Date borrowDate = getBorrowDate();
        Date returnDate = getReturnDate();

        if (borrowerId.trim().isEmpty() || bookBarcode.trim().isEmpty() || borrowDate == null || returnDate == null) {
            System.out.println("All fields are required.");
            return;
        }
        
		BorrowMessage getBorrow = new BorrowMessage();
		getBorrow.editBool=false;
		getBorrow.borrow.setBorrowDate(borrowDate);
		//return date set inside Borrow class
		getBorrow.borrow.setReturnDate(returnDate);
		getBorrow.s.setSID(borrowerId);
		getBorrow.b.setBarcode(bookBarcode);
		
		UM.send(getBorrow);
		txtResponse.setText("Borrow request sent successfully.");

        
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/gui/BorrowForm.fxml").openStream());
		BorrowWindowController borrowWindowController = loader.getController();		
		borrowWindowController.loadBorrow(UM.inb.getMessage());

	
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/BorrowForm.css").toExternalForm());
		primaryStage.setTitle("Borrow Managment Tool");

		primaryStage.setScene(scene);		
		primaryStage.show();
    }

    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Exit Borrow Tool");
        System.exit(0);
    }
}



