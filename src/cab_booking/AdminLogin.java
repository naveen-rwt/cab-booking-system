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
import java.util.Timer;
import java.util.TimerTask;

public class AdminLogin {
    private JFrame frame = new JFrame("Admin Login");
    private JLabel labeltitle = new JLabel("ADMIN SIGN-IN");
    private JLabel labelUsername = new JLabel("Username:");
    private JLabel labelPassword = new JLabel("Password:");
    private JTextField textFieldUsername = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
    private JButton buttonLogin = new JButton("Login");
    private JButton buttonCreateAccount = new JButton("Create New Account");
    private JButton buttonback = new JButton("Back");
    private int loginAttempts = 0;
    private final int MAX_LOGIN_ATTEMPTS = 3;
    private final int RESET_INTERVAL = 60000; // Reset login attempts after 1 minute (60000 milliseconds)
    private Timer resetTimer;

    public AdminLogin() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLayout(null);
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        Font labelFont = new Font("Arial", Font.PLAIN, 20);
        Font labeltitlefont = new Font("Arial", Font.BOLD, 40);
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        labeltitle.setBounds(frameWidth / 4 + 40, frameHeight / 4 - 80, 400, 40);
        labeltitle.setFont(labeltitlefont);
        labeltitle.setBorder(new EmptyBorder(10, 10, 10, 10));
        labeltitle.setForeground(Color.BLUE);

        labelUsername.setBounds(frameWidth / 4, frameHeight / 4, 200, 40);
        labelUsername.setFont(labelFont);
        labelUsername.setBorder(new EmptyBorder(10, 10, 10, 10));

        textFieldUsername.setBounds(frameWidth / 4 + 200, frameHeight / 4, 250, 40);
        textFieldUsername.setFont(labelFont);
        textFieldUsername.setBorder(new EmptyBorder(5, 5, 5, 5));

        labelPassword.setBounds(frameWidth / 4, frameHeight / 4 + 80, 200, 40);
        labelPassword.setFont(labelFont);
        labelPassword.setBorder(new EmptyBorder(10, 10, 10, 10));

        passwordField.setBounds(frameWidth / 4 + 200, frameHeight / 4 + 80, 250, 40);
        passwordField.setFont(labelFont);
        passwordField.setBorder(new EmptyBorder(5, 5, 5, 5));

        showPasswordCheckbox.setBounds(frameWidth / 4 + 600, frameHeight / 4 + 100, 190, 30); // Positioning checkbox
        showPasswordCheckbox.setFont(labelFont);

        buttonLogin.setBounds(frameWidth / 4 + 110, frameHeight / 4 + 150, 200, 40);
        buttonLogin.setFont(buttonFont);
        buttonLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttonLogin.setForeground(Color.WHITE);
        buttonLogin.setBackground(Color.GREEN);

        buttonCreateAccount.setBounds(frameWidth / 4 + 20, frameHeight / 4 + 220, 400, 40);
        buttonCreateAccount.setFont(buttonFont);
        buttonCreateAccount.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttonCreateAccount.setForeground(Color.WHITE);
        buttonCreateAccount.setBackground(Color.BLUE);

        buttonback.setBounds(frameWidth / 4 + 30, frameHeight / 4 + 280, 400, 40);
        buttonback.setFont(buttonFont);
        buttonback.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttonback.setForeground(Color.WHITE);
        buttonback.setBackground(Color.RED);

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordField.getPassword());
                // Validating empty fields
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required!");
                } else {
                    try {
                        if (validateLogin(username, password)) {
                            JOptionPane.showMessageDialog(frame, "Login successful!");
                            new AdminMenuPage(username);
                            frame.dispose();
                        } else {
                            loginAttempts++;
                            if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                                JOptionPane.showMessageDialog(frame, "Maximum login attempts reached.");
                                buttonLogin.setEnabled(false); // Disable login button
                                startResetTimer(); // Start timer to reset login attempts
                            } else {
                                JOptionPane.showMessageDialog(frame, "Invalid username or password. Attempts left: "
                                        + (MAX_LOGIN_ATTEMPTS - loginAttempts));
                            }
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        showPasswordCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckbox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Show the password
                } else {
                    passwordField.setEchoChar('\u2022'); // Hide the password using bullet character
                }
            }
        });
        
 buttonCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminSignUp();
                frame.dispose();
            }
        });
        buttonback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new cabBookingSystem();
                frame.dispose();
            }
        });
        frame.add(labeltitle);
        frame.add(labelUsername);
        frame.add(textFieldUsername);
        frame.add(labelPassword);
        frame.add(passwordField);
        frame.add(showPasswordCheckbox);
        frame.add(buttonLogin);
        frame.add(buttonCreateAccount);
        frame.add(buttonback);

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private void startResetTimer() {
        if (resetTimer != null) {
            resetTimer.cancel(); // Cancel previous timer if exists
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
        buttonLogin.setEnabled(true); // Enable login button
        resetTimer.cancel(); // Cancel timer after resetting login attempts
    }

    private boolean validateLogin(String username, String password) throws ClassNotFoundException, SQLException {
        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";
        boolean isValid = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
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
        new AdminLogin();
    }
}
