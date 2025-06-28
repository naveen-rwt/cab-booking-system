package cab_booking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class SalesReport {
    private JFrame frame = new JFrame("Sales Report");
    private JPanel chartPanel = new JPanel(new BorderLayout());
    private JTextArea reportArea = new JTextArea();
    private JComboBox<String> dateRangeComboBox;
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private String adminName;
    private JButton buttonBack;

    public SalesReport(String adminName) {
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
        JLabel titleLabel = new JLabel("Sales Report");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel dateRangeLabel = new JLabel("Date Range:");
        controlPanel.add(dateRangeLabel);
        String[] dateRanges = {"Today", "Last 7 days", "Last 30 days", "All Dates"}; // Added "All Dates"
        dateRangeComboBox = new JComboBox<>(dateRanges);
        dateRangeComboBox.addActionListener(e -> generateSalesReport());
        controlPanel.add(dateRangeComboBox);
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
        frame.add(new JScrollPane(reportArea), BorderLayout.SOUTH);
    }

    private void generateSalesReport() {
        dataset.clear();
        reportArea.setText("");

        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "";
            switch (dateRangeComboBox.getSelectedIndex()) {
                case 0: // Today
                    query = "SELECT DATE(BookingDate) AS Date, SUM(fare) AS total_sales FROM passengers WHERE DATE(BookingDate) = CURDATE() GROUP BY Date";
                    break;
                case 1: // Last 7 days
                    query = "SELECT DATE(BookingDate) AS Date, SUM(fare) AS total_sales FROM passengers WHERE BookingDate >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) GROUP BY Date";
                    break;
                case 2: // Last 30 days
                    query = "SELECT DATE_FORMAT(BookingDate, '%d-%m') AS Date, SUM(fare) AS total_sales " +
                            "FROM passengers " +
                            "WHERE BookingDate >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                            "GROUP BY Date";
                    break;
                case 3: // All Dates
                    query = "SELECT MONTH(BookingDate) AS DATE, SUM(fare) AS total_sales FROM passengers GROUP BY DATE";
                    
                    break;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String date = resultSet.getString("Date");
                int totalSales = resultSet.getInt("total_sales");
                dataset.addValue(totalSales, "Sales", date);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            reportArea.setText("Error fetching sales data: " + ex.getMessage());
        }

        JFreeChart chart = ChartFactory.createBarChart("Sales Report", "Date", "Total Sales", dataset);
        chartPanel.removeAll();
        chartPanel.add(new ChartPanel(chart), BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public static void main(String[] args) {
        String adminName = "admin";
        new SalesReport(adminName);
    }
}
