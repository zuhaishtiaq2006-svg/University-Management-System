package university.management.system;

import javax.swing.*;
import java.awt.*;

public class About extends JFrame {

    public About() {
        setUndecorated(true);
        setSize(820, 520); // ✅ Compact size like other modules
        setLocationRelativeTo(null);
        setLayout(null);

        // 💜 Background panel
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(120, 81, 169)); // dreamy purple
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(null);
        background.setBounds(0, 0, 820, 520);
        add(background);

        // ❌ Close button
        JButton close = new JButton("X");
        close.setBounds(780, 10, 30, 30);
        close.setFont(new Font("Arial", Font.BOLD, 14));
        close.setForeground(Color.WHITE);
        close.setBackground(new Color(180, 60, 80));
        close.setFocusPainted(false);
        close.setBorderPainted(false);
        close.setCursor(new Cursor(Cursor.HAND_CURSOR));
        close.addActionListener(e -> setVisible(false));
        background.add(close);

        // 🏷️ Title
        JLabel title = new JLabel("UNIVERSITY MANAGEMENT SYSTEM");
        title.setBounds(60, 20, 700, 40);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        background.add(title);

        // 📝 Description
        JLabel desc = new JLabel("<html><center>This system is designed to streamline academic operations,<br>manage student records, and enhance administrative efficiency.</center></html>");
        desc.setBounds(60, 70, 700, 50);
        desc.setFont(new Font("Tahoma", Font.PLAIN, 16));
        desc.setForeground(Color.WHITE);
        desc.setHorizontalAlignment(SwingConstants.CENTER);
        background.add(desc);

        // 👥 Team panel (grid layout)
        JPanel teamPanel = new JPanel(new GridLayout(3, 2, 30, 20));
        teamPanel.setBounds(110, 140, 600, 280);
        teamPanel.setBackground(new Color(120, 81, 169));
        background.add(teamPanel);

        String[] names = {
            "Zuha Ishtiaq", "Arbab Jabeen", "Rabia Mukhtar", "Umar Nadeem", "Basmah Haroon"
        };
      

        // ✅ Load and resize image once
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/icons/pfp.png"));
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(img);

        for (int i = 0; i < names.length; i++) {
            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(180, 160));
            card.setBackground(new Color(220, 210, 240));
            card.setLayout(null);
            card.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 220), 1));

            JLabel photo = new JLabel(resizedIcon);
            photo.setBounds(10, 10, 80, 80);
            photo.setHorizontalAlignment(SwingConstants.CENTER);
            card.add(photo);

            JLabel nameLabel = new JLabel("<html><center>" + names[i] + "</center></html>");
            nameLabel.setBounds(100, 20, 160, 30);
            nameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            card.add(nameLabel);

            JLabel roleLabel = new JLabel("<html><center>"+ "</center></html>");
            roleLabel.setBounds(90, 40, 160, 30);
            roleLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
            roleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            card.add(roleLabel);

            teamPanel.add(card);
        }

        // 📌 Footer
        JLabel footer = new JLabel("Developed by Group 1 | Roll No: 034,006,021,005,007 | Contact: groupone03@gmail.com");
        footer.setBounds(60, 460, 700, 30);
        footer.setFont(new Font("Tahoma", Font.PLAIN, 14));
        footer.setForeground(Color.WHITE);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        background.add(footer);

        setVisible(true);
    }

    public static void main(String[] args) {
        new About();
    }
}
