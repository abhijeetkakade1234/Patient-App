package view.followup;

import controller.FollowUpController;
import controller.PatientController;
import model.FollowUp;
import model.Patient;
import view.components.DatePickerDialog;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Follow-up Form View - add or edit follow-up records
 * Includes Ayurvedic examination fields
 */
public class FollowUpFormView extends JFrame {
    private final FollowUpController followUpController;
    private final PatientController patientController;
    
    private int patientId = -1;
    private FollowUp existingFollowUp = null;
    
    // Basic fields
    private JLabel patientNameLabel;
    private JTextField visitNoField;
    private JTextField visitDateField;
    private JTextField heightField;
    private JTextField weightField;
    private JTextField bmiField;
    private JTextField bpField;
    private JTextField spo2Field;
    private JTextField nextFollowUpField;
    private JTextField daysField;
    private JTextField totalPaymentField;
    private JTextField pendingPaymentField;
    
    // Ayurvedic fields
    private JTextField nadiField;
    private JTextArea samanyaLakshanaArea;
    private JTextArea rxTreatmentArea;
    private JTextArea notesArea;
    
    // History fields
    private JTextField kcoField;
    private JTextField hoField;
    private JTextField shoField;
    private JTextField mhField;
    private JTextField ohField;
    private JTextField ahField;
    
    // Samanya Parikshan fields
    private JTextField malField;
    private JTextField mutraField;
    private JTextField jivhaField;
    private JTextField shudhaField;
    private JTextField trushnaField;
    private JTextField nidraField;
    private JTextField swedaField;
    private JTextField sparshaField;
    private JTextField drukaField;
    private JTextField shabdaField;
    private JTextField akrutiField;

    public FollowUpFormView() {
        this.followUpController = new FollowUpController();
        this.patientController = new PatientController();
        initializeUI();
    }

    public FollowUpFormView(int patientId) {
        this();
        this.patientId = patientId;
        loadPatientInfo();
    }

    public FollowUpFormView(FollowUp followUp) {
        this(followUp.getPatientId());
        this.existingFollowUp = followUp;
        populateForm(followUp);
    }

