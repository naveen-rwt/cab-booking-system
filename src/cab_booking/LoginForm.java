package cab_booking;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.util.Timer;
import java.util.TimerTask;

public class LoginForm {
    private int loginAttempts = 0;
    private final int MAX_LOGIN_ATTEMPTS = 3;
    private final int RESET_INTERVAL = 60000;
    private Timer resetTimer;

    private JFrame frame = new JFrame("Login Form(user)");

    LoginForm() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

        try {
            BufferedImage backgroundImage = ImageIO.read(new File("C:\\Users\\HP\\Downloads\\BG1.png"));
            ImageIcon imageIcon = new ImageIcon(backgroundImage);
            JLabel backgroundLabel = new JLabel(imageIcon);
            frame.setContentPane(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel labelTitle = new JLabel("Cab Booking System");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 36)); 
        labelTitle.setForeground(Color.GREEN);
        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        labelTitle.setBounds(40, 50, 600, 70); 
        frame.add(labelTitle);

        JLabel labelUsername = new JLabel("Username:");
        labelUsername.setFont(new Font("Arial", Font.PLAIN, 20)); 
        labelUsername.setForeground(Color.RED);
        labelUsername.setBounds(100, 150, 150, 30); 
        frame.add(labelUsername);

        JTextField textFieldUsername = new JTextField();
        textFieldUsername.setFont(new Font("Arial", Font.PLAIN, 18)); 
        textFieldUsername.setBounds(230, 150, 300, 30); 
        frame.add(textFieldUsername);

        JLabel labelPassword = new JLabel("Password:");
        labelPassword.setForeground(Color.RED);
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        labelPassword.setBounds(100, 230, 150, 30); 
        frame.add(labelPassword);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18)); 
        passwordField.setBounds(230, 230, 300, 30); 
        frame.add(passwordField);

        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setFont(new Font("Arial", Font.PLAIN, 15)); 
        showPasswordCheckbox.setBounds(630, 230, 136, 30); 
        showPasswordCheckbox.setBackground(Color.WHITE);
        frame.add(showPasswordCheckbox);

        JButton buttonSignIn = new JButton("Sign In");
        buttonSignIn.setFont(new Font("Arial", Font.BOLD, 20));
        buttonSignIn.setBounds(200, 300, 150, 50); 
        buttonSignIn.setBackground(Color.GREEN);
        buttonSignIn.setForeground(Color.WHITE);
        buttonSignIn.setFocusPainted(false);
        frame.add(buttonSignIn);

        JButton buttonSignUp = new JButton("Sign Up");
        buttonSignUp.setFont(new Font("Arial", Font.BOLD, 20)); 
        buttonSignUp.setBounds(370, 300, 150, 50); 
        buttonSignUp.setBackground(Color.BLUE);
        buttonSignUp.setForeground(Color.WHITE);
        buttonSignUp.setFocusPainted(false);
        frame.add(buttonSignUp);

        JButton buttonforgotpassword = new JButton("forgot password");
        buttonforgotpassword.setFont(new Font("Arial", Font.PLAIN, 15)); 
        buttonforgotpassword.setBounds(150, 400, 150, 40); 
        buttonforgotpassword.setBackground(Color.BLUE);
        buttonforgotpassword.setForeground(Color.WHITE);
        buttonforgotpassword.setFocusPainted(false);
        frame.add(buttonforgotpassword);

        JButton buttonback = new JButton("back");
        buttonback.setFont(new Font("Arial", Font.BOLD, 30)); 
        buttonback.setBounds(350, 400, 150, 50); 
        buttonback.setBackground(Color.BLUE);
        buttonback.setForeground(Color.WHITE);
        frame.add(buttonback);

        buttonSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordField.getPassword());

                try {
                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "All fields are required!");
                    } else if (validateLogin(username, password)) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        CabBookingMenu cb = new CabBookingMenu(username);
                        frame.dispose();
                    } else {
                        loginAttempts++;
                        if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                            JOptionPane.showMessageDialog(frame, "Maximum login attempts reached.");
                            buttonSignIn.setEnabled(false);
                            startResetTimer();
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid username or password. Attempts left: "
                                    + (MAX_LOGIN_ATTEMPTS - loginAttempts));
                        }
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                RegistrationForm register = new RegistrationForm();
            }
        });

        buttonforgotpassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new UserForgotPassword();
            }
        });

        buttonback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new cabBookingSystem();
            }
        });

        showPasswordCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                if (checkBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Show password
                } else {
                    passwordField.setEchoChar('*'); // Hide password
                }
            }
        });

        frame.setLayout(null); 
        frame.setVisible(true);
    }

    private void startResetTimer() {
        if (resetTimer != null) {
            resetTimer.cancel(); 
        }
        resetTimer = new Timer();
        resetTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                resetLoginAttempts();
            }
        }, RESET_INTERVAL);
    }

    private void resetLoginAttempts() {
        loginAttempts = 0;
       // buttonSignIn.setEnabled(true);
        resetTimer.cancel();
    }

    private static boolean validateLogin(String username, String password) throws ClassNotFoundException, SQLException {
        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";
        boolean isValid = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            isValid = resultSet.next();

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return isValid;
    }

    public static void main(String[] args) {
        LoginForm login = new LoginForm();
    }
}
