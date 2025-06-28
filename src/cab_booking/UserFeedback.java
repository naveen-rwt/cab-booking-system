package cab_booking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class UserFeedback {
    private JFrame frame;
    private JTextArea feedbackTextArea;
    private JRadioButton suggestionRadio;
    private JRadioButton complaintRadio;
    private JRadioButton praiseRadio;
    private JTextField complaintField;
    private JLabel charCountLabel;
   

    private String username;

    public UserFeedback(String username) {
        this.username = username;
      frame = new JFrame("Feedback Zone");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(null);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        frame.add(panel);

        JLabel feedbackLabel = new JLabel("Share your thoughts:");
        feedbackLabel.setBounds(10, 10, 150, 25);
        panel.add(feedbackLabel);

        feedbackTextArea = new JTextArea();
        feedbackTextArea.setLineWrap(true);
      
        JScrollPane scrollPane = new JScrollPane(feedbackTextArea);
        scrollPane.setBounds(10, 40, 300, 150);
        panel.add(scrollPane);

        suggestionRadio = new JRadioButton("Suggestion");
        suggestionRadio.setBounds(10, 200, 100, 25);
        panel.add(suggestionRadio);

        complaintRadio = new JRadioButton("Complaint");
        complaintRadio.setBounds(120, 200, 100, 25);
        panel.add(complaintRadio);

        praiseRadio = new JRadioButton("Praise");
        praiseRadio.setBounds(230, 200, 100, 25);
        panel.add(praiseRadio);

        ButtonGroup group = new ButtonGroup();
        group.add(suggestionRadio);
        group.add(complaintRadio);
        group.add(praiseRadio);

        JLabel complaintLabel = new JLabel("Details (if any):");
        complaintLabel.setBounds(10, 240, 150, 25);
       
        panel.add(complaintLabel);

        complaintField = new JTextField();
        complaintField.setBounds(10, 270, 300, 25);
        panel.add(complaintField);

        charCountLabel = new JLabel("Characters: 0");
        charCountLabel.setBounds(10, 300, 150, 25);
        panel.add(charCountLabel);

       
        JButton submitButton = new JButton("Submit Feedback");
        submitButton.setBounds(10, 360, 150, 30);
        panel.add(submitButton);

        JButton anyQueriesButton = new JButton("Questions?");
        anyQueriesButton.setBounds(10, 400, 150, 30);
        panel.add(anyQueriesButton);

        JButton backToMainMenuButton = new JButton("Back to Main Menu");
        backToMainMenuButton.setBounds(10, 440, 200, 30);
        panel.add(backToMainMenuButton);

        frame.add(panel);

        suggestionRadio.addActionListener(e -> updateComplaintField("Suggestion"));
        complaintRadio.addActionListener(e -> updateComplaintField("Complaint"));
        praiseRadio.addActionListener(e -> updateComplaintField("Praise"));

        feedbackTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                updateCharCount();
            }
        });

      
        submitButton.addActionListener(e -> submitFeedback());
        anyQueriesButton.addActionListener(e -> showQueriesContact());
        backToMainMenuButton.addActionListener(e -> returnToMainMenu());

        frame.setMinimumSize(new Dimension(400, 500));

        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    private void updateComplaintField(String type) {
        if (type.equals("Complaint")) {
            complaintField.setVisible(true);
        } else {
            complaintField.setText("");
          //  complaintLabel.setVisible(false);
            complaintField.setVisible(false);
        }
    }

    private void updateCharCount() {
        int count = feedbackTextArea.getText().length();
        charCountLabel.setText("Characters: " + count);
    }

 
    private void submitFeedback() {
        String feedbackText = feedbackTextArea.getText();
        String feedbackType = "";
        if (suggestionRadio.isSelected()) {
            feedbackType = "Suggestion";
        } else if (complaintRadio.isSelected()) {
            feedbackType = "Complaint";
        } else if (praiseRadio.isSelected()) {
            feedbackType = "Praise";
        }
        String complaintDetails = complaintField.getText();

        Connection conn = null; // Declare Connection object
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabbook", "root", "");

            String sql = "INSERT INTO feedback (username, feedback_text, feedback_type, complaint_details) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, feedbackText);
            pstmt.setString(3, feedbackType);
            pstmt.setString(4, complaintDetails);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Feedback submitted successfully!", "Feedback Submitted", JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields after successful submission
            feedbackTextArea.setText("");
            suggestionRadio.setSelected(false);
            complaintRadio.setSelected(false);
            praiseRadio.setSelected(false);
            complaintField.setText("");
            charCountLabel.setText("Characters: 0");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(frame, "Failed to submit feedback: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (conn != null) {
                    conn.close(); // Close the connection in the finally block
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private void showQueriesContact() {
        JOptionPane.showMessageDialog(frame,
                "Need help? Contact us:\n" +
                        "support@cabbooking.com\n" +
                        "+79829819321",
                "Got Questions?", JOptionPane.INFORMATION_MESSAGE);
    }

    private void returnToMainMenu() {
        frame.dispose();
        String username = null;
		new CabBookingMenu(username);
    }

    public static void main(String[] args) {
       
      String username="naveen";
                new UserFeedback(username);
            }
       
    }

