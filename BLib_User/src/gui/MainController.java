package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;

import client.UserManager;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.util.Duration;

/**
 * MainController class responsible for handling the main application window,
 * view transitions, and the view history stack.
 * 
 * This controller handles the loading of views, applying smooth animations for
 * transitioning between them, and managing the navigation history to allow users
 * to go back to the previous views.
 */
public class MainController extends Application {

    @FXML
    private StackPane root;

    public BaseController controller;
    
    private Deque<String> windowHistory = new ArrayDeque<>();
    
    /**
     * Initializes the main window, loads the initial view, and sets up the application.
     * 
     * This method is called when the application starts. It sets up the primary stage,
     * loads the initial login view, and applies the necessary window settings.
     * 
     * @param primaryStage The primary stage (window) of the application.
     * @throws Exception If an error occurs during the initialization.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        root = new StackPane();
        root.setStyle("-fx-background-color: #355457;");

        // Load initial view
        Pane homeView = loadView("/gui/LoginWindow.fxml");
        root.getChildren().add(homeView);

        windowHistory.push("/gui/LoginWindow.fxml");

        Scene scene = new Scene(root, 1400, 1024);
        
        // Uncomment to load a CSS file
        // String cssFile = getClass().getResource("/styles/app.css").toExternalForm();
        // scene.getStylesheets().add(cssFile);

        // Set up the close request to properly quit the application
        primaryStage.setOnCloseRequest(event -> {
            UserManager.getInstance().quit();
            System.exit(0); // Ensures JVM exits if needed
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("BLib Library System");
        primaryStage.show();
    }

    /**
     * Loads an FXML view and initializes its associated controller.
     * 
     * @param fxml The path to the FXML file to load.
     * @return The loaded Pane representing the view.
     * @throws Exception If an error occurs while loading the view.
     */
    private Pane loadView(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Pane view = loader.load();

        controller = null;
        try {
            controller = loader.getController();
        } catch (Exception e) {
            System.out.println("!!!!!!!!!!!!!!class is not baseController!!!!!!!!!!!!!!!!!!");
        }

        // If the controller is not null, call onLoad method to initialize the view
        if (controller != null) {
            controller.onLoad();
        }
        return view;
    }

    /**
     * Switches to a new view and animates the transition with a fade and slide effect.
     * 
     * @param fxml The path to the FXML file for the new view.
     */
    public void switchView(String fxml) {
        windowHistory.push(fxml);
        System.out.println("switchView : " + fxml);

        try {
            Pane newView = loadView(fxml);

            // Set initial position and opacity for animation
            newView.setTranslateX(root.getWidth());
            newView.setOpacity(0);
            root.getChildren().setAll(newView);

            // Create a fade-in animation
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newView);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            // Create a slide-in animation
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), newView);
            slideIn.setFromX(-root.getWidth());
            slideIn.setToX(0);

            // Play both animations together
            ParallelTransition transition = new ParallelTransition(fadeIn, slideIn);
            transition.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Goes back to the previous view using the view history stack.
     * 
     * This method pops the top element from the history stack and loads the previous view,
     * applying a smooth animation transition to move back to it.
     */
    public void goBack() {
        windowHistory.pop();
        String fxml = windowHistory.getFirst();
        System.out.println("goBack: " + fxml);
        try {
            Pane newView = loadView(fxml);

            // Set initial position and opacity for animation
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
