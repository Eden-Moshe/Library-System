package gui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import client.SubscriberUI;
import client.UserManager;
import common.BorrowRecord;
import common.ReportMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ReportsWindow extends BaseController {

    UserManager UM = UserManager.getInstance();
    @FXML
    private Button btnMonthlyStatus;

    @FXML
    private Button btnMonthlyBorrowTimes;

    @FXML
    private Button btnBack;
    
    @FXML
    private VBox statusChartContainer;  // Reference to the VBox in FXML where the chart will be added
    
    @FXML
    private VBox borrowChartContainer;
    
    @FXML
    private LineChart<String, Number> borrowChart;
    
    @FXML
    private BarChart<String, Number> statusChart; // Link to your BarChart
    
    @FXML
    private NumberAxis yAxis; // Y-axis for the count values
    
    @FXML
    private CategoryAxis xAxis; // Reference to the x-axis from FXML
    
    
    @FXML
    private void handleMonthlyStatusReport(ActionEvent event) {
	    ReportMessage reportMsg = new ReportMessage();
	    //alert its status report
	    reportMsg.statusReport=true;
	    UM.send(reportMsg);
	    
	    // Wait for the response 
	    // Get the message from MyInbox
	    String response = UM.inb.getMessage();  
	    
	    // Parse the response string
	    String[] parts = response.split(" ");  // Split the string into parts
	    
	    try {
	        // Extract the numeric values and set them into the ReportMessage object
	        int activeCount = Integer.parseInt(parts[2]);  // Extract "5" from "active = 5"
	        int frozenCount = Integer.parseInt(parts[5]); // Extract "4" from "frozen = 4"
	        
	        // Set the values in the report message
	        reportMsg.setActiveCount(activeCount);
	        reportMsg.setFrozenCount(frozenCount);
	        
	        // Output the results
	        System.out.printf("Active count = %d, Frozen count = %d\n", activeCount, frozenCount);
	    } catch (NumberFormatException e) {
	        // Handle the case where parsing fails
	        System.err.println("Error parsing the response: " + e.getMessage());
	    }
	    
        // Create the graph
        createGraph(reportMsg.activeCount, reportMsg.frozenCount);
	    reportMsg.setStatusReport(false);
    }

    // Method to generate the graph of users status
    private void createGraph(int activeCount, int frozenCount) {
        // Make the status chart container visible when the status report is clicked
        Platform.runLater(() -> {
            statusChartContainer.setVisible(true);  // Show the Status Report
        });
    	// Calculate the total number of subscribers
        int totalSubscribers = activeCount + frozenCount;

        // Set Y-axis properties
        yAxis.setAutoRanging(false);                 // Disable auto-ranging
        yAxis.setLowerBound(1);                      // Start from 1 (instead of 0)
        yAxis.setUpperBound(totalSubscribers);       // Total number of subscribers
        yAxis.setTickUnit(1);                        // Tick for every 1 unit
        yAxis.setLabel("Count");                     // Label for Y-axis
        yAxis.setTickLabelFill(Color.BLACK);         // Set Y-axis label color
        yAxis.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;"); // Style for visibility

        // Set X-axis properties
        xAxis.setLabel("Status");
        xAxis.setTickLabelFill(Color.BLACK);         // Set X-axis label color
        xAxis.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;"); // Style for better visibility

        // Proceed to create the graph
        // Create the categories for the X-axis
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Add the active and frozen counts as data points
        XYChart.Data<String, Number> activeData = new XYChart.Data<>("Active", activeCount);
        XYChart.Data<String, Number> frozenData = new XYChart.Data<>("Frozen", frozenCount);

        // Style the data points with custom colors
        activeData.nodeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setStyle("-fx-bar-fill: green;"); // Set Active bar color to green
            }
        });
        frozenData.nodeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setStyle("-fx-bar-fill: red;"); // Set Frozen bar color to red
            }
        });

        // Add data points to the series
        series.getData().add(activeData);
        series.getData().add(frozenData);

        // Clear existing data and add the new series
        statusChart.getData().clear();
        statusChart.getData().add(series);

        // Remove the legend as it's irrelevant
        statusChart.setLegendVisible(false);

    }
    
    @FXML
    private void handleMonthlyBorrowTimesReport(ActionEvent event) {
	    ReportMessage reportMsg = new ReportMessage();
	    //alert its borrow report
	    reportMsg.borrowReport=true;
	    UM.send(reportMsg);
	    
	    // Wait for the response 
	    // Get the message from MyInbox
	    List<BorrowRecord> borrowRecords =(List<BorrowRecord>) UM.inb.getObj(); 
	    generateBorrowReport(borrowRecords);

    }
    
    
    // Method to generate the borrow report
    public void generateBorrowReport(List<BorrowRecord> borrowRecords) {
        // Define margins and spacing
        double marginLeft = 80;  // Space for Y-axis labels
        double marginBottom = 40; // Space for X-axis labels
        double marginTop = 20;    // Space at the top
        double ySpacing = Math.max(15, (borrowChartContainer.getPrefHeight() - marginTop - marginBottom) / borrowRecords.size());
        double minimumHeight = borrowRecords.size() * ySpacing + marginTop + marginBottom;

        // Get the current month and its number of days
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.of(now.getYear(), now.getMonth());
        int daysInMonth = currentMonth.lengthOfMonth();

        // Match canvas width to the status chart container width
        double columnWidth = borrowChartContainer.getPrefWidth() / daysInMonth; // Adjusted column width
        double canvasWidth = marginLeft + daysInMonth * columnWidth;
        double canvasHeight = Math.max(minimumHeight, borrowChartContainer.getPrefHeight());

        // Create a new Canvas
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Adjust drawable area
        double drawableWidth = canvasWidth - marginLeft;

        // Set white background
        gc.setFill(Color.WHITE);
        gc.fillRect(marginLeft, 0, drawableWidth, canvasHeight - marginBottom);

        // Draw vertical gridlines for days
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);
        for (int day = 1; day <= daysInMonth; day++) {
            double x = marginLeft + (day - 1) * columnWidth;

            // Darker gridline for column separation
            gc.setStroke(Color.LIGHTGRAY);
            gc.strokeLine(x, marginTop, x, canvasHeight - marginBottom); // Vertical gridlines

            // Add X-axis day numbers (beneath the canvas)
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(day), x + columnWidth / 2 - 10, canvasHeight - 10); // Center day labels in each column
        }

        // Draw horizontal gridlines for borrow rows
        for (int i = 0; i < borrowRecords.size(); i++) {
            double y = marginTop + (borrowRecords.size() - i - 1) * ySpacing;

            gc.setStroke(Color.GRAY);
            gc.setLineWidth(1.5); // Slightly thicker line for Y grid separation
            gc.strokeLine(marginLeft, y, canvasWidth, y); // Horizontal gridlines
        }

        // Draw borrow records
        gc.setLineWidth(4); // Adjust line thickness
        gc.setFont(new Font(12));

        for (int i = 0; i < borrowRecords.size(); i++) {
            BorrowRecord record = borrowRecords.get(i);
            LocalDate borrowDate = record.getBorrowDate();
            LocalDate returnDate = record.getReturnDate();
            LocalDate actualReturnDate = record.getActualReturnDate();

            // Calculate Y-coordinate for the current borrow record
            double y = marginTop + (borrowRecords.size() - i - 1) * ySpacing;

            // Borrow period (green line)
            int borrowStartDay = Math.max(borrowDate.getDayOfMonth(), 1);
            int borrowEndDay = returnDate != null ? Math.min(returnDate.getDayOfMonth(), daysInMonth) : daysInMonth;

            double startX = marginLeft + (borrowStartDay - 1) * columnWidth; // Align start with grid column
            double endX = marginLeft + (borrowEndDay - 1) * columnWidth;     // Align end with grid column
            gc.setStroke(Color.GREEN);
            gc.strokeLine(startX, y, endX, y);

            // Late period (red line) with continuous transition
            if (actualReturnDate != null && actualReturnDate.isAfter(returnDate)) { // Late return
                double lateStartX = endX;
                double lateEndX = marginLeft + (Math.min(actualReturnDate.getDayOfMonth(), daysInMonth) - 1) * columnWidth;
                gc.setStroke(Color.RED);
                gc.strokeLine(lateStartX, y, lateEndX, y);
            }

            // Draw Y-axis labels for borrow record numbers (to the left of the white square)
            gc.setFill(Color.BLACK);
            gc.fillText("Borrow " + (i + 1), marginLeft - 60, y + 4); // Adjusted position further left
        }

        // Adjust VBox height dynamically if needed
        borrowChartContainer.setPrefHeight(canvasHeight);

        // Add the Canvas to the borrowChartContainer
        borrowChartContainer.getChildren().clear();
        borrowChartContainer.getChildren().add(canvas);
    }


    
    @FXML
    private void getBackBtn(ActionEvent event) {
    	SubscriberUI.mainController.goBack();
    }
}