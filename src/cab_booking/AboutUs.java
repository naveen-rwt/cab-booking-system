package cab_booking;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class AboutUs {
    private JFrame frame;
    private JLabel titleLabel;
    private JTextArea aboutTextArea;
    private JButton closeButton;
    
    public AboutUs() {
        initializeComponents();
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);
       
    }

    private void initializeComponents() {
        
        frame = new JFrame("ABOUT CAB Buddy");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setLayout(new BorderLayout());

        
        titleLabel = new JLabel("About Cab Buddy", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

       
        aboutTextArea = new JTextArea();
        aboutTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
        aboutTextArea.setEditable(false);
        aboutTextArea.setText("Hey there, fellow students!\n\n" +
                "Welcome to Cab Buddy - your go-to travel companion for those college adventures! " +
                "We know how hectic college life can get, and getting around the city shouldn't be a hassle.\n\n" +
                "Cab Buddy is not just a cab booking system; it's your trusted friend on the road. " +
                "Here's what we do:\n\n" +
                "### Features and Functions:\n" +
                "- **Quick and Easy Booking**: Just a few taps, and you're ready to roll!\n" +
                "- **Transparent Pricing**: No surprises, no hidden fees. What you see is what you pay.\n" +
               
                "- **Multiple Payment Options**: Pay with cash, card or UPI !\n" +
                "- **24/7 Customer Support**: Got an issue? We're here to help, any time of day or night.\n\n" +
                "### Customer Commitment:\n" +
                "At Cab Buddy, we treat our customers like family. Here's what you can expect:\n" +
                "- **Safety First**: Our drivers undergo rigorous background checks for your security.\n" +
                "- **Clean and Comfortable Rides**: Say goodbye to cramped spaces and hello to legroom!\n" +
                "- **Friendly and Professional Drivers**: Expect a warm welcome and a smooth ride every time.\n" +
                "- **Feedback Matters**: Your feedback shapes our service. We're always listening.\n\n" +
                "So whether you're heading to class, hitting up the local hangout, or exploring the city, " +
                "let Cab Buddy take you there. Because the journey should be as fun as the destination!\n\n\n" +
                "Got questions, feedback, or just want to say hi? Shoot us an email at cab.buddy@collegerides.com");

        // Add padding to the text area
        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
        aboutTextArea.setBorder(BorderFactory.createCompoundBorder(aboutTextArea.getBorder(), paddingBorder));

        
        closeButton = new JButton("Close");  
       
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            new cabBookingSystem();    
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(aboutTextArea), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
 }
    
  
    public static void main(String[] args) {
  
    new AboutUs();
    }
    }
