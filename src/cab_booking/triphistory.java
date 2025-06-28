package cab_booking;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class triphistory {
    private JFrame frame;
    private JTable tripHistoryTable;
    private JButton buttonBack;
    private String username;
    private DefaultTableModel tableModel;

    public triphistory(String username) {
        this.username = username;
        initializeComponents();
        displayTripHistory();

        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private void initializeComponents() {
        frame = new JFrame("Trip History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        buttonBack = new JButton("Back");
        buttonBack.addActionListener(e -> {
            frame.dispose();
            new CabBookingMenu(username);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buttonBack);
        buttonBack.setFont(new Font("Arial", Font.PLAIN, 24));

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Create table model with columns
        String[] columns = {"Trip ID", "Date", "Username", "From", "To", "Fare"};
        tableModel = new DefaultTableModel(columns, 0);
        tripHistoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tripHistoryTable);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    private void displayTripHistory() {
        ArrayList<String[]> tripHistoryList = getTripHistoryForCurrentUser();

        for (String[] tripInfo : tripHistoryList) {
            tableModel.addRow(tripInfo);
        }
    }

    private ArrayList<String[]> getTripHistoryForCurrentUser() {
        ArrayList<String[]> tripHistoryList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabbook", "root", "");

            String query = "SELECT * FROM triphistory WHERE customername = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String[] tripInfo = new String[6];
                tripInfo[0] = String.valueOf(rs.getInt("tripid"));
                tripInfo[1] = rs.getString("tripdate");
                tripInfo[2] = rs.getString("customername");
                tripInfo[3] = rs.getString("source");
                tripInfo[4] = rs.getString("destination");
                tripInfo[5] = String.valueOf(rs.getDouble("fare"));
                tripHistoryList.add(tripInfo);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return tripHistoryList;
    }

    public static void main(String[] args) {
        String username = "naveen"; 
        new triphistory(username);
    }
}
