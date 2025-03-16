
// final version
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class Dashboard {

    JFrame dashBoardFrame;
    JMenuBar menuBar;
    JPanel mainPanel;
    DefaultListModel<String> todoListModel;
    JList<String> todoList;

    JMenu view, patientsGraph, expenditure, assistance, standard_list, settings, help;
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
            case "Add Patient":
                System.out.println("Add Patient button clicked!");
                // Add patient logic here
                NewPatientEntryPage newPatientEntryPage = new NewPatientEntryPage();
                break;
            case "Search Patient":
                System.out.println("Search Patient button clicked!");
                // Search patient logic here
                PatientSearchUI patientSearchUI = new PatientSearchUI();
                break;
            case "View Follow Up":
                System.out.println("View Follow Up button clicked!");
                // View follow-up logic here
                break;
            case "Total Patient in Month":
                System.out.println("Total Patient in Month button clicked!");
                // Total patient count logic here
                break;
            case "Follow Up Patients":
                System.out.println("Follow Up Patients button clicked!");
                // Follow-up patients logic here
                break;
            default:
                throw new UnsupportedOperationException("Unknown button action: " + source.getText());
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
        assistance = new JMenu("Assistance");
        standard_list = new JMenu("Standard List");
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

        expenditure.add(expenditure_in_month);
        expenditure.add(expenditure_in_year);
        expenditure.add(expenditure_in_10_year);

        // Menu items for 'Settings'
        change_password = new JMenuItem("Change Password");
        export_data = new JMenuItem("Export Data");
        exit = new JMenuItem("Exit");
        exit.addActionListener(e -> dashBoardFrame.dispose());

        settings.add(change_password);
        settings.add(export_data);
        settings.add(exit);

        // Menu items for 'Help'
        about_us = new JMenuItem("About Us");
        contact_us = new JMenuItem("Contact Us");

        help.add(about_us);
        help.add(contact_us);

        // Add menus to the menu bar
        menuBar.add(view);
        menuBar.add(patientsGraph); // Merged menu
        menuBar.add(expenditure);
        menuBar.add(assistance);
        menuBar.add(standard_list);
        menuBar.add(settings);
        menuBar.add(help);
    }

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
            case "Total Patient in Month":
                // For this type, fetch the total number of patients for each day of the month
                // Sample data for demonstration purposes:
                // For March 1st, 2nd, 3rd, 4th, and 5th, there were 5, 8, 3, 10, and 6
                // patients, respectively
                data.put("01-Mar", 5);
                data.put("02-Mar", 8);
                data.put("03-Mar", 3);
                data.put("04-Mar", 10);
                data.put("05-Mar", 6);
                break;

            case "Total Patient in Year":
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
                break;

            case "Total Patient in 10 Year":
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
                break;

            case "No of Follow Up Patient":
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
                break;

            case "No of Follow Up in Specific Period":
                // For this type, fetch the number of follow-up patients for a specific period
                // Sample data for demonstration purposes:
                // For March 1st, 2nd, 3rd, 4th, and 5th, there were 5, 8, 3, 10, and 6
                // follow-up patients, respectively
                data.put("01-Mar", 5);
                data.put("02-Mar", 8);
                data.put("03-Mar", 3);
                data.put("04-Mar", 10);
                data.put("05-Mar", 6);
                break;

            case "New Patient in Month":
                // For this type, fetch the number of new patients for each day of the month
                // Sample data for demonstration purposes:
                // For March 1st, 2nd, 3rd, 4th, and 5th, there were 5, 8, 3, 10, and 6 new
                // patients, respectively
                data.put("01-Mar", 5);
                data.put("02-Mar", 8);
                data.put("03-Mar", 3);
                data.put("04-Mar", 10);
                data.put("05-Mar", 6);
                break;

            case "New Patient in Year":
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
                break;

            case "New Patient in 10 Year":
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
                break;

            default:
                // Handle other cases if needed
                break;
        }
        return data;
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

    public static void main(String[] args) {
        new Dashboard();
    }
}
