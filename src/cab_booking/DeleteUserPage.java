package cab_booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteUserPage {
    private JFrame frame = new JFrame("Delete a User Profile(admin)");
    private JTextField textFieldUsername;

    public DeleteUserPage(String adminName) {
        initialize();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initialize() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        JLabel labelTitle = new JLabel("DELETE USER");
        labelTitle.setBounds(350, 50, 400, 50);
        panel.add(labelTitle);
        
        JLabel labelUsername = new JLabel("Username:");
        labelUsername.setBounds(130, 140, 100, 30);
        panel.add(labelUsername);
        
        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(250, 140, 150, 30);
        panel.add(textFieldUsername);

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setBounds(150, 190, 100, 30);
        buttonDelete.addActionListener(e -> deleteUser());
        panel.add(buttonDelete);

        JButton buttonBack = new JButton("Back to Main Menu");
        buttonBack.setBounds(300, 190, 150, 30);
        buttonBack.addActionListener(e -> navigateToMainMenu());
        panel.add(buttonBack);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void deleteUser() {
        String username = textFieldUsername.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a username.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String deleteQuery = "DELETE FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, username);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(frame, "User '" + username + "' deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "User '" + username + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error deleting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToMainMenu() {
        frame.dispose();
        new AdminMenuPage(null);
    }

    public static void main(String[] args) {
        String adminName = "admin";
        new DeleteUserPage(adminName);
    }
}
