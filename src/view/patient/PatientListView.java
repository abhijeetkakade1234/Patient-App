package view.patient;

import controller.PatientController;
import model.Patient;
import view.components.PatientTablePanel;
import view.components.DatePickerDialog;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Patient List View - displays patients with filtering options
 * Can show: Today's patients, patients on a specific day, or patients in current month
 */
public class PatientListView extends JFrame {
    private final PatientController patientController;
    private PatientTablePanel tablePanel;
    private JLabel titleLabel;
    private JLabel countLabel;
    private JButton refreshButton;
    private JButton viewDetailsButton;
    private JButton closeButton;
    
    public enum FilterType {
        TODAY("Today's Patients"),
        SPECIFIC_DATE("Patients on Selected Date"),
        THIS_MONTH("Patients This Month"),
        ALL("All Patients");
        
        private final String displayName;
        FilterType(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() { return displayName; }
    }
    
    private FilterType currentFilter;
    private LocalDate filterDate;

    public PatientListView(FilterType filterType) {
        this(filterType, null);
    }

    public PatientListView(FilterType filterType, LocalDate date) {
        this.patientController = new PatientController();
        this.currentFilter = filterType;
        this.filterDate = date;
        initializeUI();
        loadPatients();
    }

    private void initializeUI() {
        setTitle("Patient List - " + currentFilter.getDisplayName());
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Table panel
        tablePanel = new PatientTablePanel();
        tablePanel.setOnDoubleClick(this::showPatientDetails);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Title
        titleLabel = new JLabel(currentFilter.getDisplayName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));

        // Date info
        String dateInfo = "";
        switch (currentFilter) {
            case TODAY:
                dateInfo = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
                break;
            case SPECIFIC_DATE:
                if (filterDate != null) {
                    dateInfo = filterDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
                }
                break;
            case THIS_MONTH:
                dateInfo = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy"));
                break;
            case ALL:
                dateInfo = "All registered patients";
                break;
        }
        JLabel dateLabel = new JLabel(dateInfo);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setForeground(Color.GRAY);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.add(dateLabel);

        // Count and refresh
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(Color.WHITE);
        
        countLabel = new JLabel("0 patients");
        countLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        refreshButton = ButtonStyleUtil.createButton("Refresh", new Color(100, 150, 200));
        refreshButton.addActionListener(e -> loadPatients());
        
        JButton changeDateButton = ButtonStyleUtil.createPrimaryButton("Change Date");
        changeDateButton.addActionListener(e -> changeDate());
        
        rightPanel.add(countLabel);
        rightPanel.add(refreshButton);
        if (currentFilter == FilterType.SPECIFIC_DATE) {
            rightPanel.add(changeDateButton);
        }

        panel.add(titlePanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);

        viewDetailsButton = ButtonStyleUtil.createPrimaryButton("View Details");
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

        closeButton = ButtonStyleUtil.createLightButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());

        panel.add(viewDetailsButton);
        panel.add(closeButton);

        return panel;
    }

    private void loadPatients() {
        List<Patient> patients;
        
        switch (currentFilter) {
            case TODAY:
                patients = patientController.getTodaysPatients();
                break;
            case SPECIFIC_DATE:
                if (filterDate != null) {
                    patients = patientController.getPatientsByDate(filterDate);
                } else {
                    patients = List.of();
                }
                break;
            case THIS_MONTH:
                patients = patientController.getPatientsThisMonth();
                break;
            case ALL:
            default:
                patients = patientController.getAllPatients();
                break;
        }
        
        tablePanel.setPatients(patients);
        countLabel.setText(patients.size() + " patient" + (patients.size() != 1 ? "s" : ""));
    }

    private void changeDate() {
        LocalDate newDate = DatePickerDialog.showDialog(this, "Select Date", 
                filterDate != null ? filterDate : LocalDate.now());
        
        if (newDate != null) {
            filterDate = newDate;
            setTitle("Patient List - " + currentFilter.getDisplayName());
            loadPatients();
        }
    }

    private void showPatientDetails(Patient patient) {
        new PatientDetailsView(patient).setVisible(true);
    }

    /**
     * Static factory method to show today's patients
     */
    public static void showTodaysPatients() {
        new PatientListView(FilterType.TODAY).setVisible(true);
    }

    /**
     * Static factory method to show patients on a specific date
     * Shows date picker first
     */
    public static void showPatientsOnDate(Frame parent) {
        LocalDate date = DatePickerDialog.showDialog(parent, "Select Date");
        if (date != null) {
            new PatientListView(FilterType.SPECIFIC_DATE, date).setVisible(true);
        }
    }

    /**
     * Static factory method to show patients in current month
     */
    public static void showPatientsThisMonth() {
        new PatientListView(FilterType.THIS_MONTH).setVisible(true);
    }

    /**
     * Static factory method to show all patients
     */
    public static void showAllPatients() {
        new PatientListView(FilterType.ALL).setVisible(true);
    }

}

