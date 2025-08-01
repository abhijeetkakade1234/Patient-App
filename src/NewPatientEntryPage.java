
// Final version with extended fields and scroll support
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class NewPatientEntryPage extends JFrame {
    JFrame frame;
    JPanel mainPanel, personalInfoPanel, contactInfoPanel, medicalInfoPanel, buttonPanel;
    JPanel extraFieldsPanel;
    JButton submitButton, clearButton;
    JTextField nameField, ageField, sexField, htField, wtField, jobField, dobField, phoneField,
            addressField, referenceField, bpField, spo2Field, bmiField,
            diseaseField, caseNoField, pendingMoneyField, followUpField,
            previousMedicinesField, dateField;

    // Extra Fields
    JTextField nadiField, samyaLakshanaField, dailyRoutineField, rxTreatmentField, daysField;
    JTextField totalPaymentField, nextFollowUpField;

    JTextField kcoField, hoField, shoField, mhField, ohField, ahField;

    JTextField malField, mutraField, jivhaField, shudhaField, trushnaField,
            nidraField, swedaField, sparshaField, drukaField, shabdaField, akrutiField;

    JTextArea remarkArea;

    public NewPatientEntryPage() {
        frame = new JFrame("New Patient Entry");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full-screen mode
        display();
        frame.setVisible(true);
    }

    public void display() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        // Add to left and right panel
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
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

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        addToPanel(medicalInfoPanel, "Blood Pressure (BP):", bpField);
        addToPanel(medicalInfoPanel, "SPO2 (%):", spo2Field);
        addToPanel(medicalInfoPanel, "Disease:", diseaseField);
        addToPanel(medicalInfoPanel, "Case Number:", caseNoField);
        addToPanel(medicalInfoPanel, "Pending Money:", pendingMoneyField);
        addToPanel(medicalInfoPanel, "Follow-up Date:", followUpField);
        addToPanel(medicalInfoPanel, "Previous Medicines:", previousMedicinesField);
        addToPanel(medicalInfoPanel, "Date:", dateField);

        rightPanel.add(medicalInfoPanel);

        topPanel.add(leftPanel);
        topPanel.add(rightPanel);
        mainPanel.add(topPanel);

        // Additional Panels in Tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel clinicalInfo = createSectionPanel("Clinical Info");
        nadiField = createTextField();
        samyaLakshanaField = createTextField();
        dailyRoutineField = createTextField();
        rxTreatmentField = createTextField();
        daysField = createTextField();
        totalPaymentField = createTextField();
        nextFollowUpField = createTextField();

        addToPanel(clinicalInfo, "Nadi:", nadiField);
        addToPanel(clinicalInfo, "Samya Lakshana:", samyaLakshanaField);
        addToPanel(clinicalInfo, "Daily Routine:", dailyRoutineField);
        addToPanel(clinicalInfo, "Rx Treatment:", rxTreatmentField);
        addToPanel(clinicalInfo, "Days:", daysField);
        addToPanel(clinicalInfo, "Total Payment:", totalPaymentField);
        addToPanel(clinicalInfo, "Next Follow-up Date:", nextFollowUpField);

        JPanel historyPanel = createSectionPanel("History");
        kcoField = createTextField();
        hoField = createTextField();
        shoField = createTextField();
        mhField = createTextField();
        ohField = createTextField();
        ahField = createTextField();
        addToPanel(historyPanel, "K/C/O:", kcoField);
        addToPanel(historyPanel, "H/O:", hoField);
        addToPanel(historyPanel, "S/H/O:", shoField);
        addToPanel(historyPanel, "M/H:", mhField);
        addToPanel(historyPanel, "O/H:", ohField);
        addToPanel(historyPanel, "A/H:", ahField);

        JPanel parikshanPanel = createSectionPanel("Samanya Parikshan");
        malField = createTextField();
        mutraField = createTextField();
        jivhaField = createTextField();
        shudhaField = createTextField();
        trushnaField = createTextField();
        nidraField = createTextField();
        swedaField = createTextField();
        sparshaField = createTextField();
        drukaField = createTextField();
        shabdaField = createTextField();
        akrutiField = createTextField();

        addToPanel(parikshanPanel, "Mal:", malField);
        addToPanel(parikshanPanel, "Mutra:", mutraField);
        addToPanel(parikshanPanel, "Jivha:", jivhaField);
        addToPanel(parikshanPanel, "Shudha:", shudhaField);
        addToPanel(parikshanPanel, "Trushna:", trushnaField);
        addToPanel(parikshanPanel, "Nidra:", nidraField);
        addToPanel(parikshanPanel, "Sweda:", swedaField);
        addToPanel(parikshanPanel, "Sparsha:", sparshaField);
        addToPanel(parikshanPanel, "Druka:", drukaField);
        addToPanel(parikshanPanel, "Shabda:", shabdaField);
        addToPanel(parikshanPanel, "Akruti:", akrutiField);

        tabbedPane.add("Clinical Info", clinicalInfo);
        tabbedPane.add("History", historyPanel);
        tabbedPane.add("Samanya Parikshan", parikshanPanel);
        mainPanel.add(tabbedPane);

        // Remark Field
        JPanel remarkPanel = createSectionPanel("Remark");
        remarkArea = new JTextArea(5, 80);
        remarkArea.setLineWrap(true);
        remarkArea.setWrapStyleWord(true);
        JScrollPane remarkScroll = new JScrollPane(remarkArea);
        remarkScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        remarkPanel.add(remarkScroll);
        mainPanel.add(remarkPanel);

        // Button Panel
        buttonPanel = new JPanel();
        submitButton = createStyledButton("Submit", new Color(46, 204, 113), new Color(39, 174, 96));
        clearButton = createStyledButton("Clear", new Color(231, 76, 60), new Color(192, 57, 43));
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
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
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setMinimumSize(new Dimension(150, 25));
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
        remarkArea.setText("");
    }

    public static void main(String[] args) {
        new NewPatientEntryPage();
    }
}
