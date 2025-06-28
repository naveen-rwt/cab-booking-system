package cab_booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteProfile {
    private JFrame frame = new JFrame("Delete Profile");
    private JTextField textFieldUsername;

    public  DeleteProfile(String username) {
        initialize();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initialize() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        JLabel labeltitle = new JLabel("DELETE PROFILE");
        labeltitle.setBounds(350, 50, 400, 50);
        panel.add(labeltitle);
        
        JLabel labelUsername = new JLabel("Username:");
        labelUsername.setBounds(130, 140, 100, 30);
        panel.add(labelUsername);
        
        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(250, 140, 150, 30);
        panel.add(textFieldUsername);

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setBounds(150, 190, 100, 30);
        buttonDelete.addActionListener(e -> deleteProfile());
        panel.add(buttonDelete);

        JButton buttonmainmenu = new JButton("Back to Main Menu");
        buttonmainmenu.setBounds(300, 190, 150, 30);
        buttonmainmenu.addActionListener(e -> navigateToMainMenu());
        panel.add(buttonmainmenu);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void deleteProfile() {
        String username = textFieldUsername.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a username.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the profile?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
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
               JOptionPane.showMessageDialog(frame, "Profile deleted successfully.");
           } else {
               JOptionPane.showMessageDialog(frame, "Profile with username '" + username + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
           JOptionPane.showMessageDialog(frame, "Error deleting profile: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
       } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
    private void navigateToMainMenu() {
        frame.dispose();
        new CabBookingMenu(null); 
    }

    public static void main(String[] args) {
    	 String username = "sachin";
         new DeleteProfile(username);
    }
}
