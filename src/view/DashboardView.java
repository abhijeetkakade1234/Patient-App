package view;

import controller.AuthController;
import controller.PatientController;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Refactored Dashboard UI with proper separation of concerns
 * This is a simplified version showing the architecture
 */
public class DashboardView extends JFrame {
    private final AuthController authController;
    private final PatientController patientController;
    
    private JMenuBar menuBar;
    private JPanel mainPanel;

    public DashboardView() {
        this.authController = new AuthController();
        this.patientController = new PatientController();
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        setTitle("Patient Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Left panel - Quick Actions
        JPanel leftPanel = createQuickActionsPanel();
        
        // Center panel - Dashboard content
        JPanel centerPanel = createDashboardContent();

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Setup menu bar
        setupMenuBar();
        setJMenuBar(menuBar);
    }

    private JPanel createQuickActionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Quick Actions"));
        panel.setPreferredSize(new Dimension(200, 0));

        JButton addPatientBtn = ButtonStyleUtil.createPrimaryButton("Add Patient");
        JButton searchPatientBtn = ButtonStyleUtil.createPrimaryButton("Search Patient");
        JButton todaysPatientsBtn = ButtonStyleUtil.createButton("Today's Patients", new Color(100, 150, 200));
        JButton viewAllBtn = ButtonStyleUtil.createButton("View All Patients", new Color(100, 150, 200));

        // Set consistent button size
        Dimension btnSize = new Dimension(180, 40);
        addPatientBtn.setMaximumSize(btnSize);
        searchPatientBtn.setMaximumSize(btnSize);
        todaysPatientsBtn.setMaximumSize(btnSize);
        viewAllBtn.setMaximumSize(btnSize);

        panel.add(Box.createVerticalStrut(10));
        panel.add(addPatientBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(searchPatientBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(todaysPatientsBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(viewAllBtn);
        panel.add(Box.createVerticalGlue());

        // Event listeners
        addPatientBtn.addActionListener(e -> openAddPatientView());
        searchPatientBtn.addActionListener(e -> openSearchPatientView());
        todaysPatientsBtn.addActionListener(e -> showTodaysPatients());
        viewAllBtn.addActionListener(e -> showAllPatients());

        return panel;
    }

    private JPanel createDashboardContent() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        // Get statistics from controller
        int totalPatients = patientController.getTotalPatientCount();
        int todaysPatients = patientController.getTodaysPatients().size();
        int thisMonthPatients = patientController.getPatientsThisMonth().size();

        // Create stat panels
        panel.add(createStatPanel("Total Patients", String.valueOf(totalPatients), Color.BLUE));
        panel.add(createStatPanel("Today's Patients", String.valueOf(todaysPatients), Color.GREEN));
        panel.add(createStatPanel("This Month", String.valueOf(thisMonthPatients), Color.ORANGE));
        panel.add(createStatPanel("Follow-ups", "0", Color.RED)); // Placeholder

        return panel;
    }

    private JPanel createStatPanel(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(color, 2));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(color);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Export Data");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Patients Menu
        JMenu patientsMenu = new JMenu("Patients");
        JMenuItem addPatientItem = new JMenuItem("Add Patient");
        JMenuItem searchPatientItem = new JMenuItem("Search Patient");
        JMenuItem viewAllItem = new JMenuItem("View All Patients");
        
        addPatientItem.addActionListener(e -> openAddPatientView());
        searchPatientItem.addActionListener(e -> openSearchPatientView());
        viewAllItem.addActionListener(e -> showAllPatients());
        
        patientsMenu.add(addPatientItem);
        patientsMenu.add(searchPatientItem);
        patientsMenu.add(viewAllItem);

        // Settings Menu
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem changePasswordItem = new JMenuItem("Change Password");
        JMenuItem logoutItem = new JMenuItem("Logout");
        
        changePasswordItem.addActionListener(e -> openChangePasswordDialog());
        logoutItem.addActionListener(e -> handleLogout());
        
        settingsMenu.add(changePasswordItem);
        settingsMenu.addSeparator();
        settingsMenu.add(logoutItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        
        aboutItem.addActionListener(e -> showAboutDialog());
        
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(patientsMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
    }

    // Action handlers
    private void openAddPatientView() {
        SwingUtilities.invokeLater(() -> {
            NewPatientView newPatientView = new NewPatientView();
            newPatientView.setVisible(true);
        });
    }

    private void openSearchPatientView() {
        SwingUtilities.invokeLater(() -> {
            PatientSearchView searchView = new PatientSearchView();
            searchView.setVisible(true);
        });
    }

    private void showTodaysPatients() {
        int count = patientController.getTodaysPatients().size();
        JOptionPane.showMessageDialog(this, 
            "Today's Patients: " + count, 
            "Info", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAllPatients() {
        int count = patientController.getAllPatients().size();
        JOptionPane.showMessageDialog(this, 
            "Total Patients: " + count, 
            "Info", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void openChangePasswordDialog() {
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Old Password:"));
        panel.add(oldPasswordField);
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmPasswordField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Change Password", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, 
                    "New passwords do not match!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            authController.changePassword(oldPassword, newPassword);
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            authController.logout();
            this.dispose();
            SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
        }
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, 
            "Patient Management System\nVersion 1.0\n\nA comprehensive system for managing patient records.", 
            "About", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardView());
    }
}

