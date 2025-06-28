package cab_booking;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentPage extends JFrame {
    private JRadioButton cashRadioButton;
    private JRadioButton upiRadioButton;
    private ImageIcon qrCodeIcon;

    public PaymentPage(String username) {
        setTitle("Payment Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to maximize the frame
        setUndecorated(false); // Remove frame decorations
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null); // Set layout to null

        JLabel paymentTypeLabel = new JLabel("Payment Type:");
        paymentTypeLabel.setBounds(10, 10, 100, 30); // Set bounds for paymentTypeLabel

        cashRadioButton = new JRadioButton("Cash");
        cashRadioButton.setBounds(120, 10, 80, 30); // Set bounds for cashRadioButton

        upiRadioButton = new JRadioButton("UPI");
        upiRadioButton.setBounds(220, 10, 80, 30); // Set bounds for upiRadioButton

        ButtonGroup paymentTypeGroup = new ButtonGroup();
        paymentTypeGroup.add(cashRadioButton);
        paymentTypeGroup.add(upiRadioButton);

        JButton payButton = new JButton("Pay Now");
        payButton.setBounds(120, 180, 100, 30); // Set bounds for payButton

        JButton backButton = new JButton("back");
        backButton.setBounds(400, 180, 100, 30); 
        qrCodeIcon = new ImageIcon(PaymentPage.class.getResource("QRimage.png"));
    
        JButton showQRButton = new JButton("Show QR Code");
        showQRButton.setVisible(false);
        showQRButton.setBounds(120, 120, 150, 30); // Set bounds for showQRButton

        showQRButton.addActionListener((ActionEvent e) -> {
            showQRCodeFrame();
           // JOptionPane.showMessageDialog(null, "your payment is successful"); 
        });
      
         backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
             new CabBookingMenu(username);
            }
        });
         payButton.addActionListener((ActionEvent e) -> {
        	 showQRButton.setVisible(true);
        	
         });

        panel.add(paymentTypeLabel);
        panel.add(cashRadioButton);
        panel.add(upiRadioButton);
        panel.add(payButton);
        panel.add(showQRButton);
        panel.add(backButton);
        add(panel);
        setVisible(true);
    }

    private void showQRCodeFrame() {
        JFrame qrFrame = new JFrame("QR Code");
        qrCodeIcon = new ImageIcon(PaymentPage.class.getResource("QRimage.png"));
        qrCodeIcon = resizeImage(qrCodeIcon, 300, 300); // Resize the image
        JLabel qrCodeLabel = new JLabel(qrCodeIcon);
        qrCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        qrFrame.getContentPane().add(qrCodeLabel);
        qrFrame.setSize(300, 300);
        qrFrame.setLocationRelativeTo(this);
        qrFrame.setVisible(true);
      
    }

    private ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    public static void main(String[] args) {
        String username = "naveen";
        new PaymentPage(username);
    }
}
