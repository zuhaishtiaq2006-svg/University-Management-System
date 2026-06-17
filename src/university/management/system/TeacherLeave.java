package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.*;

public class TeacherLeave extends JFrame implements ActionListener {

    Choice cEmpId, ctime;
    JDateChooser dcdate;
    JButton submit, cancel;

    public TeacherLeave() {
        setUndecorated(true);
        setSize(500, 400); // ✅ compact size
        setLocationRelativeTo(null);
        setLayout(null);

        // ✅ Dark purple background panel
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(120, 81, 169)); // dark purple
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        background.setOpaque(false);
        background.setLayout(null);
        background.setBounds(0, 0, 500, 400);
        add(background);

        Color textColor = Color.WHITE;

        JLabel heading = new JLabel("Apply Leave (Teacher)");
        heading.setBounds(120, 30, 300, 30);
        heading.setFont(new Font("serif", Font.BOLD, 24));
        heading.setForeground(textColor);
        background.add(heading);

        JLabel lblempId = new JLabel("Search by Employee Id");
        lblempId.setBounds(60, 90, 200, 25);
        lblempId.setFont(new Font("serif", Font.BOLD, 16));
        lblempId.setForeground(textColor);
        background.add(lblempId);

        cEmpId = new Choice();
        cEmpId.setBounds(260, 90, 180, 25);
        background.add(cEmpId);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from teacherr");
            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lbldate = new JLabel("Date");
        lbldate.setBounds(60, 140, 200, 25);
        lbldate.setFont(new Font("serif", Font.BOLD, 16));
        lbldate.setForeground(textColor);
        background.add(lbldate);

        dcdate = new JDateChooser();
        dcdate.setBounds(260, 140, 180, 25);
        background.add(dcdate);

        JLabel lbltime = new JLabel("Time Duration");
        lbltime.setBounds(60, 190, 200, 25);
        lbltime.setFont(new Font("serif", Font.BOLD, 16));
        lbltime.setForeground(textColor);
        background.add(lbltime);

        ctime = new Choice();
        ctime.setBounds(260, 190, 180, 25);
        ctime.add("Full Day");
        ctime.add("Half Day");
        background.add(ctime);

        submit = new JButton("Submit");
        submit.setBounds(120, 270, 100, 30);
        styleButton(submit);
        submit.addActionListener(this);
        background.add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(260, 270, 100, 30);
        styleButton(cancel);
        cancel.addActionListener(this);
        background.add(cancel);

        setVisible(true);
    }

    // ✅ Button styling with hover effect
    private void styleButton(JButton button) {
        Color purple = new Color(120, 81, 169);
        Color darkPurple = new Color(90, 50, 130);
        button.setBackground(Color.WHITE);
        button.setForeground(purple);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(purple));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(darkPurple);
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(purple);
            }
        });
    }

    @Override
  
public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == submit) {

        String empId = cEmpId.getSelectedItem();
        String date = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();
        String duration = ctime.getSelectedItem();

        // ❌ Empty Check
        if (empId == null || empId.isEmpty() || date.isEmpty() || duration == null) {
            JOptionPane.showMessageDialog(null, "All fields are mandatory");
            return;
        }

        // 📅 Date Validation (no past date)
        try {
            java.util.Date selectedDate = dcdate.getDate();
            java.util.Date today = new java.util.Date();

            if (selectedDate.before(today)) {
                JOptionPane.showMessageDialog(null, "Leave date cannot be in the past");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please select a valid date");
            return;
        }

        try {
            Conn c = new Conn();

            // 🔁 Duplicate Leave Check
            String checkQuery = "select * from teacherrleave where empId = ? and date = ?";
            PreparedStatement pstCheck = c.c.prepareStatement(checkQuery);
            pstCheck.setString(1, empId);
            pstCheck.setString(2, date);
            ResultSet rs = pstCheck.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Leave already applied for this date");
                rs.close();
                pstCheck.close();
                c.c.close();
                return;
            }

            // ✅ Insert Leave
            String insertQuery = "insert into teacherrleave values(?, ?, ?)";
            PreparedStatement pst = c.c.prepareStatement(insertQuery);
            pst.setString(1, empId);
            pst.setString(2, date);
            pst.setString(3, duration);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Leave Confirmed Successfully");
            pst.close();
            c.c.close();
            setVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    } else {
        setVisible(false);
    }
}


    public static void main(String[] args) {
        new TeacherLeave();
    }
}
