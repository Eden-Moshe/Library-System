package gui;

import client.UserManager;
import common.SubMessage;
import common.Subscriber;
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
 * Controller for the Subscriber Entry Tool. This class handles the user interface 
 * logic for entering a subscriber's ID and managing their subscription information.
 */
public class SubscriberEntryController extends BaseController {
    
    private SubscriberFormController sfc;	
    private static int itemIndex = 3;

    @FXML
    private Button btnExit = null;
    
    @FXML
    private Button btnSend = null;
    
    @FXML
    private TextField idtxt;
    
    /**
     * Retrieves the subscriber ID from the input field.
     * 
     * @return The subscriber ID entered by the user.
     */
    private String getID() {
        return idtxt.getText();
    }

    /**
     * Sends the subscriber's ID to the UserManager, retrieves the subscriber 
     * information, and navigates to the SubscriberForm if the ID is valid.
     * 
     * @param event The action event triggered by the send button.
     * @throws Exception If an error occurs during the process of retrieving or displaying the subscriber's information.
     */
    public void Send(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        
        String id = getID();
        
        UserManager UM = UserManager.getInstance();
        
        // Create a new SubMessage with the subscriber ID and set the editBool flag to false
        SubMessage getSub = new SubMessage();
        getSub.editBool = false;
        getSub.pKey = id;
        
        UM.send(getSub);

        // Check if the ID is valid; if not, display an error message
        if(id.trim().isEmpty() || UM.s1.getSID() == null) {
            System.out.println("You must enter a valid id number" + id);	
        } else {
            // Hide the current window and show the SubscriberForm
            ((Node)event.getSource()).getScene().getWindow().hide(); // Hiding primary window
            Stage primaryStage = new Stage();
            Pane root = loader.load(getClass().getResource("/gui/SubscriberForm.fxml").openStream());
            SubscriberFormController subscriberFormController = loader.getController();		
            subscriberFormController.loadSubscriber(UM.s1);
            
            // Set up the new window with the subscriber form
            Scene scene = new Scene(root);			
            scene.getStylesheets().add(getClass().getResource("/gui/SubscriberForm.css").toExternalForm());
            primaryStage.setTitle("Subscriber Management Tool");

            primaryStage.setScene(scene);		
            primaryStage.show();
        }
    }

    /**
     * Initializes the Subscriber Entry window, setting up the stage, scene, 
     * and showing the window to the user.
     * 
     * @param primaryStage The main window stage.
     * @throws Exception If an error occurs while loading the FXML file.
     */
    public void start(Stage primaryStage) throws Exception {	
        Parent root = FXMLLoader.load(getClass().getResource("SubscriberEntry.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("SubscriberEntry.css").toExternalForm());
        primaryStage.setTitle("Subscriber Entry Tool");
        primaryStage.setScene(scene);
        primaryStage.show();	 	   
    }

    /**
     * Exits the application.
     * 
     * @param event The action event triggered by the exit button.
     * @throws Exception If an error occurs during the exit process.
     */
    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Exit Academic Tool");
        System.exit(0); // Terminate the application
    }

    /**
     * Loads the subscriber information into the subscriber form.
     * 
     * @param s1 The Subscriber object containing subscriber details to load.
     */
    public void loadSubscriber(Subscriber s1) {
        this.sfc.loadSubscriber(s1);
    }	

    /**
     * Displays a message to the console.
     * 
     * @param message The message to display.
     */
    public void display(String message) {
        System.out.println("Message");
    }
}
