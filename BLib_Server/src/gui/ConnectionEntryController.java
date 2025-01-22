package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.ServerUI;

public class ConnectionEntryController {

	@FXML
	private Button btnExit = null;
    @FXML
    private Label lblCIP;

    @FXML
    private Label lblConnectionStatus;

    @FXML
    private Label hostLabel;

    public void loadConnection(String CIP, String hName, String Status) throws Exception {
//        // Set the client IP dynamically (replace with your logic to fetch IP)
//        lblClientIP.setText("Client IP: " + getClientIP());
//
//        // Set connection status dynamically
//        lblConnectionStatus.setText("Connection Status: " + getConnectionStatus());
//
//        // Set the host label dynamically
//        hostLabel.setText("Host: " + getHost());
    	
    	
        // Set the client IP dynamically (replace with your logic to fetch IP)
        lblCIP.setText("Client IP: " + CIP);

        // Set connection status dynamically
        lblConnectionStatus.setText("Connection Status: " + Status);

        // Set the host label dynamically
        hostLabel.setText("Host: " + hName);
        
        
      
        
     
    }
    
   public void removeConnection(){
	   lblCIP.setText("Client IP: ");

       // Set connection status dynamically
       lblConnectionStatus.setText("Connection Status: ");

       // Set the host label dynamically
       hostLabel.setText("Host: ");
	   
   }

   public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Tool");
		System.exit(0);			
	}
   
   
    private String getClientIP() {
        // Example method to get the client IP
        return "192.168.1.100"; // Replace with actual logic
    }

    private String getConnectionStatus() {
        // Example method to get the connection status
        boolean isConnected = true; // Replace with actual logic
        return isConnected ? "Online" : "Offline";
    }

    private String getHost() {
        // Example method to get the host
        return "localhost"; // Replace with actual logic
    }

    @FXML
    private void exitApplication() {
        Platform.exit();
    }
}

