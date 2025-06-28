package cab_booking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerviewprofile {
    private JFrame frame;
    private JLabel firstnameLabel;
    private JLabel usernameLabel;
    private JLabel lastnameLabel;
    private JLabel emailIdLabel;
    private JLabel mobileLabel;
    private JLabel genderLabel;
   private JButton buttonmainmenu = new JButton("Return to main menu");
    private JButton buttonupdate = new JButton("UPDATE");
   public customerviewprofile(String username) {
        frame = new JFrame("Customer Profile");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(7, 2, 10, 10));
    usernameLabel = new JLabel("Username:");
        firstnameLabel = new JLabel("First Name:");
        lastnameLabel = new JLabel("Last Name:");
        emailIdLabel = new JLabel("Email:");
        mobileLabel = new JLabel("Mobile:");
        genderLabel = new JLabel("Gender:");

        JLabel usernameValueLabel = new JLabel();
        JLabel firstnameValueLabel = new JLabel();
        JLabel lastnameValueLabel = new JLabel();
        JLabel emailIdValueLabel = new JLabel();
        JLabel mobileValueLabel = new JLabel();
        JLabel genderValueLabel = new JLabel();

        panel.add(usernameLabel);
        panel.add(usernameValueLabel);
        panel.add(firstnameLabel);
        panel.add(firstnameValueLabel);
        panel.add(lastnameLabel);
        panel.add(lastnameValueLabel);
        panel.add(emailIdLabel);
        panel.add(emailIdValueLabel);
        panel.add(mobileLabel);
        panel.add(mobileValueLabel);
        panel.add(genderLabel);
        panel.add(genderValueLabel);
   panel.add(buttonmainmenu);
        panel.add(buttonupdate);
 buttonmainmenu.addActionListener(e -> {
            frame.dispose();
            new CabBookingMenu(username);
        });

        frame.add(panel, BorderLayout.CENTER);

        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                usernameValueLabel.setText(resultSet.getString("username"));
                firstnameValueLabel.setText(resultSet.getString("firstname"));
                lastnameValueLabel.setText(resultSet.getString("lastname"));
                emailIdValueLabel.setText(resultSet.getString("email"));
                mobileValueLabel.setText(resultSet.getString("mobile"));
                genderValueLabel.setText(resultSet.getString("gender"));
            } else {
                JOptionPane.showMessageDialog(frame, "User profile not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching profile information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        buttonupdate.addActionListener(e -> {
            frame.dispose();
            new UpdateProfile(username);
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        String username = "sachin";
        new customerviewprofile(username);
    }
}
