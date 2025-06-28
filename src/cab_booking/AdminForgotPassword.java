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

public class AdminForgotPassword {
    JFrame frame = new JFrame("Admin Forgot Password");
    JLabel labeltitle = new JLabel("FORGOT PASSWORD");
    JLabel labelAdminName = new JLabel("Admin Name:");
    JLabel labelSecurityQuestion = new JLabel("Security Question:");
    JLabel labelEmail = new JLabel("Email Address:");
    JTextField textFieldAdminName = new JTextField();
    JTextField textFieldSecurityQuestion = new JTextField();
    JTextField textFieldEmail = new JTextField();
    JButton buttonCheck = new JButton("Check");
    JComboBox<String> securityQuestionComboBox = new JComboBox<>();
    JButton buttonback = new JButton("BACK");
    public AdminForgotPassword() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLayout(null); 
        
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

       
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font labelFont2 = new Font("Arial", Font.BOLD, 35);
        labeltitle.setFont(labelFont2);
        labelAdminName.setFont(labelFont);
        labelSecurityQuestion.setFont(labelFont);
        labelEmail.setFont(labelFont);

        securityQuestionComboBox.addItem("Favorite Color");
        securityQuestionComboBox.addItem("Favorite Country");
        securityQuestionComboBox.addItem("nick name");

        
        securityQuestionComboBox.setSelectedIndex(0);
        
        
        labeltitle.setBounds(frameWidth / 4+50, frameHeight / 4-80, 500, 40);
        labelAdminName.setBounds(frameWidth / 4, frameHeight / 4, 120, 25);
        textFieldAdminName.setBounds(frameWidth / 4 + 265, frameHeight / 4, 200, 25);
        labelSecurityQuestion.setBounds(frameWidth / 4, frameHeight / 4 + 40, 150, 25);
        securityQuestionComboBox.setBounds(frameWidth / 4 + 140, frameHeight / 4 + 40, 125, 25);
        textFieldSecurityQuestion.setBounds(frameWidth / 4 + 265, frameHeight / 4 + 40, 200, 25);
       
        labelEmail.setBounds(frameWidth / 4, frameHeight / 4 + 80, 120, 25);
        textFieldEmail.setBounds(frameWidth / 4 + 265, frameHeight / 4 + 80, 200, 25);
        buttonCheck.setBounds(frameWidth / 4, frameHeight / 4 + 120, 100, 30);
        buttonback.setBounds(frameWidth / 4+35, frameHeight / 4+165 + 110, 100, 30);
      
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16);
        textFieldAdminName.setFont(textFieldFont);
        textFieldSecurityQuestion.setFont(textFieldFont);
        textFieldEmail.setFont(textFieldFont);

        buttonCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adminName = textFieldAdminName.getText();
                String securityQuestion = textFieldSecurityQuestion.getText();
                String email = textFieldEmail.getText();

                // Validating empty fields
                if (adminName.isEmpty() || securityQuestion.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!");
                } else {
                    try {
                        
                        if (checkAdminDetails(adminName, securityQuestion, email)) {
                            
                            openResetPasswordFrame(adminName); // 
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect admin details. Please try again.");
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
        new AdminLogin(); 
            }}
        );
        
        frame.add(labeltitle);
        frame.add(labelAdminName);
        frame.add(textFieldAdminName);
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

    private void openResetPasswordFrame(String adminName) {
        frame.dispose(); 
        new adminResetPassword(adminName);     }

    private boolean checkAdminDetails(String adminName, String securityQuestion, String email)
            throws ClassNotFoundException, SQLException {
        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "SELECT * FROM admin WHERE username = ? AND securityquestion = ? AND emailId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, adminName);
            preparedStatement.setString(2, securityQuestion);
            preparedStatement.setString(3, email);

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
        new AdminForgotPassword();
    }
}
