package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateStudent extends JFrame implements ActionListener {

    JTextField tfaddress, tfphone, tfemail, tfx, tfxii, tfCNIC;
    JLabel labelrollno, labelname, labelfname, labeldob;
    JComboBox cbcourse, cbbranch;
    JButton submit, cancel;
    Choice crollno;

    public UpdateStudent() {
        setUndecorated(true);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setLayout(null);

        // ✅ Dark purple background panel
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
        background.setBounds(0, 0, 700, 550);
        add(background);

        JLabel heading = new JLabel("Update Student Details");
        heading.setBounds(220, 20, 400, 40);
        heading.setFont(new Font("serif", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        JLabel lblrollnumber = new JLabel("Select Roll Number");
        lblrollnumber.setBounds(30, 75, 150, 25);
        lblrollnumber.setFont(new Font("serif", Font.BOLD, 16));
        lblrollnumber.setForeground(Color.WHITE);
        background.add(lblrollnumber);

        crollno = new Choice();
        crollno.setBounds(180, 80, 150, 25);
        background.add(crollno);

        try {
            // NOTE: Assuming Conn class correctly establishes DB connection
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from student");
            while (rs.next()) {
                crollno.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] labels = {
            "Name", "Father's Name", "Roll Number", "Date of Birth", "Address", "Phone",
            "Email Id", "Class X (%)", "Class XII (%)", "CNIC Number", "Course", "Branch"
        };

        int[][] positions = {
            {40,110}, {360,110}, {40,150}, {360,150}, {40,190}, {360,190},
            {40,230}, {360,230}, {40,270}, {360,270}, {40,310}, {360,310}
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(positions[i][0], positions[i][1], 150, 25);
            lbl.setFont(new Font("serif", Font.BOLD, 16));
            lbl.setForeground(Color.WHITE);
            background.add(lbl);
        }

        labelname = new JLabel(); labelname.setBounds(180,110,150,25); labelname.setForeground(Color.WHITE); background.add(labelname);
        labelfname = new JLabel(); labelfname.setBounds(500,110,150,25); labelfname.setForeground(Color.WHITE); background.add(labelfname);
        labelrollno = new JLabel(); labelrollno.setBounds(180,150,150,25); labelrollno.setForeground(Color.WHITE); background.add(labelrollno);
        labeldob = new JLabel(); labeldob.setBounds(500,150,150,25); labeldob.setForeground(Color.WHITE); background.add(labeldob);

        tfaddress = new JTextField(); tfaddress.setBounds(180,190,150,25); background.add(tfaddress);
        tfphone = new JTextField(); tfphone.setBounds(500,190,150,25); background.add(tfphone);
        tfemail = new JTextField(); tfemail.setBounds(180,230,150,25); background.add(tfemail);
        tfx = new JTextField(); tfx.setBounds(500,230,150,25); background.add(tfx);
        tfxii = new JTextField(); tfxii.setBounds(180,270,150,25); background.add(tfxii);
        tfCNIC = new JTextField(); tfCNIC.setBounds(500,270,150,25); background.add(tfCNIC);

        String[] course = {"B.Tech", "BBA", "BCA", "Bsc", "Msc", "MBA", "MCA", "MCom", "MA", "BA"};
        cbcourse = new JComboBox(course); cbcourse.setBounds(180,310,150,25); background.add(cbcourse);

        String[] branch = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT"};
        cbbranch = new JComboBox(branch); cbbranch.setBounds(500,310,150,25); background.add(cbbranch);

        submit = new JButton("Update");
        submit.setBounds(180, 400, 120, 30);
        styleButton(submit);
        submit.addActionListener(this);
        background.add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(360, 400, 120, 30);
        styleButton(cancel);
        cancel.addActionListener(this);
        background.add(cancel);

        loadStudentDetails();

        crollno.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                loadStudentDetails();
            }
        });

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

    private void loadStudentDetails() {
        try {
            Conn c = new Conn();
            String query = "select * from student where rollno='" + crollno.getSelectedItem() + "'";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                labelname.setText(rs.getString("name"));
                labelfname.setText(rs.getString("fname"));
                labeldob.setText(rs.getString("dob"));
                tfaddress.setText(rs.getString("address"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                tfx.setText(rs.getString("class_x"));
                tfxii.setText(rs.getString("class_xii"));
                tfCNIC.setText(rs.getString("CNIC"));
                labelrollno.setText(rs.getString("rollno"));
                // Set selected item for JComboBoxes
                cbcourse.setSelectedItem(rs.getString("course"));
                cbbranch.setSelectedItem(rs.getString("branch"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String rollno = labelrollno.getText();
            String address = tfaddress.getText().trim();
            String phone = tfphone.getText().trim();
            String email = tfemail.getText().trim();
            String x = tfx.getText().trim();
            String xii = tfxii.getText().trim();
            String CNIC = tfCNIC.getText().trim();
            String course = (String) cbcourse.getSelectedItem();
            String branch = (String) cbbranch.getSelectedItem();
            
            // --- Constraint / Input Validation ---
            
            if (address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Address cannot be empty.");
                tfaddress.requestFocus();
                return;
            }

            // Phone Number Validation (11 digits, numeric only)
            if (!phone.matches("^\\d{11}$")) {
                JOptionPane.showMessageDialog(null, "Phone number must be exactly 11 digits and contain only numbers.");
                tfphone.requestFocus();
                return;
            }

            // Email Validation (@ and .)
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email address (must contain '@' and '.').");
                tfemail.requestFocus();
                return;
            }
            
            // CNIC Validation (13 digits, numeric only)
            if (!CNIC.matches("^\\d{13}$")) {
                JOptionPane.showMessageDialog(null, "CNIC must be exactly 13 digits and contain only numbers.");
                tfCNIC.requestFocus();
                return;
            }

            try {
                // Percentage Validation (Numeric, 0-100)
                double classX = Double.parseDouble(x);
                double classXII = Double.parseDouble(xii);

                if (classX < 0 || classX > 100) {
                    JOptionPane.showMessageDialog(null, "Class X percentage must be between 0 and 100.");
                    tfx.requestFocus();
                    return;
                }
                
                if (classXII < 0 || classXII > 100) {
                    JOptionPane.showMessageDialog(null, "Class XII percentage must be between 0 and 100.");
                    tfxii.requestFocus();
                    return;
                }
                
                // If all validations pass, execute the update query
                String query = "update student set address='" + address + "', phone='" + phone + "', email='" + email + "', class_x='" + x + "', class_xii='" + xii + "', CNIC='" + CNIC + "', course='" + course + "', branch='" + branch + "' where rollno='" + rollno + "'";
                
                Conn con = new Conn();
                con.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Student Details Updated Successfully");
                setVisible(false);
                
            } catch (NumberFormatException e) {
                 JOptionPane.showMessageDialog(null, "Class X and Class XII percentages must be valid numbers.");
                 e.printStackTrace();
                 return;
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred during database update.");
            }
            
        } else {
            // Cancel button functionality
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateStudent();
    }
}