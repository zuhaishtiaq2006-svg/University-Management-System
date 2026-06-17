package university.management.system;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame implements Runnable {
    Thread t;
    JProgressBar progressBar;
    Image bgImage;

    Splash() {
        // Set title bar text
        setTitle("UNIVERSITY MANAGEMENT SYSTEM");

        // Enable frame controls
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false); // show title bar with close/minimize/restore

        // Get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        // Load and scale background image
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/icons/one.png"));
        bgImage = bgIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledBg = new ImageIcon(bgImage);

        JLabel background = new JLabel(scaledBg);
        background.setLayout(new BorderLayout());

        // Overlay panel
        JPanel overlay = new JPanel();
        overlay.setOpaque(false);
        overlay.setLayout(new BoxLayout(overlay, BoxLayout.Y_AXIS));
        overlay.setBorder(BorderFactory.createEmptyBorder(height / 3, 0, height / 5, 0));
       
        JLabel title = new JLabel("UNIVERSITY MANAGEMENT SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel tagline = new JLabel("Innovate. Educate. Administer.", SwingConstants.CENTER);
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        tagline.setForeground(new Color(200, 200, 255));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel loadingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(128, 0, 128, 80));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        loadingPanel.setOpaque(false);
        loadingPanel.setLayout(new BorderLayout());
        loadingPanel.setMaximumSize(new Dimension(width / 4, 10));

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(width / 4, 10));
        progressBar.setForeground(new Color(128, 0, 128));
        progressBar.setBackground(new Color(30, 30, 30));
        progressBar.setBorder(BorderFactory.createLineBorder(new Color(128, 0, 128), 1, true));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadingPanel.add(progressBar, BorderLayout.CENTER);

        JLabel loadingText = new JLabel("Loading...", SwingConstants.CENTER);
        loadingText.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loadingText.setForeground(Color.WHITE);
        loadingText.setAlignmentX(Component.CENTER_ALIGNMENT);

        overlay.add(title);
        overlay.add(Box.createVerticalStrut(15));
        overlay.add(tagline);
        overlay.add(Box.createVerticalStrut(80));
        overlay.add(loadingPanel);
        overlay.add(Box.createVerticalStrut(15));
        overlay.add(loadingText);

        background.add(overlay, BorderLayout.CENTER);
        setContentPane(background);

        // Frame settings
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= 100; i++) {
                progressBar.setValue(i);
                Thread.sleep(40);
            }

            setVisible(false);
            Login.showLoginOverSplash(bgImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
