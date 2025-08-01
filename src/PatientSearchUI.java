import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientSearchUI extends JFrame {
    private final JTextField txtName, txtPhone;
    private final JButton btnSearch;
    private final JTable tblPatients;
    private final DefaultTableModel model;

    public PatientSearchUI() {
        setTitle("Search Patient");
        setSize(600, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full-screen mode
        setLayout(new BorderLayout());
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Search using DOB TOOO##################################
        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        txtName = new JTextField(15);
        txtPhone = new JTextField(15);
        btnSearch = new JButton("Search");
        searchPanel.add(new JLabel("Name:"));
        searchPanel.add(txtName);
        searchPanel.add(new JLabel("Phone:"));
        searchPanel.add(txtPhone);
        searchPanel.add(btnSearch);
        add(searchPanel, BorderLayout.NORTH);

        // Table to Display Results
        String[] columns = { "ID", "Name", "Phone", "Action" };
        model = new DefaultTableModel(columns, 0);
        tblPatients = new JTable(model);
        add(new JScrollPane(tblPatients), BorderLayout.CENTER);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPatients();
            }
        });
    }

    private void searchPatients() {
        // model.setRowCount(0);
        // String name = txtName.getText().trim();
        // String phone = txtPhone.getText().trim();

        // try (Connection conn =
        // DriverManager.getConnection("jdbc:sqlite:patients.db");
        // PreparedStatement stmt = conn
        // .prepareStatement("SELECT id, name, phone FROM patients WHERE name LIKE ? OR
        // phone LIKE ?")) {
        // stmt.setString(1, "%" + name + "%");
        // stmt.setString(2, "%" + phone + "%");
        // ResultSet rs = stmt.executeQuery();
        // while (rs.next()) {
        // int id = rs.getInt("id");
        // String patientName = rs.getString("name");
        // String patientPhone = rs.getString("phone");
        // JButton btnDetails = new JButton("View Details");
        // btnDetails.addActionListener(e -> openPatientDetails(id));
        // model.addRow(new Object[] { id, patientName, patientPhone, btnDetails });
        // }
        // } catch (SQLException ex) {
        // ex.printStackTrace();
        // }
    }

    private void openPatientDetails(int patientId) {
        // new PatientDetailsUI(patientId).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientSearchUI().setVisible(true));
    }
}
