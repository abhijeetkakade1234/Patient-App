package view.panchakarma;

import controller.PanchakarmaController;
import controller.PatientController;
import model.Panchakarma;
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
 * Panchakarma List View - displays all panchakarma treatments with filtering
 */
public class PanchakarmaListView extends JFrame {
    private final PanchakarmaController panchakarmaController;
    private final PatientController patientController;
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel titleLabel;
    private JLabel countLabel;
    private List<Panchakarma> currentList;
    
    private static final String[] COLUMN_NAMES = {
        "ID", "Patient ID", "Patient Name", "Treatment", "Day", "Duration", 
        "Price (₹)", "Date"
    };

    public PanchakarmaListView() {
        this.panchakarmaController = new PanchakarmaController();
        this.patientController = new PatientController();
        initializeUI();
        loadAllPanchakarma();
    }

    private void initializeUI() {
        setTitle("Panchakarma Treatments");
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
        titleLabel = new JLabel("All Panchakarma Treatments");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));

        // Filter buttons
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(Color.WHITE);

        JButton allButton = createFilterButton("All");
        allButton.addActionListener(e -> loadAllPanchakarma());

        JButton todayButton = createFilterButton("Today");
        todayButton.addActionListener(e -> loadByDate(LocalDate.now()));

        JButton dateButton = createFilterButton("Select Date");
        dateButton.addActionListener(e -> selectDateAndLoad());

        JButton monthButton = createFilterButton("This Month");
        monthButton.addActionListener(e -> loadByMonth());

        filterPanel.add(new JLabel("Filter:"));
        filterPanel.add(allButton);
        filterPanel.add(todayButton);
        filterPanel.add(dateButton);
        filterPanel.add(monthButton);

        // Count and refresh
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        countLabel = new JLabel("0 treatments");
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton refreshButton = createFilterButton("Refresh");
        refreshButton.addActionListener(e -> loadAllPanchakarma());
        
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
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 35));

        // Column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(2).setPreferredWidth(180);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(100);

        // Custom renderer
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

        JButton addButton = ButtonStyleUtil.createSuccessButton("Add Panchakarma");
        addButton.setPreferredSize(new Dimension(150, 35));
        addButton.addActionListener(e -> {
            new PanchakarmaFormView().setVisible(true);
        });

        JButton editButton = ButtonStyleUtil.createButton("Edit", new Color(100, 150, 200));
        editButton.setPreferredSize(new Dimension(80, 35));
        editButton.addActionListener(e -> editSelected());

        JButton deleteButton = ButtonStyleUtil.createDangerButton("Delete");
        deleteButton.setPreferredSize(new Dimension(80, 35));
        deleteButton.addActionListener(e -> deleteSelected());

        JButton closeButton = ButtonStyleUtil.createLightButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());

        panel.add(addButton);
        panel.add(viewPatientButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(closeButton);

        return panel;
    }

    private void loadAllPanchakarma() {
        titleLabel.setText("All Panchakarma Treatments");
        currentList = panchakarmaController.getAllPanchakarma();
        refreshTable();
    }

    private void loadByDate(LocalDate date) {
        titleLabel.setText("Panchakarma on " + date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        currentList = panchakarmaController.getPanchakarmaByDate(date);
        refreshTable();
    }

    private void selectDateAndLoad() {
        LocalDate date = DatePickerDialog.showDialog(this, "Select Date");
        if (date != null) {
            loadByDate(date);
        }
    }

    private void loadByMonth() {
        LocalDate now = LocalDate.now();
        titleLabel.setText("Panchakarma in " + now.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        currentList = panchakarmaController.getPanchakarmaByMonth(now.getYear(), now.getMonthValue());
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        
        if (currentList != null) {
            for (Panchakarma p : currentList) {
                Patient patient = patientController.getPatientById(p.getPatientId());
                String patientName = patient != null ? patient.getName() : "Unknown";

                tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getPatientId(),
                    patientName,
                    p.getPanchakarmaName(),
                    p.getDay() + "/" + p.getNoOfDays(),
                    p.getDurationTime(),
                    String.format("%.2f", p.getPrice()),
                    p.getDayAndDate() != null ? p.getDayAndDate().toString() : ""
                });
            }
        }
        
        int count = currentList != null ? currentList.size() : 0;
        countLabel.setText(count + " treatment" + (count != 1 ? "s" : ""));
    }

    private void viewSelectedPatient() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a treatment from the list",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int patientId = (int) tableModel.getValueAt(modelRow, 1);
        
        Patient patient = patientController.getPatientById(patientId);
        if (patient != null) {
            new PatientDetailsView(patient).setVisible(true);
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a treatment to edit",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int panchakarmaId = (int) tableModel.getValueAt(modelRow, 0);
        
        Panchakarma panchakarma = panchakarmaController.getPanchakarmaById(panchakarmaId);
        if (panchakarma != null) {
            new PanchakarmaFormView(panchakarma).setVisible(true);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a treatment to delete",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int panchakarmaId = (int) tableModel.getValueAt(modelRow, 0);
        
        if (panchakarmaController.deletePanchakarma(panchakarmaId)) {
            loadAllPanchakarma();
        }
    }

    /**
     * Static factory method to show panchakarma on a specific date
     */
    public static void showPanchakarmaOnDate(Frame parent) {
        LocalDate date = DatePickerDialog.showDialog(parent, "Select Date");
        if (date != null) {
            PanchakarmaListView view = new PanchakarmaListView();
            view.loadByDate(date);
            view.setVisible(true);
        }
    }

    /**
     * Static factory method to show panchakarma in current month
     */
    public static void showPanchakarmaThisMonth() {
        PanchakarmaListView view = new PanchakarmaListView();
        view.loadByMonth();
        view.setVisible(true);
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

