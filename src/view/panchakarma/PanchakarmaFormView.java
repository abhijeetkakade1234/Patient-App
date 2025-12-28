package view.panchakarma;

import controller.PanchakarmaController;
import controller.PatientController;
import model.Panchakarma;
import model.Patient;
import view.components.DatePickerDialog;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Panchakarma Form View - add or edit panchakarma treatment records
 */
public class PanchakarmaFormView extends JFrame {
    private final PanchakarmaController panchakarmaController;
    private final PatientController patientController;
    
    private int patientId = -1;
    private Panchakarma existingPanchakarma = null;
    
    // Form fields
    private JLabel patientNameLabel;
    private JTextField panchakarmaNameField;
    private JTextField advisedField;
    private JTextField noOfDaysField;
    private JTextField dayField;
    private JTextArea typesOfKarmaArea;
    private JTextField priceField;
    private JTextField durationTimeField;
    private JTextField therapistTimeField;
    private JTextField dayAndDateField;
    private JTextArea noteArea;

    // Common panchakarma types for dropdown
    private static final String[] PANCHAKARMA_TYPES = {
        "Select Type...",
        "Abhyanga (Oil Massage)",
        "Swedana (Steam Therapy)",
        "Shirodhara",
        "Basti (Enema)",
        "Nasya (Nasal Treatment)",
        "Virechana (Purgation)",
        "Vamana (Emesis)",
        "Raktamokshana (Blood Letting)",
        "Udvartana (Powder Massage)",
        "Pizhichil (Oil Bath)",
        "Njavarakizhi",
        "Elakizhi",
        "Podikizhi",
        "Kati Basti",
        "Janu Basti",
        "Greeva Basti",
        "Netra Basti",
        "Other"
    };

    public PanchakarmaFormView() {
        this.panchakarmaController = new PanchakarmaController();
        this.patientController = new PatientController();
        initializeUI();
    }

    public PanchakarmaFormView(int patientId) {
        this();
        this.patientId = patientId;
        loadPatientInfo();
    }

    public PanchakarmaFormView(Panchakarma panchakarma) {
        this(panchakarma.getPatientId());
        this.existingPanchakarma = panchakarma;
        populateForm(panchakarma);
        setTitle("Edit Panchakarma Treatment");
    }

