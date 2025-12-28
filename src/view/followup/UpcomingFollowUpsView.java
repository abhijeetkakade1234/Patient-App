package view.followup;

import controller.FollowUpController;
import controller.PatientController;
import model.FollowUp;
import model.Patient;
import view.patient.PatientDetailsView;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Upcoming Follow-ups View - shows patients due for follow-up
 */
public class UpcomingFollowUpsView extends JFrame {
    private final FollowUpController followUpController;
    private final PatientController patientController;
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel countLabel;
    private JLabel overdueLabel;
    private List<FollowUp> currentFollowUps;
    
    private static final String[] COLUMN_NAMES = {
        "Patient ID", "Patient Name", "Phone", "Last Visit", "Due Date", "Status", "Days"
    };

    public UpcomingFollowUpsView() {
        this.followUpController = new FollowUpController();
        this.patientController = new PatientController();
        initializeUI();
        loadDueFollowUps();
    }

    private void initializeUI() {
        setTitle("Follow-up Patients");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Table
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Title
        JLabel titleLabel = new JLabel("Patients Due for Follow-up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));

        // Stats panel
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        statsPanel.setBackground(Color.WHITE);

        countLabel = new JLabel("0 total");
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));

        overdueLabel = new JLabel("0 overdue");
        overdueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        overdueLabel.setForeground(Color.RED);

        JButton refreshButton = createFilterButton("Refresh");
        refreshButton.addActionListener(e -> loadDueFollowUps());

        statsPanel.add(countLabel);
        statsPanel.add(overdueLabel);
        statsPanel.add(refreshButton);

        // Filter buttons
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBackground(Color.WHITE);

        JButton allDueButton = createFilterButton("All Due");
        allDueButton.addActionListener(e -> loadDueFollowUps());

        JButton overdueOnlyButton = createFilterButton("Overdue Only");
        overdueOnlyButton.addActionListener(e -> loadOverdueOnly());

        JButton todayButton = createFilterButton("Due Today");
        todayButton.addActionListener(e -> loadDueToday());

        JButton weekButton = createFilterButton("Due This Week");
        weekButton.addActionListener(e -> loadDueThisWeek());

        filterPanel.add(new JLabel("Filter:"));
        filterPanel.add(allDueButton);
        filterPanel.add(overdueOnlyButton);
        filterPanel.add(todayButton);
        filterPanel.add(weekButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(statsPanel, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(filterPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setAutoCreateRowSorter(true);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 35));

        // Column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(80);

        // Custom renderer with color coding
        table.setDefaultRenderer(Object.class, new StatusColorRenderer());

        // Double-click to view patient
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewSelectedPatient();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);

        JButton viewPatientButton = ButtonStyleUtil.createPrimaryButton("View Patient");
        viewPatientButton.setPreferredSize(new Dimension(120, 35));
        viewPatientButton.addActionListener(e -> viewSelectedPatient());

        JButton addFollowUpButton = ButtonStyleUtil.createSuccessButton("Record Follow-up");
        addFollowUpButton.setPreferredSize(new Dimension(140, 35));
        addFollowUpButton.addActionListener(e -> recordFollowUp());

        JButton closeButton = ButtonStyleUtil.createLightButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());

        panel.add(addFollowUpButton);
        panel.add(viewPatientButton);
        panel.add(closeButton);

        return panel;
    }

    private void loadDueFollowUps() {
        currentFollowUps = followUpController.getDueFollowUps();
        refreshTable();
    }

    private void loadOverdueOnly() {
        List<FollowUp> allDue = followUpController.getDueFollowUps();
        LocalDate today = LocalDate.now();
        currentFollowUps = allDue.stream()
            .filter(f -> f.getNextFollowUpDate() != null && f.getNextFollowUpDate().isBefore(today))
            .toList();
        refreshTable();
    }

    private void loadDueToday() {
        List<FollowUp> allDue = followUpController.getDueFollowUps();
        LocalDate today = LocalDate.now();
        currentFollowUps = allDue.stream()
            .filter(f -> f.getNextFollowUpDate() != null && f.getNextFollowUpDate().equals(today))
            .toList();
        refreshTable();
    }

    private void loadDueThisWeek() {
        List<FollowUp> allDue = followUpController.getDueFollowUps();
        LocalDate today = LocalDate.now();
        LocalDate weekEnd = today.plusDays(7);
        currentFollowUps = allDue.stream()
            .filter(f -> f.getNextFollowUpDate() != null && 
                        !f.getNextFollowUpDate().isAfter(weekEnd))
            .toList();
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        int overdueCount = 0;
        LocalDate today = LocalDate.now();

        if (currentFollowUps != null) {
            for (FollowUp f : currentFollowUps) {
                Patient patient = patientController.getPatientById(f.getPatientId());
                String patientName = patient != null ? patient.getName() : "Unknown";
                String phone = patient != null ? patient.getPhone() : "";

                LocalDate dueDate = f.getNextFollowUpDate();
                String status;
                long days;

                if (dueDate == null) {
                    status = "N/A";
                    days = 0;
                } else if (dueDate.isBefore(today)) {
                    days = ChronoUnit.DAYS.between(dueDate, today);
                    status = "OVERDUE";
                    overdueCount++;
                } else if (dueDate.equals(today)) {
                    days = 0;
                    status = "DUE TODAY";
                } else {
                    days = ChronoUnit.DAYS.between(today, dueDate);
                    status = "UPCOMING";
                }

                tableModel.addRow(new Object[]{
                    f.getPatientId(),
                    patientName,
                    phone,
                    f.getVisitDate() != null ? f.getVisitDate().toString() : "",
                    dueDate != null ? dueDate.toString() : "",
                    status,
                    days
                });
            }
        }

        int total = currentFollowUps != null ? currentFollowUps.size() : 0;
        countLabel.setText(total + " total");
        overdueLabel.setText(overdueCount + " overdue");
    }

    private void viewSelectedPatient() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a patient from the list",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int patientId = (int) tableModel.getValueAt(modelRow, 0);

        Patient patient = patientController.getPatientById(patientId);
        if (patient != null) {
            new PatientDetailsView(patient).setVisible(true);
        }
    }

    private void recordFollowUp() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a patient to record follow-up",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int patientId = (int) tableModel.getValueAt(modelRow, 0);
        new FollowUpFormView(patientId).setVisible(true);
    }

    /**
     * Custom renderer for status-based color coding
     */
    private class StatusColorRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);

            if (isSelected) {
                c.setBackground(new Color(184, 207, 229));
                c.setForeground(Color.BLACK);
            } else {
                String status = (String) tableModel.getValueAt(
                        table.convertRowIndexToModel(row), 5);

                if ("OVERDUE".equals(status)) {
                    c.setBackground(new Color(255, 235, 235));
                } else if ("DUE TODAY".equals(status)) {
                    c.setBackground(new Color(255, 248, 220));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                c.setForeground(Color.BLACK);
            }

            // Bold and color for status column
            if (column == 5) {
                setFont(getFont().deriveFont(Font.BOLD));
                String status = value != null ? value.toString() : "";
                if ("OVERDUE".equals(status)) {
                    setForeground(Color.RED);
                } else if ("DUE TODAY".equals(status)) {
                    setForeground(new Color(200, 150, 0));
                } else {
                    setForeground(new Color(0, 128, 0));
                }
            }

            setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            return c;
        }
    }

    private JButton createFilterButton(String text) {
        JButton button = ButtonStyleUtil.createButton(text, new Color(240, 240, 240), Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 2),
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));
        button.setPreferredSize(new Dimension(110, 32));
        return button;
    }

}

