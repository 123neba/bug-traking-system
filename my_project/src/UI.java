import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;

public class UI {

    static DefaultTableModel model;
    static JTable table;
    static JLabel totalLabel;
    static JLabel fixedLabel;

    public static void openDashboard(String role) {
        JFrame frame = new JFrame("Bug Tracker Pro - Dashboard");
        frame.setSize(950, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(25, 25, 25));
        frame.setLayout(null);


        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 950, 70);
        topPanel.setBackground(new Color(40, 40, 40));
        topPanel.setLayout(null);
        
        JLabel logo = new JLabel("Bug Tracker Pro", SwingConstants.LEFT);
        logo.setBounds(20, 15, 200, 40);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(Color.WHITE);
        topPanel.add(logo);

        JLabel userBadge = new JLabel("Role: " + role, SwingConstants.RIGHT);
        userBadge.setBounds(750, 15, 150, 40);
        userBadge.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        userBadge.setForeground(new Color(150, 150, 150));
        topPanel.add(userBadge);

        frame.add(topPanel);


        JLabel addTitle = new JLabel("Add New Bug:", SwingConstants.LEFT);
        addTitle.setBounds(20, 85, 200, 20);
        addTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addTitle.setForeground(new Color(200, 200, 200));
        frame.add(addTitle);

        JTextField titleField = new JTextField();
        titleField.setBounds(20, 110, 160, 35);
        styleTextField(titleField, "Title");

        JTextField descField = new JTextField();
        descField.setBounds(190, 110, 240, 35);
        styleTextField(descField, "Description");

        JTextField devField = new JTextField();
        devField.setBounds(440, 110, 150, 35);
        styleTextField(devField, "Assign To");

        JButton addBtn = new JButton("Add Bug");
        addBtn.setBounds(600, 110, 100, 35);
        styleButton(addBtn, new Color(16, 185, 129));
        
        if ("Developer".equalsIgnoreCase(role)) {

            titleField.setEnabled(false);
            descField.setEnabled(false);
            devField.setEnabled(false);
            addBtn.setEnabled(false);
            addBtn.setToolTipText("Only Admins can add bugs.");
        }

        JTextField searchField = new JTextField();
        searchField.setBounds(715, 110, 200, 35);
        styleTextField(searchField, "Search...");

        frame.add(titleField);
        frame.add(descField);
        frame.add(devField);
        frame.add(addBtn);
        frame.add(searchField);


        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Description");
        model.addColumn("Status");
        model.addColumn("Developer");

        table = new JTable(model);
        styleTable(table);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20, 160, 895, 300);
        pane.getViewport().setBackground(new Color(30, 30, 30));
        pane.setBorder(BorderFactory.createEmptyBorder());
        frame.add(pane);


        JButton fixBtn = new JButton("Mark Fixed");
        fixBtn.setBounds(20, 480, 130, 40);
        styleButton(fixBtn, new Color(59, 130, 246));
        
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(160, 480, 130, 40);
        styleButton(deleteBtn, new Color(239, 68, 68));
        
        if ("Developer".equalsIgnoreCase(role)) {
            deleteBtn.setEnabled(false);
            deleteBtn.setToolTipText("Only Admins can delete bugs.");
        }

        totalLabel = new JLabel("Total Bugs: 0", SwingConstants.CENTER);
        totalLabel.setBounds(650, 480, 130, 40);
        totalLabel.setOpaque(true);
        totalLabel.setBackground(new Color(50, 50, 50));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        fixedLabel = new JLabel("Fixed Bugs: 0", SwingConstants.CENTER);
        fixedLabel.setBounds(790, 480, 120, 40);
        fixedLabel.setOpaque(true);
        fixedLabel.setBackground(new Color(16, 185, 129));
        fixedLabel.setForeground(Color.WHITE);
        fixedLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        frame.add(fixBtn);
        frame.add(deleteBtn);
        frame.add(totalLabel);
        frame.add(fixedLabel);


        addBtn.addActionListener(e -> {
            BugDAO.addBugWithDev(titleField.getText(), descField.getText(), devField.getText());
            titleField.setText("");
            descField.setText("");
            devField.setText("");
            loadBugs("");
        });

        fixBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                BugDAO.updateStatus(id, "Fixed");
                loadBugs("");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a bug from the table.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) model.getValueAt(row, 0);
                BugDAO.deleteBug(id);
                loadBugs("");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a bug to delete.");
            }
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loadBugs(searchField.getText());
            }
        });


        loadBugs("");
        
        frame.setVisible(true);
    }

    private static void loadBugs(String keyword) {
        try {
            model.setRowCount(0);
            ResultSet rs = BugDAO.searchBugs(keyword);

            int totalCount = 0;
            int fixedCount = 0;

            if (rs != null) {
                while (rs.next()) {
                    totalCount++;
                    String status = rs.getString("status");
                    if ("Fixed".equalsIgnoreCase(status)) {
                        fixedCount++;
                    }

                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            status,
                            rs.getString("assigned_to")
                    });
                }
            }

            totalLabel.setText("Total Bugs: " + totalCount);
            fixedLabel.setText("Fixed Bugs: " + fixedCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void styleTextField(JTextField field, String placeholder) {
        field.setBackground(new Color(40, 40, 40));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setToolTipText(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private static void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private static void styleTable(JTable table) {
        table.setBackground(new Color(35, 35, 35));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(55, 55, 55));
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(37, 99, 235));
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(25, 25, 25));
        header.setForeground(Color.LIGHT_GRAY);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBorder(BorderFactory.createLineBorder(new Color(55, 55, 55)));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); 
    }
}
