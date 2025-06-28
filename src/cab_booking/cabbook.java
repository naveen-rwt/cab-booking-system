package cab_booking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class cabbook {
    JFrame frame = new JFrame("Book Cab");
 // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cabbook";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    public cabbook() {
    }
    public cabbook(String username) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(-7, -7, 1300, 687);
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font textFont = new Font("Arial", Font.PLAIN, 16);
        JLabel labelCustomerName = new JLabel("Customer Name:");
        labelCustomerName.setFont(labelFont);
        JTextField textFieldCustomerName = new JTextField(20);
        textFieldCustomerName.setFont(textFont);
        JLabel labelCabType = new JLabel("Cab Type:");
        labelCabType.setFont(labelFont);
        JTextField textFieldCabType = new JTextField(10);
        textFieldCabType.setFont(textFont);
        JComboBox<String> cabtypeComboBox = new JComboBox<>();
        cabtypeComboBox.addItem("SUV");
        cabtypeComboBox.addItem("Auto-Rikshaw");
        // cabtypeComboBox.addItem("Sedan");
        cabtypeComboBox.setSelectedIndex(0);
        JLabel labelPickupLocation = new JLabel("Pickup Location:");
        labelPickupLocation.setFont(labelFont);
        JTextField textFieldPickupLocation = new JTextField(20);
        textFieldPickupLocation.setFont(textFont);
        JLabel labelDropLocation = new JLabel("Drop Location:");
        labelDropLocation.setFont(labelFont);
        JTextField textFieldDropLocation = new JTextField(20);
        textFieldDropLocation.setFont(textFont);
        JButton buttonhomemenu = new JButton("Go to home menu");
        buttonhomemenu.setFont(labelFont);
        buttonhomemenu.setBounds(320, 560, 200, 50);

        JButton buttoncalculatefare = new JButton("Calculate Fare");
        buttoncalculatefare.setFont(labelFont);
        buttoncalculatefare.setBounds(560, 560, 200, 50);

        JButton buttonBook = new JButton("Book Now");
        buttonBook.setFont(labelFont);
        buttonBook.setBounds(790, 560, 200, 50);

        buttonhomemenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CabBookingMenu(username);
            }
        });

        buttonBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = textFieldCustomerName.getText();
                String cabType = cabtypeComboBox.getSelectedItem().toString();
                String pickupLocation = textFieldPickupLocation.getText();
                String dropLocation = textFieldDropLocation.getText();
                double distance = calculateDistance(pickupLocation, dropLocation);

                if (distance == -1) {
                    JOptionPane.showMessageDialog(frame, "Invalid locations. Please enter valid pickup and drop locations.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Calculate fare based on distance and cab type
                double fare = calculateFare(cabType, distance);

                // Generating a unique Bill ID
                int billId = generateBillId();
                int bookingid = generateBookingId();
                // Insert booking information into the database
                LocalDate bookingDate = LocalDate.now(); // Get the current date
                insertBookingIntoDatabase(customerName, cabType, pickupLocation, dropLocation, fare, distance, billId, bookingid, bookingDate);
                // Display the fare and generate a receipt
                displayBill(customerName, cabType, pickupLocation, dropLocation, fare, billId);
            }
        });
       buttoncalculatefare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new BillGeneration(username);
            }
        });
        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 180, 180));
        panel.add(labelCustomerName);
        panel.add(textFieldCustomerName);
        panel.add(labelCabType);
        panel.add(cabtypeComboBox);
        panel.add(labelPickupLocation);
        panel.add(textFieldPickupLocation);
        panel.add(labelDropLocation);
        panel.add(textFieldDropLocation);
        frame.add(buttonhomemenu);
        frame.add(buttoncalculatefare);
        frame.add(buttonBook);
        frame.add(panel);
        frame.setVisible(true);
    }

    private double calculateFare(String cabType, double distance) {
        double baseFare = 0.0;
        double ratePerKm = 0.0;

        if (cabType.equalsIgnoreCase("SUV")) {
            baseFare = 100.0;
            ratePerKm = 15.0;
        } else if (cabType.equalsIgnoreCase("Auto-Rikshaw")) {
            baseFare = 50.0;
            ratePerKm = 8.0;
        }

        double fare = baseFare + (ratePerKm * distance);
        return fare;
    }

    private int generateBillId() {
        return (int) (Math.random() * 1000000);
    }

    private int generateBookingId() {
        return (int) (Math.random() * 1000000);
    }

    // Calculate distance between two locations using CityDistanceCalculator
    private double calculateDistance(String pickupLocation, String dropLocation) {
        try {
            double[] pickupCoordinates = CityDistanceCalculator.getCoordinates(pickupLocation);
            double[] dropCoordinates = CityDistanceCalculator.getCoordinates(dropLocation);
            return CityDistanceCalculator.calculateDistance(pickupCoordinates[0], pickupCoordinates[1],
                    dropCoordinates[0], dropCoordinates[1]);
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Return -1 in case of an error
        }
    }

    // Display the fare and generate a receipt
    private void displayBill(String customerName, String cabType, String pickupLocation, String dropLocation,
                             double fare, int billId) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Customer Name: ").append(customerName).append("\n");
        receipt.append("Cab Type: ").append(cabType).append("\n");
        receipt.append("Pickup Location: ").append(pickupLocation).append("\n");
        receipt.append("Drop Location: ").append(dropLocation).append("\n");
        receipt.append("Distance: ").append(String.format("%.2f", calculateDistance(pickupLocation, dropLocation))).append(" km\n");
        receipt.append("Fare: ₹").append(String.format("%.2f", fare)).append("\n");
        receipt.append("Bill ID: ").append(billId).append("\n");
        receipt.append("Thank you for booking!");

        JOptionPane.showMessageDialog(frame, receipt.toString(), "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    private void insertBookingIntoDatabase(String customerName, String cabType, String pickupLocation, String dropLocation,
            double fare, double distance, int billId, int bookingId, LocalDate bookingDate) {
try {
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

// Insert into passengers table (for trip history)
String sqlPassengers = "INSERT INTO passengers (customername, cabtype, pickuplocation, droplocation, fare, distance, bookingid, bookingdate,billid) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
PreparedStatement pstmtPassengers = conn.prepareStatement(sqlPassengers);
pstmtPassengers.setString(1, customerName);
pstmtPassengers.setString(2, cabType);
pstmtPassengers.setString(3, pickupLocation);
pstmtPassengers.setString(4, dropLocation);
pstmtPassengers.setDouble(5, fare);
pstmtPassengers.setDouble(6, distance);
pstmtPassengers.setInt(7, bookingId);
pstmtPassengers.setDate(8, java.sql.Date.valueOf(bookingDate));
pstmtPassengers.setInt(9, billId);
// Convert LocalDate to java.sql.Date
pstmtPassengers.executeUpdate();
pstmtPassengers.close();

// Insert into billing table
String sqlBilling = "INSERT INTO billing (billid, customerName, cabtype, fare, bookingdate) VALUES (?, ?, ?, ?, ?)";
PreparedStatement pstmtBilling = conn.prepareStatement(sqlBilling);
pstmtBilling.setInt(1, billId);
pstmtBilling.setString(2, customerName);
pstmtBilling.setString(3, cabType);
pstmtBilling.setDouble(4, fare);
pstmtBilling.setDate(5, java.sql.Date.valueOf(bookingDate)); // Convert LocalDate to java.sql.Date
pstmtBilling.executeUpdate();
pstmtBilling.close();

// Insert into trip history table
String sqlTripHistory = "INSERT INTO triphistory (customerName, tripdate, source, destination, fare) VALUES (?, ?, ?, ?, ?)";
PreparedStatement pstmtTripHistory = conn.prepareStatement(sqlTripHistory);
pstmtTripHistory.setString(1, customerName);
pstmtTripHistory.setDate(2, java.sql.Date.valueOf(bookingDate)); // Convert LocalDate to java.sql.Date
pstmtTripHistory.setString(3, pickupLocation);
pstmtTripHistory.setString(4, dropLocation);
pstmtTripHistory.setDouble(5, fare);
pstmtTripHistory.executeUpdate();
pstmtTripHistory.close();

conn.close();
} catch (SQLException e) {
e.printStackTrace();
JOptionPane.showMessageDialog(frame, "Failed to save booking information to database.", "Database Error", JOptionPane.ERROR_MESSAGE);
} catch (ClassNotFoundException e) {
e.printStackTrace();
}
}

    public static void main(String[] args) {
        String username = "naveen";
        cabbook c1 = new cabbook(username);
    }
}
