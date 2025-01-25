package gui;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import common.Book;
import javafx.event.ActionEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import java.util.Date;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.SubscriberUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The BookResultsController class is responsible for managing the view 
 * of search results related to books. It displays the results in a table 
 * and allows the user to interact with them, such as going back to the previous screen.
 */
public class BookResultsController extends BaseController implements Initializable {
    
    @FXML
    private TableView<Book> tblResults;

    @FXML
    private TableColumn<Book, String> colBookName;

    @FXML
    private TableColumn<Book, String> colBookGenre;

    @FXML
    private TableColumn<Book, String> colBookDesc;

    @FXML
    private TableColumn<Book, String> colBookExists;

    @FXML
    private TableColumn<Book, String> colPlaceOnShelf;

    @FXML
    private TableColumn<Book, String> colReturnDate;
    
    /**
     * This method is invoked when the view is loaded to populate the table 
     * with book results based on the response from the server.
     */
    @Override
    public void onLoad() {
        Object response = UM.inb.getObj();
        if (response instanceof ArrayList<?>) {
            ArrayList<Book> searchResults = (ArrayList<Book>) response;
            if (!searchResults.isEmpty()) {
                setBookResults(searchResults);
            } else {
                showAlert("No Results", "No books were found matching the search criteria.");
            }
        } else {
            showAlert("Error", "Unexpected response received from the server.");
        }
    }

    /**
     * Initializes the table columns and their cell factories, 
     * sets up the table layout, and prepares dynamic row heights.
     * 
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Initialize basic columns with data from the Book class
            colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            colBookGenre.setCellValueFactory(new PropertyValueFactory<>("bookGenre"));
            colBookDesc.setCellValueFactory(new PropertyValueFactory<>("bookDesc"));
            colBookExists.setCellValueFactory(cellData -> {
                boolean exists = cellData.getValue().isBookAvailable();
                return new SimpleStringProperty(exists ? "Available" : "Unavailable");
            });
            colPlaceOnShelf.setCellValueFactory(new PropertyValueFactory<>("placeOnShelf"));
            colReturnDate.setCellValueFactory(cellData -> {
                Date returnDate = cellData.getValue().getReturnDate();
                return new SimpleStringProperty(returnDate == null ? "N/A" : returnDate.toString());
            });

            // Set up description column with text wrapping
            colBookDesc.setCellFactory(new Callback<TableColumn<Book, String>, TableCell<Book, String>>() {
                @Override
                public TableCell<Book, String> call(TableColumn<Book, String> param) {
                    TableCell<Book, String> cell = new TableCell<Book, String>() {
                        private Text text;

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                text = new Text(item);
                                text.setTextAlignment(TextAlignment.CENTER);
                                text.setWrappingWidth(230); // Adjust width as needed
                                setGraphic(text);
                            }
                        }
                    };

                    cell.setWrapText(true);
                    return cell;
                }
            });

            // Configure table for dynamic row heights
            tblResults.setFixedCellSize(-1);
            tblResults.setRowFactory(tv -> {
                TableRow<Book> row = new TableRow<>();
                row.setMinHeight(50); // Minimum height for rows
                return row;
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the search results to be displayed in the table.
     * 
     * @param searchResults List of books to be displayed in the table.
     */
    public void setBookResults(ArrayList<Book> searchResults) {
        try {
            if (searchResults != null && tblResults != null) {
                ObservableList<Book> data = FXCollections.observableArrayList(searchResults);
                tblResults.setItems(data);
                tblResults.refresh();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the user clicks the "Back" button.
     * Navigates the user back to the previous screen.
     * 
     * @param event The event that triggered the action.
     */
    @FXML
    public void handleBackAction(ActionEvent event) {
        SubscriberUI.mainController.goBack();
    }
}
