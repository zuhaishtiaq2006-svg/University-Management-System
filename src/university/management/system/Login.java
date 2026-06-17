package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JTextField tfusername;
    JPasswordField tfpassword;
    JButton login, cancel;

    Login(Image backgroundImage) {
        setTitle("UNIVERSITY MANAGEMENT SYSTEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        // ✅ Blur background image
        Image blurredImage = blurImage(backgroundImage);
        JLabel background = new JLabel(new ImageIcon(blurredImage));
        background.setLayout(null);
        setContentPane(background);

        // Transparent purple login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(new Color(128, 0, 128, 160));
        loginPanel.setBounds(width / 2 - 300, height / 2 - 150, 600, 300);
        background.add(loginPanel);

        // Username
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 70, 100, 25);
        lblusername.setForeground(Color.WHITE);
        lblusername.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginPanel.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(160, 70, 200, 25);
        loginPanel.add(tfusername);

        // Password
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 120, 100, 25);
        lblpassword.setForeground(Color.WHITE);
        lblpassword.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginPanel.add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(160, 120, 200, 25);
        loginPanel.add(tfpassword);

        // Login button
        login = new JButton("Login");
        login.setBounds(40, 180, 140, 30);
        login.setBackground(new Color(200, 180, 255)); // light purple
        login.setForeground(Color.BLACK);
        login.setFont(new Font("Tahoma", Font.BOLD, 15));
        login.addActionListener(this);
        loginPanel.add(login);

        // Cancel button
        cancel = new JButton("Cancel");
        cancel.setBounds(220, 180, 140, 30);
        cancel.setBackground(new Color(100, 0, 150)); // dark purple
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.addActionListener(this);
        loginPanel.add(cancel);

        // Profile icon
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/man.png"));
        Image rawImage = i1.getImage();
        int iconWidth = 260;
        int iconHeight = 160;
        Image scaledImage = rawImage.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(scaledImage));
        image.setBounds(370, 60, iconWidth, iconHeight);
        loginPanel.add(image);

        // ✅ Press Enter triggers Login
        getRootPane().setDefaultButton(login);

        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ✅ Blur method
    private Image blurImage(Image img) {
        BufferedImage buffered = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffered.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();

        float[] blurKernel = {
            1f/9f, 1f/9f, 1f/9f,
            1f/9f, 1f/9f, 1f/9f,
            1f/9f, 1f/9f, 1f/9f
        };
        ConvolveOp op = new ConvolveOp(new Kernel(3, 3, blurKernel));
        BufferedImage blurred = op.filter(buffered, null);
        return blurred;
    }

   @Override
public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == login) {
        String username = tfusername.getText();
        String password = tfpassword.getText();
            
        String query = "select * from login where username='"+username+"' and password='"+password+"'";

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(query);
                
            if (rs.next()) {
                setVisible(false);
                new Project();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    } else if (ae.getSource() == cancel) {
        setVisible(false);
    }
}

    public static void showLoginOverSplash(Image splashImage) {
        new Login(splashImage);
    }
}
