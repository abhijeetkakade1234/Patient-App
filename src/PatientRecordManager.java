import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// To Display Patient Details with Follow-Ups and Panchakarma Treatment
public class PatientRecordManager {

    // --- Add Follow-Up Function --- ############
    public static void addFollowUp(JFrame parent, int patientId, DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(parent, "Add Follow-Up for Patient ID: " + patientId, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final int[] row = { 0 }; // Use array for mutability in lambda

        // Fields
        JTextField visitNo = new JTextField(10);
        JTextField date = new JTextField("2025-07-25", 10);
        JTextField height = new JTextField(10);
        JTextField weight = new JTextField(10);
        JTextField bmi = new JTextField(10);
        JTextField bp = new JTextField(10);
        JTextField spo2 = new JTextField(10);
        JTextField nextFollow = new JTextField(10);
        JTextField nadi = new JTextField(10);
        JTextField samanyaLakshana = new JTextField(20);
        JTextField rxTreatment = new JTextField(20);
        JTextField days = new JTextField(10);
        JTextField totalPayment = new JTextField(10);
        JTextField pendingPayment = new JTextField(10);
        JTextArea notes = new JTextArea(5, 30);
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        JScrollPane notesScroll = new JScrollPane(notes);

        // Add Row Helper
        BiConsumer<String, JTextField> addRow = (label, field) -> {
            gbc.gridx = 0;
            gbc.gridy = row[0];
            gbc.gridwidth = 1;
            mainPanel.add(new JLabel(label), gbc);
            gbc.gridx = 1;
            gbc.gridwidth = 3;
            mainPanel.add(field, gbc);
            row[0]++;
        };

        // Adding all fields
        addRow.accept("Visit No:", visitNo);
        addRow.accept("Date (YYYY-MM-DD):", date);
        addRow.accept("Height (cm):", height);
        addRow.accept("Weight (kg):", weight);
        addRow.accept("BMI:", bmi);
        addRow.accept("Blood Pressure:", bp);
        addRow.accept("SPO2 (%):", spo2);
        addRow.accept("Next Follow-up Date:", nextFollow);
        addRow.accept("Nadi:", nadi);
        addRow.accept("Samanya Lakshana:", samanyaLakshana);
        addRow.accept("Rx Treatment:", rxTreatment);
        addRow.accept("Days:", days);
        addRow.accept("Total Payment:", totalPayment);
        addRow.accept("Pending Payment:", pendingPayment);

        // Notes area
        gbc.gridx = 0;
        gbc.gridy = row[0];
        gbc.gridwidth = 4;
        mainPanel.add(new JLabel("Notes / Remark:"), gbc);
        row[0]++;
        gbc.gridy = row[0];
        mainPanel.add(notesScroll, gbc);
        row[0]++;

        // History Button
        JButton historyBtn = new JButton("Add History");
        historyBtn.addActionListener(e -> {
            JTextField kco = new JTextField();
            JTextField ho = new JTextField();
            JTextField sho = new JTextField();
            JTextField mh = new JTextField();
            JTextField oh = new JTextField();
            JTextField ah = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
            panel.add(new JLabel("K/C/O:"));
            panel.add(kco);
            panel.add(new JLabel("H/O:"));
            panel.add(ho);
            panel.add(new JLabel("S/H/O:"));
            panel.add(sho);
            panel.add(new JLabel("M/H:"));
            panel.add(mh);
            panel.add(new JLabel("O/H:"));
            panel.add(oh);
            panel.add(new JLabel("A/H:"));
            panel.add(ah);

            JOptionPane.showMessageDialog(dialog, panel, "Patient History", JOptionPane.PLAIN_MESSAGE);
        });

        gbc.gridx = 0;
        gbc.gridy = row[0];
        gbc.gridwidth = 2;
        mainPanel.add(historyBtn, gbc);

        // Collapsible Samanya Parikshan
        JButton toggleSamanyaBtn = new JButton("Show Samanya Parikshan");
        JPanel samanyaPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        samanyaPanel.setVisible(false);

        String[] labels = { "Mal", "Mutra", "Jivha", "Shudha", "Teushna", "Nidra", "Sweda", "Sparsha", "Druka",
                "Shabda", "Akruti" };
        Map<String, JTextField> samanyaFields = new HashMap<>();

        for (String label : labels) {
            JTextField field = new JTextField();
            samanyaFields.put(label, field);
            samanyaPanel.add(new JLabel(label + ":"));
            samanyaPanel.add(field);
        }

        toggleSamanyaBtn.addActionListener(e -> {
            boolean visible = !samanyaPanel.isVisible();
            samanyaPanel.setVisible(visible);
            toggleSamanyaBtn.setText(visible ? "Hide Samanya Parikshan" : "Show Samanya Parikshan");
            dialog.revalidate();
            dialog.repaint();
        });

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        mainPanel.add(toggleSamanyaBtn, gbc);
        row[0]++;

        gbc.gridx = 0;
        gbc.gridy = row[0];
        gbc.gridwidth = 4;
        mainPanel.add(samanyaPanel, gbc);
        row[0]++;

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Save");
        JButton clear = new JButton("Clear All");
        JButton cancel = new JButton("Cancel");
        buttonPanel.add(save);
        buttonPanel.add(clear);
        buttonPanel.add(cancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Action Listeners
        save.addActionListener(e -> {
            tableModel.addRow(new Object[] {
                    visitNo.getText(), date.getText(), nadi.getText(),
                    samanyaLakshana.getText(), rxTreatment.getText(), notes.getText()
            });
            JOptionPane.showMessageDialog(dialog, "Follow-up added!");
            dialog.dispose();
        });

        clear.addActionListener(e -> {
            for (Component c : mainPanel.getComponents()) {
                if (c instanceof JTextField)
                    ((JTextField) c).setText("");
            }
            for (JTextField f : samanyaFields.values())
                f.setText("");
            notes.setText("");
        });

        cancel.addActionListener(e -> dialog.dispose());

        // Final View
        dialog.setSize(900, 700); // Set a fixed size instead of full screen
        dialog.setLocationRelativeTo(parent); // Center the dialog relative to parent
        dialog.setVisible(true);
    }

    // --- Update Follow-Up Function --- ############
    public static void updateFollowUp(JFrame parent, int patientId, int followUpId, DefaultTableModel model) {
        // Show dialog prefilled with selected data (fetch from DB later)
        // Update DB record and model.setValueAt(...) to update table UI
    }
    /*
     * public static void updateFollowUp(JFrame parent, int patientId, int
     * followUpId, DefaultTableModel model) {
     * Connection conn = null;
     * PreparedStatement stmt = null;
     * ResultSet rs = null;
     * 
     * try {
     * conn = DBUtil.getConnection();
     * 
     * // 1. Fetch current follow-up data from DB
     * String fetchSql =
     * "SELECT follow_up_date, notes FROM follow_ups WHERE fu_id = ?";
     * stmt = conn.prepareStatement(fetchSql);
     * stmt.setInt(1, followUpId);
     * rs = stmt.executeQuery();
     * 
     * if (!rs.next()) {
     * JOptionPane.showMessageDialog(parent, "Unable to fetch follow-up data.");
     * return;
     * }
     * 
     * String currentDate = rs.getString("follow_up_date");
     * String currentNotes = rs.getString("notes");
     * 
     * // 2. Show input dialog prefilled with current values
     * JTextField dateField = new JTextField(currentDate);
     * JTextArea notesArea = new JTextArea(currentNotes, 5, 20);
     * JScrollPane notesScroll = new JScrollPane(notesArea);
     * 
     * JPanel panel = new JPanel(new BorderLayout());
     * panel.add(new JLabel("Follow-up Date (YYYY-MM-DD):"), BorderLayout.NORTH);
     * panel.add(dateField, BorderLayout.CENTER);
     * panel.add(notesScroll, BorderLayout.SOUTH);
     * 
     * int result = JOptionPane.showConfirmDialog(parent, panel, "Update Follow-Up",
     * JOptionPane.OK_CANCEL_OPTION);
     * 
     * if (result == JOptionPane.OK_OPTION) {
     * String newDate = dateField.getText().trim();
     * String newNotes = notesArea.getText().trim();
     * 
     * // 3. Update database
     * String updateSql =
     * "UPDATE follow_ups SET follow_up_date = ?, notes = ? WHERE fu_id = ?";
     * stmt = conn.prepareStatement(updateSql);
     * stmt.setString(1, newDate);
     * stmt.setString(2, newNotes);
     * stmt.setInt(3, followUpId);
     * int rowsAffected = stmt.executeUpdate();
     * 
     * if (rowsAffected > 0) {
     * JOptionPane.showMessageDialog(parent, "Follow-up updated successfully.");
     * 
     * // 4. Update UI table directly (optional)
     * int rowCount = model.getRowCount();
     * for (int i = 0; i < rowCount; i++) {
     * int idInTable = (int) model.getValueAt(i, 0); // assuming ID column
     * if (idInTable == followUpId) {
     * model.setValueAt(newDate, i, 1); // date column
     * model.setValueAt(newNotes, i, 2); // notes column
     * break;
     * }
     * }
     * } else {
     * JOptionPane.showMessageDialog(parent, "Update failed.");
     * }
     * }
     * 
     * } catch (Exception e) {
     * JOptionPane.showMessageDialog(parent, "Error: " + e.getMessage());
     * e.printStackTrace();
     * } finally {
     * DBUtil.closeQuietly(rs);
     * DBUtil.closeQuietly(stmt);
     * DBUtil.closeQuietly(conn);
     * }
     * }
     * 
     */

    // --- Delete Follow-Up Function --- ############
    // public static void deleteFollowUp(int followUpId, DefaultTableModel model,
    // int rowIndex) {
    // // Delete from DB and model.removeRow(rowIndex);
    // }
    public static void deleteFollowUp(int followUpId, DefaultTableModel model, int rowIndex) {
        Connection conn = null;
        PreparedStatement stmt = null;

        // try {
        // conn = DBUtil.getConnection();

        // String sql = "DELETE FROM follow_ups WHERE fu_id = ?";
        // stmt = conn.prepareStatement(sql);
        // stmt.setInt(1, followUpId);
        // int rowsDeleted = stmt.executeUpdate();

        // if (rowsDeleted > 0) {
        // model.removeRow(rowIndex); // delete from table UI
        // JOptionPane.showMessageDialog(null, "Follow-up deleted successfully.");
        // } else {
        // JOptionPane.showMessageDialog(null, "Failed to delete follow-up.");
        // }

        // } catch (Exception e) {
        // JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        // e.printStackTrace();
        // } finally {
        // DBUtil.closeQuietly(stmt);
        // DBUtil.closeQuietly(conn);
        // }
        return;
    }

}
