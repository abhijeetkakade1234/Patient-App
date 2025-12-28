package view;

import controller.PatientController;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Patient Search UI
 * Allows searching patients by name or phone number
 */
public class PatientSearchView extends JFrame {
    private final PatientController patientController;
    
    private JTextField searchField;
    private JButton searchButton;
    private JButton viewAllButton;
    private JButton clearButton;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JButton viewDetailsButton;
    private JButton closeButton;

    public PatientSearchView() {
        this.patientController = new PatientController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Search Patients");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Search panel
        JPanel searchPanel = createSearchPanel();
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Table panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Search"));

        JLabel searchLabel = new JLabel("Search (Name/Phone):");
        searchField = new JTextField(25);
        searchButton = new JButton("Search");
        viewAllButton = new JButton("View All");
        clearButton = new JButton("Clear");

        // Event listeners
        searchButton.addActionListener(e -> performSearch());
        viewAllButton.addActionListener(e -> viewAllPatients());
        clearButton.addActionListener(e -> clearSearch());
        searchField.addActionListener(e -> performSearch()); // Enter key triggers search

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(viewAllButton);
        panel.add(clearButton);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Search Results"));

        // Create table
        String[] columnNames = {"ID", "Name", "Age", "Sex", "Phone", "Disease", "Registration Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        resultTable = new JTable(tableModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        resultTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        resultTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        resultTable.getColumnModel().getColumn(5).setPreferredWidth(200);
        resultTable.getColumnModel().getColumn(6).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        viewDetailsButton = new JButton("View Details");
        closeButton = new JButton("Close");

        viewDetailsButton.addActionListener(e -> viewPatientDetails());
        closeButton.addActionListener(e -> dispose());

        panel.add(viewDetailsButton);
        panel.add(closeButton);

        return panel;
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a search term",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Patient> patients = patientController.searchPatients(searchTerm);
        displayResults(patients);
    }

    private void viewAllPatients() {
        List<Patient> patients = patientController.getAllPatients();
        displayResults(patients);
        searchField.setText("");
    }

    private void clearSearch() {
        searchField.setText("");
        tableModel.setRowCount(0);
    }

    private void displayResults(List<Patient> patients) {
        tableModel.setRowCount(0); // Clear existing data

        if (patients.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No patients found",
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Patient patient : patients) {
            Object[] row = {
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getSex(),
                patient.getPhone(),
                patient.getDisease(),
                patient.getRegistrationDate()
            };
            tableModel.addRow(row);
        }
    }

    private void viewPatientDetails() {
        int selectedRow = resultTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a patient from the table",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int patientId = (int) tableModel.getValueAt(selectedRow, 0);
        Patient patient = patientController.getPatientById(patientId);

        if (patient != null) {
            new PatientDetailsView(patient).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PatientSearchView view = new PatientSearchView();
            view.setVisible(true);
        });
    }
}

