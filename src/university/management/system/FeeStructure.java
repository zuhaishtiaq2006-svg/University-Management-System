package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import javax.swing.table.*;
import java.awt.event.*;

public class FeeStructure extends JFrame implements ActionListener {

    JTable table;
    JButton cancel;

    public FeeStructure() {
        setUndecorated(true);
        setSize(820, 520); // ✅ Compact size like other modules
        setLocationRelativeTo(null);
        setLayout(null);

        // ✅ Purple background panel
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

        JLabel heading = new JLabel("Fee Structure");
        heading.setBounds(40, 20, 400, 40);
        heading.setFont(new Font("serif", Font.BOLD, 26));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        // ✅ Table with auto-resizing columns
        table = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
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
        table.setRowHeight(28);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // ✅ Auto resize columns

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(20, 80, 780, 360);
        jsp.getViewport().setBackground(new Color(220, 210, 240));

        // ✅ Light purple scroll bar
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

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from fee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ Cancel button at bottom-right
        cancel = new JButton("Cancel");
        cancel.setBounds(680, 460, 120, 30);
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
        if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new FeeStructure();
    }
}
