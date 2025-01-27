package gui;

import java.util.ArrayList;
import java.util.Date;

import client.*;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public  class AccountStatusHistoryController extends BaseController   {
	
	UserManager UM = UserManager.getInstance();


	  @FXML
	    private TableView<AccountStatus> tableView;

	    @FXML
	    private TableColumn<AccountStatus, String> userStatusColumn;

	    @FXML
	    private TableColumn<AccountStatus, Date> fromDateColumn;

	    @FXML
	    private TableColumn<AccountStatus, Date> untilDateColumn;

	    @FXML
	    private Button btnGoBack;

	   
	
	@Override
	public void onLoad() {
		Object fromServer = UM.inb.getObj();
		if (fromServer == null)
		{
			//implement error message
		}
		ArrayList<AccountStatus> accountStatus = (ArrayList<AccountStatus>) fromServer;
		
		
		if (accountStatus == null || accountStatus.isEmpty()) {
            
            // Clear TableView if there are no messages
            tableView.setItems(FXCollections.observableArrayList());
            return;
        }

		
		 //  Set the cell value factories to match the fields in Borrow
		untilDateColumn.setCellValueFactory(new PropertyValueFactory<>("end_date"));
		fromDateColumn.setCellValueFactory(new PropertyValueFactory<>("set_date"));
		
		userStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        //  Convert ArrayList to an ObservableList

        ObservableList<AccountStatus> observableMessages = FXCollections.observableArrayList(accountStatus);

        //  Load the data into the TableView
        tableView.setItems(observableMessages);
		
		
		
	}
	
	 @FXML
	    private void goBack() {
		 SubscriberUI.mainController.goBack();
	    }
	

	
	public void getExitBtn(ActionEvent event) throws Exception {
		System.exit(0);
	}
	
}
