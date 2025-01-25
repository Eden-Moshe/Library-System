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

/**
 * Controller class for the Reports window, responsible for handling user interactions
 * and generating graphical reports based on monthly subscriber status and borrow history.
 */
public class ReportsWindow extends BaseController {

    UserManager UM = UserManager.getInstance();

    @FXML
    private Button btnMonthlyStatus;

    @FXML
    private Button btnMonthlyBorrowTimes;

    @FXML
    private Button btnBack;

    @FXML
    private VBox statusChartContainer;  // Container for the status chart

    @FXML
    private VBox borrowChartContainer;  // Container for the borrow history chart

    @FXML
    private LineChart<String, Number> borrowChart;

    @FXML
    private BarChart<String, Number> statusChart;  // Bar chart for status report

    @FXML
    private NumberAxis yAxis;  // Y-axis for the bar chart

    @FXML
    private CategoryAxis xAxis;  // X-axis for the bar chart

    /**
     * Handles the event when the "Monthly Status" button is clicked.
     * Sends a request for the monthly subscriber status report and processes the response.
     * 
     * @param event the action event triggered by clicking the button
     */
    @FXML
    private void handleMonthlyStatusReport(ActionEvent event) {
        ReportMessage reportMsg = new ReportMessage();
        // Requesting status report
        reportMsg.statusReport = true;
        UM.send(reportMsg);

        // Wait for the response from the server
        String response = UM.inb.getMessage();

        // Parse the response string to extract the active and frozen counts
        String[] parts = response.split(" ");
        try {
            int activeCount = Integer.parseInt(parts[2]);  // Active count
            int frozenCount = Integer.parseInt(parts[5]); // Frozen count

            reportMsg.setActiveCount(activeCount);
            reportMsg.setFrozenCount(frozenCount);

            // Log the results for debugging
            System.out.printf("Active count = %d, Frozen count = %d\n", activeCount, frozenCount);
        } catch (NumberFormatException e) {
            // Handle parsing errors
            System.err.println("Error parsing the response: " + e.getMessage());
        }

        // Create the graph based on the received data
        createGraph(reportMsg.activeCount, reportMsg.frozenCount);
        reportMsg.setStatusReport(false);
    }

