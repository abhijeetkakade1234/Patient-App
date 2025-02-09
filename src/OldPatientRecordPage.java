import java.awt.*;
import javax.swing.*;

public class OldPatientRecordPage {

    JFrame frame;
    JPanel formPanel;
    JButton updateButton, searchButton, clearButton;
    JTextField caseNoField, nameField, diseaseField, pendingMoneyField, followUpField;

    public OldPatientRecordPage() {
        frame = new JFrame("Old Patient Record");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        display();
        frame.setVisible(true);
    }

    public void display() {
        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fields and Labels
        caseNoField = createTextField();
        nameField = createTextField();
        diseaseField = createTextField();
        pendingMoneyField = createTextField();
        followUpField = createTextField();

        // Adding components to the form
        addToForm(formPanel, new JLabel("Case Number:"), caseNoField, gbc, 0);
        addToForm(formPanel, new JLabel("Name:"), nameField, gbc, 1);
        addToForm(formPanel, new JLabel("Disease:"), diseaseField, gbc, 2);
        addToForm(formPanel, new JLabel("Pending Money:"), pendingMoneyField, gbc, 3);
        addToForm(formPanel, new JLabel("Follow-up Date:"), followUpField, gbc, 4);

        // Buttons
        searchButton = new JButton("Search");
        updateButton = new JButton("Update");
        clearButton = new JButton("Clear");

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(searchButton, gbc);

        gbc.gridx = 1;
        formPanel.add(updateButton, gbc);

        gbc.gridx = 2;
        formPanel.add(clearButton, gbc);

        frame.add(formPanel);

        // Button Actions
        searchButton.addActionListener(e -> handleSearch());
        updateButton.addActionListener(e -> handleUpdate());
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

    private void handleSearch() {
        String caseNo = caseNoField.getText();
        if (caseNo.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a Case Number to search!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Patient record found and loaded for Case Number: " + caseNo, "Success", JOptionPane.INFORMATION_MESSAGE);
            // Implement the logic to fetch and display patient data
        }
    }

    private void handleUpdate() {
        JOptionPane.showMessageDialog(frame, "Patient record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        // Implement the logic to update patient data
    }

    private void clearForm() {
        caseNoField.setText("");
        nameField.setText("");
        diseaseField.setText("");
        pendingMoneyField.setText("");
        followUpField.setText("");
    }

    public static void main(String[] args) {
        new OldPatientRecordPage();
    }
}
