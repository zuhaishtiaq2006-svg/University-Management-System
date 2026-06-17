package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;

public class UpdateTeacher extends JFrame implements ActionListener {

    JTextField tfaddress, tfphone, tfemail, tfCNIC;
    JLabel labelEmpId, labelname, labelfname, labeldob;
    JDateChooser dchire;
    JComboBox cbexperience, cbeducation, cbdepartment;
    JButton submit, cancel;
    Choice cEmpId;

    public UpdateTeacher() {

        setUndecorated(true);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel background = new JPanel();
        background.setBounds(0, 0, 700, 550);
        background.setLayout(null);
        background.setBackground(new Color(120, 81, 169));
        add(background);

        JLabel heading = new JLabel("Update Teacher Details");
        heading.setBounds(220, 20, 400, 40);
        heading.setFont(new Font("serif", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        JLabel lblempId = new JLabel("Select Employee Id");
        lblempId.setBounds(40, 70, 150, 25);
        lblempId.setFont(new Font("serif", Font.BOLD, 16));
        lblempId.setForeground(Color.WHITE);
        background.add(lblempId);

        cEmpId = new Choice();
        cEmpId.setBounds(200, 75, 150, 25);
        background.add(cEmpId);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select empId from teacherr");
            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] labels = {
            "Name", "Father's Name", "Employee Id", "Date of Birth",
            "Address", "Phone", "Email", "Hire Date",
            "Experience", "CNIC", "Education", "Department"
        };

        int[][] pos = {
            {40,110}, {360,110}, {40,150}, {360,150},
            {40,190}, {360,190}, {40,230}, {360,230},
            {40,270}, {360,270}, {40,310}, {360,310}
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(pos[i][0], pos[i][1], 150, 25);
            lbl.setFont(new Font("serif", Font.BOLD, 16));
            lbl.setForeground(Color.WHITE);
            background.add(lbl);
        }

        labelname = new JLabel();
        labelname.setBounds(200,110,150,25);
        labelname.setForeground(Color.WHITE);
        background.add(labelname);

        labelfname = new JLabel();
        labelfname.setBounds(500,110,150,25);
        labelfname.setForeground(Color.WHITE);
        background.add(labelfname);

        labelEmpId = new JLabel();
        labelEmpId.setBounds(200,150,150,25);
        labelEmpId.setForeground(Color.WHITE);
        background.add(labelEmpId);

        labeldob = new JLabel();
        labeldob.setBounds(500,150,150,25);
        labeldob.setForeground(Color.WHITE);
        background.add(labeldob);

        tfaddress = new JTextField();
        tfaddress.setBounds(200,190,150,25);
        background.add(tfaddress);

        tfphone = new JTextField();
        tfphone.setBounds(500,190,150,25);
        background.add(tfphone);

        tfemail = new JTextField();
        tfemail.setBounds(200,230,150,25);
        background.add(tfemail);

        dchire = new JDateChooser();
        dchire.setBounds(500,230,150,25);
        background.add(dchire);

        String[] exp = {"1", "2", "3", "4", "5", "5+"};
        cbexperience = new JComboBox(exp);
        cbexperience.setBounds(200,270,150,25);
        background.add(cbexperience);

        tfCNIC = new JTextField();
        tfCNIC.setBounds(500,270,150,25);
        background.add(tfCNIC);

        String[] education = {"Bachelors", "Masters", "M.Phil", "PhD"};
        cbeducation = new JComboBox(education);
        cbeducation.setBounds(200,310,150,25);
        background.add(cbeducation);

        String[] department = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT"};
        cbdepartment = new JComboBox(department);
        cbdepartment.setBounds(500,310,150,25);
        background.add(cbdepartment);

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

        loadTeacherDetails();
        cEmpId.addItemListener(e -> loadTeacherDetails());

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
    private void loadTeacherDetails() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery(
                "select * from teacherr where empId='" + cEmpId.getSelectedItem() + "'"
            );

            if (rs.next()) {
                labelname.setText(rs.getString("name"));
                labelfname.setText(rs.getString("fname"));
                labelEmpId.setText(rs.getString("empId"));
                labeldob.setText(rs.getString("dob"));
                tfaddress.setText(rs.getString("address"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                ((JTextField)dchire.getDateEditor().getUiComponent())
                        .setText(rs.getString("hiredate"));
                cbexperience.setSelectedItem(rs.getString("experience"));
                tfCNIC.setText(rs.getString("CNIC"));
                cbeducation.setSelectedItem(rs.getString("education"));
                cbdepartment.setSelectedItem(rs.getString("department"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {

            String empId = labelEmpId.getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String hire = ((JTextField)dchire.getDateEditor().getUiComponent()).getText();
            String experience = (String) cbexperience.getSelectedItem();
            String CNIC = tfCNIC.getText();
            String education = (String) cbeducation.getSelectedItem();
            String department = (String) cbdepartment.getSelectedItem();

            try {
                String query =
                    "update teacherr set address='" + address +
                    "', phone='" + phone +
                    "', email='" + email +
                    "', hiredate='" + hire +
                    "', experience='" + experience +
                    "', CNIC='" + CNIC +
                    "', education='" + education +
                    "', department='" + department +
                    "' where empId='" + empId + "'";

                Conn con = new Conn();
                con.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Teacher Details Updated Successfully");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateTeacher();
    }
}
