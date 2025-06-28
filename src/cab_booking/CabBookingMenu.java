package cab_booking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class CabBookingMenu  {
    JFrame frame = new JFrame("Cab Booking Menu");
    private String username;
    CabBookingMenu(String username) {
        this.username = username;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        Font menuFont = new Font("Arial", Font.PLAIN, 18);
        JMenu customerProfileMenu = new JMenu("Customer Profile");
        customerProfileMenu.setFont(menuFont);
        JMenuItem addProfileItem = new JMenuItem("update Profile");
        addProfileItem.addActionListener(e -> {
            frame.dispose();
        new UpdateProfile(username);
        });
       
       addProfileItem.setFont(menuFont);
        addProfileItem.setToolTipText("Add a new customer profile");
        customerProfileMenu.add(addProfileItem);

        JMenuItem viewProfileItem = new JMenuItem("View Profile");
        viewProfileItem.addActionListener(e -> {
            frame.dispose();
        new customerviewprofile(username);
        });
       
        viewProfileItem.setFont(menuFont);
        viewProfileItem.setToolTipText("View your customer profile");
        customerProfileMenu.add(viewProfileItem);

        JMenuItem deleteProfileItem = new JMenuItem("Delete Profile");
        deleteProfileItem.setFont(menuFont);
         deleteProfileItem.addActionListener(e -> {
            frame.dispose();
        new DeleteProfile(username);
        });
        deleteProfileItem.setToolTipText("Delete your customer profile");
        customerProfileMenu.add(deleteProfileItem);

        menuBar.add(customerProfileMenu);

        JMenu bookRideMenu = new JMenu("Book a Ride");
        bookRideMenu.setFont(menuFont);
     
        JMenuItem bookRideItem = new JMenuItem("Book Now");
        bookRideItem.addActionListener(e -> {
            frame.dispose();
            cabbook c1 = new cabbook(username);
        });
        bookRideItem.setFont(menuFont);
        bookRideItem.setToolTipText("Book a ride now");
        bookRideMenu.add(bookRideItem);

        menuBar.add(bookRideMenu);

        // Manage Bookings Menu
        JMenu manageBookingsMenu = new JMenu("Manage Bookings");
        manageBookingsMenu.setFont(menuFont);
        JMenuItem manageBookingsItem = new JMenuItem("Manage");
        manageBookingsItem.setFont(menuFont);
        manageBookingsItem.setToolTipText("Manage your bookings");
        manageBookingsMenu.add(manageBookingsItem);
        manageBookingsItem.addActionListener(e -> {
            frame.dispose();
            ManageBookings m1 = new ManageBookings(username);
        });
        menuBar.add(manageBookingsMenu);

        JMenu viewHistoryMenu = new JMenu("View Trip History");
        viewHistoryMenu.setFont(menuFont);
      JMenuItem viewHistoryItem = new JMenuItem("View History");

        viewHistoryItem.setFont(menuFont);
        viewHistoryItem.addActionListener(e -> {
            frame.dispose();
         new triphistory(username);
        });
              viewHistoryMenu.add(viewHistoryItem);
        menuBar.add(viewHistoryMenu);

        JMenu aboutusMenu = new JMenu("About Us");
        aboutusMenu.setFont(menuFont);
       JMenuItem aboutusItem = new JMenuItem("About Us");
        aboutusItem.setFont(menuFont);
        aboutusItem.setToolTipText("About our cab booking service");
        aboutusMenu.add(aboutusItem);
        aboutusItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
              new AboutUs();
            }
        });
        menuBar.add(aboutusMenu);

        // User Feedback Menu
        JMenu feedbackMenu = new JMenu("User Feedback");
        feedbackMenu.setFont(menuFont);
        JMenuItem feedbackItem = new JMenuItem("Feedback");
        feedbackItem.setFont(menuFont);
        feedbackItem.addActionListener(e -> {
            frame.dispose();
            new UserFeedback(username);
        });
       
        feedbackMenu.add(feedbackItem);
      
        menuBar.add(feedbackMenu);

        JMenu paymentMenu = new JMenu("Payment");
        paymentMenu.setFont(menuFont);
        JMenuItem paymentItem = new JMenuItem("pay");
       paymentItem.setFont(menuFont);
        paymentItem.setToolTipText("payment");
       
        paymentItem.addActionListener(e -> {
            frame.dispose();
           new PaymentPage(username);
        });
        paymentMenu.add(paymentItem);
        menuBar.add(paymentMenu);
        
        // Logout Menu
        JMenu logoutMenu = new JMenu("Logout");
        logoutMenu.setFont(menuFont);
             JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.setFont(menuFont);
        logoutItem.setToolTipText("Logout from your account");
        logoutItem.addActionListener(e -> {
            frame.dispose();
            cabBookingSystem cab=new cabBookingSystem();
        });
        menuBar.add(aboutusMenu);
  
        logoutMenu.add(logoutItem);
        menuBar.add(logoutMenu);

        frame.setJMenuBar(menuBar);

        try {
            BufferedImage backgroundImage = ImageIO.read(new File("C:\\Users\\HP\\Downloads\\taxibg.jpg"));
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            frame.setContentPane(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	  String username = "naveen";
    	    CabBookingMenu cb = new CabBookingMenu(username);
    	
    }
}
