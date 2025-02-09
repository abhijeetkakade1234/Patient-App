import java.awt.*;
import javax.swing.*;

public class NewPatientEntryPage {

    JFrame frame;
    JPanel formPanel;
    JButton submitButton, clearButton;
    JTextField nameField, dobField, addressField, diseaseField, phoneField, caseNoField, pendingMoneyField, followUpField, previousMedicinesField, dateField;
    JComboBox<String> genderComboBox;

    public NewPatientEntryPage() {
        frame = new JFrame("New Patient Entry");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        display();
        frame.setVisible(true);
    }

    public void display() {
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fields and Labels
        nameField = createTextField();
        dobField = createTextField();
        addressField = createTextField();
        diseaseField = createTextField();
        phoneField = createTextField();
        caseNoField = createTextField();
        pendingMoneyField = createTextField();
        followUpField = createTextField();
        previousMedicinesField = createTextField();
        dateField = createTextField();

        String[] genders = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genders);

        // Adding components to the form
        addToForm(formPanel, new JLabel("Name:"), nameField, gbc, 0);
        addToForm(formPanel, new JLabel("Date of Birth (dd/mm/yyyy):"), dobField, gbc, 1);
        addToForm(formPanel, new JLabel("Address:"), addressField, gbc, 2);
        addToForm(formPanel, new JLabel("Disease:"), diseaseField, gbc, 3);
        addToForm(formPanel, new JLabel("Phone Number:"), phoneField, gbc, 4);
        addToForm(formPanel, new JLabel("Case Number:"), caseNoField, gbc, 5);
        addToForm(formPanel, new JLabel("Gender:"), genderComboBox, gbc, 6);
        addToForm(formPanel, new JLabel("Pending Money:"), pendingMoneyField, gbc, 7);
        addToForm(formPanel, new JLabel("Follow-up Date:"), followUpField, gbc, 8);
        addToForm(formPanel, new JLabel("Previous Medicines:"), previousMedicinesField, gbc, 9);
        addToForm(formPanel, new JLabel("Date (dd/mm/yyyy):"), dateField, gbc, 10);

        // Buttons
        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");

        gbc.gridx = 0;
        gbc.gridy = 11;
        formPanel.add(submitButton, gbc);

        gbc.gridx = 1;
        formPanel.add(clearButton, gbc);

        frame.add(formPanel);

        // Button Actions
        submitButton.addActionListener(e -> handleNewPatientSubmit());
        clearButton.addActionListener(e -> clearForm());
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25));
        return textField;
    }

    private void addToForm(JPanel panel, JLabel label, Component field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void handleNewPatientSubmit() {
        // Collect and validate data
        String name = nameField.getText();
        String dob = dobField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || dob.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name, DOB, and Address are mandatory!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame, "New Patient Details Submitted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        nameField.setText("");
        dobField.setText("");
        addressField.setText("");
        diseaseField.setText("");
        phoneField.setText("");
        caseNoField.setText("");
        pendingMoneyField.setText("");
        followUpField.setText("");
        previousMedicinesField.setText("");
        dateField.setText("");
        genderComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new NewPatientEntryPage();
    }
}
