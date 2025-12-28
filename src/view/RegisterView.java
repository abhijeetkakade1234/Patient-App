package view;

import controller.AuthController;
import model.User;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Refactored Register UI with proper separation of concerns
 */
public class RegisterView extends JFrame {
    private final AuthController authController;
    
    // UI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JTextField fullNameField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterView() {
        this.authController = new AuthController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Patient Management System - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Register New User", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Full Name
        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Full Name:"), gbc);

        gbc.gridx = 1;
        fullNameField = new JTextField(20);
        mainPanel.add(fullNameField, gbc);

        // Email
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);

        // Username
        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridy = 4;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        // Confirm Password
        gbc.gridy = 5;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        mainPanel.add(confirmPasswordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        registerButton = ButtonStyleUtil.createSuccessButton("Register");
        backButton = ButtonStyleUtil.createSecondaryButton("Back to Login");
        
        registerButton.setPreferredSize(new Dimension(120, 30));
        backButton.setPreferredSize(new Dimension(120, 30));
        
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);

        // Event listeners
        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> handleBack());
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String email = emailField.getText().trim();
        String fullName = fullNameField.getText().trim();

        // Validate passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Passwords do not match!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create user object
        User user = new User(username, password);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setActive(true);

        // Register through controller
        if (authController.register(user)) {
            // Registration successful - go back to login
            this.dispose();
            SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
        }
    }

    private void handleBack() {
        this.dispose();
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterView registerView = new RegisterView();
            registerView.setVisible(true);
        });
    }
}

