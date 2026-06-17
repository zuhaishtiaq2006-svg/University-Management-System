package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class EnterMarks extends JFrame implements ActionListener {

    Choice crollno;
    JComboBox cbsemester;
    JTextField tfsub1, tfsub2, tfsub3, tfsub4, tfsub5;
    JTextField tfmarks1, tfmarks2, tfmarks3, tfmarks4, tfmarks5;
    JButton cancel, submit;

    public EnterMarks() {
        setUndecorated(true);
        setSize(800, 520); // ✅ Slightly taller for button spacing
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(120, 81, 169));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        background.setLayout(null);
        background.setBounds(0, 0, 820, 520);
        add(background);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/examss.png"));
        Image i2 = i1.getImage().getScaledInstance(280, 220, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(40, 120, 280, 220);
        background.add(image);

        int formX = 360;
        int labelWidth = 140;
        int fieldWidth = 180;
        int marksWidth = 120; // ✅ Wider marks fields
        int rowHeight = 28;
        int gapY = 35;
        int startY = 40;

        JLabel heading = new JLabel("Enter Marks of Student");
        heading.setBounds(formX, startY, 300, 30);
        heading.setFont(new Font("serif", Font.BOLD, 22));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        startY += gapY;

        JLabel lblrollnumber = new JLabel("Select Roll Number");
        lblrollnumber.setBounds(formX, startY, labelWidth, rowHeight);
        lblrollnumber.setFont(new Font("serif", Font.BOLD, 16));
        lblrollnumber.setForeground(Color.WHITE);
        background.add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(formX + labelWidth + 10, startY, fieldWidth, rowHeight);
        background.add(crollno);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        startY += gapY;

        JLabel lblsemester = new JLabel("Select Semester");
        lblsemester.setBounds(formX, startY, labelWidth, rowHeight);
        lblsemester.setFont(new Font("serif", Font.BOLD, 16));
        lblsemester.setForeground(Color.WHITE);
        background.add(lblsemester);

        String[] semester = {
            "1st Semester", "2nd Semester", "3rd Semester", "4th Semester",
            "5th Semester", "6th Semester", "7th Semester", "8th Semester"
        };
        cbsemester = new JComboBox(semester);
        cbsemester.setBounds(formX + labelWidth + 10, startY, fieldWidth, rowHeight);
        cbsemester.setBackground(Color.WHITE);
        background.add(cbsemester);

        startY += gapY + 10;

        JLabel lblentersubject = new JLabel("Enter Subject");
        lblentersubject.setBounds(formX, startY, labelWidth, rowHeight);
        lblentersubject.setFont(new Font("serif", Font.BOLD, 16));
        lblentersubject.setForeground(Color.WHITE);
        background.add(lblentersubject);

        JLabel lblentermarks = new JLabel("Enter Marks");
        lblentermarks.setBounds(formX + fieldWidth + 30, startY, labelWidth, rowHeight);
        lblentermarks.setFont(new Font("serif", Font.BOLD, 16));
        lblentermarks.setForeground(Color.WHITE);
        background.add(lblentermarks);

        startY += gapY;

        tfsub1 = new JTextField(); tfsub1.setBounds(formX, startY, fieldWidth, rowHeight); background.add(tfsub1);
        tfmarks1 = new JTextField(); tfmarks1.setBounds(formX + fieldWidth + 30, startY, marksWidth, rowHeight); background.add(tfmarks1); startY += gapY;
        tfsub2 = new JTextField(); tfsub2.setBounds(formX, startY, fieldWidth, rowHeight); background.add(tfsub2);
        tfmarks2 = new JTextField(); tfmarks2.setBounds(formX + fieldWidth + 30, startY, marksWidth, rowHeight); background.add(tfmarks2); startY += gapY;
        tfsub3 = new JTextField(); tfsub3.setBounds(formX, startY, fieldWidth, rowHeight); background.add(tfsub3);
        tfmarks3 = new JTextField(); tfmarks3.setBounds(formX + fieldWidth + 30, startY, marksWidth, rowHeight); background.add(tfmarks3); startY += gapY;
        tfsub4 = new JTextField(); tfsub4.setBounds(formX, startY, fieldWidth, rowHeight); background.add(tfsub4);
        tfmarks4 = new JTextField(); tfmarks4.setBounds(formX + fieldWidth + 30, startY, marksWidth, rowHeight); background.add(tfmarks4); startY += gapY;
        tfsub5 = new JTextField(); tfsub5.setBounds(formX, startY, fieldWidth, rowHeight); background.add(tfsub5);
        tfmarks5 = new JTextField(); tfmarks5.setBounds(formX + fieldWidth + 30, startY, marksWidth, rowHeight); background.add(tfmarks5);

        submit = new JButton("Submit");
        submit.setBounds(formX, startY + gapY + 20, 150, 30); // ✅ Lowered
        styleButton(submit);
        submit.addActionListener(this);
        background.add(submit);

        cancel = new JButton("Back");
        cancel.setBounds(formX + 170, startY + gapY + 20, 150, 30); // ✅ Lowered
        styleButton(cancel);
        cancel.addActionListener(this);
        background.add(cancel);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        Color purple = new Color(120, 81, 169);
        Color darkPurple = new Color(90, 50, 130);
        button.setBackground(Color.WHITE);
        button.setForeground(purple);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(purple));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(darkPurple);
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(purple);
            }
        });
    }

  @Override