    /**
     * Creates and displays the status chart with the active and frozen subscriber counts.
     * 
     * @param activeCount the number of active subscribers
     * @param frozenCount the number of frozen subscribers
     */
    private void createGraph(int activeCount, int frozenCount) {
        // Make the status chart container visible
        Platform.runLater(() -> {
            statusChartContainer.setVisible(true);
        });

        // Calculate the total number of subscribers
        int totalSubscribers = activeCount + frozenCount;

        // Configure Y-axis properties
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(1);
        yAxis.setUpperBound(totalSubscribers);
        yAxis.setTickUnit(1);
        yAxis.setLabel("Count");
        yAxis.setTickLabelFill(Color.BLACK);
        yAxis.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        // Configure X-axis properties
        xAxis.setLabel("Status");
        xAxis.setTickLabelFill(Color.BLACK);
        xAxis.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Prepare the data series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Data<String, Number> activeData = new XYChart.Data<>("Active", activeCount);
        XYChart.Data<String, Number> frozenData = new XYChart.Data<>("Frozen", frozenCount);

        // Style the data points with custom colors
        activeData.nodeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setStyle("-fx-bar-fill: green;");
            }
        });
        frozenData.nodeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.setStyle("-fx-bar-fill: red;");
            }
        });

        // Add the data points to the series and update the chart
        series.getData().add(activeData);
        series.getData().add(frozenData);
        statusChart.getData().clear();
        statusChart.getData().add(series);

        // Hide the legend as it is not necessary
        statusChart.setLegendVisible(false);
    }

    /**
     * Handles the event when the "Monthly Borrow Times" button is clicked.
     * Requests and processes the borrow records report from the server.
     * 
     * @param event the action event triggered by clicking the button
     */
    @FXML
    private void handleMonthlyBorrowTimesReport(ActionEvent event) {
        ReportMessage reportMsg = new ReportMessage();
        // Requesting borrow report
        reportMsg.borrowReport = true;
        UM.send(reportMsg);

        // Wait for the response (borrow records)
        List<BorrowRecord> borrowRecords = (List<BorrowRecord>) UM.inb.getObj();
        generateBorrowReport(borrowRecords);
        reportMsg.setBorrowReport(false);
    }

    /**
     * Generates and displays the borrow report as a graphical chart.
     * 
     * @param borrowRecords the list of borrow records to be displayed
     */
    public void generateBorrowReport(List<BorrowRecord> borrowRecords) {
        // Define margins
        double marginLeft = 80;
        double marginBottom = 40;
        double marginTop = 20;

        // Get current month and the number of days in it
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.of(now.getYear(), now.getMonth());
        int daysInMonth = currentMonth.lengthOfMonth();
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        // Count only borrow records within the current month
        long validRecordCount = borrowRecords.stream()
                .filter(record -> record.getBorrowDate().getMonth() == now.getMonth() &&
                                  record.getBorrowDate().getYear() == now.getYear())
                .count();

        // Calculate dynamic spacing and minimum height
        double ySpacing = Math.max(15, (borrowChartContainer.getPrefHeight() - marginTop - marginBottom) / validRecordCount);
        double minimumHeight = validRecordCount * ySpacing + marginTop + marginBottom;

        // Adjust canvas dimensions
        double columnWidth = borrowChartContainer.getPrefWidth() / daysInMonth;
        double canvasWidth = marginLeft + daysInMonth * columnWidth;
        double canvasHeight = Math.max(minimumHeight, borrowChartContainer.getPrefHeight());

        // Create a new Canvas for drawing
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Set background color
        gc.setFill(Color.WHITE);
        gc.fillRect(marginLeft, 0, canvasWidth - marginLeft, canvasHeight - marginBottom);

        // Draw gridlines for the days of the month
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);
        for (int day = 1; day <= daysInMonth; day++) {
            double x = marginLeft + (day - 1) * columnWidth;
            gc.strokeLine(x, marginTop, x, canvasHeight - marginBottom);
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(day), x + columnWidth / 2 - 10, canvasHeight - 10);
        }

        // Draw borrow records as lines
        gc.setLineWidth(4);
        gc.setFont(new Font(12));
        int currentYIndex = 0;  // Track the current row for valid records

        for (BorrowRecord record : borrowRecords) {
            LocalDate borrowDate = record.getBorrowDate();
            LocalDate returnDate = record.getReturnDate();
            LocalDate actualReturnDate = record.getActualReturnDate();

            // Skip records not in the current month
            if (borrowDate.getMonth() != now.getMonth() || borrowDate.getYear() != now.getYear()) {
                continue;
            }

            // Adjust returnDate and actualReturnDate if they are outside the current month
            if (returnDate != null && returnDate.isAfter(endOfMonth)) {
                returnDate = endOfMonth;
            }
            if (actualReturnDate != null && actualReturnDate.isAfter(endOfMonth)) {
                actualReturnDate = endOfMonth;
            }

            // Calculate Y-coordinate for the current borrow record
            double y = marginTop + (validRecordCount - currentYIndex - 1) * ySpacing;
            currentYIndex++;  // Increment for the next record

            // Draw the borrow period (green line)
            int borrowStartDay = Math.max(borrowDate.getDayOfMonth(), 1);
            int borrowEndDay = returnDate != null ? Math.min(returnDate.getDayOfMonth(), daysInMonth) : daysInMonth;
            double startX = marginLeft + (borrowStartDay - 1) * columnWidth;
            double endX = marginLeft + (borrowEndDay - 1) * columnWidth;
            gc.setStroke(Color.GREEN);
            gc.strokeLine(startX, y, endX, y);

            // Draw the late period (red line)
            if (actualReturnDate != null && actualReturnDate.isAfter(returnDate)) {
                double lateStartX = endX;
                double lateEndX = marginLeft + (Math.min(actualReturnDate.getDayOfMonth(), daysInMonth) - 1) * columnWidth;
                gc.setStroke(Color.RED);
                gc.strokeLine(lateStartX, y, lateEndX, y);
            }

            // Draw labels for borrow record numbers
            gc.setFill(Color.BLACK);
            gc.fillText("Borrow " + (currentYIndex), marginLeft - 60, y + 4);
        }

        // Adjust the height of the borrow chart container if needed
        borrowChartContainer.setPrefHeight(canvasHeight);

        // Add the canvas to the borrow chart container
        borrowChartContainer.getChildren().clear();
        borrowChartContainer.getChildren().add(canvas);
    }



    /**
     * Handles the event when the "Back" button is clicked.
     * Navigates back to the previous screen.
     * 
     * @param event the action event triggered by clicking the button
     */
    @FXML
    private void getBackBtn(ActionEvent event) {
        SubscriberUI.mainController.goBack();
    }
}
