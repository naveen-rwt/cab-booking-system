package cab_booking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm implements ActionListener {
    private JFrame frame = new JFrame("Registration Form");
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JTextField textFieldEmail;
    private JTextField textFieldMobile;
    private JTextField textFieldSecurityQuestion; // New field for security question
    private JRadioButton radioMale;
    private JRadioButton radioFemale;
    
    public RegistrationForm() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(238, 238, 238));

        JLabel labelTitle = new JLabel("Registration Form");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 35));
        labelTitle.setBounds(200, 15, 350, 40);

        JLabel labelFirstName = new JLabel("First Name:");
        labelFirstName.setFont(new Font("Arial", Font.PLAIN, 18));
        labelFirstName.setBounds(250, 80, 120, 30);
        textFieldFirstName = new JTextField(20);
        textFieldFirstName.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldFirstName.setBounds(500, 80, 300, 30);

        JLabel labelLastName = new JLabel("Last Name:");
        labelLastName.setFont(new Font("Arial", Font.PLAIN, 18));
        labelLastName.setBounds(250, 120, 120, 30);
        textFieldLastName = new JTextField(20);
        textFieldLastName.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldLastName.setBounds(500, 120, 300, 30);

        JLabel labelUsername = new JLabel("Username:");
        labelUsername.setFont(new Font("Arial", Font.PLAIN, 18));
        labelUsername.setBounds(250, 160, 120, 30);
        textFieldUsername = new JTextField(20);
        textFieldUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldUsername.setBounds(500, 160, 300, 30);

        JLabel labelPassword = new JLabel("Password:");
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        labelPassword.setBounds(250, 200, 120, 30);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(500, 200, 300, 30);
        
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setFont(new Font("Arial", Font.PLAIN, 15)); 
        showPasswordCheckbox.setBounds(860, 200, 136, 30); 
        frame.add(showPasswordCheckbox);
        
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.PLAIN, 18));
        labelEmail.setBounds(250, 240, 120, 30);
        textFieldEmail = new JTextField(20);
        textFieldEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldEmail.setBounds(500, 240, 300, 30);

        JLabel labelMobile = new JLabel("Mobile:");
        labelMobile.setFont(new Font("Arial", Font.PLAIN, 18));
        labelMobile.setBounds(250, 280, 120, 30);
        textFieldMobile = new JTextField(20);
        textFieldMobile.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldMobile.setBounds(500, 280, 300, 30);

        JLabel labelSecurityQuestion = new JLabel("Security Question:"); // New label
        labelSecurityQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
        labelSecurityQuestion.setBounds(250, 320, 180, 30);
        JComboBox<String> securityQuestionComboBox = new JComboBox<>();
        securityQuestionComboBox.addItem("Favorite Color");
        securityQuestionComboBox.addItem("Favorite Country");
        securityQuestionComboBox.addItem("Nick Name");
        securityQuestionComboBox.setSelectedIndex(0);
        securityQuestionComboBox.setBounds(840, 320, 200, 30);
        textFieldSecurityQuestion = new JTextField(20); // New text field
        textFieldSecurityQuestion.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldSecurityQuestion.setBounds(500, 320, 300, 30);

        JLabel labelGender = new JLabel("Gender:");
        labelGender.setFont(new Font("Arial", Font.PLAIN, 18));
        labelGender.setBounds(250, 360, 120, 30);
    radioMale = new JRadioButton("Male");
        radioMale.setFont(new Font("Arial", Font.PLAIN, 16));
        radioMale.setBounds(380, 360, 80, 30);
        radioFemale = new JRadioButton("Female");
        radioFemale.setFont(new Font("Arial", Font.PLAIN, 16));
        radioFemale.setBounds(470, 360, 100, 30);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(radioMale);
        genderGroup.add(radioFemale);
        
        
        JButton buttonSubmit = new JButton("Submit");
        buttonSubmit.setFont(new Font("Arial", Font.BOLD, 18));
        buttonSubmit.setBounds(250, 420, 120, 40);
        buttonSubmit.setBackground(Color.GREEN);
        buttonSubmit.setForeground(Color.WHITE);
        buttonSubmit.setFocusPainted(false);
        buttonSubmit.addActionListener(this);

        JButton buttonSignIn = new JButton("Sign In");
        buttonSignIn.setFont(new Font("Arial", Font.BOLD, 18));
        buttonSignIn.setBounds(400, 420, 120, 40);
        buttonSignIn.setBackground(Color.BLUE);
        buttonSignIn.setForeground(Color.WHITE);
        buttonSignIn.setFocusPainted(false);
        buttonSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                LoginForm form1 = new LoginForm();
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

        panel.add(labelTitle);
        panel.add(labelFirstName);
        panel.add(textFieldFirstName);
        panel.add(labelLastName);
        panel.add(textFieldLastName);
        panel.add(labelUsername);
        panel.add(textFieldUsername);
        panel.add(labelPassword);
        panel.add(passwordField);
        panel.add(showPasswordCheckbox);
        panel.add(labelEmail);
        panel.add(textFieldEmail);
        panel.add(labelMobile);
        panel.add(textFieldMobile);
        panel.add(labelSecurityQuestion); 
      panel.add(securityQuestionComboBox);
       panel.add(textFieldSecurityQuestion); 
        panel.add(labelGender);
        panel.add(radioMale);
        panel.add(radioFemale);
        panel.add(buttonSubmit);
        panel.add(buttonSignIn);

        frame.add(panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String username = textFieldUsername.getText();
        String password = new String(passwordField.getPassword());
        String email = textFieldEmail.getText();
        String mobile = textFieldMobile.getText();
        String securityQuestion = textFieldSecurityQuestion.getText(); // Retrieve security question
        String gender = radioMale.isSelected() ? "Male" : "Female";

        // Check for empty fields
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                password.isEmpty() || email.isEmpty() || mobile.isEmpty() || securityQuestion.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all required fields including the security question.");
            return;
        }

        // Validate password length
        if (password.length() < 3) {
            JOptionPane.showMessageDialog(frame, "Password must be at least 3 characters long.");
            return;
        }

        // Validate email format using regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid email address.");
            return;
        }

        // Validate mobile number length
        if (mobile.length() != 10) {
            JOptionPane.showMessageDialog(frame, "Mobile number should be 10 characters long.");
            return;
        }

        try {
            registerUser(firstName, lastName, username, password, email, mobile, gender, securityQuestion);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }


    private void registerUser(String firstName, String lastName, String username, String password, String email, String mobile, String gender, String securityQuestion) throws ClassNotFoundException {
        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);

            String insertQuery = "INSERT INTO users (firstname, lastname, username, password, email, mobile, gender, securityquestion) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, mobile);
            preparedStatement.setString(7, gender);
            preparedStatement.setString(8, securityQuestion);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "User created successfully!");

            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Failed to insert data into the database.");
        }
    }

    public static void main(String[] args) {
        RegistrationForm registration = new RegistrationForm();
    }
}
