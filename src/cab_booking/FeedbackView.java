package cab_booking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class FeedbackView {
    JFrame frame = new JFrame("Feedback View");
    JTable feedbackTable;
    private JButton buttonBack;
    DefaultTableModel tableModel;

    public FeedbackView(String username) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Create table model with columns
        String[] columns = {"Username", "Feedback Text", "Feedback Type", "Complaint Details"};
        tableModel = new DefaultTableModel(columns, 0);
        feedbackTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        frame.add(scrollPane, BorderLayout.CENTER);
        buttonBack = new JButton("Back");
        frame.add(buttonBack, BorderLayout.SOUTH);
        buttonBack.addActionListener(e -> {
            frame.dispose();
            new AdminMenuPage(username);
        });

        populateFeedbackTable();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void populateFeedbackTable() {
        tableModel.setRowCount(0); // Clear table 

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabbook", "root", "");

            String query = "SELECT * FROM feedback";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String feedbackText = resultSet.getString("feedback_text");
                String feedbackType = resultSet.getString("feedback_type");
                String complaintDetails = resultSet.getString("complaint_details");

                tableModel.addRow(new Object[]{username, feedbackText, feedbackType, complaintDetails});
            }

            resultSet.close();
            preparedStatement.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
    	  String adminName = "admin";
                
                  new FeedbackView(adminName);
    }
}
