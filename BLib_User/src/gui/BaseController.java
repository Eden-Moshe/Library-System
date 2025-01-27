package gui;

import client.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The BaseController class provides a base structure for GUI controllers in the application.
 * 
 * This class includes common functionality that can be utilized by its inheritors,
 * such as displaying alerts and handling barcode scans.
 * 
 * It also includes methods that can be overridden by child classes to implement
 * specific behaviors.
 */
public class BaseController {

    /** The UserManager instance for managing user-related operations. */
    protected UserManager UM = UserManager.getInstance();

    /**
     * Handles a barcode scan action.
     * 
     * This method is intended to be overridden by inherited classes to provide
     * specific behavior for handling barcode scans.
     * 
     * @param barcode The scanned barcode as a string.
     */
    public void barcodeScan(String barcode) {
        // Implement in each inherited class to handle barcode scans.
    }

    /**
     * Displays an alert message to the user.
     * 
     * This method creates a simple information alert dialog with a specified title and content.
     * 
     * @param title   The title of the alert dialog.
     * @param content The content message displayed in the alert dialog.
     */
    protected void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Executes actions when the controller is loaded.
     * 
     * This method is a placeholder and does nothing by default. 
     * It can be overridden by inheriting classes to define specific 
     * actions to be performed when the controller is initialized or loaded.
     */
    public void onLoad() {
        // Nothing by default, let inheritors implement this when needed.
    }
}
