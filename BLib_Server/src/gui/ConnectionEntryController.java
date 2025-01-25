package gui;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    	
    			System.out.println("removed user");
    		}
    	}
    	updateTable();
    	
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
		
		//ServerUI.conEntry = loader.getController();	
		server.conEntry = loader.getController();	
		
		//conEntry.loadConnection(CIP,CCON,CSTATUS);
		//ServerUI.conEntry.loadConnection("","","");
		Scene scene = new Scene(root);			
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















//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//
//public class ConnectionEntryController {
//
//	@FXML
//	private Button btnExit = null;
//    @FXML
//    private Label lblCIP;
//
//    @FXML
//    private Label lblConnectionStatus;
//
//    @FXML
//    private Label hostLabel;
//    
//    @FXML
//    private void exitApplication() {
//    	Platform.exit();
//    }
//
//    public void loadConnection(String CIP, String hName, String Status) throws Exception {
////        // Set the client IP dynamically (replace with your logic to fetch IP)
////        lblClientIP.setText("Client IP: " + getClientIP());
////
////        // Set connection status dynamically
////        lblConnectionStatus.setText("Connection Status: " + getConnectionStatus());
////
////        // Set the host label dynamically
////        hostLabel.setText("Host: " + getHost());
//    	
//    	
//        // Set the client IP dynamically (replace with your logic to fetch IP)
//        lblCIP.setText("Client IP: " + CIP);
//
//        // Set connection status dynamically
//        lblConnectionStatus.setText("Connection Status: " + Status);
//
//        // Set the host label dynamically
//        hostLabel.setText("Host: " + hName);
//        
//        
//      
//        
//     
//    }
//    
//   public void removeConnection(){
//	   lblCIP.setText("Client IP: ");
//
//       // Set connection status dynamically
//       lblConnectionStatus.setText("Connection Status: ");
//
//       // Set the host label dynamically
//       hostLabel.setText("Host: ");
//	   
//   }
//
//   public void getExitBtn(ActionEvent event) throws Exception {
//		System.out.println("exit Tool");
//		System.exit(0);			
//	}
//   
//   
//    private String getClientIP() {
//        // Example method to get the client IP
//        return "192.168.1.100"; // Replace with actual logic
//    }
//
//    private String getConnectionStatus() {
//        // Example method to get the connection status
//        boolean isConnected = true; // Replace with actual logic
//        return isConnected ? "Online" : "Offline";
//    }
//
//    private String getHost() {
//        // Example method to get the host
//        return "localhost"; // Replace with actual logic
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
