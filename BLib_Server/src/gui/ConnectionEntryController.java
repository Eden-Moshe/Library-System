package gui;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import common.Borrow;

public class ConnectionEntryController {

    @FXML
    private TableView<ClientEntry> tableView;

    @FXML
    private TableColumn<ClientEntry, String> userColumn;

    @FXML
    private TableColumn<ClientEntry, String> ipColumn;

    @FXML
    private TableColumn<ClientEntry, String> timeColumn;

    private final ObservableList<ClientEntry> clients = FXCollections.observableArrayList();

    // Initialize method called after FXML components are loaded
    @FXML
    public void initialize() {
        // Set up the table columns
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        ipColumn.setCellValueFactory(new PropertyValueFactory<>("ip"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeConnected"));

        // Bind the ObservableList to the TableView
        tableView.setItems(clients);
    }

    // Add a client to the TableView
    public void addClient(String user, String ip) {
//        Platform.runLater(() -> {
//            String timeConnected = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            clients.add(new ClientEntry(user, ip, timeConnected));
//        });
//        
    	String timeConnected = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    	clients.add(new ClientEntry(user, ip, timeConnected));
    	updateTable();
        
    }
    
    public void updateTable() {
//    	 // Set up the table columns
//        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
//        ipColumn.setCellValueFactory(new PropertyValueFactory<>("ip"));
//        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeConnected"));

          


           //  Load the data into the TableView
           tableView.setItems(clients);
    }

    // Remove a client from the TableView by user name
    public void removeClient(String user) {
        //Platform.runLater(() -> clients.removeIf(client -> client.getUser().equals(user)));
    	
    	for (ClientEntry c : clients)
    	{
    		if (c.getUser().equals(user))
    		{
    			clients.remove(c);
    	
    		}
    	}
    	updateTable();
    	
    }
	public void shutDown (ActionEvent event) {
		System.exit(0);
		
		
	}	

	public void start(Stage primaryStage) throws Exception {	
//		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
//				
//		Scene scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
//		primaryStage.setTitle("Client");
//		primaryStage.setScene(scene);
//		
//		primaryStage.show();		
		
		
		
		FXMLLoader loader = new FXMLLoader();

		
		ServerUI.runServer("5555");
		
		Pane root = loader.load(getClass().getResource("/gui/Connection.fxml").openStream());
		
		server.conEntry = loader.getController();	
		
		
		Scene scene = new Scene(root);			
		
		// Close the application completely when the window is closed
        primaryStage.setOnCloseRequest(event -> {
            ServerUI.sv.shutDown();
        });
		
		primaryStage.setTitle("Client Managment Window");

		primaryStage.setScene(scene);		
		primaryStage.show();
		
		
		
		
	}

    // Inner class to represent each client entry
    public static class ClientEntry {
        private final String user;
        private final String ip;
        private final String timeConnected;

        public ClientEntry(String user, String ip, String timeConnected) {
            this.user = user;
            this.ip = ip;
            this.timeConnected = timeConnected;
        }

        public String getUser() {
            return user;
        }

        public String getIp() {
            return ip;
        }

        public String getTimeConnected() {
            return timeConnected;
        }
    }
}



	


