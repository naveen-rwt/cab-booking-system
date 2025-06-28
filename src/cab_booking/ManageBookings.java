package cab_booking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManageBookings {
    JFrame frame = new JFrame("Manage Booking");
    JTable bookingTable;
    DefaultTableModel tableModel;
    JButton buttonViewBookings = new JButton("View Bookings");
    JButton buttonCancelBooking = new JButton("Cancel Booking");
    JButton buttonBack = new JButton("Back");

    public ManageBookings(String username) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to full screen
        frame.setLayout(new BorderLayout());

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(buttonViewBookings);
        buttonPanel.add(buttonCancelBooking);
        buttonPanel.add(buttonBack);

        buttonViewBookings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBookings(username);
            }
        });

        buttonCancelBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBooking(username);
            }
        });

        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                CabBookingMenu menu = new CabBookingMenu(username);
            }
        });

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Create table model with columns
        String[] columns = {"Booking ID", "Pickup Location", "Drop Location", "Fare", "Distance", "Booking Date"};
        tableModel = new DefaultTableModel(columns, 0);
        bookingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void viewBookings(String username) {
        tableModel.setRowCount(0); // Clear table 

        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "SELECT * FROM passengers WHERE customername = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from result set
                int bookingID = resultSet.getInt("bookingid");
                String pickupLocation = resultSet.getString("pickuplocation");
                String dropLocation = resultSet.getString("droplocation");
                int fare = resultSet.getInt("fare");
                int distance = resultSet.getInt("distance");
                String bookingDate = resultSet.getString("BookingDate");

                // Add row to table model
                tableModel.addRow(new Object[]{bookingID, pickupLocation, dropLocation, fare, distance, bookingDate});
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void cancelBooking(String username) {
        String bookingIDString = JOptionPane.showInputDialog(frame, "Enter Booking ID to cancel:");
        if (bookingIDString != null && !bookingIDString.isEmpty()) {
            try {
                int bookingID = Integer.parseInt(bookingIDString);

                String u = "root";
                String pass = "";
                String url = "jdbc:mysql://localhost:3306/cabbook";

                Connection connection = null;
                PreparedStatement preparedStatementPassengers = null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(url, u, pass);
                    
                    // Check if the booking ID exists
                    String checkBookingQuery = "SELECT * FROM passengers WHERE bookingid = ?";
                    PreparedStatement preparedStatementCheckBooking = connection.prepareStatement(checkBookingQuery);
                    preparedStatementCheckBooking.setInt(1, bookingID);
                    ResultSet resultSetCheckBooking = preparedStatementCheckBooking.executeQuery();
                    if (!resultSetCheckBooking.next()) {
                        JOptionPane.showMessageDialog(frame, "Invalid Booking ID. Please enter a valid ID.");
                        return;
                    }
                    
                    // Update passengers table
                    String updatePassengersQuery = "DELETE FROM passengers WHERE bookingid = ?";
                    preparedStatementPassengers = connection.prepareStatement(updatePassengersQuery);
                    preparedStatementPassengers.setInt(1, bookingID);
                    preparedStatementPassengers.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Booking with ID " + bookingID + " cancelled successfully.");
                } finally {
                    try {
                        if (preparedStatementPassengers != null) {
                            preparedStatementPassengers.close();
                        }
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (NumberFormatException | ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid Booking ID.");
            }
        }
    }


    public static void main(String[] args) {
        String username = "naveen";
        new ManageBookings(username);
    }
}
