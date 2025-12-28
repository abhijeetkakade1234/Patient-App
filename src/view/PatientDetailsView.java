package view;

import model.Patient;

import javax.swing.*;
import java.awt.*;

/**
 * Patient Details View
 * Displays detailed information about a patient
 */
public class PatientDetailsView extends JFrame {
    private final Patient patient;

    public PatientDetailsView(Patient patient) {
        this.patient = patient;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Patient Details - " + patient.getName());
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Details panel
        JPanel detailsPanel = createDetailsPanel();
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Personal Information
        addSectionHeader(panel, gbc, row++, "Personal Information");
        addField(panel, gbc, row++, "Patient ID:", String.valueOf(patient.getId()));
        addField(panel, gbc, row++, "Name:", patient.getName());
        addField(panel, gbc, row++, "Age:", String.valueOf(patient.getAge()));
        addField(panel, gbc, row++, "Sex:", patient.getSex());
        addField(panel, gbc, row++, "Date of Birth:", patient.getDob() != null ? patient.getDob().toString() : "N/A");
        addField(panel, gbc, row++, "Phone:", patient.getPhone());
        addField(panel, gbc, row++, "Address:", patient.getAddress());
        addField(panel, gbc, row++, "Job:", patient.getJob());
        addField(panel, gbc, row++, "Reference:", patient.getReference());

        // Medical Information
        addSectionHeader(panel, gbc, row++, "Medical Information");
        addField(panel, gbc, row++, "Height (cm):", String.valueOf(patient.getHeight()));
        addField(panel, gbc, row++, "Weight (kg):", String.valueOf(patient.getWeight()));
        addField(panel, gbc, row++, "BMI:", String.valueOf(patient.getBmi()));
        addField(panel, gbc, row++, "Blood Pressure:", patient.getBloodPressure());
        addField(panel, gbc, row++, "SpO2:", String.valueOf(patient.getSpo2()));
        addField(panel, gbc, row++, "Disease:", patient.getDisease());
        addField(panel, gbc, row++, "Case No:", patient.getCaseNo());
        addField(panel, gbc, row++, "Previous Medicines:", patient.getPreviousMedicines());

        // Administrative Information
        addSectionHeader(panel, gbc, row++, "Administrative Information");
        addField(panel, gbc, row++, "Registration Date:", patient.getRegistrationDate() != null ? patient.getRegistrationDate().toString() : "N/A");
        addField(panel, gbc, row++, "Follow-up Date:", patient.getFollowUpDate() != null ? patient.getFollowUpDate().toString() : "N/A");
        addField(panel, gbc, row++, "Pending Money:", "₹" + patient.getPendingMoney());
        addField(panel, gbc, row++, "Remark:", patient.getRemark());

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

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        // Label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(labelComponent, gbc);

        // Value
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        JLabel valueComponent = new JLabel(value != null && !value.isEmpty() ? value : "N/A");
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(valueComponent, gbc);
    }
}

