package cab_booking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AdminSignUp {
    JFrame frame = new JFrame("Admin Sign Up");
    JLabel labeltitle = new JLabel("SIGN-UP");
    JLabel labelName = new JLabel("Name:");
    JLabel labelPassword = new JLabel("Password:");
    JLabel labelSecurityQuestion = new JLabel("Security Question:");
    JLabel labelEmail = new JLabel("Email Address:");
    JTextField textFieldName = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    private JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
    JTextField textFieldSecurityQuestion = new JTextField();
    JComboBox<String> securityQuestionComboBox = new JComboBox<>();
    JTextField textFieldEmail = new JTextField();
    JButton buttonSubmit = new JButton("Submit");
    JButton buttonSignIn = new JButton("Sign In");

    AdminSignUp() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLayout(null);
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        Font labeltitleFont = new Font("Arial", Font.BOLD, 40);
        Font labelFont = new Font("Arial", Font.PLAIN, 20);

        labeltitle.setFont(labeltitleFont);
        labeltitle.setForeground(Color.BLUE);

        labelName.setFont(labelFont);
        labelPassword.setFont(labelFont);
        labelSecurityQuestion.setFont(labelFont);
        labelEmail.setFont(labelFont);

        labeltitle.setBounds(frameWidth / 4 + 120, frameHeight / 4 - 80, 400, 40);
        labelName.setBounds(frameWidth / 4, frameHeight / 4, 150, 40);
        textFieldName.setBounds(frameWidth / 4 + 300, frameHeight / 4, 200, 40);
        labelPassword.setBounds(frameWidth / 4, frameHeight / 4 + 70, 200, 40);
        passwordField.setBounds(frameWidth / 4 + 300, frameHeight / 4 + 70, 200, 40);
        showPasswordCheckbox.setBounds(frameWidth / 4 + 560, frameHeight / 4 + 80, 185, 30);
        showPasswordCheckbox.setFont(labelFont);

        labelSecurityQuestion.setBounds(frameWidth / 4, frameHeight / 4 + 120, 200, 40);
        securityQuestionComboBox.addItem("Favorite Color");
        securityQuestionComboBox.addItem("Favorite Country");
        securityQuestionComboBox.addItem("Nick Name");
        securityQuestionComboBox.setSelectedIndex(0);
        securityQuestionComboBox.setBounds(frameWidth / 4 + 180, frameHeight / 4 + 128, 115, 25);
        textFieldSecurityQuestion.setBounds(frameWidth / 4 + 300, frameHeight / 4 + 120, 200, 40);

        labelEmail.setBounds(frameWidth / 4, frameHeight / 4 + 180, 200, 40);
        textFieldEmail.setBounds(frameWidth / 4 + 300, frameHeight / 4 + 180, 200, 40);

        buttonSubmit.setBounds(frameWidth / 4, frameHeight / 4 + 260, 200, 40);
        buttonSignIn.setBounds(frameWidth / 4 + 300, frameHeight / 4 + 260, 200, 40);

        Font textFieldFont = new Font("Arial", Font.BOLD, 20);
        textFieldName.setFont(textFieldFont);
        passwordField.setFont(textFieldFont);
        textFieldSecurityQuestion.setFont(textFieldFont);
        textFieldEmail.setFont(textFieldFont);

        Font ButtonFont = new Font("Arial", Font.BOLD, 20);
        buttonSubmit.setFont(ButtonFont);
        buttonSignIn.setFont(ButtonFont);

        buttonSubmit.setForeground(Color.WHITE);
        buttonSubmit.setBackground(Color.GREEN);
        buttonSignIn.setForeground(Color.WHITE);
        buttonSignIn.setBackground(Color.RED);

        
        
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFieldName.getText();
                String password = new String(passwordField.getPassword());
                String securityQuestion = textFieldSecurityQuestion.getText();
                String email = textFieldEmail.getText();

                if (name.isEmpty() || password.isEmpty() || securityQuestion.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!");
                }
                // Validation for password length
                if (password.length() < 3) {
                    JOptionPane.showMessageDialog(null, "Password must be at least 3 characters long!");
                    return; // Exit the method if validation fails
                }

                // Validation for mobile number length (assuming it's the email in this case)
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                if (!email.matches(emailRegex)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid email address!");
                    return;
                    }

                // Validation for empty fields
                else {
                    try {
                        if (addAdminToDatabase(name, password, securityQuestion, email)) {
                            JOptionPane.showMessageDialog(null, "Admin added successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error adding admin. Please try again.");
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        buttonSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLogin login = new AdminLogin();
                frame.dispose();
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


        frame.add(labeltitle);
        frame.add(labelName);
        frame.add(textFieldName);
        frame.add(labelPassword);
        frame.add(passwordField);
        frame.add(showPasswordCheckbox);
        frame.add(labelSecurityQuestion);
        frame.add(textFieldSecurityQuestion);
        frame.add(securityQuestionComboBox);
        frame.add(labelEmail);
        frame.add(textFieldEmail);
        frame.add(buttonSubmit);
        frame.add(buttonSignIn);

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private boolean addAdminToDatabase(String name, String password, String securityQuestion, String email)
            throws ClassNotFoundException, SQLException {
        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);
            String query = "INSERT INTO admin (admin_id, username, password, securityquestion, emailId) VALUES (DEFAULT, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, securityQuestion);
            preparedStatement.setString(4, email);
            int rowsInserted = preparedStatement.executeUpdate();
            // Retrieve the generated admin_id
            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int adminId = generatedKeys.getInt(1);
                    System.out.println("Generated Admin ID: " + adminId);
                }
            }
            preparedStatement.close();
            connection.close();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminSignUp());
    }
}
