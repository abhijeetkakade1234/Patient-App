package view.patient;

import controller.PatientController;
import model.Patient;
import view.components.PatientTablePanel;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Patient Search View - search patients by name or phone
 */
public class PatientSearchView extends JFrame {
    private final PatientController patientController;
    
    private JTextField searchField;
    private JButton searchButton;
    private JButton viewAllButton;
    private JButton clearButton;
    private PatientTablePanel tablePanel;
    private JLabel statusLabel;

    public PatientSearchView() {
        this.patientController = new PatientController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Search Patients");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Search panel
        JPanel searchPanel = createSearchPanel();
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Table panel
        tablePanel = new PatientTablePanel();
        tablePanel.setOnDoubleClick(this::showPatientDetails);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Search Patients"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Search input
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        inputPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Search by Name or Phone:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 13));

        searchField = new JTextField(30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });

        searchButton = ButtonStyleUtil.createPrimaryButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 35));
        searchButton.addActionListener(e -> performSearch());

        viewAllButton = ButtonStyleUtil.createButton("View All", new Color(100, 150, 200));
        viewAllButton.setPreferredSize(new Dimension(100, 35));
        viewAllButton.addActionListener(e -> loadAllPatients());

        clearButton = ButtonStyleUtil.createSecondaryButton("Clear");
        clearButton.setPreferredSize(new Dimension(80, 35));
        clearButton.addActionListener(e -> clearSearch());

        inputPanel.add(searchLabel);
        inputPanel.add(searchField);
        inputPanel.add(searchButton);
        inputPanel.add(viewAllButton);
        inputPanel.add(clearButton);

        // Status label
        statusLabel = new JLabel("Enter search term and press Search");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);

        JButton viewDetailsButton = ButtonStyleUtil.createPrimaryButton("View Details");
        viewDetailsButton.setPreferredSize(new Dimension(120, 35));
        viewDetailsButton.addActionListener(e -> {
            Patient selected = tablePanel.getSelectedPatient();
            if (selected != null) {
                showPatientDetails(selected);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a patient from the list",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton addNewButton = ButtonStyleUtil.createSuccessButton("Add New Patient");
        addNewButton.setPreferredSize(new Dimension(140, 35));
        addNewButton.addActionListener(e -> {
            new NewPatientForm().setVisible(true);
        });

        JButton closeButton = ButtonStyleUtil.createLightButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());

        panel.add(addNewButton);
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

        List<Patient> results = patientController.searchPatients(searchTerm);
        tablePanel.setPatients(results);
        
        if (results.isEmpty()) {
            statusLabel.setText("No patients found matching '" + searchTerm + "'");
        } else {
            statusLabel.setText("Found " + results.size() + " patient(s) matching '" + searchTerm + "'");
        }
    }

    private void loadAllPatients() {
        List<Patient> allPatients = patientController.getAllPatients();
        tablePanel.setPatients(allPatients);
        searchField.setText("");
        statusLabel.setText("Showing all " + allPatients.size() + " patients");
    }

    private void clearSearch() {
        searchField.setText("");
        tablePanel.clear();
        statusLabel.setText("Enter search term and press Search");
        searchField.requestFocus();
    }

    private void showPatientDetails(Patient patient) {
        new PatientDetailsView(patient).setVisible(true);
    }
}

