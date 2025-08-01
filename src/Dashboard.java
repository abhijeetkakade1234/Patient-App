
// final version
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

// To manage the dashboard of the application
// This class sets up the main dashboard UI with a menu bar, a To-Do list
public class Dashboard {

    JFrame dashBoardFrame;
    JMenuBar menuBar;
    JPanel mainPanel;
    DefaultListModel<String> todoListModel;
    JList<String> todoList;

    JMenu view, patientsGraph, expenditure, settings, help;
    // Menu items for the menu bar
    JMenuItem todays_patient, patient_list_on_a_day, patient_in_this_month,
            panchakarma_list_on_a_day, panchakarma_in_this_month,
            total_patient_in_month, total_patient_in_year, total_patient_in_10_year,
            no_of_follow_up_patient, no_of_follow_up_in_specific_period,
            new_patient_in_month, new_patient_in_year, new_patient_in_10_year;
    JMenuItem expenditure_in_month, expenditure_in_year, expenditure_in_10_year;
    JMenuItem change_password, export_data, exit;
    JMenuItem about_us, contact_us;

    Dashboard() {
        dashBoardFrame = new JFrame("Dashboard");
        dashBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashBoardFrame.setResizable(false); // Prevent resizing
        dashBoardFrame.setSize(600, 500);
        dashBoardFrame.setLocationRelativeTo(null);
        display();
        dashBoardFrame.setVisible(true);
    }

    /**
     * Initializes and sets up the main display components of the Dashboard.
     * Configures the menu bar, left panel with a To-Do List, and the right panel
     * with quick action buttons. Arranges the layout using BorderLayout for
     * the main panel and GridBagLayout for the right panel to ensure proper
     * alignment and spacing of components.
     */
    public void display() {
        menuBar = new JMenuBar();
        mainPanel = new JPanel(new BorderLayout(10, 10)); // Added gaps between components
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Added padding

        // Left panel for To-Do List (unchanged)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createTitledBorder("To-Do List"));

        JPanel todoPanel = createTodoPanel();

        todoListModel = new DefaultListModel<>();
        todoList = new JList<>(todoListModel);
        JScrollPane todoScrollPane = new JScrollPane(todoList);
        leftPanel.add(todoPanel);
        leftPanel.add(todoScrollPane);

        // Improved right panel with GridBagLayout for better alignment
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Consistent spacing
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make buttons expand horizontally
        gbc.weightx = 1.0; // Allow buttons to take full width

        // Create buttons with consistent sizing
        JButton[] actionButtons = {
                new JButton("Add Patient"),
                new JButton("Search Patient"),
                new JButton("View Follow Up"),
                new JButton("Total Patient in Month"),
                new JButton("Follow Up Patients")
        };

        // Standardize button appearance
        for (JButton button : actionButtons) {
            button.setPreferredSize(new Dimension(200, 40)); // Consistent button size
            button.setFont(new Font("Arial", Font.BOLD, 14)); // Improved typography
        }

        // Method to handle button clicks
        for (JButton button : actionButtons) {
            button.addActionListener(e -> handleButtonClick(e));
        }

        // Add buttons to right panel with proper grid positioning
        gbc.gridx = 0;
        for (int i = 0; i < actionButtons.length; i++) {
            gbc.gridy = i;
            rightPanel.add(actionButtons[i], gbc);
        }

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        dashBoardFrame.add(mainPanel);

        // Setup the menu bar (unchanged)
        setupMenuBar(menuBar);
        dashBoardFrame.setJMenuBar(menuBar);
    }

    /**
     * Handles button click events.
     * 
     * @param e the ActionEvent triggered by the button click
     */
    private void handleButtonClick(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        switch (source.getText()) {
            case "Add Patient" -> {
                System.out.println("Add Patient button clicked!");
                // Add patient logic here
                NewPatientEntryPage newPatientEntryPage = new NewPatientEntryPage();
            }
            case "Search Patient" -> {
                System.out.println("Search Patient button clicked!");
                // Search patient logic here
                PatientSearchUI patientSearchUI = new PatientSearchUI();
                patientSearchUI.setVisible(true);
            }
            case "View Follow Up" -> System.out.println("View Follow Up button clicked!");
            // View follow-up logic here
            case "Total Patient in Month" -> System.out.println("Total Patient in Month button clicked!");
            // Total patient count logic here
            case "Follow Up Patients" -> System.out.println("Follow Up Patients button clicked!");
            // Follow-up patients logic here
            default -> throw new UnsupportedOperationException("Unknown button action: " + source.getText());
        }
    }