    private void initializeUI() {
        setTitle("Follow-up Record");
        setSize(950, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Tabbed form
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        tabbedPane.addTab("Basic Info", createBasicInfoPanel());
        tabbedPane.addTab("History (Itihas)", createHistoryPanel());
        tabbedPane.addTab("Examination (Parikshan)", createExaminationPanel());
        tabbedPane.addTab("Treatment (Rx)", createTreatmentPanel());
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

        JLabel titleLabel = new JLabel(existingFollowUp != null ? "Edit Follow-up" : "New Follow-up Record");
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

    private JScrollPane createBasicInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Visit No and Date
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Visit No:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.2;
        visitNoField = new JTextField(5);
        panel.add(visitNoField, gbc);

        gbc.gridx = 2; gbc.weightx = 0.15;
        panel.add(createLabel("Visit Date: *"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5;
        JPanel datePanel = createDatePanel();
        panel.add(datePanel, gbc);
        row++;

        // Height, Weight, BMI
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Height (cm):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.2;
        heightField = new JTextField(8);
        heightField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { calculateBMI(); }
        });
        panel.add(heightField, gbc);

        gbc.gridx = 2; gbc.weightx = 0.15;
        panel.add(createLabel("Weight (kg):"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.2;
        weightField = new JTextField(8);
        weightField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { calculateBMI(); }
        });
        panel.add(weightField, gbc);

        gbc.gridx = 4; gbc.weightx = 0.1;
        panel.add(createLabel("BMI:"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.2;
        bmiField = new JTextField(6);
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

        // Nadi
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Nadi (Pulse):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 0.85;
        nadiField = new JTextField(30);
        panel.add(nadiField, gbc);
        gbc.gridwidth = 1;
        row++;

        // Next follow-up and Days
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Next Follow-up:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.35;
        JPanel nextDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        nextDatePanel.setBackground(Color.WHITE);
        nextFollowUpField = new JTextField(12);
        nextFollowUpField.setEditable(false);
        JButton nextDateBtn = ButtonStyleUtil.createPrimaryButton("Select");
        nextDateBtn.setPreferredSize(new Dimension(80, 30));
        nextDateBtn.addActionListener(e -> selectNextFollowUpDate());
        nextDatePanel.add(nextFollowUpField);
        nextDatePanel.add(Box.createHorizontalStrut(5));
        nextDatePanel.add(nextDateBtn);
        panel.add(nextDatePanel, gbc);

        gbc.gridx = 2; gbc.weightx = 0.15;
        panel.add(createLabel("Treatment Days:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.35;
        daysField = new JTextField(8);
        panel.add(daysField, gbc);
        row++;

        // Payment
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.15;
        panel.add(createLabel("Total Payment (₹):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.35;
        totalPaymentField = new JTextField(15);
        totalPaymentField.setText("0");
        panel.add(totalPaymentField, gbc);

        gbc.gridx = 2; gbc.weightx = 0.15;
        panel.add(createLabel("Pending (₹):"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.35;
        pendingPaymentField = new JTextField(15);
        pendingPaymentField.setText("0");
        panel.add(pendingPaymentField, gbc);
        row++;

        // Filler
        gbc.gridy = row;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        return new JScrollPane(panel);
    }

    private JPanel createDatePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(Color.WHITE);
        visitDateField = new JTextField(12);
        visitDateField.setEditable(false);
        visitDateField.setText(LocalDate.now().toString());
        JButton dateBtn = ButtonStyleUtil.createPrimaryButton("Select");
        dateBtn.setPreferredSize(new Dimension(80, 30));
        dateBtn.addActionListener(e -> {
            LocalDate date = DatePickerDialog.showDialog(this, "Select Visit Date");
            if (date != null) {
                visitDateField.setText(date.toString());
            }
        });
        panel.add(visitDateField);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(dateBtn);
        return panel;
    }

    private JScrollPane createHistoryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // History fields
        kcoField = addLabeledTextField(panel, gbc, row++, "K/C/O (Known Case Of):", 50);
        hoField = addLabeledTextField(panel, gbc, row++, "H/O (History Of):", 50);
        shoField = addLabeledTextField(panel, gbc, row++, "S/H/O (Surgical History):", 50);
        mhField = addLabeledTextField(panel, gbc, row++, "M/H (Medical History):", 50);
        ohField = addLabeledTextField(panel, gbc, row++, "O/H (Occupational History):", 50);
        ahField = addLabeledTextField(panel, gbc, row++, "A/H (Allergy History):", 50);

        // Filler
        gbc.gridy = row;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        return new JScrollPane(panel);
    }

    private JScrollPane createExaminationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        JLabel titleLabel = new JLabel("Samanya Parikshan (General Examination)");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        int row = 1;

        // Two columns of fields
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panel.add(createLabel("Mal (Bowel):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        malField = new JTextField(15);
        panel.add(malField, gbc);

        gbc.gridx = 2; gbc.weightx = 0.2;
        panel.add(createLabel("Mutra (Urine):"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.3;
        mutraField = new JTextField(15);
        panel.add(mutraField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(createLabel("Jivha (Tongue):"), gbc);
        gbc.gridx = 1;
        jivhaField = new JTextField(15);
        panel.add(jivhaField, gbc);

        gbc.gridx = 2;
        panel.add(createLabel("Shudha (Purity):"), gbc);
        gbc.gridx = 3;
        shudhaField = new JTextField(15);
        panel.add(shudhaField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(createLabel("Trushna (Thirst):"), gbc);
        gbc.gridx = 1;
        trushnaField = new JTextField(15);
        panel.add(trushnaField, gbc);

        gbc.gridx = 2;
        panel.add(createLabel("Nidra (Sleep):"), gbc);
        gbc.gridx = 3;
        nidraField = new JTextField(15);
        panel.add(nidraField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(createLabel("Sweda (Sweat):"), gbc);
        gbc.gridx = 1;
        swedaField = new JTextField(15);
        panel.add(swedaField, gbc);

        gbc.gridx = 2;
        panel.add(createLabel("Sparsha (Touch):"), gbc);
        gbc.gridx = 3;
        sparshaField = new JTextField(15);
        panel.add(sparshaField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(createLabel("Druka (Vision):"), gbc);
        gbc.gridx = 1;
        drukaField = new JTextField(15);
        panel.add(drukaField, gbc);

        gbc.gridx = 2;
        panel.add(createLabel("Shabda (Voice):"), gbc);
        gbc.gridx = 3;
        shabdaField = new JTextField(15);
        panel.add(shabdaField, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row;
        panel.add(createLabel("Akruti (Build):"), gbc);
        gbc.gridx = 1;
        akrutiField = new JTextField(15);
        panel.add(akrutiField, gbc);
        row++;

        // Samanya Lakshana (large text area)
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(createLabel("Samanya Lakshana:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        samanyaLakshanaArea = new JTextArea(4, 40);
        samanyaLakshanaArea.setLineWrap(true);
        samanyaLakshanaArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(samanyaLakshanaArea), gbc);
        row++;

        // Filler
        gbc.gridy = row;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        return new JScrollPane(panel);
    }

    private JScrollPane createTreatmentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        int row = 0;

        // Rx Treatment
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panel.add(createLabel("Rx (Treatment):"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        rxTreatmentArea = new JTextArea(8, 50);
        rxTreatmentArea.setLineWrap(true);
        rxTreatmentArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(rxTreatmentArea), gbc);
        row++;

        // Notes
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.2;
        panel.add(createLabel("Notes:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.8;
        notesArea = new JTextArea(5, 50);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(notesArea), gbc);
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

        JButton saveButton = ButtonStyleUtil.createSuccessButton(existingFollowUp != null ? "Update" : "Save Follow-up");
        saveButton.setPreferredSize(new Dimension(140, 40));
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.addActionListener(e -> saveFollowUp());

        JButton clearButton = ButtonStyleUtil.createSecondaryButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
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

    private JTextField addLabeledTextField(JPanel panel, GridBagConstraints gbc, int row, String label, int columns) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        panel.add(createLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.75;
        JTextField field = new JTextField(columns);
        panel.add(field, gbc);

        return field;
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

    private void selectNextFollowUpDate() {
        LocalDate date = DatePickerDialog.showDialog(this, "Select Next Follow-up Date");
        if (date != null) {
            nextFollowUpField.setText(date.toString());
        }
    }

    private void calculateBMI() {
        try {
            double height = Double.parseDouble(heightField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());
            if (height > 0 && weight > 0) {
                double bmi = followUpController.calculateBMI(height, weight);
                bmiField.setText(String.format("%.1f", bmi));
            }
        } catch (NumberFormatException e) {
            bmiField.setText("");
        }
    }

    private void saveFollowUp() {
        if (patientId <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a patient first", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (visitDateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Visit date is required", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        FollowUp followUp = existingFollowUp != null ? existingFollowUp : new FollowUp();
        followUp.setPatientId(patientId);
        followUp.setVisitNo(parseIntOrZero(visitNoField.getText()));
        followUp.setVisitDate(parseDate(visitDateField.getText()));
        followUp.setHeight(parseDoubleOrZero(heightField.getText()));
        followUp.setWeight(parseDoubleOrZero(weightField.getText()));
        followUp.setBmi(parseDoubleOrZero(bmiField.getText()));
        followUp.setBloodPressure(bpField.getText().trim());
        followUp.setSpo2(parseIntOrZero(spo2Field.getText()));
        followUp.setNadi(nadiField.getText().trim());
        followUp.setNextFollowUpDate(parseDate(nextFollowUpField.getText()));
        followUp.setDays(parseIntOrZero(daysField.getText()));
        followUp.setTotalPayment(parseDoubleOrZero(totalPaymentField.getText()));
        followUp.setPendingPayment(parseDoubleOrZero(pendingPaymentField.getText()));

        // History
        followUp.setKco(kcoField.getText().trim());
        followUp.setHo(hoField.getText().trim());
        followUp.setSho(shoField.getText().trim());
        followUp.setMh(mhField.getText().trim());
        followUp.setOh(ohField.getText().trim());
        followUp.setAh(ahField.getText().trim());

        // Examination
        followUp.setMal(malField.getText().trim());
        followUp.setMutra(mutraField.getText().trim());
        followUp.setJivha(jivhaField.getText().trim());
        followUp.setShudha(shudhaField.getText().trim());
        followUp.setTrushna(trushnaField.getText().trim());
        followUp.setNidra(nidraField.getText().trim());
        followUp.setSweda(swedaField.getText().trim());
        followUp.setSparsha(sparshaField.getText().trim());
        followUp.setDruka(drukaField.getText().trim());
        followUp.setShabda(shabdaField.getText().trim());
        followUp.setAkruti(akrutiField.getText().trim());
        followUp.setSamanyaLakshana(samanyaLakshanaArea.getText().trim());

        // Treatment
        followUp.setRxTreatment(rxTreatmentArea.getText().trim());
        followUp.setNotes(notesArea.getText().trim());

        boolean success;
        if (existingFollowUp != null) {
            success = followUpController.updateFollowUp(followUp);
        } else {
            success = followUpController.addFollowUp(followUp) > 0;
        }

        if (success) {
            dispose();
        }
    }

    private void populateForm(FollowUp f) {
        visitNoField.setText(String.valueOf(f.getVisitNo()));
        visitDateField.setText(f.getVisitDate() != null ? f.getVisitDate().toString() : "");
        heightField.setText(String.valueOf(f.getHeight()));
        weightField.setText(String.valueOf(f.getWeight()));
        bmiField.setText(String.valueOf(f.getBmi()));
        bpField.setText(f.getBloodPressure());
        spo2Field.setText(String.valueOf(f.getSpo2()));
        nadiField.setText(f.getNadi());
        nextFollowUpField.setText(f.getNextFollowUpDate() != null ? f.getNextFollowUpDate().toString() : "");
        daysField.setText(String.valueOf(f.getDays()));
        totalPaymentField.setText(String.valueOf(f.getTotalPayment()));
        pendingPaymentField.setText(String.valueOf(f.getPendingPayment()));

        kcoField.setText(f.getKco());
        hoField.setText(f.getHo());
        shoField.setText(f.getSho());
        mhField.setText(f.getMh());
        ohField.setText(f.getOh());
        ahField.setText(f.getAh());

        malField.setText(f.getMal());
        mutraField.setText(f.getMutra());
        jivhaField.setText(f.getJivha());
        shudhaField.setText(f.getShudha());
        trushnaField.setText(f.getTrushna());
        nidraField.setText(f.getNidra());
        swedaField.setText(f.getSweda());
        sparshaField.setText(f.getSparsha());
        drukaField.setText(f.getDruka());
        shabdaField.setText(f.getShabda());
        akrutiField.setText(f.getAkruti());
        samanyaLakshanaArea.setText(f.getSamanyaLakshana());

        rxTreatmentArea.setText(f.getRxTreatment());
        notesArea.setText(f.getNotes());
    }

    private void clearForm() {
        visitNoField.setText("");
        visitDateField.setText(LocalDate.now().toString());
        heightField.setText("");
        weightField.setText("");
        bmiField.setText("");
        bpField.setText("");
        spo2Field.setText("");
        nadiField.setText("");
        nextFollowUpField.setText("");
        daysField.setText("");
        totalPaymentField.setText("0");
        pendingPaymentField.setText("0");

        kcoField.setText("");
        hoField.setText("");
        shoField.setText("");
        mhField.setText("");
        ohField.setText("");
        ahField.setText("");

        malField.setText("");
        mutraField.setText("");
        jivhaField.setText("");
        shudhaField.setText("");
        trushnaField.setText("");
        nidraField.setText("");
        swedaField.setText("");
        sparshaField.setText("");
        drukaField.setText("");
        shabdaField.setText("");
        akrutiField.setText("");
        samanyaLakshanaArea.setText("");

        rxTreatmentArea.setText("");
        notesArea.setText("");
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

