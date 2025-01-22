//import java.beans.Statement;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.XYChart;
//import javafx.scene.control.Button;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//
//public class ReportsWindow {
//
//    @FXML
//    private Button btnMonthlyStatus;
//
//    @FXML
//    private Button btnMonthlyBorrowTimes;
//
//    @FXML
//    private Button btnBack;
//    
//    @FXML
//    private LineChart<Number, Number> statusChart; // For Monthly Status Report
//
//    @FXML
//    private LineChart<Number, Number> borrowChart; // For Monthly Borrow Times Report
//    
//    private Connection connection;
//    
//    
//    @FXML
//    private void handleMonthlyStatusReport(ActionEvent event) {
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT id, status FROM subscriber");
//
//            XYChart.Series<Number, Number> seriesActive = new XYChart.Series<>();
//            XYChart.Series<Number, Number> seriesFrozen = new XYChart.Series<>();
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String status = resultSet.getString("status");
//
//                if ("Active".equals(status)) {
//                    seriesActive.getData().add(new XYChart.Data<>(id, 1)); // Active
//                } else if ("Frozen".equals(status)) {
//                    seriesFrozen.getData().add(new XYChart.Data<>(id, 0)); // Frozen
//                }
//            }
//
//            statusChart.getData().addAll(seriesActive, seriesFrozen);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void handleMonthlyBorrowTimesReport(ActionEvent event) {
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT book_name, borrow_date, return_date FROM borrow");
//
//            XYChart.Series<Number, Number> series = new XYChart.Series<>();
//
//            while (resultSet.next()) {
//                String bookName = resultSet.getString("book_name");
//                String borrowDate = resultSet.getString("borrow_date");
//                String returnDate = resultSet.getString("return_date");
//
//                // Calculate the duration and plot the green/red lines
//                // (Assuming you are converting the dates to appropriate day numbers)
//                int startDay = getDayOfMonth(borrowDate);
//                int endDay = getDayOfMonth(returnDate);
//
//                // Green line: Borrowing period
//                for (int day = startDay; day <= endDay; day++) {
//                    series.getData().add(new XYChart.Data<>(day, bookName));
//                }
//
//                // Red line: Overdue period
//                if (endDay > startDay + 14) { // Example: Overdue after 14 days
//                    for (int day = endDay; day <= 30; day++) {
//                        series.getData().add(new XYChart.Data<>(day, bookName));
//                    }
//                }
//            }
//
//            borrowChart.getData().add(series);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    private int getDayOfMonth(String date) {
//        try {
//            // Define the date format for "yyyyMMdd"
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//
//            // Parse the date string into a Date object
//            Date parsedDate = sdf.parse(date);
//
//            // Extract and return the day of the month
//            return parsedDate.getDate();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1; // Return -1 in case of error (invalid date format)
//        }
//    }
//    
//    
//    
//    
//    
//    @FXML
//    private void getBackBtn(ActionEvent event) {
//        try {
//            // Close the current window
//            ((Node) event.getSource()).getScene().getWindow().hide();
//
//            // Load the previous screen (Librarian Main Menu)
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LibrarianMainMenu.fxml"));
//            Pane root = loader.load();
//
//            // Set up the new stage
//            Stage stage = new Stage();
//            Scene scene = new Scene(root);
//            stage.setTitle("Librarian Main Menu");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Failed to load LibrarianMainMenu.fxml.");
//       }
//    }
//}