    /**
     * Sets up the menu bar with the main menus: View, Graph, Patients, Expenditure,
     * Assistance, Standard List, Settings, and Help.
     * Each menu contains various menu items relevant to the menu's category.
     * 
     * @param menuBar the JMenuBar to add the menus to
     */
    private void setupMenuBar(JMenuBar menuBar) {
        // Create menus
        view = new JMenu("View");
        patientsGraph = new JMenu("Patients & Graph"); // Merged menu
        expenditure = new JMenu("Expenditure");
        settings = new JMenu("Settings");
        help = new JMenu("Help");

        // Menu items for 'View'
        todays_patient = new JMenuItem("Today's Patient");
        patient_list_on_a_day = new JMenuItem("Patient List on a Day");
        patient_in_this_month = new JMenuItem("Patients in This Month");
        panchakarma_list_on_a_day = new JMenuItem("Panchakarma List on a Day");
        panchakarma_in_this_month = new JMenuItem("Panchakarma in This Month");

        view.add(todays_patient);
        view.add(patient_list_on_a_day);
        view.add(patient_in_this_month);
        view.add(panchakarma_list_on_a_day);
        view.add(panchakarma_in_this_month);

        // action listeners for menu items in 'View'
        todays_patient.addActionListener(e -> viewTodaysPatient());
        patient_list_on_a_day.addActionListener(e -> viewPatientListOnADay());
        patient_in_this_month.addActionListener(e -> viewPatientInThisMonth());
        panchakarma_list_on_a_day.addActionListener(e -> viewPanchakarmaListOnADay());
        panchakarma_in_this_month.addActionListener(e -> viewPanchakarmaInThisMonth());

        // Menu items for 'Patients & Graph'
        total_patient_in_month = new JMenuItem("Total Patient in Month");
        total_patient_in_year = new JMenuItem("Total Patient in Year");
        total_patient_in_10_year = new JMenuItem("Total Patient in 10 Year");
        no_of_follow_up_patient = new JMenuItem("No of Follow Up Patient");
        no_of_follow_up_in_specific_period = new JMenuItem("No of Follow Up in Specific Period");
        new_patient_in_month = new JMenuItem("New Patient in Month");
        new_patient_in_year = new JMenuItem("New Patient in Year");
        new_patient_in_10_year = new JMenuItem("New Patient in 10 Year");

        // action listeners for menu items in 'Patients & Graph'
        total_patient_in_month.addActionListener(e -> showGraphFor("Total Patient in Month"));
        total_patient_in_year.addActionListener(e -> showGraphFor("Total Patient in Year"));
        total_patient_in_10_year.addActionListener(e -> showGraphFor("Total Patient in 10 Year"));
        no_of_follow_up_patient.addActionListener(e -> showGraphFor("No of Follow Up Patient"));
        no_of_follow_up_in_specific_period.addActionListener(e -> showGraphFor("No of Follow Up in Specific Period"));
        new_patient_in_month.addActionListener(e -> showGraphFor("New Patient in Month"));
        new_patient_in_year.addActionListener(e -> showGraphFor("New Patient in Year"));
        new_patient_in_10_year.addActionListener(e -> showGraphFor("New Patient in 10 Year"));

        // Add items to merged menu
        patientsGraph.add(total_patient_in_month);
        patientsGraph.add(total_patient_in_year);
        patientsGraph.add(total_patient_in_10_year);
        patientsGraph.add(no_of_follow_up_patient);
        patientsGraph.add(no_of_follow_up_in_specific_period);
        patientsGraph.add(new_patient_in_month);
        patientsGraph.add(new_patient_in_year);
        patientsGraph.add(new_patient_in_10_year);

        // Menu items for 'Expenditure'
        expenditure_in_month = new JMenuItem("Expenditure in Month");
        expenditure_in_year = new JMenuItem("Expenditure in Year");
        expenditure_in_10_year = new JMenuItem("Expenditure in 10 Year");

        // Add items to 'Expenditure' menu
        expenditure.add(expenditure_in_month);
        expenditure.add(expenditure_in_year);
        expenditure.add(expenditure_in_10_year);

        // action listeners for expenditure menu items
        expenditure_in_month.addActionListener(e -> showExpenditureAmount("month"));
        expenditure_in_year.addActionListener(e -> showExpenditureAmount("year"));
        expenditure_in_10_year.addActionListener(e -> showExpenditureAmount("10_year"));

        // Menu items for 'Settings'
        change_password = new JMenuItem("Change Password");
        export_data = new JMenuItem("Export Data");
        exit = new JMenuItem("Exit");

        // Add items to 'Settings' menu
        settings.add(change_password);
        settings.add(export_data);
        settings.add(exit);

        // action listeners for menu items in 'Settings'
        exit.addActionListener(e -> dashBoardFrame.dispose());
        change_password.addActionListener(e -> {
            // Logic to change password
            System.out.println("Change Password clicked");
            // This could open a new UI for changing password
        });
        export_data.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(null);

            if (option == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                // Add .csv extension if missing
                if (!filePath.toLowerCase().endsWith(".csv")) {
                    filePath += ".csv";
                }

                try {
                    exportTableToCSV(filePath);
                    JOptionPane.showMessageDialog(null, "Data exported successfully to:\n" + filePath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error exporting data:\n" + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Menu items for 'Help'
        about_us = new JMenuItem("About Us");
        contact_us = new JMenuItem("Contact Us");

        // Add items to 'Help' menu
        help.add(about_us);
        help.add(contact_us);

        // action listeners for menu items in 'Help'
        about_us.addActionListener(e -> {
            // Logic to show about us information
            JOptionPane.showMessageDialog(dashBoardFrame, "About Us: This is a healthcare management system.",
                    "About Us", JOptionPane.INFORMATION_MESSAGE);
        });
        contact_us.addActionListener(e -> {
            // Logic to show contact information
            JOptionPane.showMessageDialog(dashBoardFrame, "Contact Us: You can reach us at 123-456-7890.",
                    "Contact Us", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add menus to the menu bar
        menuBar.add(view);
        menuBar.add(patientsGraph); // Merged menu
        menuBar.add(expenditure);
        menuBar.add(settings);
        menuBar.add(help);
    }

    // Method to export table data to CSV
    private void exportTableToCSV(String filePath) throws Exception {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "your_user";
        String pass = "your_password";

        String query = "SELECT * FROM your_table_name";

        // try (
        // Connection conn = DriverManager.getConnection(url, user, pass);
        // Statement stmt = conn.createStatement();
        // ResultSet rs = stmt.executeQuery(query);
        // FileWriter fw = new FileWriter(filePath)) {
        // ResultSetMetaData meta = rs.getMetaData();
        // int colCount = meta.getColumnCount();

        // // Write header
        // for (int i = 1; i <= colCount; i++) {
        // fw.append(meta.getColumnName(i));
        // if (i < colCount)
        // fw.append(',');
        // }
        // fw.append('\n');

        // // Write data
        // while (rs.next()) {
        // for (int i = 1; i <= colCount; i++) {
        // String data = rs.getString(i);
        // if (data != null) {
        // data = data.replaceAll(",", " "); // Avoid breaking CSV
        // }
        // fw.append(data);
        // if (i < colCount)
        // fw.append(',');
        // }
        // fw.append('\n');
        // }

        // fw.flush();
        // }
    }
    // end of exportTableToCSV method

    // Method to create the To-Do List panel with buttons and input field
    private JPanel createTodoPanel() {
        JPanel todoPanel = new JPanel();
        todoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JTextField todoInput = new JTextField(12); // Adjusted size
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String task = todoInput.getText().trim();
                if (!task.isEmpty()) {
                    addTodoToList(task); // Method to add task to list and DB
                    todoInput.setText(""); // Clear the input field after adding
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = todoList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String taskToRemove = todoListModel.getElementAt(selectedIndex);
                    removeTodoFromList(taskToRemove); // Method to remove task from list and DB
                    todoListModel.remove(selectedIndex);
                }
            }
        });

        todoPanel.add(todoInput);
        todoPanel.add(addButton);
        todoPanel.add(removeButton);

        return todoPanel;
    }

    // Method to add a To-Do item to the list and database
    private void addTodoToList(String task) {
        // Add the task to the model (JList)
        todoListModel.addElement(task);

        // Here, you would connect to the database and insert the task
        // Example:
        // dbConnection.executeUpdate("INSERT INTO todo_list (task) VALUES ('" + task +
        // "')");
        System.out.println("Added to database: " + task); // Replace with actual DB logic
    }

    // Method to remove a To-Do item from the list and database
    private void removeTodoFromList(String task) {
        // Here, you would connect to the database and remove the task
        // Example:
        // dbConnection.executeUpdate("DELETE FROM todo_list WHERE task = '" + task +
        // "'");
        System.out.println("Removed from database: " + task); // Replace with actual DB logic
    }

    // Method to show the graph based on the selected type
    // This method takes a String parameter representing the type of
    // patient data to show in the graph, fetches or generates the
    // corresponding patient data dynamically, and then calls the
    // showGraph method from the PatientGraph class to display the graph.
    private void showGraphFor(String type) {
        // Fetch or generate patient data dynamically
        // This method is a placeholder for actual database logic,
        // and is used to generate sample data for demonstration purposes
        Map<String, Integer> data = fetchPatientData(type);
        // Call the showGraph method from the PatientGraph class to display the graph
        // The showGraph method takes two parameters: the title of the graph
        // and the data to be displayed in the graph
        PatientGraph.showGraph(type, data);
    }

    // Method to fetch patient data based on the selected type
    // This method is a placeholder for actual database logic,
    // and is used to generate sample data for demonstration purposes
    private Map<String, Integer> fetchPatientData(String type) {
        Map<String, Integer> data = new HashMap<>();

        // Switch on the type of data to fetch
        switch (type) {
            case "Total Patient in Month" -> {
                // For this type, fetch the total number of patients for each day of the month
                // Sample data for demonstration purposes:
                // For March 1st, 2nd, 3rd, 4th, and 5th, there were 5, 8, 3, 10, and 6
                // patients, respectively
                data.put("01-Mar", 5);
                data.put("02-Mar", 8);
                data.put("03-Mar", 3);
                data.put("04-Mar", 10);
                data.put("05-Mar", 6);
            }

            case "Total Patient in Year" -> {
                // For this type, fetch the total number of patients for each month of the year
                // Sample data for demonstration purposes:
                // For January, February, March, April, and May, there were 50, 60, 40, 70, and
                // 80 patients, respectively
                data.put("Jan", 50);
                data.put("Feb", 60);
                data.put("Mar", 40);
                data.put("Apr", 70);
                data.put("May", 80);
                // Add more cases as needed
            }

            case "Total Patient in 10 Year" -> {
                // For this type, fetch the total number of patients for each year of the last
                // 10 years
                // Sample data for demonstration purposes:
                // For 2010, 2011, 2012, 2013, and 2014, there were 50, 60, 40, 70, and 80
                // patients, respectively
                data.put("2010", 50);
                data.put("2011", 60);
                data.put("2012", 40);
                data.put("2013", 70);
                data.put("2014", 80);
                // Add more cases as needed
            }

            case "No of Follow Up Patient" -> {
                // For this type, fetch the number of follow-up patients for each day of the
                // month
                // Sample data for demonstration purposes:
                // For March 1st, 2nd, 3rd, 4th, and 5th, there were 5, 8, 3, 10, and 6
                // follow-up patients, respectively
                data.put("01-Mar", 5);
                data.put("02-Mar", 8);
                data.put("03-Mar", 3);
                data.put("04-Mar", 10);
                data.put("05-Mar", 6);
            }

            case "No of Follow Up in Specific Period" -> {
                // For this type, fetch the number of follow-up patients for a specific period
                // Sample data for demonstration purposes:
                // For March 1st, 2nd, 3rd, 4th, and 5th, there were 5, 8, 3, 10, and 6
                // follow-up patients, respectively
                data.put("01-Mar", 5);
                data.put("02-Mar", 8);
                data.put("03-Mar", 3);
                data.put("04-Mar", 10);
                data.put("05-Mar", 6);
            }

            case "New Patient in Month" -> {
                // For this type, fetch the number of new patients for each day of the month
                // Sample data for demonstration purposes:
                // For March 1st, 2nd, 3rd, 4th, and 5th, there were 5, 8, 3, 10, and 6 new
                // patients, respectively
                data.put("01-Mar", 5);
                data.put("02-Mar", 8);
                data.put("03-Mar", 3);
                data.put("04-Mar", 10);
                data.put("05-Mar", 6);
            }

            case "New Patient in Year" -> {
                // For this type, fetch the number of new patients for each month of the year
                // Sample data for demonstration purposes:
                // For January, February, March, April, and May, there were 50, 60, 40, 70, and
                // 80 new patients, respectively
                data.put("Jan", 50);
                data.put("Feb", 60);
                data.put("Mar", 40);
                data.put("Apr", 70);
                data.put("May", 80);
                // Add more cases as needed
            }

            case "New Patient in 10 Year" -> {
                // For this type, fetch the number of new patients for each year of the last 10
                // years
                // Sample data for demonstration purposes:
                // For 2010, 2011, 2012, 2013, and 2014, there were 50, 60, 40, 70, and 80 new
                // patients, respectively
                data.put("2010", 50);
                data.put("2011", 60);
                data.put("2012", 40);
                data.put("2013", 70);
                data.put("2014", 80);
                // Add more cases as needed
            }

            default -> {
            }
        }
        // Handle other cases if needed
        return data;
    }

    // ##################### Method to view patients **************************
    private void viewTodaysPatient() {
        // Logic to view today's patients
        System.out.println("Viewing today's patients...");
        // This could open a new UI or display a dialog with today's patient list
    }

    private void viewPatientListOnADay() {
        // Logic to view patient list on a specific day
        System.out.println("Viewing patient list on a specific day...");
        // This could open a new UI or display a dialog with the patient list for that
        // day
    }

    private void viewPatientInThisMonth() {
        // Logic to view patients in this month
        System.out.println("Viewing patients in this month...");
        // This could open a new UI or display a dialog with the patient list for this
        // month
    }

    private void viewPanchakarmaListOnADay() {
        // Logic to view Panchakarma list on a specific day
        System.out.println("Viewing Panchakarma list on a specific day...");
        // This could open a new UI or display a dialog with the Panchakarma list for
        // that day
    }

    private void viewPanchakarmaInThisMonth() {
        // Logic to view Panchakarma in this month
        System.out.println("Viewing Panchakarma in this month...");
        // This could open a new UI or display a dialog with the Panchakarma list for
        // this month
    }
    // ##################### End of Method to view patients ******************

    // ##################### Method to show expenditure graph ******************
    private void showExpenditureAmount(String type) {
        int amount = fetchExpenditureAmount(type);
        JOptionPane.showMessageDialog(null,
                "Total expenditure in " + formatLabel(type) + ": ₹" + amount,
                "Expenditure Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private int fetchExpenditureAmount(String type) {
        return switch (type) {
            case "month" -> 5400;
            case "year" -> 65000;
            case "10_year" -> 420000;
            default -> 0;
        };
    }

    // Helper method to format the label for expenditure type
    private String formatLabel(String type) {
        return switch (type) {
            case "month" -> "Month";
            case "year" -> "Year";
            case "10_year" -> "10 Years";
            default -> type;
        };
    }
    // ##################### End of Method to show expenditure graph *************

    public static void main(String[] args) {
        new Dashboard();
    }
}
