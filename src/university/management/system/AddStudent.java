package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.*;

public class AddStudent extends JFrame implements ActionListener {

    JTextField tfname, tffname, tfaddress, tfphone, tfemail, tfx, tfxii, tfCNIC;
    JLabel labelrollno;
    JDateChooser dcdob;
    JComboBox cbcourse, cbbranch;
    JButton submit, cancel;

    Random ran = new Random();
    long first4 = Math.abs((ran.nextLong() % 9000L) + 1000L);

    public AddStudent() {
        setUndecorated(true); // removes title bar
        setSize(700, 550);    // same as AddTeacher
        setLocationRelativeTo(null); // center on screen
        setLayout(null);

        // ✅ Transparent purple background panel
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
        background.setOpaque(false);
        background.setLayout(null);
        background.setBounds(0, 0, 700, 550);
        add(background);

        JLabel heading = new JLabel("New Student Details");
        heading.setBounds(220, 20, 400, 40);
        heading.setFont(new Font("serif", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        String[] labels = {
            "Name", "Father's Name", "Roll Number", "Date of Birth", "Address", "Phone",
            "Email Id", "Class X (%)", "Class XII (%)", "CNIC Number", "Course", "Branch"
        };

        int[][] positions = {
            {40,80}, {360,80}, {40,130}, {360,130}, {40,180}, {360,180},
            {40,230}, {360,230}, {40,280}, {360,280}, {40,330}, {360,330}
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(positions[i][0], positions[i][1], 150, 25);
            lbl.setFont(new Font("serif", Font.BOLD, 16));
            lbl.setForeground(Color.WHITE);
            background.add(lbl);
        }

        tfname = new JTextField(); tfname.setBounds(180,80,150,25); background.add(tfname);
        tffname = new JTextField(); tffname.setBounds(500,80,150,25); background.add(tffname);
        labelrollno = new JLabel("1533" + first4); labelrollno.setBounds(180,130,150,25);
        labelrollno.setFont(new Font("serif", Font.BOLD, 16)); labelrollno.setForeground(Color.WHITE); background.add(labelrollno);
        dcdob = new JDateChooser(); dcdob.setBounds(500,130,150,25); background.add(dcdob);
        tfaddress = new JTextField(); tfaddress.setBounds(180,180,150,25); background.add(tfaddress);
        tfphone = new JTextField(); tfphone.setBounds(500,180,150,25); background.add(tfphone);
        tfemail = new JTextField(); tfemail.setBounds(180,230,150,25); background.add(tfemail);
        tfx = new JTextField(); tfx.setBounds(500,230,150,25); background.add(tfx);
        tfxii = new JTextField(); tfxii.setBounds(180,280,150,25); background.add(tfxii);
        tfCNIC = new JTextField(); tfCNIC.setBounds(500,280,150,25); background.add(tfCNIC);

        String[] course = {"B.Tech", "BBA", "BCA", "Bsc", "Msc", "MBA", "MCA", "MCom", "MA", "BA"};
        cbcourse = new JComboBox(course); cbcourse.setBounds(180,330,150,25); background.add(cbcourse);

        String[] branch = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT"};
        cbbranch = new JComboBox(branch); cbbranch.setBounds(500,330,150,25); background.add(cbbranch);

        // ✅ Buttons with hover effect
        submit = new JButton("Submit");
        submit.setBounds(180, 420, 120, 30);
        styleButton(submit);
        submit.addActionListener(this);
        background.add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(360, 420, 120, 30);
        styleButton(cancel);
        cancel.addActionListener(this);
        background.add(cancel);

        setVisible(true);
    }

    // ✅ Method to style buttons with hover effect
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

        String name = tfname.getText().trim();
        String fname = tffname.getText().trim();
        String rollno = labelrollno.getText();
        String dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText();
        String address = tfaddress.getText().trim();
        String phone = tfphone.getText().trim();
        String email = tfemail.getText().trim();
        String x = tfx.getText().trim();
        String xii = tfxii.getText().trim();
        String CNIC = tfCNIC.getText().trim();
        String course = (String) cbcourse.getSelectedItem();
        String branch = (String) cbbranch.getSelectedItem();

        // ❌ Empty Fields Check
        if (name.isEmpty() || fname.isEmpty() || dob.isEmpty() || address.isEmpty()
                || phone.isEmpty() || email.isEmpty() || x.isEmpty()
                || xii.isEmpty() || CNIC.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are mandatory");
            return;
        }

        // 📱 Phone Constraint
        if (!phone.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(null, "Phone number must be 11 digits");
            return;
        }

        // 🆔 CNIC Constraint
        if (!CNIC.matches("\\d{13}")) {
            JOptionPane.showMessageDialog(null, "CNIC must be 13 digits (without dashes)");
            return;
        }

        // 📧 Email Constraint
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(null, "Invalid Email Format");
            return;
        }

        // 📊 Marks Constraint
        try {
            double xMarks = Double.parseDouble(x);
            double xiiMarks = Double.parseDouble(xii);

            if (xMarks < 0 || xMarks > 100 || xiiMarks < 0 || xiiMarks > 100) {
                JOptionPane.showMessageDialog(null, "Marks must be between 0 and 100");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Marks must be numeric");
            return;
        }

        try {
            String query = "insert into student values('"+name+"', '"+fname+"', '"+rollno+"', '"+dob+"', '"+address+"', '"+phone+"', '"+email+"', '"+x+"', '"+xii+"', '"+CNIC+"', '"+course+"', '"+branch+"')";
            Conn con = new Conn();
            con.s.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Student Details Inserted Successfully");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    } else {
        setVisible(false);
    }
}


    public static void main(String[] args) {
        new AddStudent();
    }
}
