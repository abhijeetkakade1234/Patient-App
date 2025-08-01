// import java.awt.*;
// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;

// // To Display Patient Details with Follow-Ups and Panchakarma Treatment
// // TODO : Add refresh functionality to update tables after adding/updating/deleting records
// public class PatientDetailsUI extends JFrame {
//     private final JTable followUpTable, panchakarmaTable;
//     private final JButton addFollowUpButton, updateFollowUpButton, deleteFollowUpButton;
//     private final JButton addPkDayButton;
//     private final int patientId;

//     public PatientDetailsUI(int patientId) {
//         this.patientId = patientId;
//         setTitle("Patient Full Details");
//         setSize(900, 700);
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         setLayout(new BorderLayout());

//         // Main tabs
//         JTabbedPane tabbedPane = new JTabbedPane();

//         // --- Follow-Up Panel ---
//         JPanel followUpPanel = new JPanel(new BorderLayout());
//         String[] followUpCols = { "Visit No.", "Date", "Symptoms", "Observations", "Next Visit" };
//         DefaultTableModel followUpModel = new DefaultTableModel(followUpCols, 0);
//         followUpTable = new JTable(followUpModel);
//         followUpPanel.add(new JScrollPane(followUpTable), BorderLayout.CENTER);

//         JPanel followUpButtons = new JPanel();
//         addFollowUpButton = new JButton("Add Follow-Up");
//         updateFollowUpButton = new JButton("Update Follow-Up");
//         deleteFollowUpButton = new JButton("Delete Follow-Up");
//         followUpButtons.add(addFollowUpButton);
//         followUpButtons.add(updateFollowUpButton);
//         followUpButtons.add(deleteFollowUpButton);
//         followUpPanel.add(followUpButtons, BorderLayout.SOUTH);
//         tabbedPane.addTab("Follow-Ups", followUpPanel);

//         // --- Panchakarma Panel ---
//         JPanel panchakarmaPanel = new JPanel(new BorderLayout());
//         String[] pkCols = { "Day", "Date", "Therapy Type", "Details", "Remarks" };
//         DefaultTableModel pkModel = new DefaultTableModel(pkCols, 0);
//         panchakarmaTable = new JTable(pkModel);
//         panchakarmaPanel.add(new JScrollPane(panchakarmaTable), BorderLayout.CENTER);

//         JPanel pkButtons = new JPanel();
//         addPkDayButton = new JButton("Add Panchakarma Day");
//         pkButtons.add(addPkDayButton);
//         panchakarmaPanel.add(pkButtons, BorderLayout.SOUTH);
//         tabbedPane.addTab("Panchakarma", panchakarmaPanel);

//         // Add all to frame
//         add(tabbedPane, BorderLayout.CENTER);

//         // Load data
//         loadFollowUpData();
//         loadPanchakarmaData();

//         // Button actions
//         addFollowUpButton.addActionListener(e -> addFollowUp());
//         updateFollowUpButton.addActionListener(e -> updateFollowUp());
//         deleteFollowUpButton.addActionListener(e -> deleteFollowUp());
//         addPkDayButton.addActionListener(e -> addPanchakarmaDay());

//         setVisible(true);
//     }

//     // --- Follow-Up Functions ---
//     private void loadFollowUpData() {
//         // TODO: Fetch follow-up history from DB using patientId
//     }

//     private void addFollowUp() {
//         PatientRecordManager.addFollowUp(
//                 this, // parent JFrame
//                 patientId, // assuming you have this from selected row
//                 (DefaultTableModel) followUpTable.getModel() // pass the model to update UI
//         );

//         loadFollowUpData(); // Optional: if you want to re-fetch from DB after adding
//     }

//     private void updateFollowUp() {
//         int selectedRow = followUpTable.getSelectedRow();
//         if (selectedRow == -1) {
//             JOptionPane.showMessageDialog(this, "Please select a row to update.");
//             return;
//         }

//         int followUpId = (int) followUpTable.getValueAt(selectedRow, 0); // assuming ID is in column 0

//         PatientRecordManager.updateFollowUp(
//                 this,
//                 patientId,
//                 followUpId,
//                 (DefaultTableModel) followUpTable.getModel());

//         loadFollowUpData(); // Optional: re-fetch everything
//     }

//     private void deleteFollowUp() {
//         int selectedRow = followUpTable.getSelectedRow();
//         if (selectedRow == -1) {
//             JOptionPane.showMessageDialog(this, "Please select a row to delete.");
//             return;
//         }

//         int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this follow-up?",
//                 "Confirm Delete", JOptionPane.YES_NO_OPTION);
//         if (confirm != JOptionPane.YES_OPTION)
//             return;

//         int followUpId = (int) followUpTable.getValueAt(selectedRow, 0); // ID column

//         PatientRecordManager.deleteFollowUp(
//                 followUpId,
//                 (DefaultTableModel) followUpTable.getModel(),
//                 selectedRow);

//         loadFollowUpData(); // Optional
//     }

//     // --- Panchakarma Functions ---
//     private void loadPanchakarmaData() {
//         // TODO: Fetch Panchakarma history from DB using patientId
//     }

//     private void addPanchakarmaDay() {
//         PanchakarmaUI panchakarmaUI = new PanchakarmaUI(patientId);
//         panchakarmaUI.setVisible(true);
//     }