public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == submit) {
        try {
            // Arrays for easy iteration
            JTextField[] subjects = {tfsub1, tfsub2, tfsub3, tfsub4, tfsub5};
            JTextField[] marksArr = {tfmarks1, tfmarks2, tfmarks3, tfmarks4, tfmarks5};

            // 1. CONSTRAINT: Minimum 3 Subjects mandatory
            for (int i = 0; i < 3; i++) {
                if (subjects[i].getText().trim().isEmpty() || marksArr[i].getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Error: First 3 subjects and marks are mandatory!");
                    subjects[i].requestFocus();
                    return;
                }
            }

            // 2. CONSTRAINT: Check Optional Fields (4th and 5th)
            // Agar subject likha hai toh marks hone chahiye, aur marks hain toh subject
            for (int i = 3; i < 5; i++) {
                boolean subEmpty = subjects[i].getText().trim().isEmpty();
                boolean markEmpty = marksArr[i].getText().trim().isEmpty();
                
                if (subEmpty != markEmpty) { // One is filled, other is empty
                    JOptionPane.showMessageDialog(null, "Error: Please provide both Subject and Marks for Row " + (i + 1));
                    if (subEmpty) subjects[i].requestFocus(); else marksArr[i].requestFocus();
                    return;
                }
            }

            // 3. CONSTRAINT: Numeric & Range Check (Only for filled marks)
            for (int i = 0; i < 5; i++) {
                String markText = marksArr[i].getText().trim();
                if (!markText.isEmpty()) {
                    try {
                        int m = Integer.parseInt(markText);
                        if (m < 0 || m > 100) {
                            JOptionPane.showMessageDialog(null, "Error: Marks must be between 0-100 (Subject " + (i+1) + ")");
                            marksArr[i].requestFocus();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: Marks must be a number (Subject " + (i+1) + ")");
                        marksArr[i].requestFocus();
                        return;
                    }
                }
            }

            // 4. CONSTRAINT: Duplicate Entry Check (Roll No + Semester)
            Conn c = new Conn();
            String roll = crollno.getSelectedItem();
            String sem = (String) cbsemester.getSelectedItem();
            
            ResultSet rs = c.s.executeQuery("select * from marks where rollno = '"+roll+"' and semester = '"+sem+"'");
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Error: Marks already exist for this Student and Semester!");
                return;
            }

            // 5. DATA INSERTION
            String q1 = "insert into subject values('" + roll + "', '" + sem + "', '" + tfsub1.getText() + "', '" + tfsub2.getText() + "', '" + tfsub3.getText() + "', '" + tfsub4.getText() + "', '" + tfsub5.getText() + "')";
            String q2 = "insert into marks values('" + roll + "', '" + sem + "', '" + tfmarks1.getText() + "', '" + tfmarks2.getText() + "', '" + tfmarks3.getText() + "', '" + tfmarks4.getText() + "', '" + tfmarks5.getText() + "')";

            c.s.executeUpdate(q1);
            c.s.executeUpdate(q2);

            JOptionPane.showMessageDialog(null, "Success: Records Inserted Successfully!");
            setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Connection Error!");
        }
    } else {
        setVisible(false);
    }
}
    public static void main(String[] args) {
        new EnterMarks();
    }
}
