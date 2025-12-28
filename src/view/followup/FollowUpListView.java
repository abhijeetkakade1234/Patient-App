package view.followup;

import controller.FollowUpController;
import controller.PatientController;
import model.FollowUp;
import model.Patient;
import view.components.DatePickerDialog;
import view.patient.PatientDetailsView;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Follow-up List View - displays all follow-ups with filtering options
 */
public class FollowUpListView extends JFrame {
    private final FollowUpController followUpController;
    private final PatientController patientController;
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel titleLabel;
    private JLabel countLabel;
    private List<FollowUp> currentFollowUps;
    
    private static final String[] COLUMN_NAMES = {
        "ID", "Patient ID", "Visit No", "Visit Date", "BP", "Weight", 
        "Treatment", "Next Follow-up"
    };
    
    private static final Color HEADER_BG = new Color(0, 102, 204);

    public FollowUpListView() {
        this.followUpController = new FollowUpController();
        this.patientController = new PatientController();
        initializeUI();
        loadAllFollowUps();
    }

    private void initializeUI() {
        setTitle("Follow-up Records");
        setSize(1100, 650);
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
        titleLabel = new JLabel("All Follow-up Records");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));

        // Filter buttons
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(Color.WHITE);

        JButton allButton = createFilterButton("All");
        allButton.addActionListener(e -> loadAllFollowUps());

        JButton todayButton = createFilterButton("Today");
        todayButton.addActionListener(e -> loadFollowUpsByDate(LocalDate.now()));

        JButton dateButton = createFilterButton("Select Date");
        dateButton.addActionListener(e -> selectDateAndLoad());

        JButton monthButton = createFilterButton("This Month");
        monthButton.addActionListener(e -> loadFollowUpsByMonth());

        filterPanel.add(new JLabel("Filter:"));
        filterPanel.add(allButton);
        filterPanel.add(todayButton);
        filterPanel.add(dateButton);
        filterPanel.add(monthButton);

        // Count
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        countLabel = new JLabel("0 records");
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton refreshButton = createFilterButton("Refresh");
        refreshButton.addActionListener(e -> loadAllFollowUps());
        
        rightPanel.add(countLabel);
        rightPanel.add(refreshButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

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
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setAutoCreateRowSorter(true);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 35));

        // Column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(2).setPreferredWidth(70);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(80);
        columnModel.getColumn(6).setPreferredWidth(300);
        columnModel.getColumn(7).setPreferredWidth(100);

        // Custom renderer for alternating rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(184, 207, 229));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return c;
            }
        });

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

        JButton addFollowUpButton = ButtonStyleUtil.createSuccessButton("Add Follow-up");
        addFollowUpButton.setPreferredSize(new Dimension(130, 35));
        addFollowUpButton.addActionListener(e -> {
            new FollowUpFormView().setVisible(true);
        });

        JButton closeButton = ButtonStyleUtil.createLightButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());

        panel.add(addFollowUpButton);
        panel.add(viewPatientButton);
        panel.add(closeButton);

        return panel;
    }

    private void loadAllFollowUps() {
        titleLabel.setText("All Follow-up Records");
        currentFollowUps = followUpController.getAllFollowUps();
        refreshTable();
    }

    private void loadFollowUpsByDate(LocalDate date) {
        titleLabel.setText("Follow-ups on " + date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        currentFollowUps = followUpController.getFollowUpsByDate(date);
        refreshTable();
    }

    private void selectDateAndLoad() {
        LocalDate date = DatePickerDialog.showDialog(this, "Select Date");
        if (date != null) {
            loadFollowUpsByDate(date);
        }
    }

    private void loadFollowUpsByMonth() {
        LocalDate now = LocalDate.now();
        titleLabel.setText("Follow-ups in " + now.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        currentFollowUps = followUpController.getFollowUpsByMonth(now.getYear(), now.getMonthValue());
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        
        if (currentFollowUps != null) {
            for (FollowUp f : currentFollowUps) {
                tableModel.addRow(new Object[]{
                    f.getId(),
                    f.getPatientId(),
                    f.getVisitNo(),
                    f.getVisitDate() != null ? f.getVisitDate().toString() : "",
                    f.getBloodPressure(),
                    f.getWeight() > 0 ? f.getWeight() + " kg" : "",
                    truncate(f.getRxTreatment(), 50),
                    f.getNextFollowUpDate() != null ? f.getNextFollowUpDate().toString() : ""
                });
            }
        }
        
        int count = currentFollowUps != null ? currentFollowUps.size() : 0;
        countLabel.setText(count + " record" + (count != 1 ? "s" : ""));
    }

    private void viewSelectedPatient() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a record from the list",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int patientId = (int) tableModel.getValueAt(modelRow, 1);
        
        Patient patient = patientController.getPatientById(patientId);
        if (patient != null) {
            new PatientDetailsView(patient).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                "Patient not found",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    private JButton createFilterButton(String text) {
        JButton button = ButtonStyleUtil.createButton(text, new Color(240, 240, 240), Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150), 2),
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));
        button.setPreferredSize(new Dimension(100, 32));
        return button;
    }

}

