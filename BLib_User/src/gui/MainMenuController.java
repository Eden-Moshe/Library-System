package gui;

import java.io.IOException;
import client.*;
import common.*;
import static common.GenericMessage.Action.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * MainMenuController class responsible for handling user interactions in the 
 * main menu of the subscriber interface, including navigating to various forms 
 * and sending appropriate messages for actions such as borrowing books, 
 * creating a subscriber, and viewing account history.
 * 
 * The controller handles the UI events, communicates with the UserManager, 
 * and switches between different views based on the user's choice.
 */
public class MainMenuController extends BaseController {
    
    private SubscriberFormController sfc;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnCreateSubscriber;

    @FXML
    private TextField lblID;

    /**
     * Handles the logout action and goes back to the previous screen.
     * Logs the user out and returns to the previous screen in the application.
     * 
     * @param event The action event triggered by the logout button.
     */
    public void goBack(ActionEvent event) {
        UM.logOut();
        SubscriberUI.mainController.goBack();
    }

    /**
     * Navigates to the BorrowForm view where the user can lend a book.
     * 
     * @param event The action event triggered by the "Lend Book" button.
     * @throws Exception If an error occurs while switching views.
     */
    public void btnLendBook(ActionEvent event) throws Exception {
        SubscriberUI.mainController.switchView("/gui/BorrowForm.fxml");
    }

    /**
     * Navigates to the CreateSubscriberForm view where the user can create a new subscriber.
     * 
     * @param event The action event triggered by the "Create Subscriber" button.
     * @throws Exception If an error occurs while switching views.
     */
    public void btnCreateSubscriber(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        UserManager UM = UserManager.getInstance();

        // Hide the current window
        ((Node) event.getSource()).getScene().getWindow().hide();

        Stage primaryStage = new Stage();
        Pane root = loader.load(getClass().getResource("/gui/CreateSubscriberForm.fxml").openStream());

        Scene scene = new Scene(root);
        primaryStage.setTitle("Subscriber Creation Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Navigates to the BorrowHistory view to view the borrow history.
     * 
     * @param event The action event triggered by the "View History" button.
     * @throws Exception If an error occurs while sending the message or switching views.
     */
    public void btnViewHistory(ActionEvent event) throws Exception {
        GenericMessage msg = new GenericMessage();
        msg.subscriber = UM.s1;
        msg.action = get_Borrow_History;
        
        UM.send(msg);
        
        SubscriberUI.mainController.switchView("/gui/BorrowHistory.fxml");
    }

    /**
     * Navigates to the SearchBook view where the user can search for books.
     * 
     * @param event The action event triggered by the "Search Book" button.
     * @throws IOException If an error occurs while loading the FXML view.
     */
    public void btnSearchBook(ActionEvent event) throws IOException {
        SubscriberUI.mainController.switchView("/gui/SearchBook.fxml");
    }

    /**
     * Navigates to the ExtendBorrowForm view where the user can extend a borrow period.
     * 
     * @param event The action event triggered by the "Return Book" button.
     * @throws Exception If an error occurs while switching views.
     */
    public void btnReturnBook(ActionEvent event) throws Exception {
        SubscriberUI.mainController.switchView("/gui/ExtendBorrowForm.fxml");
    }

    /**
     * Navigates to the ExtendBorrowForm view where the user can extend the borrow period of a book.
     * 
     * @param event The action event triggered by the "Extend Borrow" button.
     * @throws Exception If an error occurs while switching views.
     */
    public void btnExtendBorrow(ActionEvent event) throws Exception {
        SubscriberUI.mainController.switchView("/gui/ExtendBorrowForm.fxml");
    }

    /**
     * Navigates to the SubscriberInformationPersonal view to display user information.
     * 
     * @param event The action event triggered by the "User Info" button.
     * @throws Exception If an error occurs while switching views.
     */
    public void btnUserInfo(ActionEvent event) throws Exception {
		SubscriberUI.mainController.switchView("/gui/SubscriberInformationPersonal.fxml");
    }

    /**
     * Navigates to the AccountStatusHistory view and sends a request for the account status.
     * 
     * @param event The action event triggered by the "View Account Status" button.
     */
    public void viewAccountStatus(ActionEvent event) {
        GenericMessage getStatus = new GenericMessage();
        getStatus.subscriber = UM.s1;
        getStatus.action = get_Account_Status_History;
        
        UM.send(getStatus);
        
        SubscriberUI.mainController.switchView("/gui/AccountStatusHistory.fxml");
    }

    /**
     * Initializes the main menu window with the UserManager instance and 
     * sets up the primary stage.
     * 
     * @param primaryStage The primary stage (window) of the application.
     * @throws Exception If an error occurs while loading the FXML view or setting the scene.
     */
    public void start(Stage primaryStage) throws Exception {
        UM = UserManager.getInstance();

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Subscriber Entry Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Exits the application when the exit button is clicked.
     * 
     * @param event The action event triggered by the "Exit" button.
     * @throws Exception If an error occurs during the exit process.
     */
    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("exit Academic Tool");
        System.exit(0);
    }

    /**
     * Loads the subscriber information into the form.
     * 
     * @param s1 The subscriber whose information needs to be loaded.
     */
    public void loadSubscriber(Subscriber s1) {
        this.sfc.loadSubscriber(s1);
    }

    /**
     * Displays a message in the console (debugging purpose).
     * 
     * @param message The message to display.
     */
    public void display(String message) {
        System.out.println("message");
    }

    /**
     * Navigates to the CheckAvailability view where the user can order a book.
     * 
     * @param event The action event triggered by the "Order Book" button.
     * @throws Exception If an error occurs while switching views.
     */
    public void btnOrderBook(ActionEvent event) throws Exception {
        SubscriberUI.mainController.switchView("/gui/CheckAvailability.fxml");
    }
}
