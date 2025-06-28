package cab_booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class adminResetPassword extends JFrame {
    JLabel labelUsername = new JLabel();
    JLabel labelNewPassword = new JLabel("New Password:");
    JLabel labelConfirmPassword = new JLabel("Confirm Password:");
    JPasswordField passwordFieldNewPassword = new JPasswordField();
    JPasswordField passwordFieldConfirmPassword = new JPasswordField();
    JButton buttonResetPassword = new JButton("Reset Password");

    public adminResetPassword(String adminName) {
        setTitle("Admin Reset Password");
        setSize(600, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        int frameWidth = getWidth();
        int frameHeight = getHeight();

        labelUsername.setText("Username= " + adminName);
        labelUsername.setBounds(50, 30, 200, 40); 
        labelUsername.setFont(new Font("Arial", Font.BOLD, 20)); 

        labelNewPassword.setBounds(50, 100, 150, 40); // 
        labelNewPassword.setFont(new Font("Arial", Font.PLAIN, 20)); 
        passwordFieldNewPassword.setBounds(240, 100, 300, 40); 
        passwordFieldNewPassword.setFont(new Font("Arial", Font.PLAIN, 20)); 

        labelConfirmPassword.setBounds(50, 180, 180, 40); 
        labelConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 20)); 
        passwordFieldConfirmPassword.setBounds(240, 180, 300, 40); 
        passwordFieldConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 20));

        buttonResetPassword.setBounds(150, 240, 200, 50); 
        buttonResetPassword.setFont(new Font("Arial", Font.PLAIN, 20)); 

        buttonResetPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(passwordFieldNewPassword.getPassword());
                String confirmPassword = new String(passwordFieldConfirmPassword.getPassword());

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Both fields are required!");
                } else if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
                } else {
                    try {
                        String u = "root";
                        String pass = "";
                        String url = "jdbc:mysql://localhost:3306/cabbook";

                        Class.forName("com.mysql.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(url, u, pass);
                        String query = "UPDATE admin SET password = ? WHERE username = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, newPassword);
                        preparedStatement.setString(2, adminName); // Using the passed username
                        int rowsUpdated = preparedStatement.executeUpdate();
                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(null, "Password updated successfully!");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update password. Please try again.");
                        }

                        preparedStatement.close();
                        connection.close();
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        add(labelUsername);
        add(labelNewPassword);
        add(passwordFieldNewPassword);
        add(labelConfirmPassword);
        add(passwordFieldConfirmPassword);
        add(buttonResetPassword);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        
       String adminName = "admin"; 
        new adminResetPassword(adminName);
    }
}
