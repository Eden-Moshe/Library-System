package gui;

import java.util.List;

import client.SubscriberUI;
import common.DestRecord;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller class for the "DestroyedBooksWindow" view. 
 * This class is responsible for managing the interaction with the destroyed books table, 
 * including loading the data into the table and providing navigation functionality.
 */
public class DestroyedBooksWindow extends BaseController {

    @FXML
    private TableView<DestRecord> destroyedBooksTable;
    
    @FXML
    private TableColumn<DestRecord, String> bookBarcodeColumn;
    
    @FXML
    private TableColumn<DestRecord, String> subscriberIdColumn;

    /**
     * Initializes the controller after the FXML is loaded. 
     * It triggers the loading of the destroyed books data into the table.
     */
    @FXML
    private void initialize() {
        onLoad();
    }

    /**
     * Loads the list of destroyed book records into the TableView.
     * The records are fetched from the server and displayed in the table with
     * the corresponding barcode and subscriber ID in separate columns.
     */
    public void onLoad() {
        // Fetch the list of DestRecords (assuming the data is already fetched and stored)
        List<DestRecord> records = (List<DestRecord>) UM.inb.getObj();
        
        // Bind the TableView to the list of records
        destroyedBooksTable.getItems().setAll(records);
        
        // Set the cell value factories for the columns,
        bookBarcodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        subscriberIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBarcode()));
    }

    /**
     * Handles the back button action. Navigates back to the previous view.
     * 
     * @param event the ActionEvent triggered by the back button
     * @throws Exception if an error occurs during navigation
     */
    public void goBack(ActionEvent event) throws Exception {
        SubscriberUI.mainController.goBack();
    }
}
