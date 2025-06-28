package cab_booking;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Random;

public class BillGeneration {
    private JFrame frame;
    private JTextField distanceField;
    private JTextField timeField;
    private JComboBox<String> carTypeComboBox;
    private JCheckBox acCheckBox;
    private JLabel fareLabel;
    private JLabel billIdLabel;
    private JButton confirmButton;
    private JButton receiptButton;
    protected String username;
    private static final double RATE_PER_KM_SUV = 15.0;
    private static final double RATE_PER_KM_AUTO = 8.0;
    private static final double RATE_PER_HOUR = 5.0;
    private static final double BASE_FARE_SUV = 100.0;
    private static final double BASE_FARE_AUTO = 50.0;
    private static final double AC_CHARGE = 50.0;

    public BillGeneration(String username) {
    	 this.username = username;
        frame = new JFrame("Fare Calculator");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(100, 200, 100, 200));
        panel.setLayout(null);

        JLabel distanceLabel = new JLabel("Distance (in km):");
        distanceLabel.setBounds(250, 50, 150, 25);
        panel.add(distanceLabel);

        distanceField = new JTextField();
        distanceField.setBounds(400, 50, 150, 25);
        panel.add(distanceField);
        distanceField.setVisible(false);
        JLabel timeLabel = new JLabel("Time (in hours):");
        timeLabel.setBounds(250, 100, 150, 25);
        panel.add(timeLabel);

        timeField = new JTextField();
        timeField.setBounds(400, 100, 150, 25);
        panel.add(timeField);

        JLabel carTypeLabel = new JLabel("Car Type:");
        carTypeLabel.setBounds(250, 150, 150, 25);
        panel.add(carTypeLabel);

        String[] carTypes = {"SUV", "Auto-Rickshaw"};
        carTypeComboBox = new JComboBox<>(carTypes);
        carTypeComboBox.setBounds(400, 150, 150, 25);
        panel.add(carTypeComboBox);

        JLabel acLabel = new JLabel("AC:");
        acLabel.setBounds(250, 200, 150, 25);
        panel.add(acLabel);

        acCheckBox = new JCheckBox("AC");
        acCheckBox.setBounds(400, 200, 100, 25);
        panel.add(acCheckBox);

        JButton calculateButton = new JButton("Calculate Fare");
        calculateButton.setBounds(350, 250, 150, 30);
        panel.add(calculateButton);

        fareLabel = new JLabel("Fare: ");
        fareLabel.setBounds(350, 300, 150, 25);
        fareLabel.setVisible(false);
        panel.add(fareLabel);

        confirmButton = new JButton("Confirm Booking");
        confirmButton.setBounds(300, 350, 250, 30);
        confirmButton.setVisible(false);
        panel.add(confirmButton);

        receiptButton = new JButton("Generate Receipt");
        receiptButton.setBounds(300, 400, 250, 30);
        receiptButton.setVisible(false);
        panel.add(receiptButton);
        billIdLabel = new JLabel("Bill ID: ");
        billIdLabel.setBounds(350, 450, 200, 25);
        billIdLabel.setVisible(false);
        panel.add(billIdLabel);

        JButton backButton = new JButton("Back");
        backButton.setBounds(500, 450, 250, 30);
        backButton.setVisible(true);
        panel.add(backButton);

        frame.add(panel, BorderLayout.CENTER);
       confirmButton.setVisible(false);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                distanceField.setVisible(false);
                calculateFare();
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // confirmBooking();
            }
        });

        receiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //generateReceipt();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new cabbook(username);
            }
        });

        frame.setVisible(true);
    }
	private void calculateFare() {
        String city1 = "Delhi";
        String city2 = "Noida";

        try {
            double[] coordinates1 = CityDistanceCalculator.getCoordinates(city1);
            double[] coordinates2 = CityDistanceCalculator.getCoordinates(city2);

            double distance = CityDistanceCalculator.calculateDistance(coordinates1[0], coordinates1[1], coordinates2[0], coordinates2[1]);

            String distanceText = String.valueOf(distance);

            distanceField.setText(distanceText);

            String timeText = timeField.getText();

            if (distanceText.isEmpty() || timeText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter distance and time.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double time = Double.parseDouble(timeText);

            if (distance <= 0 || time <= 0) {
                JOptionPane.showMessageDialog(frame, "Distance and time must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selectedCarType = (String) carTypeComboBox.getSelectedItem();
            boolean isAC = acCheckBox.isSelected();

            double ratePerKm;
            double baseFare;
            if (selectedCarType.equals("SUV")) {
                ratePerKm = RATE_PER_KM_SUV;
                baseFare = BASE_FARE_SUV;
            } else {
                ratePerKm = RATE_PER_KM_AUTO;
                baseFare = BASE_FARE_AUTO;
            }

            if (isAC) {
                baseFare += AC_CHARGE;
            }

            double fare = baseFare + (ratePerKm * distance) + (RATE_PER_HOUR * time);

            DecimalFormat df = new DecimalFormat("#.##");
            String formattedFare = "₹ " + df.format(fare);

            fareLabel.setText("Fare: " + formattedFare);
            fareLabel.setVisible(true);

            // Enables other components
            billIdLabel.setText("Bill ID: " + generateBillID());
            billIdLabel.setVisible(true);
            confirmButton.setVisible(true);
            receiptButton.setVisible(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error calculating fare: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateBillID() {
        // Generate a random 6-digit bill ID
        Random rand = new Random();
        int billID = rand.nextInt(900000) + 100000;
        return String.valueOf(billID);
    }

    private void confirmBooking() {
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to confirm the booking?", "Confirm Booking", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            insertBillingDetails();
            JOptionPane.showMessageDialog(frame, "Booking confirmed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void insertBillingDetails() {
        String billID = billIdLabel.getText().substring(8);
        String fare = fareLabel.getText().substring(6);
      this.username=username;

        String url = "jdbc:mysql://localhost:3306/cabbook";
        String user = "root";
        String password = "";

        try  {
        	Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
            String insertquery = "INSERT INTO billing (bill_id, fare, username) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertquery);
            statement.setString(1, billID);
            statement.setString(2, fare);
            statement.setString(3, this.username);
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: Failed to insert billing details into the database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    private void generateReceipt() {
        String fareText = fareLabel.getText();
        String billIdText = billIdLabel.getText();

        if (fareText.equals("Fare: ") || billIdText.equals("Bill ID: ")) {
            JOptionPane.showMessageDialog(frame, "Calculate fare first to generate receipt.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String receipt = "Bill ID: " + billIdText.substring(8) + "\n" +
                "-------------------------------------\n" +
                "Fare: " + fareText.substring(6) + "\n" +
                "Thank you for using our service!";

        JOptionPane.showMessageDialog(frame, receipt, "Receipt", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
    	 String username = "naveen";
    	new BillGeneration(username);
    }
}
