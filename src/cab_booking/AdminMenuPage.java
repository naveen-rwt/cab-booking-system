package cab_booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class AdminMenuPage {
    JFrame frame = new JFrame("Admin Menu");
    private String adminName;

    public AdminMenuPage(String adminName) {
        this.adminName = adminName;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        Font menuFont = new Font("Arial", Font.PLAIN, 18);

        JMenu manageUsersMenu = new JMenu("Manage Users");
        manageUsersMenu.setFont(menuFont);

        JMenuItem viewUsersItem = new JMenuItem("View Users");
        viewUsersItem.addActionListener(e -> {
            frame.dispose();
            new ViewUsers(adminName);
        });
        viewUsersItem.setFont(menuFont);
        viewUsersItem.setToolTipText("View all users");
        manageUsersMenu.add(viewUsersItem);

        JMenuItem deleteUserItem = new JMenuItem("Delete User");
        deleteUserItem.addActionListener(e -> {
            frame.dispose();
            new DeleteUserPage(adminName);          
        });
        deleteUserItem.setFont(menuFont);
        deleteUserItem.setToolTipText("Delete a user");
        manageUsersMenu.add(deleteUserItem);

        menuBar.add(manageUsersMenu);

        JMenu manageBookingsMenu = new JMenu("Manage Bookings");
        manageBookingsMenu.setFont(menuFont);

        JMenuItem viewBookingsItem = new JMenuItem("View Bookings");
        viewBookingsItem.addActionListener(e -> {
            frame.dispose();
            new ViewBookingPage(adminName);       
        });
        viewBookingsItem.setFont(menuFont);
        viewBookingsItem.setToolTipText("View all bookings");
        manageBookingsMenu.add(viewBookingsItem);

        JMenuItem cancelBookingItem = new JMenuItem("Cancel Booking");
        cancelBookingItem.addActionListener(e -> {
            frame.dispose();
            new DeleteBookingPage(adminName);    
        });
        cancelBookingItem.setFont(menuFont);
        cancelBookingItem.setToolTipText("Cancel a booking");
        manageBookingsMenu.add(cancelBookingItem);

        menuBar.add(manageBookingsMenu);

        JMenu viewReportsMenu = new JMenu("View Reports");
        viewReportsMenu.setFont(menuFont);

        JMenuItem viewSalesReportItem = new JMenuItem("Sales Report");
        viewSalesReportItem.addActionListener(e -> {
            frame.dispose();
            new SalesReport(adminName);    
        });
        viewSalesReportItem.setFont(menuFont);
        viewSalesReportItem.setToolTipText("View sales report");
        viewReportsMenu.add(viewSalesReportItem);

        JMenuItem viewUsageReportItem = new JMenuItem("Analysis Report");
        viewUsageReportItem.addActionListener(e -> {
            frame.dispose();
            new CustomerAnalysisReport(adminName);                
        });
        viewUsageReportItem.setFont(menuFont);
        viewUsageReportItem.setToolTipText("View Analysis report");
        viewReportsMenu.add(viewUsageReportItem);

        menuBar.add(viewReportsMenu);

        // Add Feedback View menu
        JMenu feedbackViewMenu = new JMenu(" View Feedback");
        feedbackViewMenu.setFont(menuFont);
        
        JMenuItem feedbackViewItem = new JMenuItem("View Feedback");
        feedbackViewItem.addActionListener(e -> {
            frame.dispose();
            new FeedbackView(adminName);    
        });
        feedbackViewItem.setFont(menuFont);
        feedbackViewItem.setToolTipText("View feedback from users");
        feedbackViewMenu.add(feedbackViewItem);

        menuBar.add(feedbackViewMenu);

        JMenu logoutMenu = new JMenu("Logout");
        logoutMenu.setFont(menuFont);
       
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.setFont(menuFont);
        logoutItem.setToolTipText("Logout from admin account");
        logoutItem.addActionListener(e -> {
            frame.dispose();
            new cabBookingSystem(); 
        });
        logoutMenu.add(logoutItem);
        menuBar.add(logoutMenu);

        frame.setJMenuBar(menuBar);

        // Set background image
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("C:\\Users\\HP\\Downloads\\bgimage.jpg"));
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            frame.setContentPane(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        String adminName = "admin";
        AdminMenuPage adminMenuPage = new AdminMenuPage(adminName);
    }
}
