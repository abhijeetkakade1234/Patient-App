package view.patient;

import controller.PatientController;
import controller.FollowUpController;
import controller.PanchakarmaController;
import model.Patient;
import model.FollowUp;
import model.Panchakarma;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Patient Details View - displays comprehensive patient information with tabs
 */
public class PatientDetailsView extends JFrame {
    private final Patient patient;
    private final PatientController patientController;
    private final FollowUpController followUpController;
    private final PanchakarmaController panchakarmaController;
    
    private JTabbedPane tabbedPane;

    public PatientDetailsView(Patient patient) {
        this.patient = patient;
        this.patientController = new PatientController();
        this.followUpController = new FollowUpController();
        this.panchakarmaController = new PanchakarmaController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Patient Details - " + patient.getName());
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        
        tabbedPane.addTab("Personal Info", createPersonalInfoPanel());
        tabbedPane.addTab("Medical Info", createMedicalInfoPanel());
        tabbedPane.addTab("Follow-ups (" + getFollowUpCount() + ")", createFollowUpsPanel());
        tabbedPane.addTab("Panchakarma (" + getPanchakarmaCount() + ")", createPanchakarmaPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 102, 204));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Patient name and ID
        JLabel nameLabel = new JLabel(patient.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(Color.WHITE);

        JLabel idLabel = new JLabel("Patient ID: " + patient.getId());
        idLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        idLabel.setForeground(new Color(200, 220, 255));

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setOpaque(false);
        leftPanel.add(nameLabel);
        leftPanel.add(idLabel);

        // Quick info
        JPanel rightPanel = new JPanel(new GridLayout(2, 2, 20, 5));
        rightPanel.setOpaque(false);
        
        addQuickInfo(rightPanel, "Age:", patient.getAge() + " years");
        addQuickInfo(rightPanel, "Phone:", patient.getPhone() != null ? patient.getPhone() : "N/A");
        addQuickInfo(rightPanel, "Registered:", patient.getRegistrationDate() != null ? 
                patient.getRegistrationDate().toString() : "N/A");
        addQuickInfo(rightPanel, "Follow-up:", patient.getFollowUpDate() != null ? 
                patient.getFollowUpDate().toString() : "N/A");

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private void addQuickInfo(JPanel panel, String label, String value) {
        JLabel l = new JLabel(label + " " + value);
        l.setFont(new Font("Arial", Font.PLAIN, 12));
        l.setForeground(Color.WHITE);
        panel.add(l);
    }

    private JScrollPane createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        addField(panel, gbc, row++, "Name:", patient.getName());
        addField(panel, gbc, row++, "Age:", String.valueOf(patient.getAge()));
        addField(panel, gbc, row++, "Sex:", patient.getSex());
        addField(panel, gbc, row++, "Date of Birth:", patient.getDob() != null ? patient.getDob().toString() : "N/A");
        addField(panel, gbc, row++, "Phone:", patient.getPhone());
        addField(panel, gbc, row++, "Address:", patient.getAddress());
        addField(panel, gbc, row++, "Job/Occupation:", patient.getJob());
        addField(panel, gbc, row++, "Reference:", patient.getReference());
        addField(panel, gbc, row++, "Case No:", patient.getCaseNo());
        addField(panel, gbc, row++, "Registration Date:", patient.getRegistrationDate() != null ? 
                patient.getRegistrationDate().toString() : "N/A");

        // Add filler
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
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        addField(panel, gbc, row++, "Height:", patient.getHeight() + " cm");
        addField(panel, gbc, row++, "Weight:", patient.getWeight() + " kg");
        addField(panel, gbc, row++, "BMI:", String.valueOf(patient.getBmi()));
        addField(panel, gbc, row++, "Blood Pressure:", patient.getBloodPressure());
        addField(panel, gbc, row++, "SpO2:", patient.getSpo2() + "%");
        addField(panel, gbc, row++, "Disease/Complaint:", patient.getDisease());
        addField(panel, gbc, row++, "Previous Medicines:", patient.getPreviousMedicines());
        addField(panel, gbc, row++, "Follow-up Date:", patient.getFollowUpDate() != null ? 
                patient.getFollowUpDate().toString() : "N/A");
        addField(panel, gbc, row++, "Pending Amount:", "₹" + patient.getPendingMoney());
        addField(panel, gbc, row++, "Remarks:", patient.getRemark());

        // Add filler
        gbc.gridy = row;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        return new JScrollPane(panel);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(labelComponent, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JLabel valueComponent = new JLabel(value != null && !value.isEmpty() ? value : "N/A");
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(valueComponent, gbc);
    }

    private JPanel createFollowUpsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table
        String[] columns = {"Visit No", "Date", "BP", "Weight", "Treatment", "Next Follow-up"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<FollowUp> followUps = followUpController.getFollowUpsByPatientId(patient.getId());
        for (FollowUp f : followUps) {
            model.addRow(new Object[]{
                f.getVisitNo(),
                f.getVisitDate() != null ? f.getVisitDate().toString() : "",
                f.getBloodPressure(),
                f.getWeight() + " kg",
                truncate(f.getRxTreatment(), 40),
                f.getNextFollowUpDate() != null ? f.getNextFollowUpDate().toString() : ""
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add follow-up button
        JButton addButton = ButtonStyleUtil.createSuccessButton("Add Follow-up");
        addButton.addActionListener(e -> {
            // TODO: Open follow-up form
            JOptionPane.showMessageDialog(this, "Follow-up form will open here");
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createPanchakarmaPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table
        String[] columns = {"Name", "Day", "Duration", "Therapist Time", "Price", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Panchakarma> panchakarmaList = panchakarmaController.getPanchakarmaByPatientId(patient.getId());
        for (Panchakarma p : panchakarmaList) {
            model.addRow(new Object[]{
                p.getPanchakarmaName(),
                p.getDay() + "/" + p.getNoOfDays(),
                p.getDurationTime(),
                p.getTherapistTime(),
                "₹" + p.getPrice(),
                p.getDayAndDate() != null ? p.getDayAndDate().toString() : ""
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add panchakarma button
        JButton addButton = ButtonStyleUtil.createButton("Add Panchakarma", new Color(128, 0, 128));
        addButton.addActionListener(e -> {
            // TODO: Open panchakarma form
            JOptionPane.showMessageDialog(this, "Panchakarma form will open here");
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);

        JButton editButton = ButtonStyleUtil.createPrimaryButton("Edit Patient");
        editButton.setPreferredSize(new Dimension(120, 35));
        editButton.addActionListener(e -> {
            // TODO: Open edit form
            JOptionPane.showMessageDialog(this, "Edit form will open here");
        });

        JButton deleteButton = ButtonStyleUtil.createDangerButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100, 35));
        deleteButton.addActionListener(e -> {
            if (patientController.deletePatient(patient.getId())) {
                dispose();
            }
        });

        JButton closeButton = ButtonStyleUtil.createLightButton("Close");
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(e -> dispose());

        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(closeButton);

        return panel;
    }

    private int getFollowUpCount() {
        return followUpController.getFollowUpCountByPatientId(patient.getId());
    }

    private int getPanchakarmaCount() {
        return panchakarmaController.getPanchakarmaCountByPatientId(patient.getId());
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

}

