import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// To Store Panchakarma Treatment Details
public class PanchakarmaUI extends JFrame {
    private final JTable panchakarmaTable;
    private final JButton addDayButton, saveButton;
    private final int patientId;

    public PanchakarmaUI(int patientId) {
        this.patientId = patientId;
        setTitle("Panchakarma Treatment Details");
        setSize(1400, 500); // Wider size to accommodate all columns
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Extended table setup
        String[] columnNames = {
                "Panchakarma Name", "Advised", "No of Days", "Day",
                "Types of Karma & Medicines Used", "Price",
                "Duration Time", "Therapist Time", "Day & Date", "Note"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        panchakarmaTable = new JTable(tableModel);

        // Set preferred column width for "Note"
        panchakarmaTable.getColumnModel().getColumn(9).setPreferredWidth(300);

        add(new JScrollPane(panchakarmaTable), BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        addDayButton = new JButton("Add Day");
        saveButton = new JButton("Save");

        buttonPanel.add(addDayButton);
        buttonPanel.add(saveButton);
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        add(buttonPanel, BorderLayout.SOUTH);

        // Load existing Panchakarma data
        loadPanchakarmaData();

        // Button actions
        addDayButton.addActionListener(e -> addNewDayRow());
        saveButton.addActionListener(e -> savePanchakarmaData());

        setVisible(true);
    }

    private void addNewDayRow() {
        DefaultTableModel model = (DefaultTableModel) panchakarmaTable.getModel();
        model.addRow(new Object[] { "", "", "", "", "", "", "", "", "", "" });
    }

    private void loadPanchakarmaData() {
        // TODO: Load panchakarma data from database using patientId
        // Example (commented for now):
        /*
         * List<PanchakarmaRecord> records =
         * PanchakarmaDatabase.getRecordsByPatientId(patientId);
         * for (PanchakarmaRecord record : records) {
         * model.addRow(new Object[] {
         * record.getPanchakarmaName(), record.getAdvised(), record.getNoOfDays(),
         * record.getDay(), record.getTypesOfKarmaAndMedicines(), record.getPrice(),
         * record.getDuration(), record.getTherapistTime(), record.getDayAndDate(),
         * record.getNote()
         * });
         * }
         */
    }

    private void savePanchakarmaData() {
        DefaultTableModel model = (DefaultTableModel) panchakarmaTable.getModel();
        int rowCount = model.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            String pName = (String) model.getValueAt(i, 0);
            String advised = (String) model.getValueAt(i, 1);
            String noOfDays = (String) model.getValueAt(i, 2);
            String day = (String) model.getValueAt(i, 3);
            String karmaAndMeds = (String) model.getValueAt(i, 4);
            String price = (String) model.getValueAt(i, 5);
            String duration = (String) model.getValueAt(i, 6);
            String therapistTime = (String) model.getValueAt(i, 7);
            String dayAndDate = (String) model.getValueAt(i, 8);
            String note = (String) model.getValueAt(i, 9);

            // TODO: Save to database
            // PanchakarmaDatabase.saveRecord(patientId, pName, advised, noOfDays, day,
            // karmaAndMeds, price, duration, therapistTime, dayAndDate, note);
        }

        JOptionPane.showMessageDialog(this, "Panchakarma details saved successfully!");
    }

    // Main method for testing
    public static void main(String[] args) {
        new PanchakarmaUI(1);
    }
}