//     public static void main(String[] args) {
//         new PatientDetailsUI(1); // Example patient
//     }
// }
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientDetailsUI extends JFrame {
    private final JTable followUpTable;
    private final JButton addFollowUpButton;
    private final JButton addPkDayButton;
    private final int patientId;
    private final JPanel patientInfoPanel;

    public PatientDetailsUI(int patientId) {
        this.patientId = patientId;
        setTitle("Patient Full Details");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Patient Info Panel ---
        patientInfoPanel = new JPanel(new GridLayout(2, 2));
        patientInfoPanel.setBorder(BorderFactory.createTitledBorder("Patient Info"));
        add(patientInfoPanel, BorderLayout.NORTH);
        loadPatientInfo(); // fill from DB (currently commented)

        // --- Tabbed Pane ---
        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Follow-Up Tab ---
        JPanel followUpPanel = new JPanel(new BorderLayout());
        String[] followUpCols = {
                "Visit No", "Day", "Ht", "Wt", "BMI", "BP", "SPO2",
                "Next Follow-Up", "Nadi", "Samanya Lakshana", "Rx Treatment", "Days",
                "Total ₹", "Pending ₹", "Note",
                "K/C/O", "H/O", "M/H", "O/H", "A/H",
                "Mal", "Mutra", "Jivha", "Shudha", "Trushna", "Nidra", "Sweda",
                "Sparsha", "Druka", "Shabda", "Akruti"
        };

        DefaultTableModel followUpModel = new DefaultTableModel(followUpCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only
            }
        };
        followUpTable = new JTable(followUpModel);
        followUpTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Resize note column for readability
        followUpTable.getColumnModel().getColumn(14).setPreferredWidth(300);

        // Set default width for others
        for (int i = 0; i < followUpTable.getColumnCount(); i++) {
            if (i != 14)
                followUpTable.getColumnModel().getColumn(i).setPreferredWidth(120);
        }

        JScrollPane scrollPane = new JScrollPane(followUpTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        followUpPanel.add(scrollPane, BorderLayout.CENTER);

        // Add Button Panel
        JPanel followUpButtonPanel = new JPanel();
        addFollowUpButton = new JButton("➕ Add New Follow-Up");
        followUpButtonPanel.add(addFollowUpButton);
        followUpPanel.add(followUpButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Follow-Ups", followUpPanel);

        // --- Panchakarma Tab ---
        JPanel panchakarmaPanel = new JPanel(new BorderLayout());
        String[] pkCols = {
                "Panchakarma Name", "Advised", "No of Days", "Day",
                "Types of Karma & Medicines Used", "Price", "Duration Time",
                "Therapist Time", "Day & Date", "Note"
        };
        JTable pkTable = new JTable(new DefaultTableModel(pkCols, 0));
        pkTable.getColumnModel().getColumn(9).setPreferredWidth(250);

        JScrollPane pkScrollPane = new JScrollPane(pkTable);
        panchakarmaPanel.add(pkScrollPane, BorderLayout.CENTER);

        JPanel pkButtonPanel = new JPanel();
        addPkDayButton = new JButton("Add Panchakarma Day");
        pkButtonPanel.add(addPkDayButton);
        panchakarmaPanel.add(pkButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Panchakarma", panchakarmaPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Load Follow-Up Records
        loadFollowUpData(followUpModel);

        // Button Action: open add dialog
        addFollowUpButton.addActionListener(e -> {
            PatientRecordManager.addFollowUp(
                    this, // parent JFrame
                    patientId, // patient ID
                    followUpModel // model to update UI
            );
        });

        addPkDayButton.addActionListener(e -> new PanchakarmaUI(patientId));

        setVisible(true);
    }

    // --- Dummy Patient Info (DB logic commented) ---
    private void loadPatientInfo() {
        patientInfoPanel.add(new JLabel("Name: Rahul Sharma"));
        patientInfoPanel.add(new JLabel("Age: 35"));
        patientInfoPanel.add(new JLabel("Gender: M"));
        patientInfoPanel.add(new JLabel("Daily Routine: Wakes at 6 AM, yoga, light diet"));
    }

    // --- Dummy Follow-Up Data (replace with DB logic) ---
    private void loadFollowUpData(DefaultTableModel model) {
        String[][] sample = {
                { "5", "Wed", "165", "60", "22", "120/80", "98", "2025-08-06", "Sama", "Normal", "Basti", "7", "500",
                        "100",
                        "Improved, mild cough.", "Asthma", "Dust allergy", "N/A", "N/A", "N/A",
                        "Mal: Normal", "Mutra: Regular", "Jivha: Coated", "Shudha: Good", "Trushna: Mild",
                        "Nidra: Disturbed", "Sweda: Normal", "Sparsha: Warm", "Druka: Sharp", "Shabda: Clear",
                        "Akruti: Stable" },
                { "4", "Mon", "162", "59", "21.7", "118/76", "99", "2025-07-30", "Teevra", "Low", "Nasya", "5", "400",
                        "50",
                        "Slight nasal blockage", "Sinusitis", "Cold exposure", "N/A", "N/A", "N/A",
                        "Mal: Constipated", "Mutra: Low", "Jivha: Clean", "Shudha: Poor", "Trushna: High",
                        "Nidra: Good", "Sweda: Less", "Sparsha: Dry", "Druka: Dull", "Shabda: Low", "Akruti: Weak" }
        };

        for (int i = sample.length - 1; i >= 0; i--) {
            model.addRow(sample[i]);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientDetailsUI(1));
    }
}
