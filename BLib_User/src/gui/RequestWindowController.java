package gui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import client.UserManager;
import common.RequestMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RequestWindowController {

    @FXML
    private Button btnExit = null;

    @FXML
    private Button btnRequestExtension = null;

    @FXML
    private TextField txtBorrowerId;

    @FXML
    private TextField txtBookBarcode;

    @FXML
    private TextField txtRequestDate;

    @FXML
    private TextField txtResponse;

    @FXML
    private Button btnReset;
    
    @FXML
    private Button btnExtension;

    private String getBorrowerId() {
        return txtBorrowerId.getText();
    }

    private String getBookBarcode() {
        return txtBookBarcode.getText();
    }

    private Date getRequestDate() {
        String requestDateText = txtRequestDate.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(requestDateText); // Parse the String into a Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }

    public void loadResponse(String msg) {
        this.txtResponse.setText(msg);
    }

    @FXML
    private void resetFields(ActionEvent event) {
        txtBorrowerId.clear();
        txtBookBarcode.clear();
        txtRequestDate.clear();
        txtResponse.clear();
    }

    public void sendExtensionRequest(ActionEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader();
        UserManager UM = UserManager.getInstance();
        String borrowerId = getBorrowerId();
        String bookBarcode = getBookBarcode();
        Date requestDate = getRequestDate();

        if (borrowerId.trim().isEmpty() || bookBarcode.trim().isEmpty() || requestDate == null) {
            System.out.println("All fields are required.");
            return;
        }

        // Creating a RequestMessage to send for extension
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.s.setSID(borrowerId);
        requestMessage.b.setBarcode(bookBarcode);
        requestMessage.borrow.setBorrowDate(requestDate);

        UM.send(requestMessage); // Send the message to the server

        txtResponse.setText("Extension request sent successfully.");

        // Close the current window and open a new window to show the result
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        Pane root = loader.load(getClass().getResource("/gui/RequestForm.fxml").openStream());
        RequestWindowController requestWindowController = loader.getController();
        requestWindowController.loadResponse(UM.inb.getMessage());

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/RequestForm.css").toExternalForm());
        primaryStage.setTitle("Request Management Tool");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Exit Request Tool");
        System.exit(0);
    }
}