    private void initializeUI() {
        setTitle("New Panchakarma Treatment");
        setSize(750, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = createFormPanel();
        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(128, 0, 128)); // Purple for Panchakarma
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel(existingPanchakarma != null ? 
                "Edit Panchakarma Treatment" : "New Panchakarma Treatment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        // Patient selection
        JPanel patientPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        patientPanel.setOpaque(false);

        patientNameLabel = new JLabel("No patient selected");
        patientNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        patientNameLabel.setForeground(Color.WHITE);

        JButton selectPatientButton = ButtonStyleUtil.createPrimaryButton("Select Patient");
        selectPatientButton.addActionListener(e -> selectPatient());

        patientPanel.add(patientNameLabel);
        patientPanel.add(selectPatientButton);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(patientPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Panchakarma Type with dropdown + text field
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.25;
        panel.add(createLabel("Panchakarma Type: *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        JPanel typePanel = new JPanel(new BorderLayout(5, 0));
        typePanel.setBackground(Color.WHITE);
        JComboBox<String> typeCombo = new JComboBox<>(PANCHAKARMA_TYPES);
        panchakarmaNameField = new JTextField(20);
        typeCombo.addActionListener(e -> {
            String selected = (String) typeCombo.getSelectedItem();
            if (selected != null && !selected.startsWith("Select") && !selected.equals("Other")) {
                panchakarmaNameField.setText(selected);
            }
        });
        typePanel.add(typeCombo, BorderLayout.WEST);
        typePanel.add(panchakarmaNameField, BorderLayout.CENTER);
        panel.add(typePanel, gbc);
        row++;

        // Advised by
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.25;
        panel.add(createLabel("Advised By:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        advisedField = new JTextField(30);
        panel.add(advisedField, gbc);
        row++;

        // Days (Day / No of Days)
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.25;
        panel.add(createLabel("Day / Total Days:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        JPanel daysPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        daysPanel.setBackground(Color.WHITE);
        dayField = new JTextField(5);
        dayField.setText("1");
        noOfDaysField = new JTextField(5);
        noOfDaysField.setText("7");
        daysPanel.add(new JLabel("Day"));
        daysPanel.add(dayField);
        daysPanel.add(new JLabel("of"));
        daysPanel.add(noOfDaysField);
        daysPanel.add(new JLabel("days"));
        panel.add(daysPanel, gbc);
        row++;

        // Date
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.25;
        panel.add(createLabel("Date: *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        datePanel.setBackground(Color.WHITE);
        dayAndDateField = new JTextField(12);
        dayAndDateField.setEditable(false);
        dayAndDateField.setText(LocalDate.now().toString());
        JButton dateBtn = ButtonStyleUtil.createPrimaryButton("Select");
        dateBtn.setPreferredSize(new Dimension(80, 30));
        dateBtn.addActionListener(e -> {
            LocalDate date = DatePickerDialog.showDialog(this, "Select Date");
            if (date != null) {
                dayAndDateField.setText(date.toString());
            }
        });
        datePanel.add(dayAndDateField);
        datePanel.add(Box.createHorizontalStrut(5));
        datePanel.add(dateBtn);
        panel.add(datePanel, gbc);
        row++;

        // Duration Time
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.25;
        panel.add(createLabel("Duration:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        durationTimeField = new JTextField(20);
        durationTimeField.setToolTipText("e.g., 45 minutes, 1 hour");
        panel.add(durationTimeField, gbc);
        row++;

        // Therapist Time
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.25;
        panel.add(createLabel("Therapist Time:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        therapistTimeField = new JTextField(20);
        therapistTimeField.setToolTipText("e.g., 10:00 AM - 11:00 AM");
        panel.add(therapistTimeField, gbc);
        row++;

        // Price
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.25;
        panel.add(createLabel("Price (₹):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        priceField = new JTextField(15);
        priceField.setText("0");
        panel.add(priceField, gbc);
        row++;

        // Types of Karma and Medicines
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.25;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(createLabel("Karma & Medicines:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        typesOfKarmaArea = new JTextArea(4, 40);
        typesOfKarmaArea.setLineWrap(true);
        typesOfKarmaArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(typesOfKarmaArea), gbc);
        row++;

        // Notes
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.25;
        panel.add(createLabel("Notes:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        noteArea = new JTextArea(4, 40);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(noteArea), gbc);
        row++;

        // Filler
        gbc.gridy = row;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);

        JButton saveButton = ButtonStyleUtil.createButton(existingPanchakarma != null ? "Update" : "Save Treatment", new Color(128, 0, 128));
        saveButton.setPreferredSize(new Dimension(140, 40));
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.addActionListener(e -> savePanchakarma());

        JButton saveNextButton = ButtonStyleUtil.createButton("Save & Next Day", new Color(100, 150, 200));
        saveNextButton.setPreferredSize(new Dimension(150, 40));
        saveNextButton.addActionListener(e -> saveAndNextDay());

        JButton clearButton = ButtonStyleUtil.createSecondaryButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.addActionListener(e -> clearForm());

        JButton cancelButton = ButtonStyleUtil.createLightButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.addActionListener(e -> dispose());

        panel.add(saveButton);
        if (existingPanchakarma == null) {
            panel.add(saveNextButton);
        }
        panel.add(clearButton);
        panel.add(cancelButton);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    private void selectPatient() {
        String input = JOptionPane.showInputDialog(this, "Enter Patient ID:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(input.trim());
                Patient patient = patientController.getPatientById(id);
                if (patient != null) {
                    this.patientId = id;
                    patientNameLabel.setText("Patient: " + patient.getName() + " (ID: " + id + ")");
                } else {
                    JOptionPane.showMessageDialog(this, "Patient not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Patient ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadPatientInfo() {
        if (patientId > 0) {
            Patient patient = patientController.getPatientById(patientId);
            if (patient != null) {
                patientNameLabel.setText("Patient: " + patient.getName() + " (ID: " + patientId + ")");
            }
        }
    }

    private void savePanchakarma() {
        if (!validateForm()) {
            return;
        }

        Panchakarma panchakarma = existingPanchakarma != null ? existingPanchakarma : new Panchakarma();
        populatePanchakarmaFromForm(panchakarma);

        boolean success;
        if (existingPanchakarma != null) {
            success = panchakarmaController.updatePanchakarma(panchakarma);
        } else {
            success = panchakarmaController.addPanchakarma(panchakarma) > 0;
        }

        if (success) {
            dispose();
        }
    }

    private void saveAndNextDay() {
        if (!validateForm()) {
            return;
        }

        Panchakarma panchakarma = new Panchakarma();
        populatePanchakarmaFromForm(panchakarma);

        if (panchakarmaController.addPanchakarma(panchakarma) > 0) {
            // Increment day and date for next entry
            int currentDay = parseIntOrZero(dayField.getText());
            int totalDays = parseIntOrZero(noOfDaysField.getText());
            
            if (currentDay < totalDays) {
                dayField.setText(String.valueOf(currentDay + 1));
                LocalDate currentDate = parseDate(dayAndDateField.getText());
                if (currentDate != null) {
                    dayAndDateField.setText(currentDate.plusDays(1).toString());
                }
                JOptionPane.showMessageDialog(this,
                    "Day " + currentDay + " saved. Ready for Day " + (currentDay + 1),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "All " + totalDays + " days completed!",
                    "Treatment Complete",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }
    }

    private boolean validateForm() {
        if (patientId <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a patient first", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (panchakarmaNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Panchakarma type is required", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            panchakarmaNameField.requestFocus();
            return false;
        }

        if (dayAndDateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Date is required", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void populatePanchakarmaFromForm(Panchakarma panchakarma) {
        panchakarma.setPatientId(patientId);
        panchakarma.setPanchakarmaName(panchakarmaNameField.getText().trim());
        panchakarma.setAdvised(advisedField.getText().trim());
        panchakarma.setDay(parseIntOrZero(dayField.getText()));
        panchakarma.setNoOfDays(parseIntOrZero(noOfDaysField.getText()));
        panchakarma.setDayAndDate(parseDate(dayAndDateField.getText()));
        panchakarma.setDurationTime(durationTimeField.getText().trim());
        panchakarma.setTherapistTime(therapistTimeField.getText().trim());
        panchakarma.setPrice(parseDoubleOrZero(priceField.getText()));
        panchakarma.setTypesOfKarmaAndMedicines(typesOfKarmaArea.getText().trim());
        panchakarma.setNote(noteArea.getText().trim());
    }

    private void populateForm(Panchakarma p) {
        panchakarmaNameField.setText(p.getPanchakarmaName());
        advisedField.setText(p.getAdvised());
        dayField.setText(String.valueOf(p.getDay()));
        noOfDaysField.setText(String.valueOf(p.getNoOfDays()));
        dayAndDateField.setText(p.getDayAndDate() != null ? p.getDayAndDate().toString() : "");
        durationTimeField.setText(p.getDurationTime());
        therapistTimeField.setText(p.getTherapistTime());
        priceField.setText(String.valueOf(p.getPrice()));
        typesOfKarmaArea.setText(p.getTypesOfKarmaAndMedicines());
        noteArea.setText(p.getNote());
    }

    private void clearForm() {
        panchakarmaNameField.setText("");
        advisedField.setText("");
        dayField.setText("1");
        noOfDaysField.setText("7");
        dayAndDateField.setText(LocalDate.now().toString());
        durationTimeField.setText("");
        therapistTimeField.setText("");
        priceField.setText("0");
        typesOfKarmaArea.setText("");
        noteArea.setText("");
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

