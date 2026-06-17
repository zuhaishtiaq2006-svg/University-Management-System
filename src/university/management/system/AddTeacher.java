package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.*;

public class AddTeacher extends JFrame implements ActionListener {

    JTextField tfname, tffname, tfaddress, tfphone, tfemail, tfCNIC;
    JLabel labelempId;
    JDateChooser dcdob,dchire;
    JComboBox cbcourse, cbbranch,cbexperience;
    JButton submit, cancel;

    Random ran = new Random();
    long first4 = Math.abs((ran.nextLong() % 9000L) + 1000L);

    public AddTeacher() {
        setUndecorated(true); // removes title bar
        setSize(700, 550);    // compact size
        setLocationRelativeTo(null); // center on screen
        setLayout(null);

        // ✅ Light purple background panel
        JPanel background = new JPanel();
        background.setLayout(null);
        background.setBounds(0, 0, 700, 550);
        background.setBackground(new Color(120, 81, 169)); // light purple
        add(background);

        JLabel heading = new JLabel("New Teacher Details");
        heading.setBounds(220, 20, 400, 40);
        heading.setFont(new Font("serif", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        String[] labels = {
            "Name", "Father's Name", "Employee Id", "Date of Birth", "Address", "Phone",
            "Email Id", "Hire Date", "Exerience", "CNIC Number", "Qualification", "Department"
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
        labelempId = new JLabel("101" + first4); labelempId.setBounds(180,130,150,25);
        labelempId.setFont(new Font("serif", Font.BOLD, 16)); labelempId.setForeground(Color.WHITE); background.add(labelempId);
        dcdob = new JDateChooser(); dcdob.setBounds(500,130,150,25); background.add(dcdob);
        tfaddress = new JTextField(); tfaddress.setBounds(180,180,150,25); background.add(tfaddress);
        tfphone = new JTextField(); tfphone.setBounds(500,180,150,25); background.add(tfphone);
        tfemail = new JTextField(); tfemail.setBounds(180,230,150,25); background.add(tfemail);
        dchire= new JDateChooser(); dchire.setBounds(500,230,150,25); background.add(dchire);
        String[] experien = {"1", "2", "3", "4", "5", "5+"};
        cbexperience = new JComboBox(experien); cbexperience.setBounds(180,280,150,25); background.add(cbexperience);
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
        Color purple = new Color(120, 81, 169);       // normal purple
        Color darkPurple = new Color(90, 50, 130);    // darker purple for hover
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
            String name = tfname.getText();
            String fname = tffname.getText();
            String rollno = labelempId.getText();
            String dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String hire = ((JTextField)dchire.getDateEditor().getUiComponent()).getText();
            String experience = (String)cbexperience.getSelectedItem();
            String CNIC = tfCNIC.getText();
            String course = (String) cbcourse.getSelectedItem();
            String branch = (String) cbbranch.getSelectedItem();
            
             // ❌ Empty Fields Check
        if (name.isEmpty() || fname.isEmpty() || dob.isEmpty() || address.isEmpty()
                || phone.isEmpty() || email.isEmpty() || hire.isEmpty()
                || experience.isEmpty() || CNIC.isEmpty()) {
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

            try {
                String query = "insert into teacherr values('"+name+"', '"+fname+"', '"+rollno+"', '"+dob+"', '"+address+"', '"+phone+"', '"+email+"', '"+hire+"', '"+experience+"', '"+CNIC+"', '"+course+"', '"+branch+"')";
                Conn con = new Conn();
                con.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Teacher Details Inserted Successfully");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddTeacher();
    }
}
