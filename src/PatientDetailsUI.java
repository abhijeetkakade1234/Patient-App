
// its not final 
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientDetailsUI extends JFrame {
    private JTable followUpTable;
    private JButton addFollowUpButton, updateFollowUpButton, deleteFollowUpButton;
    private int patientId;

    public PatientDetailsUI(int patientId) {
        this.patientId = patientId;
        setTitle("Patient Details");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = { "Visit No.", "Date", "Symptoms", "Observations", "Next Visit" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        followUpTable = new JTable(tableModel);
        add(new JScrollPane(followUpTable), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        addFollowUpButton = new JButton("Add Follow-Up");
        updateFollowUpButton = new JButton("Update Follow-Up");
        deleteFollowUpButton = new JButton("Delete Follow-Up");

        buttonPanel.add(addFollowUpButton);
        buttonPanel.add(updateFollowUpButton);
        buttonPanel.add(deleteFollowUpButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load patient follow-up data
        loadFollowUpData();

        // Button actions
        addFollowUpButton.addActionListener(e -> addFollowUp());
        updateFollowUpButton.addActionListener(e -> updateFollowUp());
        deleteFollowUpButton.addActionListener(e -> deleteFollowUp());

        setVisible(true);
    }

    private void loadFollowUpData() {
        // Fetch follow-up history from the database and populate the table
        // This part will be implemented in PatientDatabase.java
    }

    private void addFollowUp() {
        // Open a dialog or new form to add a new follow-up entry
    }

    private void updateFollowUp() {
        // Open selected follow-up entry for updating
    }

    private void deleteFollowUp() {
        // Delete selected follow-up entry from database
    }

    public static void main(String[] args) {
        new PatientDetailsUI(1);
    }
}
