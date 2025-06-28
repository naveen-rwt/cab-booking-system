package cab_booking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteBookingPage {
    private JFrame frame = new JFrame("Delete Booking(Admin)");
    private JTextField textFieldBookingID;
    private String adminName; 

    public DeleteBookingPage(String adminName) { 
        this.adminName = adminName; 
        initialize();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    
    }

    private void initialize() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        JLabel labelTitle = new JLabel("DELETE BOOKING");
        labelTitle.setBounds(350, 50, 400, 50);
        panel.add(labelTitle);
        
        JLabel labelBookingID = new JLabel("Booking ID:");
        labelBookingID.setBounds(130, 140, 100, 30);
        panel.add(labelBookingID);
        
        textFieldBookingID = new JTextField();
        textFieldBookingID.setBounds(250, 140, 150, 30);
        panel.add(textFieldBookingID);

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setBounds(150, 190, 100, 30);
        buttonDelete.addActionListener(e -> deleteBooking());
        panel.add(buttonDelete);

        JButton buttonBack = new JButton("Back to Main Menu");
        buttonBack.setBounds(300, 190, 150, 30);
        buttonBack.addActionListener(e -> navigateToMainMenu());
        panel.add(buttonBack);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void deleteBooking() {
        String bookingIDStr = textFieldBookingID.getText();
        if (bookingIDStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a Booking ID.");
            return;
        }

        int bookingID;
        try {
            bookingID = Integer.parseInt(bookingIDStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid Booking ID. Please enter a valid number.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the booking?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String deleteQuery = "DELETE FROM passengers WHERE bookingid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, bookingID);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(frame, "Booking with ID " + bookingID + " deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Booking with ID " + bookingID + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error deleting booking: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToMainMenu() {
        frame.dispose();
        new AdminMenuPage(adminName); // Assuming adminName is accessible here
    }
    public static void main(String[] args) {
        String adminName = "admin";
        new DeleteBookingPage(adminName);
    }
}
