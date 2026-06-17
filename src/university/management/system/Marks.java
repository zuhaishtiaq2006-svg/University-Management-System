package university.management.system;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class Marks extends JFrame implements ActionListener {

    String rollno;
    JButton cancel;
    JTable table;
    JLabel lblsemester;

    public Marks(String rollno) {
        this.rollno = rollno;

        setUndecorated(true);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        // ✅ Dark purple background
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(120, 81, 169));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(null);
        background.setBounds(0, 0, 500, 600);
        add(background);

        JLabel heading = new JLabel("CUI SAHIWAL CAMPUS");
        heading.setBounds(120, 20, 400, 30);
        heading.setFont(new Font("serif", Font.BOLD, 22));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        JLabel subheading = new JLabel("Result of Examination 2024");
        subheading.setBounds(120, 55, 400, 25);
        subheading.setFont(new Font("serif", Font.BOLD, 18));
        subheading.setForeground(Color.WHITE);
        background.add(subheading);

        JLabel lblrollno = new JLabel("Roll Number: " + rollno);
        lblrollno.setBounds(60, 100, 400, 25);
        lblrollno.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblrollno.setForeground(Color.WHITE);
        background.add(lblrollno);

        lblsemester = new JLabel();
        lblsemester.setBounds(60, 130, 400, 25);
        lblsemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblsemester.setForeground(Color.WHITE);
        background.add(lblsemester);

        // ✅ Table for subjects and marks
        String[] columnNames = {"Subject", "Marks"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    comp.setBackground(row % 2 == 0 ? new Color(220, 210, 240) : Color.WHITE);
                } else {
                    comp.setBackground(new Color(180, 160, 220));
                }
                return comp;
            }
        };
        table.setFont(new Font("Tahoma", Font.PLAIN, 15));
        table.setRowHeight(25);

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(60, 170, 380, 300);
        jsp.getViewport().setBackground(new Color(220, 210, 240));
        background.add(jsp);

        // ✅ Fetch subjects and marks
        try {
            Conn c = new Conn();
            String[] subjects = new String[5];
            String[] marks = new String[5];

            ResultSet rs1 = c.s.executeQuery("select * from subject where rollno = '" + rollno + "'");
            while (rs1.next()) {
                subjects[0] = rs1.getString("subject1");
                subjects[1] = rs1.getString("subject2");
                subjects[2] = rs1.getString("subject3");
                subjects[3] = rs1.getString("subject4");
                subjects[4] = rs1.getString("subject5");
            }

            ResultSet rs2 = c.s.executeQuery("select * from marks where rollno = '" + rollno + "'");
            while (rs2.next()) {
                marks[0] = rs2.getString("marks1");
                marks[1] = rs2.getString("marks2");
                marks[2] = rs2.getString("marks3");
                marks[3] = rs2.getString("marks4");
                marks[4] = rs2.getString("marks5");
                lblsemester.setText("Semester: " + rs2.getString("semester"));
            }

            for (int i = 0; i < 5; i++) {
                if (subjects[i] != null && marks[i] != null) {
                    model.addRow(new Object[]{subjects[i], marks[i]});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cancel = new JButton("Back");
        cancel.setBounds(180, 500, 120, 30);
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
        setVisible(false);
    }

    public static void main(String[] args) {
        new Marks("");
    }
}
