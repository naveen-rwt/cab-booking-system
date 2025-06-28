package cab_booking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateProfile implements ActionListener {
    private JFrame frame = new JFrame("Update Profile");
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JTextField textFieldEmail;
    private JTextField textFieldMobile;
    private JRadioButton radioMale;
    private JRadioButton radioFemale;

    public UpdateProfile(String username) {
        initialize(username);
        populateFields(username);
    }

    private void initialize(String username) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(238, 238, 238));

        JLabel labelTitle = new JLabel("Update Profile");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 35));
        labelTitle.setBounds(200, 15, 300, 40);

        JLabel labelFirstName = new JLabel("First Name:");
        labelFirstName.setFont(new Font("Arial", Font.PLAIN, 18));
        labelFirstName.setBounds(50, 80, 120, 30);
        textFieldFirstName = new JTextField(20);
        textFieldFirstName.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldFirstName.setBounds(180, 80, 300, 30);

        JLabel labelLastName = new JLabel("Last Name:");
        labelLastName.setFont(new Font("Arial", Font.PLAIN, 18));
        labelLastName.setBounds(50, 120, 120, 30);
        textFieldLastName = new JTextField(20);
        textFieldLastName.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldLastName.setBounds(180, 120, 300, 30);

        JLabel labelUsername = new JLabel("Username:");
        labelUsername.setFont(new Font("Arial", Font.PLAIN, 18));
        labelUsername.setBounds(50, 160, 120, 30);
        textFieldUsername = new JTextField(20);
        textFieldUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldUsername.setBounds(180, 160, 300, 30);

        JLabel labelPassword = new JLabel("Password:");
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        labelPassword.setBounds(50, 200, 120, 30);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(180, 200, 300, 30);

        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setFont(new Font("Arial", Font.PLAIN, 15)); 
        showPasswordCheckbox.setBounds(530, 200, 136, 30); 
     JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.PLAIN, 18));
        labelEmail.setBounds(50, 240, 120, 30);
        textFieldEmail = new JTextField(20);
        textFieldEmail.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldEmail.setBounds(180, 240, 300, 30);

        JLabel labelMobile = new JLabel("Mobile:");
        labelMobile.setFont(new Font("Arial", Font.PLAIN, 18));
        labelMobile.setBounds(50, 280, 120, 30);
        textFieldMobile = new JTextField(20);
        textFieldMobile.setFont(new Font("Arial", Font.PLAIN, 16));
        textFieldMobile.setBounds(180, 280, 300, 30);

        JLabel labelGender = new JLabel("Gender:");
        labelGender.setFont(new Font("Arial", Font.PLAIN, 18));
        labelGender.setBounds(50, 320, 120, 30);
        radioMale = new JRadioButton("Male");
        radioMale.setFont(new Font("Arial", Font.PLAIN, 16));
        radioMale.setBounds(180, 320, 80, 30);
        radioFemale = new JRadioButton("Female");
        radioFemale.setFont(new Font("Arial", Font.PLAIN, 16));
        radioFemale.setBounds(270, 320, 100, 30);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(radioMale);
        genderGroup.add(radioFemale);

        JButton buttonUpdate = new JButton("Update");
        buttonUpdate.setFont(new Font("Arial", Font.BOLD, 18));
        buttonUpdate.setBounds(200, 450, 200, 40);
        buttonUpdate.setBackground(Color.GREEN);
        buttonUpdate.setForeground(Color.WHITE);
        buttonUpdate.setFocusPainted(false);
        buttonUpdate.addActionListener(this);

        JButton buttonmainmenu = new JButton("back to main menu");
        buttonmainmenu.setFont(new Font("Arial", Font.BOLD, 18));
        buttonmainmenu.setBounds(500, 450, 350, 40);
        buttonmainmenu.setBackground(Color.GREEN);
        buttonmainmenu.setForeground(Color.WHITE);
        buttonmainmenu.setFocusPainted(false);
   
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
        panel.add(labelGender);
        panel.add(radioMale);
        panel.add(radioFemale);
        panel.add(buttonUpdate);
        panel.add(buttonmainmenu);
      buttonmainmenu.addActionListener(e -> {
            frame.dispose();
            new CabBookingMenu(username);
        });
   frame.add(panel);
        frame.setVisible(true);
    }
      private void populateFields(String username) {
        String u = "root";
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";

        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection connection = DriverManager.getConnection(url, u, pass);

            String query = "SELECT * FROM users WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                textFieldFirstName.setText(resultSet.getString("firstname"));
                textFieldLastName.setText(resultSet.getString("lastname"));
                textFieldUsername.setText(resultSet.getString("username"));
                passwordField.setText(resultSet.getString("password")); // Not recommended to store passwords in plain text
                textFieldEmail.setText(resultSet.getString("email"));
                textFieldMobile.setText(resultSet.getString("mobile"));
                String gender = resultSet.getString("gender");
                if (gender.equals("Male")) {
                    radioMale.setSelected(true);
                } else {
                    radioFemale.setSelected(true);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "User not found.");
            }
            
            connection.close(); // Close the connection after use
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: MySQL JDBC Driver not found.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: Failed to populate fields.");
        }
    }
   @Override
    public void actionPerformed(ActionEvent e) {
        if (!isValidInput()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all required fields and provide valid data.");
            return;
        }
        String firstName = textFieldFirstName.getText();
        String lastName = textFieldLastName.getText();
        String username = textFieldUsername.getText();
        String password = new String(passwordField.getPassword());
        String email = textFieldEmail.getText();
        String mobile = textFieldMobile.getText();
        String gender = radioMale.isSelected() ? "Male" : "Female";

        User user = new User(firstName, lastName, username,password, email, mobile, gender);

        try {
            UserDAO.updateUserProfile(user);
           // JOptionPane.showMessageDialog(null, "Profile updated successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
           JOptionPane.showMessageDialog(null, "Error: Failed to update profile.");
        }
    }

    private boolean isValidInput() {
        if (textFieldFirstName.getText().isEmpty() ||
                textFieldLastName.getText().isEmpty() ||
                textFieldUsername.getText().isEmpty() ||
                passwordField.getPassword().length == 0 ||
                textFieldEmail.getText().isEmpty() ||
                textFieldMobile.getText().isEmpty() ||
                (!radioMale.isSelected() && !radioFemale.isSelected())) {
            return false;
        }

        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String mobilePattern = "\\d{10}";

        if (!textFieldEmail.getText().matches(emailPattern) ||
                !textFieldMobile.getText().matches(mobilePattern)) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String username = "naveen";
        new UpdateProfile(username);
    }
}

class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String gender;
   

    public User(String firstName, String lastName, String username,String password, String email, String mobile, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
       
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password; // Return the actual password field value
     }
     public void setPassword(String password) {
         this.password = password;
     }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
  
   
}

class UserDAO {
    public static void updateUserProfile(User user) throws SQLException {
        try{
        	String u = "root";
        
        String pass = "";
        String url = "jdbc:mysql://localhost:3306/cabbook";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, u, pass);
   
            String updateQuery = "UPDATE users SET firstname = ?, lastname = ?, username = ?, password = ?, email = ?, mobile = ?, gender = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getUsername());
                preparedStatement.setString(4, user.getPassword()); // Not recommended to store passwords in plain text
                preparedStatement.setString(5, user.getEmail());
                preparedStatement.setString(6, user.getMobile());
                preparedStatement.setString(7, user.getGender());
                preparedStatement.setString(8, user.getUsername());
              //  preparedStatement.executeUpdate();
                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "details updated successfully!");
               
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update details. Please try again.");
                }

                preparedStatement.close();
                connection.close();
        } 
        catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}
