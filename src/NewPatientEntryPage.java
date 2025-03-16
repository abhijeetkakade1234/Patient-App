
// final version    
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class NewPatientEntryPage {
    JFrame frame;
    JPanel mainPanel, personalInfoPanel, contactInfoPanel, medicalInfoPanel, buttonPanel;
    JButton submitButton, clearButton;
    JTextField nameField, ageField, sexField, htField, wtField, jobField, dobField, phoneField,
            addressField, referenceField, bpField, spo2Field, bmiField,
            diseaseField, caseNoField, pendingMoneyField, followUpField,
            previousMedicinesField, dateField;

    public NewPatientEntryPage() {
        frame = new JFrame("New Patient Entry");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full-screen mode
        display();
        frame.setVisible(true);
    }

    public void display() {
        mainPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // Two-column layout
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Creating sections
        personalInfoPanel = createSectionPanel("Personal Information");
        contactInfoPanel = createSectionPanel("Contact Information");
        medicalInfoPanel = createSectionPanel("Medical Information");

        // Creating text fields
        nameField = createTextField();
        ageField = createTextField();
        sexField = createTextField();
        dobField = createTextField();
        htField = createTextField();
        wtField = createTextField();
        bmiField = createTextField();
        jobField = createTextField();
        phoneField = createTextField();
        addressField = createTextField();
        referenceField = createTextField();
        bpField = createTextField();
        spo2Field = createTextField();
        diseaseField = createTextField();
        caseNoField = createTextField();
        pendingMoneyField = createTextField();
        followUpField = createTextField();
        previousMedicinesField = createTextField();
        dateField = createTextField();

        // Left Column: Personal & Contact Information
        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        addToPanel(personalInfoPanel, "Name:", nameField);
        addToPanel(personalInfoPanel, "Age:", ageField);
        addToPanel(personalInfoPanel, "Sex:", sexField);
        addToPanel(personalInfoPanel, "Date of Birth:", dobField);
        addToPanel(personalInfoPanel, "Height (cm):", htField);
        addToPanel(personalInfoPanel, "Weight (kg):", wtField);
        addToPanel(personalInfoPanel, "BMI:", bmiField);
        addToPanel(personalInfoPanel, "Job:", jobField);

        addToPanel(contactInfoPanel, "Phone Number:", phoneField);
        addToPanel(contactInfoPanel, "Address:", addressField);
        addToPanel(contactInfoPanel, "Reference:", referenceField);

        leftPanel.add(personalInfoPanel);
        leftPanel.add(contactInfoPanel);

        // Right Column: Medical Information
        JPanel rightPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        addToPanel(medicalInfoPanel, "Blood Pressure (BP):", bpField);
        addToPanel(medicalInfoPanel, "SPO2 (%):", spo2Field);
        addToPanel(medicalInfoPanel, "Disease:", diseaseField);
        addToPanel(medicalInfoPanel, "Case Number:", caseNoField);
        addToPanel(medicalInfoPanel, "Pending Money:", pendingMoneyField);
        addToPanel(medicalInfoPanel, "Follow-up Date:", followUpField);
        addToPanel(medicalInfoPanel, "Previous Medicines:", previousMedicinesField);
        addToPanel(medicalInfoPanel, "Date:", dateField);

        rightPanel.add(medicalInfoPanel);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        // Buttons Panel
        buttonPanel = new JPanel();

        submitButton = createStyledButton("Submit", new Color(46, 204, 113), new Color(39, 174, 96));
        clearButton = createStyledButton("Clear", new Color(231, 76, 60), new Color(192, 57, 43));

        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        submitButton.addActionListener(e -> handleNewPatientSubmit());
        clearButton.addActionListener(e -> clearForm());
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 1), title,
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        return panel;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30)); // Larger fields for full-screen
        textField.setMinimumSize(new Dimension(150, 25)); // Allows shrinking
        return textField;
    }

    private void addToPanel(JPanel panel, String label, JTextField field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = panel.getComponentCount() / 2;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    private void handleNewPatientSubmit() {
        String name = nameField.getText();
        String age = ageField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || age.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name, Age, Phone, and Address are required!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Add sql logic to save patient details
        JOptionPane.showMessageDialog(frame, "Patient Details Submitted Successfully!", "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        sexField.setText("");
        dobField.setText("");
        htField.setText("");
        wtField.setText("");
        bmiField.setText("");
        jobField.setText("");
        phoneField.setText("");
        addressField.setText("");
        referenceField.setText("");
        bpField.setText("");
        spo2Field.setText("");
        diseaseField.setText("");
        caseNoField.setText("");
        pendingMoneyField.setText("");
        followUpField.setText("");
        previousMedicinesField.setText("");
        dateField.setText("");
    }

    public static void main(String[] args) {
        new NewPatientEntryPage();
    }
}
