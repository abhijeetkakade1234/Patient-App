package view.components;

import model.Patient;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Reusable table component for displaying patients
 * Features: Sortable columns, row selection, alternating colors, double-click action
 */
public class PatientTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Patient> patients;
    private Consumer<Patient> onDoubleClick;
    private Consumer<Patient> onSelect;
    
    private static final String[] COLUMN_NAMES = {
        "ID", "Name", "Age", "Sex", "Phone", "Disease", "Reg. Date", "Follow-up"
    };
    
    private static final Color HEADER_BG = new Color(0, 102, 204);
    private static final Color HEADER_FG = Color.WHITE;
    private static final Color ALT_ROW_COLOR = new Color(245, 245, 245);
    private static final Color SELECTED_COLOR = new Color(184, 207, 229);

    public PatientTablePanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create table model (non-editable)
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0 || column == 2) return Integer.class;
                return String.class;
            }
        };
        
        // Create table
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setAutoCreateRowSorter(true);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        
        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(HEADER_BG);
        header.setForeground(HEADER_FG);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 35));
        header.setReorderingAllowed(false);
        
        // Column widths
        setColumnWidths();
        
        // Custom renderer for alternating rows
        table.setDefaultRenderer(Object.class, new AlternatingRowRenderer());
        table.setDefaultRenderer(Integer.class, new AlternatingRowRenderer());
        
        // Double-click listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && onDoubleClick != null) {
                    Patient selected = getSelectedPatient();
                    if (selected != null) {
                        onDoubleClick.accept(selected);
                    }
                }
            }
        });
        
        // Selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && onSelect != null) {
                Patient selected = getSelectedPatient();
                if (selected != null) {
                    onSelect.accept(selected);
                }
            }
        });
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);   // ID
        columnModel.getColumn(1).setPreferredWidth(150);  // Name
        columnModel.getColumn(2).setPreferredWidth(50);   // Age
        columnModel.getColumn(3).setPreferredWidth(60);   // Sex
        columnModel.getColumn(4).setPreferredWidth(100);  // Phone
        columnModel.getColumn(5).setPreferredWidth(200);  // Disease
        columnModel.getColumn(6).setPreferredWidth(100);  // Reg. Date
        columnModel.getColumn(7).setPreferredWidth(100);  // Follow-up
    }

    /**
     * Set patients to display in table
     * @param patients List of patients
     */
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
        refreshTable();
    }

    /**
     * Refresh table with current data
     */
    public void refreshTable() {
        tableModel.setRowCount(0);
        
        if (patients == null || patients.isEmpty()) {
            return;
        }
        
        for (Patient patient : patients) {
            Object[] row = {
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getSex(),
                patient.getPhone(),
                truncate(patient.getDisease(), 50),
                patient.getRegistrationDate() != null ? patient.getRegistrationDate().toString() : "",
                patient.getFollowUpDate() != null ? patient.getFollowUpDate().toString() : ""
            };
            tableModel.addRow(row);
        }
    }

    /**
     * Get currently selected patient
     * @return Selected patient or null
     */
    public Patient getSelectedPatient() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0 || patients == null) {
            return null;
        }
        
        // Convert view row to model row (for sorted tables)
        int modelRow = table.convertRowIndexToModel(viewRow);
        if (modelRow >= 0 && modelRow < patients.size()) {
            int patientId = (int) tableModel.getValueAt(modelRow, 0);
            return patients.stream()
                    .filter(p -> p.getId() == patientId)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Set double-click action handler
     * @param handler Consumer that receives the clicked patient
     */
    public void setOnDoubleClick(Consumer<Patient> handler) {
        this.onDoubleClick = handler;
    }

    /**
     * Set selection change handler
     * @param handler Consumer that receives the selected patient
     */
    public void setOnSelect(Consumer<Patient> handler) {
        this.onSelect = handler;
    }

    /**
     * Get the underlying JTable
     * @return JTable instance
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Get current patient count
     * @return Number of patients displayed
     */
    public int getPatientCount() {
        return patients != null ? patients.size() : 0;
    }

    /**
     * Clear the table
     */
    public void clear() {
        this.patients = null;
        tableModel.setRowCount(0);
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }

    /**
     * Custom renderer for alternating row colors
     */
    private class AlternatingRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component c = super.getTableCellRendererComponent(table, value, 
                    isSelected, hasFocus, row, column);
            
            if (isSelected) {
                c.setBackground(SELECTED_COLOR);
                c.setForeground(Color.BLACK);
            } else {
                c.setBackground(row % 2 == 0 ? Color.WHITE : ALT_ROW_COLOR);
                c.setForeground(Color.BLACK);
            }
            
            // Center align ID and Age columns
            if (column == 0 || column == 2) {
                setHorizontalAlignment(SwingConstants.CENTER);
            } else {
                setHorizontalAlignment(SwingConstants.LEFT);
            }
            
            setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            
            return c;
        }
    }
}

