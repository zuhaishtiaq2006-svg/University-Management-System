package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentFeeForm extends JFrame implements ActionListener {

    Choice crollno;
    JComboBox<String> cbcourse, cbbranch, cbsemester;
    JLabel labeltotal, labelname, labelfname;
    JButton update, pay, back;

    public StudentFeeForm() {
        setUndecorated(true);
        setSize(820, 520);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(120, 81, 169));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(null);
        background.setBounds(0, 0, 820, 520);
        add(background);

        JLabel heading = new JLabel("Student Fee Payment");
        heading.setBounds(40, 20, 600, 40);
        heading.setFont(new Font("Serif", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/icons/feee.png"));
        Image i2 = i1.getImage().getScaledInstance(280, 220, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(40, 120, 280, 220);
        background.add(image);

        int formX = 360, labelW = 140, fieldW = 180, rowH = 28, gapY = 35;
        int y = 80;

        JLabel lblroll = new JLabel("Roll No");
        lblroll.setBounds(formX, y, labelW, rowH);
        styleLabel(lblroll); background.add(lblroll);

        crollno = new Choice();
        crollno.setBounds(formX + labelW + 10, y, fieldW, 25);
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

        y += gapY;
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(formX, y, labelW, rowH);
        styleLabel(lblname); background.add(lblname);

        labelname = new JLabel();
        labelname.setBounds(formX + labelW + 10, y, fieldW, rowH);
        styleValue(labelname); background.add(labelname);

        y += gapY;
        JLabel lblfname = new JLabel("Father's Name");
        lblfname.setBounds(formX, y, labelW, rowH);
        styleLabel(lblfname); background.add(lblfname);

        labelfname = new JLabel();
        labelfname.setBounds(formX + labelW + 10, y, fieldW, rowH);
        styleValue(labelfname); background.add(labelfname);

        y += gapY;
        JLabel lblcourse = new JLabel("Course");
        lblcourse.setBounds(formX, y, labelW, rowH);
        styleLabel(lblcourse); background.add(lblcourse);

        String[] course = {"BTech", "BBA", "BCA", "Bsc", "Msc", "MBA", "MCA", "MCom", "MA", "BA"};
        cbcourse = new JComboBox<>(course);
        cbcourse.setBounds(formX + labelW + 10, y, fieldW, 25);
        background.add(cbcourse);

        y += gapY;
        JLabel lblbranch = new JLabel("Branch");
        lblbranch.setBounds(formX, y, labelW, rowH);
        styleLabel(lblbranch); background.add(lblbranch);

        String[] branch = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT"};
        cbbranch = new JComboBox<>(branch);
        cbbranch.setBounds(formX + labelW + 10, y, fieldW, 25);
        background.add(cbbranch);

        y += gapY;
        JLabel lblsem = new JLabel("Semester");
        lblsem.setBounds(formX, y, labelW, rowH);
        styleLabel(lblsem); background.add(lblsem);

        String[] semester = {"Semester1", "Semester2", "Semester3", "Semester4", "Semester5", "Semester6", "Semester7", "Semester8"};
        cbsemester = new JComboBox<>(semester);
        cbsemester.setBounds(formX + labelW + 10, y, fieldW, 25);
        background.add(cbsemester);

        y += gapY;
        JLabel lbltotal = new JLabel("Total Payable");
        lbltotal.setBounds(formX, y, labelW, rowH);
        styleLabel(lbltotal); background.add(lbltotal);

        labeltotal = new JLabel();
        labeltotal.setBounds(formX + labelW + 10, y, fieldW, rowH);
        styleValue(labeltotal); background.add(labeltotal);

        update = new JButton("Update");
        update.setBounds(formX, y + gapY + 20, 100, 30);
        styleButton(update); update.addActionListener(this); background.add(update);

        pay = new JButton("Pay Fee");
        pay.setBounds(formX + 120, y + gapY + 20, 120, 30);
        styleButton(pay); pay.addActionListener(this); background.add(pay);

        back = new JButton("Back");
        back.setBounds(formX + 260, y + gapY + 20, 100, 30);
        styleButton(back); back.addActionListener(this); background.add(back);

        updateStudentInfo();
        crollno.addItemListener(e -> {
            updateStudentInfo();
            labeltotal.setText(""); // Constraint: Reset total when student changes
        });

        setVisible(true);
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
    }

    private void styleValue(JLabel label) {
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
    }

    private void styleButton(JButton button) {
        Color purple = new Color(120, 81, 169);
        Color darkPurple = new Color(90, 50, 130);
        button.setBackground(Color.WHITE);
        button.setForeground(purple);
        button.setFont(new Font("Tahoma", Font.BOLD, 13));
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

    private void updateStudentInfo() {
        try {
            Conn c = new Conn();
            String query = "select * from student where rollno='" + crollno.getSelectedItem() + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                labelname.setText(rs.getString("name"));
                labelfname.setText(rs.getString("fname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String course = (String) cbcourse.getSelectedItem();
            String semester = (String) cbsemester.getSelectedItem();
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from fee where course = '" + course + "'");
                if (rs.next()) {
                    labeltotal.setText(rs.getString(semester));
                } else {
                    JOptionPane.showMessageDialog(null, "Fee structure not found for " + course);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == pay) {
            String rollno = crollno.getSelectedItem();
            String course = (String) cbcourse.getSelectedItem();
            String semester = (String) cbsemester.getSelectedItem();
            String branch = (String) cbbranch.getSelectedItem();
            String total = labeltotal.getText();

            // CONSTRAINT 1: Check if fee is updated before paying
            if (total == null || total.equals("")) {
                JOptionPane.showMessageDialog(null, "Please click 'Update' to calculate fee first!");
                return;
            }

            try {
                Conn c = new Conn();
                
                // CONSTRAINT 2: Duplicate Payment Check (Check if already paid)
                String checkQuery = "select * from collegefee where rollno = '"+rollno+"' and semester = '"+semester+"'";
                ResultSet rs = c.s.executeQuery(checkQuery);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Fee already submitted for this Semester!");
                    return;
                }

                // DATA INSERTION
                String query = "insert into collegefee values('" + rollno + "', '" + course + "', '" + branch + "', '" + semester + "', '" + total + "')";
                c.s.executeUpdate(query);
                
                JOptionPane.showMessageDialog(null, "College fee submitted successfully");
                setVisible(false);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentFeeForm();
    }
}