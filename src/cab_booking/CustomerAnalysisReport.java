package cab_booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class CustomerAnalysisReport {
    private JFrame frame = new JFrame("Customer Analysis Report");
    private JPanel chartPanel = new JPanel(new BorderLayout());
    private JPanel pieChartPanel = new JPanel(new BorderLayout());
    private JTextArea reportArea = new JTextArea();
    private JComboBox<String> locationComboBox;
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private DefaultPieDataset pieDataset = new DefaultPieDataset();
    private String adminName;
    private JButton buttonBack;

    public CustomerAnalysisReport(String adminName) {
        this.adminName = adminName;
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Customer Analysis Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel locationLabel = new JLabel("Select Location:");
        controlPanel.add(locationLabel);
        String[] locations = {"Pickup", "Drop"};
        locationComboBox = new JComboBox<>(locations);
        locationComboBox.addActionListener(e -> generateAndDisplayReport());
        controlPanel.add(locationComboBox);
        panel.add(controlPanel, BorderLayout.CENTER);

        buttonBack = new JButton("Return to main menu");
        buttonBack.addActionListener(e -> {
            frame.dispose();
            new AdminMenuPage(adminName);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(buttonBack);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.add(pieChartPanel, BorderLayout.EAST);
        frame.add(new JScrollPane(reportArea), BorderLayout.SOUTH);
    }

    private void generateAndDisplayReport() {
        dataset.clear();
        pieDataset.clear();
        reportArea.setText("");

        String selectedLocation = (String) locationComboBox.getSelectedItem();
        String query = "";

        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            switch (selectedLocation) {
                case "Pickup":
                    query = "SELECT PickupLocation, COUNT(*) AS TripCount FROM passengers GROUP BY PickupLocation ORDER BY TripCount DESC LIMIT 7";
                    break;
                case "Drop":
                    query = "SELECT DropLocation, COUNT(*) AS TripCount FROM passengers GROUP BY DropLocation ORDER BY TripCount DESC LIMIT 7";
                    break;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            int totalTrips = 0;
            while (resultSet.next()) {
                String location = resultSet.getString(1);
                int tripCount = resultSet.getInt(2);
                totalTrips += tripCount;
                dataset.addValue(tripCount, "Trips", location);
                if (dataset.getRowCount() <= 10) {
                    pieDataset.setValue(location, tripCount);
                } else {
                    pieDataset.setValue("Others", totalTrips - tripCount);
                }
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            reportArea.setText("Error fetching data: " + ex.getMessage());
        }

        // Creating and displaying bar chart
        JFreeChart barChart = ChartFactory.createBarChart("Top Trip Count by Location", "Location", "Number of Trips", dataset);
        chartPanel.removeAll();
        chartPanel.add(new ChartPanel(barChart), BorderLayout.CENTER);

        // Creating and displaying pie chart
        JFreeChart pieChart = ChartFactory.createPieChart("Top Trip Count Distribution", pieDataset, true, true, false);
        pieChartPanel.removeAll();
        pieChartPanel.add(new ChartPanel(pieChart), BorderLayout.CENTER);

        // Refresh frame
        frame.revalidate();
        frame.repaint();
    }


    public static void main(String[] args) {
        String adminName = "admin";
        new CustomerAnalysisReport(adminName);
    }
}
