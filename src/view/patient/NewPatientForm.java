package view.patient;

import controller.PatientController;
import model.Patient;
import view.components.DatePickerDialog;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * New Patient Registration Form
 * Clean, elegant form with proper validation
 */
public class NewPatientForm extends JFrame {
    private final PatientController patientController;
    
    // Personal Information
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> sexComboBox;
    private JTextField dobField;
    private JTextField phoneField;
    private JTextArea addressArea;
    private JTextField jobField;
    private JTextField referenceField;
    
    // Medical Information
    private JTextField heightField;
    private JTextField weightField;
    private JTextField bmiField;
    private JTextField bpField;
    private JTextField spo2Field;
    private JTextArea diseaseArea;
    private JTextField caseNoField;
    private JTextArea previousMedicinesArea;
    
    // Administrative
    private JTextField pendingMoneyField;
    private JTextField followUpDateField;
    private JTextArea remarkArea;

    public NewPatientForm() {
        this.patientController = new PatientController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("New Patient Registration");
        setSize(850, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        tabbedPane.addTab("Personal Info", createPersonalInfoPanel());
        tabbedPane.addTab("Medical Info", createMedicalInfoPanel());
        tabbedPane.addTab("Administrative", createAdministrativePanel());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 102, 204));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("New Patient Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Fill in the patient details below. Fields marked with * are required.");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(200, 220, 255));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);

        panel.add(textPanel, BorderLayout.WEST);
        return panel;
    }

    private JScrollPane createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Name
        nameField = addTextField(panel, gbc, row++, "Name: *", 30);
        
        // Age and Sex on same row
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Age:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.15;
        ageField = new JTextField(5);
        panel.add(ageField, gbc);
        gbc.gridx = 2; gbc.weightx = 0.15;
        panel.add(createLabel("Sex:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.55;
        sexComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        panel.add(sexComboBox, gbc);
        row++;

        // DOB with date picker
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        panel.add(createLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.weightx = 0.5;
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        dobPanel.setBackground(Color.WHITE);
        dobField = new JTextField(12);
        dobField.setEditable(false);
        JButton dobButton = ButtonStyleUtil.createPrimaryButton("Select");
        dobButton.setPreferredSize(new Dimension(80, 30));
        dobButton.addActionListener(e -> selectDate(dobField));
        dobPanel.add(dobField);
        dobPanel.add(Box.createHorizontalStrut(5));
        dobPanel.add(dobButton);
        panel.add(dobPanel, gbc);
        gbc.gridwidth = 1;
        row++;

        // Phone
        phoneField = addTextField(panel, gbc, row++, "Phone: *", 20);

        // Address
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        panel.add(createLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 0.7;
        addressArea = new JTextArea(3, 30);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        JScrollPane addressScroll = new JScrollPane(addressArea);
        panel.add(addressScroll, gbc);
        gbc.gridwidth = 1;
        row++;

        // Job
        jobField = addTextField(panel, gbc, row++, "Job/Occupation:", 25);

        // Reference
        referenceField = addTextField(panel, gbc, row++, "Reference:", 25);

        // Filler
        gbc.gridy = row;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        return new JScrollPane(panel);
    }

    private JScrollPane createMedicalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Height, Weight, BMI on same row
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Height (cm):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.15;
        heightField = new JTextField(8);
        heightField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { calculateBMI(); }
        });
        panel.add(heightField, gbc);

        gbc.gridx = 2; gbc.weightx = 0.15;
        panel.add(createLabel("Weight (kg):"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.15;
        weightField = new JTextField(8);
        weightField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { calculateBMI(); }
        });
        panel.add(weightField, gbc);

        gbc.gridx = 4; gbc.weightx = 0.15;
        panel.add(createLabel("BMI:"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.25;
        bmiField = new JTextField(8);
        bmiField.setEditable(false);
        bmiField.setBackground(new Color(240, 240, 240));
        panel.add(bmiField, gbc);
        row++;

        // BP and SpO2
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Blood Pressure:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.35;
        bpField = new JTextField(15);
        panel.add(bpField, gbc);

        gbc.gridx = 2; gbc.weightx = 0.15;
        panel.add(createLabel("SpO2 (%):"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.35;
        spo2Field = new JTextField(8);
        panel.add(spo2Field, gbc);
        row++;

        // Disease/Complaint
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Disease/Complaint:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 5; gbc.weightx = 0.85;
        diseaseArea = new JTextArea(3, 40);
        diseaseArea.setLineWrap(true);
        diseaseArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(diseaseArea), gbc);
        gbc.gridwidth = 1;
        row++;

        // Case No
        caseNoField = addTextField(panel, gbc, row++, "Case No:", 20);

        // Previous Medicines
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Previous Medicines:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 5; gbc.weightx = 0.85;
        previousMedicinesArea = new JTextArea(3, 40);
        previousMedicinesArea.setLineWrap(true);
        previousMedicinesArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(previousMedicinesArea), gbc);
        gbc.gridwidth = 1;
        row++;

        // Filler
        gbc.gridy = row;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        return new JScrollPane(panel);
    }

    private JScrollPane createAdministrativePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Pending Money
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        panel.add(createLabel("Pending Amount (₹):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pendingMoneyField = new JTextField(15);
        pendingMoneyField.setText("0");
        panel.add(pendingMoneyField, gbc);
        row++;

        // Follow-up Date
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        panel.add(createLabel("Follow-up Date:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        JPanel followUpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        followUpPanel.setBackground(Color.WHITE);
        followUpDateField = new JTextField(12);
        followUpDateField.setEditable(false);
        JButton followUpButton = ButtonStyleUtil.createPrimaryButton("Select");
        followUpButton.setPreferredSize(new Dimension(80, 30));
        followUpButton.addActionListener(e -> selectDate(followUpDateField));
        followUpPanel.add(followUpDateField);
        followUpPanel.add(Box.createHorizontalStrut(5));
        followUpPanel.add(followUpButton);
        panel.add(followUpPanel, gbc);
        row++;

        // Remark
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        panel.add(createLabel("Remarks:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        remarkArea = new JTextArea(4, 30);
        remarkArea.setLineWrap(true);
        remarkArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(remarkArea), gbc);
        row++;

        // Filler
        gbc.gridy = row;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        return new JScrollPane(panel);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);

        JButton saveButton = ButtonStyleUtil.createSuccessButton("Save Patient");
        saveButton.setPreferredSize(new Dimension(130, 40));
        saveButton.setMinimumSize(new Dimension(130, 40));
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.addActionListener(e -> savePatient());

        JButton clearButton = ButtonStyleUtil.createSecondaryButton("Clear Form");
        clearButton.setPreferredSize(new Dimension(120, 40));
        clearButton.addActionListener(e -> clearForm());

        JButton cancelButton = ButtonStyleUtil.createLightButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.addActionListener(e -> dispose());

        panel.add(saveButton);
        panel.add(clearButton);
        panel.add(cancelButton);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    private JTextField addTextField(JPanel panel, GridBagConstraints gbc, int row, String label, int columns) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(createLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 0.7;
        JTextField field = new JTextField(columns);
        panel.add(field, gbc);
        gbc.gridwidth = 1;

        return field;
    }

    private void selectDate(JTextField field) {
        LocalDate date = DatePickerDialog.showDialog(this, "Select Date");
        if (date != null) {
            field.setText(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    private void calculateBMI() {
        try {
            double height = Double.parseDouble(heightField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());
            
            if (height > 0 && weight > 0) {
                double bmi = patientController.calculateBMI(height, weight);
                bmiField.setText(String.format("%.1f", bmi));
            }
        } catch (NumberFormatException e) {
            bmiField.setText("");
        }
    }

    private void savePatient() {
        // Validate
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required", "Validation Error", JOptionPane.WARNING_MESSAGE);
            nameField.requestFocus();
            return;
        }

        if (phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phone is required", "Validation Error", JOptionPane.WARNING_MESSAGE);
            phoneField.requestFocus();
            return;
        }

        // Create patient
        Patient patient = new Patient();
        patient.setName(nameField.getText().trim());
        patient.setAge(parseIntOrZero(ageField.getText()));
        patient.setSex((String) sexComboBox.getSelectedItem());
        patient.setDob(parseDate(dobField.getText()));
        patient.setPhone(phoneField.getText().trim());
        patient.setAddress(addressArea.getText().trim());
        patient.setJob(jobField.getText().trim());
        patient.setReference(referenceField.getText().trim());
        patient.setHeight(parseDoubleOrZero(heightField.getText()));
        patient.setWeight(parseDoubleOrZero(weightField.getText()));
        patient.setBmi(parseDoubleOrZero(bmiField.getText()));
        patient.setBloodPressure(bpField.getText().trim());
        patient.setSpo2(parseIntOrZero(spo2Field.getText()));
        patient.setDisease(diseaseArea.getText().trim());
        patient.setCaseNo(caseNoField.getText().trim());
        patient.setPreviousMedicines(previousMedicinesArea.getText().trim());
        patient.setPendingMoney(parseDoubleOrZero(pendingMoneyField.getText()));
        patient.setFollowUpDate(parseDate(followUpDateField.getText()));
        patient.setRemark(remarkArea.getText().trim());
        patient.setRegistrationDate(LocalDate.now());

        // Save
        int patientId = patientController.addPatient(patient);
        if (patientId > 0) {
            JOptionPane.showMessageDialog(this,
                "Patient saved successfully!\nPatient ID: " + patientId,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        sexComboBox.setSelectedIndex(0);
        dobField.setText("");
        phoneField.setText("");
        addressArea.setText("");
        jobField.setText("");
        referenceField.setText("");
        heightField.setText("");
        weightField.setText("");
        bmiField.setText("");
        bpField.setText("");
        spo2Field.setText("");
        diseaseArea.setText("");
        caseNoField.setText("");
        previousMedicinesArea.setText("");
        pendingMoneyField.setText("0");
        followUpDateField.setText("");
        remarkArea.setText("");
        nameField.requestFocus();
    }

    private int parseIntOrZero(String text) {
        try {
            return text.trim().isEmpty() ? 0 : Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double parseDoubleOrZero(String text) {
        try {
            return text.trim().isEmpty() ? 0.0 : Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private LocalDate parseDate(String text) {
        try {
            return text.trim().isEmpty() ? null : LocalDate.parse(text.trim());
        } catch (Exception e) {
            return null;
        }
    }

}

