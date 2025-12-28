package view;

import controller.PatientController;
import model.Patient;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * New Patient Entry Form
 * Production-ready patient registration form
 */
public class NewPatientView extends JFrame {
    private final PatientController patientController;
    
    // Personal Information Fields
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> sexComboBox;
    private JTextField dobField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField jobField;
    private JTextField referenceField;
    
    // Medical Information Fields
    private JTextField heightField;
    private JTextField weightField;
    private JTextField bmiField;
    private JTextField bloodPressureField;
    private JTextField spo2Field;
    private JTextArea diseaseArea;
    private JTextField caseNoField;
    private JTextArea previousMedicinesArea;
    
    // Administrative Fields
    private JTextField pendingMoneyField;
    private JTextField followUpDateField;
    private JTextArea remarkArea;
    
    // Buttons
    private JButton saveButton;
    private JButton clearButton;
    private JButton cancelButton;

    public NewPatientView() {
        this.patientController = new PatientController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("New Patient Registration");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Form panel with scroll
        JPanel formPanel = createFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Personal Information Section
        addSectionHeader(panel, gbc, row++, "Personal Information");
        
        nameField = addTextField(panel, gbc, row++, "Name:*", 30);
        ageField = addTextField(panel, gbc, row++, "Age:", 10);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        sexComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        panel.add(sexComboBox, gbc);
        row++;
        
        dobField = addTextField(panel, gbc, row++, "Date of Birth (YYYY-MM-DD):", 20);
        phoneField = addTextField(panel, gbc, row++, "Phone:*", 20);
        addressField = addTextField(panel, gbc, row++, "Address:", 40);
        jobField = addTextField(panel, gbc, row++, "Job/Occupation:", 30);
        referenceField = addTextField(panel, gbc, row++, "Reference:", 30);

        // Medical Information Section
        addSectionHeader(panel, gbc, row++, "Medical Information");
        
        heightField = addTextField(panel, gbc, row++, "Height (cm):", 10);
        weightField = addTextField(panel, gbc, row++, "Weight (kg):", 10);
        
        bmiField = addTextField(panel, gbc, row++, "BMI (auto-calculated):", 10);
        bmiField.setEditable(false);
        bmiField.setBackground(Color.LIGHT_GRAY);
        
        // Add listeners for BMI calculation
        heightField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calculateBMI();
            }
        });
        weightField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calculateBMI();
            }
        });
        
        bloodPressureField = addTextField(panel, gbc, row++, "Blood Pressure:", 15);
        spo2Field = addTextField(panel, gbc, row++, "SpO2 (%):", 10);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Disease/Complaint:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        diseaseArea = new JTextArea(3, 30);
        diseaseArea.setLineWrap(true);
        diseaseArea.setWrapStyleWord(true);
        JScrollPane diseaseScroll = new JScrollPane(diseaseArea);
        panel.add(diseaseScroll, gbc);
        row++;
        
        caseNoField = addTextField(panel, gbc, row++, "Case No:", 20);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Previous Medicines:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        previousMedicinesArea = new JTextArea(3, 30);
        previousMedicinesArea.setLineWrap(true);
        previousMedicinesArea.setWrapStyleWord(true);
        JScrollPane medicinesScroll = new JScrollPane(previousMedicinesArea);
        panel.add(medicinesScroll, gbc);
        row++;

        // Administrative Information Section
        addSectionHeader(panel, gbc, row++, "Administrative Information");
        
        pendingMoneyField = addTextField(panel, gbc, row++, "Pending Money (₹):", 15);
        pendingMoneyField.setText("0");
        followUpDateField = addTextField(panel, gbc, row++, "Follow-up Date (YYYY-MM-DD):", 20);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Remark:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        remarkArea = new JTextArea(3, 30);
        remarkArea.setLineWrap(true);
        remarkArea.setWrapStyleWord(true);
        JScrollPane remarkScroll = new JScrollPane(remarkArea);
        panel.add(remarkScroll, gbc);

        return panel;
    }

    private void addSectionHeader(JPanel panel, GridBagConstraints gbc, int row, String title) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 10, 10);

        JLabel headerLabel = new JLabel(title);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(new Color(0, 102, 204));
        panel.add(headerLabel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
    }

    private JTextField addTextField(JPanel panel, GridBagConstraints gbc, int row, String label, int columns) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JTextField textField = new JTextField(columns);
        panel.add(textField, gbc);

        return textField;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        saveButton = ButtonStyleUtil.createSuccessButton("Save Patient");
        clearButton = ButtonStyleUtil.createSecondaryButton("Clear Form");
        cancelButton = ButtonStyleUtil.createLightButton("Cancel");

        saveButton.setPreferredSize(new Dimension(120, 35));
        clearButton.setPreferredSize(new Dimension(120, 35));
        cancelButton.setPreferredSize(new Dimension(120, 35));

        saveButton.addActionListener(e -> savePatient());
        clearButton.addActionListener(e -> clearForm());
        cancelButton.addActionListener(e -> dispose());

        panel.add(saveButton);
        panel.add(clearButton);
        panel.add(cancelButton);

        return panel;
    }

    private void calculateBMI() {
        try {
            String heightStr = heightField.getText().trim();
            String weightStr = weightField.getText().trim();

            if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
                double height = Double.parseDouble(heightStr);
                double weight = Double.parseDouble(weightStr);

                if (height > 0 && weight > 0) {
                    double bmi = patientController.calculateBMI(height, weight);
                    bmiField.setText(String.format("%.1f", bmi));
                }
            }
        } catch (NumberFormatException e) {
            // Ignore invalid input
            bmiField.setText("");
        }
    }

    private void savePatient() {
        // Validate required fields
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Name is required",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            nameField.requestFocus();
            return;
        }

        if (phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Phone number is required",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            phoneField.requestFocus();
            return;
        }

        try {
            // Create patient object
            Patient patient = new Patient();
            
            // Personal information
            patient.setName(nameField.getText().trim());
            patient.setAge(parseIntOrZero(ageField.getText()));
            patient.setSex((String) sexComboBox.getSelectedItem());
            patient.setDob(parseDate(dobField.getText()));
            patient.setPhone(phoneField.getText().trim());
            patient.setAddress(addressField.getText().trim());
            patient.setJob(jobField.getText().trim());
            patient.setReference(referenceField.getText().trim());
            
            // Medical information
            patient.setHeight(parseDoubleOrZero(heightField.getText()));
            patient.setWeight(parseDoubleOrZero(weightField.getText()));
            patient.setBmi(parseDoubleOrZero(bmiField.getText()));
            patient.setBloodPressure(bloodPressureField.getText().trim());
            patient.setSpo2(parseIntOrZero(spo2Field.getText()));
            patient.setDisease(diseaseArea.getText().trim());
            patient.setCaseNo(caseNoField.getText().trim());
            patient.setPreviousMedicines(previousMedicinesArea.getText().trim());
            
            // Administrative information
            patient.setPendingMoney(parseDoubleOrZero(pendingMoneyField.getText()));
            patient.setFollowUpDate(parseDate(followUpDateField.getText()));
            patient.setRemark(remarkArea.getText().trim());
            patient.setRegistrationDate(LocalDate.now());

            // Save patient
            int patientId = patientController.addPatient(patient);
            
            if (patientId > 0) {
                int choice = JOptionPane.showConfirmDialog(this,
                    "Patient registered successfully!\nPatient ID: " + patientId + "\n\nDo you want to add another patient?",
                    "Success",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
                
                if (choice == JOptionPane.YES_OPTION) {
                    clearForm();
                } else {
                    dispose();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error saving patient: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        sexComboBox.setSelectedIndex(0);
        dobField.setText("");
        phoneField.setText("");
        addressField.setText("");
        jobField.setText("");
        referenceField.setText("");
        heightField.setText("");
        weightField.setText("");
        bmiField.setText("");
        bloodPressureField.setText("");
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
            if (text.trim().isEmpty()) {
                return null;
            }
            return LocalDate.parse(text.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NewPatientView view = new NewPatientView();
            view.setVisible(true);
        });
    }
}

