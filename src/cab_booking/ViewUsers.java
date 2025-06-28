package cab_booking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class ViewUsers {
    private JFrame frame;
    private JTable table;
    private JButton buttonMainMenu;

    public ViewUsers(String adminName) {
        frame = new JFrame("View Users(admin)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout());

        buttonMainMenu = new JButton("Return to main menu");
        buttonMainMenu.addActionListener(e -> {
            frame.dispose();
            new AdminMenuPage(adminName);
            // Add functionality to return to main menu
        });

      

        try {
            String u = "root";
            String pass = "";
            String url = "jdbc:mysql://localhost:3306/cabbook";

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, u, pass);

            String query = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Create a table model
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = metaData.getColumnLabel(i + 1);
            }

            Object[][] data = new Object[100][columnCount]; // Assuming a maximum of 100 users
            int rowCount = 0;
            while (resultSet.next()) {
                for (int i = 0; i < columnCount; i++) {
                    data[rowCount][i] = resultSet.getObject(i + 1);
                }
                rowCount++;
            }

            table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching user information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        panel.add(buttonMainMenu, BorderLayout.SOUTH);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	  String adminName = "admin";
        new ViewUsers(adminName);
    }
}
