package cab_booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/cabbook"; 
        String username = "root"; 
        String password = ""; 

        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC driver: " + e.getMessage());
            return;
        }

        // Establish a connection to the database
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            System.out.println("Connected to the database successfully!");
            
            
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
}
