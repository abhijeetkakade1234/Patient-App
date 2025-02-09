import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dashboard {

    JFrame dashBoardFrame;
    JMenuBar menuBar;
    JPanel mainPanel;
    DefaultListModel<String> todoListModel;
    JList<String> todoList;

    JMenu view, graph, patients, expenditure, assistance, standard_list, settings, help;
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
                break;
            case "Search Patient":
                System.out.println("Search Patient button clicked!");
                // Search patient logic here
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
        graph = new JMenu("Graph");
        patients = new JMenu("Patients");
        expenditure = new JMenu("Expenditure");
        assistance = new JMenu("Assistance");
        standard_list = new JMenu("Standard List");
        settings = new JMenu("Settings");
        help = new JMenu("Help");

        // Menu items for 'View' (removed frequent actions)
        todays_patient = new JMenuItem("Today's Patient");
        patient_list_on_a_day = new JMenuItem("Patient List on a Day");
        patient_in_this_month = new JMenuItem("Patients in This Month");
        panchakarma_list_on_a_day = new JMenuItem("Panchakarma List on a Day");
        panchakarma_in_this_month = new JMenuItem("Panchakarma in This Month");

        // Add items to 'View' menu
        view.add(todays_patient);
        view.add(patient_list_on_a_day);
        view.add(patient_in_this_month);
        view.add(panchakarma_list_on_a_day);
        view.add(panchakarma_in_this_month);

        // Menu items for 'Patients' (removed Add and Search Patient)
        total_patient_in_month = new JMenuItem("Total Patient in Month");
        total_patient_in_year = new JMenuItem("Total Patient in Year");
        total_patient_in_10_year = new JMenuItem("Total Patient in 10 Year");
        no_of_follow_up_patient = new JMenuItem("No of Follow Up Patient");
        no_of_follow_up_in_specific_period = new JMenuItem("No of Follow Up in Specific Period");
        new_patient_in_month = new JMenuItem("New Patient in Month");
        new_patient_in_year = new JMenuItem("New Patient in Year");
        new_patient_in_10_year = new JMenuItem("New Patient in 10 Year");

        // Add items to 'Patients' menu
        patients.add(total_patient_in_month);
        patients.add(total_patient_in_year);
        patients.add(total_patient_in_10_year);
        patients.add(no_of_follow_up_patient);
        patients.add(no_of_follow_up_in_specific_period);
        patients.add(new_patient_in_month);
        patients.add(new_patient_in_year);
        patients.add(new_patient_in_10_year);

        // Menu items for 'Expenditure'
        expenditure_in_month = new JMenuItem("Expenditure in Month");
        expenditure_in_year = new JMenuItem("Expenditure in Year");
        expenditure_in_10_year = new JMenuItem("Expenditure in 10 Year");

        // Add items to 'Expenditure' menu
        expenditure.add(expenditure_in_month);
        expenditure.add(expenditure_in_year);
        expenditure.add(expenditure_in_10_year);

        // Menu items for 'Settings'
        change_password = new JMenuItem("Change Password");
        export_data = new JMenuItem("Export Data");
        exit = new JMenuItem("Exit");
        exit.addActionListener(e -> dashBoardFrame.dispose());

        // Add items to 'Settings' menu
        settings.add(change_password);
        settings.add(export_data);
        settings.add(exit);

        // Menu items for 'Help'
        about_us = new JMenuItem("About Us");
        contact_us = new JMenuItem("Contact Us");

        // Add items to 'Help' menu
        help.add(about_us);
        help.add(contact_us);

        // Add menus to the menu bar
        menuBar.add(view);
        menuBar.add(graph);
        menuBar.add(patients);
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

    public void setVisible(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVisible'");
    }
}
