package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.util.Duration;


public class MainController extends Application {
    @FXML
    private StackPane root;

    
    
    
    
   	@Override
       public void start(Stage primaryStage) throws Exception {
   		
   		
   		root = new StackPane();
   		root.setStyle("-fx-background-color: #355457;");

           // Load initial view
           Pane homeView = loadView("/gui/LoginWindow.fxml");
           root.getChildren().add(homeView);

           
           Scene scene = new Scene(root, 1440, 1024);
           primaryStage.setScene(scene);
           primaryStage.setTitle("BLib Library System");
           primaryStage.show();
   		
   		
   		
   	
       }
   	  // Method to load a view
       private Pane loadView(String fxml) throws Exception {
           FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
           Pane view = loader.load();
           
           BaseController controller = loader.getController();
           if (controller != null) {
           	System.out.println("controller is not null");
           	controller.setMainApp(this);
           }
           return view;
       }

       // Method to switch views
       public void switchView(String fxml) {
           try {
//               Pane view = loadView(fxml);
//               root.getChildren().setAll(view); // Replace the current view
        	   
        	    Pane newView = loadView(fxml);

                // Set initial position and opacity
                newView.setTranslateX(root.getWidth());
                newView.setOpacity(0);
                root.getChildren().setAll(newView);

                // Create a fade-in animation
                FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newView);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);

                // Create a slide-in animation
                TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), newView);
                slideIn.setFromX(root.getWidth());
                slideIn.setToX(0);

                // Play both animations together
                ParallelTransition transition = new ParallelTransition(fadeIn, slideIn);
                transition.play();
        	   
        	   
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       
    
    
    
    
    
    
    
    

}