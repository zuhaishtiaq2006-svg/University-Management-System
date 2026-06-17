package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Project extends JFrame implements ActionListener {

    private JPanel sidebar;
    private Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
    private Color btnFg = Color.WHITE;

    Project() {
        setTitle("University Management System");
        setSize(1540, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/one.png"));
        Image i2 = i1.getImage().getScaledInstance(1540, 850, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 1540, 850);
        add(background);

        // Sidebar panel with transparent + blur simulation
        sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                g2d.setColor(new Color(0, 0, 0));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        sidebar.setOpaque(false);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBounds(0, 0, 220, 850);
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        background.add(sidebar);

        String[] mainMenu = {
            "New Information", "View Details", "Apply Leave", "Leave Details",
            "Update Details", "Examination", "Fee Details", "Utility", "About", "Exit"
        };

        for (String item : mainMenu) {
            JButton btn = new JButton(item);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 40));
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setForeground(btnFg);
            btn.setFont(btnFont);
            btn.setFocusPainted(false);

            // Hover effect
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    btn.setForeground(new Color(255, 200, 255)); // lavender hover
                }
                public void mouseExited(MouseEvent e) {
                    btn.setForeground(btnFg);
                }
                public void mousePressed(MouseEvent e) {
                    btn.setForeground(Color.YELLOW); // click feedback
                }
                public void mouseReleased(MouseEvent e) {
                    btn.setForeground(new Color(255, 200, 255));
                }
            });

            btn.addActionListener(this);
            sidebar.add(btn);

            // Separator line
            JSeparator line = new JSeparator();
            line.setMaximumSize(new Dimension(180, 1));
            line.setForeground(new Color(255, 255, 255, 80)); // subtle white line
            sidebar.add(line);
            sidebar.add(Box.createVerticalStrut(6));
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String msg = ae.getActionCommand();

        switch (msg) {
            case "Exit":
                setVisible(false);
                break;

            case "Utility":
                showSubMenu(new String[]{"Notepad", "Calculator"}, ae);
                break;

            case "New Information":
                showSubMenu(new String[]{"New Faculty Information", "New Student Information"}, ae);
                break;

            case "View Details":
                showSubMenu(new String[]{"View Faculty Details", "View Student Details"}, ae);
                break;

            case "Apply Leave":
                showSubMenu(new String[]{"Faculty Leave", "Student Leave"}, ae);
                break;

            case "Leave Details":
                showSubMenu(new String[]{"Faculty Leave Details", "Student Leave Details"}, ae);
                break;

            case "Update Details":
                showSubMenu(new String[]{"Update Faculty Details", "Update Student Details"}, ae);
                break;

            case "Examination":
                showSubMenu(new String[]{"Enter Marks", "Examination Results"}, ae);
                break;

            case "Fee Details":
                showSubMenu(new String[]{"Fee Structure", "Student Fee Form"}, ae);
                break;

            case "About":
                new About();
                break;
        }
    }

    private void showSubMenu(String[] options, ActionEvent ae) {
        JButton sourceBtn = (JButton) ae.getSource();

        JPopupMenu popup = new JPopupMenu();
        popup.setOpaque(false);
        popup.setBackground(new Color(0, 0, 0, 0));
        popup.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));

        for (String opt : options) {
            JMenuItem item = new JMenuItem(opt);
            item.setOpaque(false);
            item.setContentAreaFilled(false);
            item.setBorderPainted(false);
            item.setForeground(Color.WHITE);
            item.setFont(btnFont);
            item.addActionListener(e -> launchModule(opt));
            popup.add(item);
        }

        popup.show(sourceBtn, sourceBtn.getWidth() + 10, 0);
    }

    private void launchModule(String opt) {
        try {
            switch (opt) {
                case "New Faculty Information": new AddTeacher(); break;
                case "New Student Information": new AddStudent(); break;
                case "View Faculty Details": new TeacherDetails(); break;
                case "View Student Details": new StudentDetails(); break;
                case "Faculty Leave": new TeacherLeave(); break;
                case "Student Leave": new StudentLeave(); break;
                case "Faculty Leave Details": new TeacherLeaveDetails(); break;
                case "Student Leave Details": new StudentLeaveDetails(); break;
                case "Update Faculty Details": new UpdateTeacher(); break;
                case "Update Student Details": new UpdateStudent(); break;
                case "Enter Marks": new EnterMarks(); break;
                case "Examination Results": new ExaminationDetails(); break;
                case "Fee Structure": new FeeStructure(); break;
                case "Student Fee Form": new StudentFeeForm(); break;
                case "Notepad": Runtime.getRuntime().exec("notepad.exe"); break;
                case "Calculator": Runtime.getRuntime().exec("calc.exe"); break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Project();
    }
}
