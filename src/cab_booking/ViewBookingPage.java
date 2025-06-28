package cab_booking;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewBookingPage {
    private JFrame frame;
    private JTable bookingsTable;
    private JButton buttonBack;

    public ViewBookingPage(String adminName) {
        frame = new JFrame("View Bookings(admin)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create table model with column names
        String[] columnNames = {"Customer Name", "Pickup Location", "Drop Location", "Fare", "Distance", "Booking ID", "Bill ID", "Booking Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Create table and set model
        bookingsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        buttonBack = new JButton("Back");
        buttonBack.addActionListener(e -> {
            frame.dispose();
            new AdminMenuPage(adminName);
            // Add functionality to return to main menu
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(buttonBack);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        displayBookings(model);
    }

    private void displayBookings(DefaultTableModel model) {
        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "SELECT * FROM passengers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String customerName = resultSet.getString("customername");
                String pickupLocation = resultSet.getString("pickuplocation");
                String dropLocation = resultSet.getString("droplocation");
                int fare = resultSet.getInt("fare");
                int distance = resultSet.getInt("distance");
                int bookingID = resultSet.getInt("bookingid");
                int billID = resultSet.getInt("billid");
                String bookingDate = resultSet.getString("BookingDate");

                // Add row to the table model
                model.addRow(new Object[]{customerName, pickupLocation, dropLocation, fare, distance, bookingID, billID, bookingDate});
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	 String adminName = "admin";
    	new ViewBookingPage(adminName);
    }
}
