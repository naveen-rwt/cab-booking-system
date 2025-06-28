package cab_booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserForgotPassword {
	
    JFrame frame = new JFrame("User Forgot Password");
    JLabel labeltitle = new JLabel("FORGOT PASSWORD");
    JLabel labelUsername = new JLabel("Username:");
    JLabel labelSecurityQuestion = new JLabel("Security Question:");
    JLabel labelEmail = new JLabel("Email Address:");
    JTextField textFieldUsername = new JTextField();
    JTextField textFieldSecurityQuestion = new JTextField();
    JComboBox<String> securityQuestionComboBox = new JComboBox<>();
    JTextField textFieldEmail = new JTextField();
    JButton buttonCheck = new JButton("Check");
    JButton buttonback = new JButton("back");

    public UserForgotPassword() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLayout(null); 
        // Calculate positions based on frame size
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font labelFont1 = new Font("Arial", Font.BOLD, 30);
        labeltitle.setFont(labelFont1);
        labelUsername.setFont(labelFont);
        labelSecurityQuestion.setFont(labelFont);
        labelEmail.setFont(labelFont); 
        securityQuestionComboBox.addItem("Favorite Color");
        securityQuestionComboBox.addItem("Favorite Country");
        securityQuestionComboBox.addItem("nickname");
     securityQuestionComboBox.setSelectedIndex(0);
      labeltitle.setBounds(frameWidth / 4+10, frameHeight / 4-70, 400, 40);
        labelUsername.setBounds(frameWidth / 4, frameHeight / 4, 120, 30);
        textFieldUsername.setBounds(frameWidth / 4 + 280, frameHeight / 4, 200, 30);
        labelSecurityQuestion.setBounds(frameWidth / 4, frameHeight / 4 + 50, 150, 30);
        textFieldSecurityQuestion.setBounds(frameWidth / 4 + 280, frameHeight / 4 + 50, 200,30);
        securityQuestionComboBox.setBounds(frameWidth / 4 + 140, frameHeight / 4 + 50, 130, 30);
        labelEmail.setBounds(frameWidth / 4, frameHeight / 4 + 100, 120, 30);
        textFieldEmail.setBounds(frameWidth / 4 + 280, frameHeight / 4 + 100, 200,30);
        buttonCheck.setBounds(frameWidth / 4, frameHeight / 4 + 200, 100, 30);
        buttonback.setBounds(frameWidth / 4+150, frameHeight / 4 + 200, 100, 30);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16);
        textFieldUsername.setFont(textFieldFont);
        textFieldSecurityQuestion.setFont(textFieldFont);
        textFieldEmail.setFont(textFieldFont);
  buttonCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String securityQuestion = textFieldSecurityQuestion.getText();
                String email = textFieldEmail.getText();
               if (username.isEmpty() || securityQuestion.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!");
                } else {
                    try {
                        if (checkUserDetails(username, securityQuestion, email)) {
                          openResetPasswordFrame(username);
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect user details. Please try again.");
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
   buttonback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
frame.dispose();
new LoginForm();
            }});
       frame.add(labeltitle);
        frame.add(labelUsername);
        frame.add(textFieldUsername);
        frame.add(labelSecurityQuestion);
        frame.add(textFieldSecurityQuestion);
        frame.add(securityQuestionComboBox);
        frame.add(labelEmail);
        frame.add(textFieldEmail);
        frame.add(buttonCheck);
        frame.add(buttonback);
      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
  private void openResetPasswordFrame(String username) {
        frame.dispose(); 
   new UserResetPassword(username);
    }
  private boolean checkUserDetails(String username, String securityQuestion, String email)
            throws ClassNotFoundException, SQLException {
        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";
   try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "SELECT * FROM users WHERE username = ? AND email = ?  AND securityquestion =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, securityQuestion);
   ResultSet resultSet = preparedStatement.executeQuery();
            boolean isValid = resultSet.next();
   resultSet.close();
            preparedStatement.close();
            connection.close();
   return isValid;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
 return false;
    }
public static void main(String[] args) {
        new UserForgotPassword();
    }
}
