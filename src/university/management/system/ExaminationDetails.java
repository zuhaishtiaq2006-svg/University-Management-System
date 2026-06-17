package university.management.system;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import javax.swing.table.*;

public class ExaminationDetails extends JFrame implements ActionListener {

    JTextField search;
    JButton submit, cancel;
    JTable table;

    public ExaminationDetails() {
        setUndecorated(true);
        setSize(820, 520); 
        setLocationRelativeTo(null);
        setLayout(null);

        // ✅ Aapka original Purple background panel
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

        Color textColor = Color.WHITE;

        JLabel heading = new JLabel("Check Result");
        heading.setBounds(60, 20, 400, 40);
        heading.setFont(new Font("serif", Font.BOLD, 24));
        heading.setForeground(textColor);
        background.add(heading);

        search = new JTextField();
        search.setBounds(60, 80, 200, 30);
        search.setFont(new Font("Tahoma", Font.PLAIN, 16));
        background.add(search);

        // ✅ Constraint: KeyListener to prevent special characters in search
        search.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        submit = new JButton("Result");
        submit.setBounds(280, 80, 120, 30);
        styleButton(submit);
        submit.addActionListener(this);
        background.add(submit);

        cancel = new JButton("Back");
        cancel.setBounds(420, 80, 120, 30);
        styleButton(cancel);
        cancel.addActionListener(this);
        background.add(cancel);

        // ✅ Aapka original Table styling logic (Alternating colors)
        table = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    Color lightPurple = new Color(220, 210, 240);
                    comp.setBackground(row % 2 == 0 ? lightPurple : Color.WHITE);
                } else {
                    comp.setBackground(new Color(180, 160, 220));
                }
                return comp;
            }
        };
        table.setFont(new Font("Tahoma", Font.PLAIN, 15));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(20, 130, 780, 340);
        jsp.getViewport().setBackground(new Color(220, 210, 240));

        // ✅ Aapka original Scroll bar UI logic
        jsp.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(200, 180, 230);
                this.trackColor = new Color(240, 230, 250);
            }
        });
        jsp.getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(200, 180, 230);
                this.trackColor = new Color(240, 230, 250);
            }
        });

        background.add(jsp);

        // Load initial student data
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from student");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ MouseListener with Row Index Constraint
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int row = table.getSelectedRow();
                if (row != -1) { // Constraint: Ensure a row is selected
                    // Yahan index 2 aapka roll number hona chahiye database query ke hisab se
                    search.setText(table.getModel().getValueAt(row, 2).toString());
                    
                    // Double Click Constraint: Seedha result pe le jaye
                    if (me.getClickCount() == 2) {
                        openResult();
                    }
                }
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

    // ✅ Centralized Result Validation Constraint Logic
    private void openResult() {
        String rollno = search.getText().trim();
        
        // 1. Constraint: Empty check
        if (rollno.equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Please enter or select a Roll Number");
            return;
        }

        try {
            Conn c = new Conn();
            // 2. Constraint: Check if student actually has marks in database
            ResultSet rs = c.s.executeQuery("select * from marks where rollno = '"+rollno+"'");
            if (rs.next()) {
                setVisible(false);
                new Marks(rollno);
            } else {
                JOptionPane.showMessageDialog(null, "No marks record found for Roll No: " + rollno);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            openResult();
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ExaminationDetails();
    }
}