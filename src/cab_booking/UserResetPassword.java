package cab_booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserResetPassword {
    JFrame frame = new JFrame("Reset Password");
    JLabel labelNewPassword = new JLabel("New Password:");
    JPasswordField passwordFieldNewPassword = new JPasswordField();
    JLabel labelConfirmPassword = new JLabel("Confirm Password:");
    JPasswordField passwordFieldConfirmPassword = new JPasswordField();
    JButton buttonReset = new JButton("Reset");

    public UserResetPassword(String username) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(null); 

       
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        labelNewPassword.setFont(labelFont);
        labelConfirmPassword.setFont(labelFont);


        labelNewPassword.setBounds(frameWidth / 4, frameHeight / 4, 150, 25);
        passwordFieldNewPassword.setBounds(frameWidth / 4 + 180, frameHeight / 4, 200, 25);
        labelConfirmPassword.setBounds(frameWidth / 4, frameHeight / 4 + 40, 150, 25);
        passwordFieldConfirmPassword.setBounds(frameWidth / 4 + 180, frameHeight / 4 + 40, 200, 25);
        buttonReset.setBounds(frameWidth / 4, frameHeight / 4 + 80, 100, 30);

        JCheckBox showPasswordCheckbox1 = new JCheckBox("Show Password");
        showPasswordCheckbox1.setFont(new Font("Arial", Font.PLAIN, 15)); 
        showPasswordCheckbox1.setBounds(frameWidth / 4 + 400, frameHeight /4, 400, 25); 
        frame.add(showPasswordCheckbox1);

        JCheckBox showPasswordCheckbox2 = new JCheckBox("Show Password");
        showPasswordCheckbox2.setFont(new Font("Arial", Font.PLAIN, 15)); 
        showPasswordCheckbox2.setBounds(frameWidth / 4 + 400, frameHeight / 4+40, 200, 25); 
        frame.add(showPasswordCheckbox2);

        Font passwordFieldFont = new Font("Arial", Font.PLAIN, 16);
        passwordFieldNewPassword.setFont(passwordFieldFont);
        passwordFieldConfirmPassword.setFont(passwordFieldFont);

        showPasswordCheckbox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                if (checkBox.isSelected()) {
                    passwordFieldNewPassword.setEchoChar((char) 0); // Show password
                } else {
                    passwordFieldNewPassword.setEchoChar('*'); // Hide password
                }
            }
        });
        
        showPasswordCheckbox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                if (checkBox.isSelected()) {
                    passwordFieldConfirmPassword.setEchoChar((char) 0); // Show password
                } else {
                    passwordFieldConfirmPassword.setEchoChar('*'); // Hide password
                }
            }
        });
                buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = String.valueOf(passwordFieldNewPassword.getPassword());
                String confirmPassword = String.valueOf(passwordFieldConfirmPassword.getPassword());

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!");
                } else if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.");
                } else {
                    try {
                        
                        if (updatePassword(username, newPassword)) {
                            JOptionPane.showMessageDialog(null, "Password reset successful!");
                            frame.dispose();
                            new LoginForm();
                        } else {
                            JOptionPane.showMessageDialog(null, "Password reset failed. Please try again.");
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        frame.add(labelNewPassword);
        frame.add(passwordFieldNewPassword);
        frame.add(labelConfirmPassword);
        frame.add(passwordFieldConfirmPassword);
        frame.add(buttonReset);
 
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private boolean updatePassword(String username, String newPassword)
            throws ClassNotFoundException, SQLException {
        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "UPDATE users SET password = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);

            int rowsUpdated = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
       
       
        new UserResetPassword("username");
    }
}
