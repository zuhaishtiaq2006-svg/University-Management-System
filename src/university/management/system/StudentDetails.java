package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import javax.swing.table.*;

public class StudentDetails extends JFrame implements ActionListener {

    Choice crollno;
    JTable table;
    JButton search, print, update, add, cancel;

    public StudentDetails() {
        setUndecorated(true);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setLayout(null);

        // ✅ Transparent purple background panel
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(120, 81, 169)); // purple with reduced opacity
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        background.setOpaque(false);
        background.setLayout(null);
        background.setBounds(0, 0, 700, 550);
        add(background);

        JLabel heading = new JLabel("Search by Roll Number");
        heading.setBounds(40, 20, 200, 25);
        heading.setFont(new Font("serif", Font.BOLD, 20));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        crollno = new Choice();
        crollno.setBounds(270, 25, 150, 25);
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

        table = new JTable() {
            // ✅ Alternate row coloring
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    Color lightPurple = new Color(220, 210, 240); // soft purple
                    comp.setBackground(row % 2 == 0 ? lightPurple : Color.WHITE);
                } else {
                    comp.setBackground(new Color(180, 160, 220)); // selected row
                }
                return comp;
            }
        };

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from student");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(20, 100, 660, 350);
        jsp.getViewport().setBackground(new Color(220, 210, 240)); // ✅ Light purple inner background
        background.add(jsp);

        search = new JButton("Search");
        search.setBounds(40, 60, 90, 25);
        styleButton(search);
        search.addActionListener(this);
        background.add(search);

        print = new JButton("Print");
        print.setBounds(140, 60, 90, 25);
        styleButton(print);
        print.addActionListener(this);
        background.add(print);

        add = new JButton("Add");
        add.setBounds(240, 60, 90, 25);
        styleButton(add);
        add.addActionListener(this);
        background.add(add);

        update = new JButton("Update");
        update.setBounds(340, 60, 90, 25);
        styleButton(update);
        update.addActionListener(this);
        background.add(update);

        cancel = new JButton("Cancel");
        cancel.setBounds(440, 60, 90, 25);
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "select * from student where rollno = '" + crollno.getSelectedItem() + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == add) {
            setVisible(false);
            new AddStudent();
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateStudent();
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentDetails();
    }
}
