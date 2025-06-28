package cab_booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cabBookingSystem {
    JFrame frame = new JFrame("Welcome Page");
    JLabel emptylabel = new JLabel();
    JLabel labelTitle = new JLabel("Welcome to the Cab Buddy");
    JButton buttonAboutUs = new JButton("About Us");
    JButton buttonAdminLogin = new JButton("Admin Login");
    JButton buttonUserLogin = new JButton("User Login");

    cabBookingSystem() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        labelTitle.setFont(new Font("Arial", Font.BOLD, 60));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(labelTitle, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(150, 150, 250, 150)); // Adding padding

        buttonAdminLogin.setFont(new Font("Arial", Font.BOLD, 35));
        buttonUserLogin.setFont(new Font("Arial", Font.BOLD, 35));
        buttonAboutUs.setFont(new Font("Arial", Font.BOLD, 35));

        // Setting button colors
        buttonAdminLogin.setBackground(new Color(255, 92, 92)); // Red
        buttonUserLogin.setBackground(new Color(92, 184, 92)); // Green
        buttonAboutUs.setBackground(new Color(92, 92, 255));    // Blue

        buttonPanel.add(buttonAdminLogin);
        buttonPanel.add(buttonUserLogin);
        buttonPanel.add(buttonAboutUs);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Button actions
        buttonAdminLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLogin();
                frame.dispose();
            }
        });

        buttonUserLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginForm();
                frame.dispose();
            }
        });

        buttonAboutUs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutUs();
                frame.dispose();
            }
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        cabBookingSystem cab1 = new cabBookingSystem();
    }
}
