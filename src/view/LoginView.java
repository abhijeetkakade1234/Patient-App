package view;

import controller.AuthController;
import view.util.ButtonStyleUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Refactored Login UI with proper separation of concerns
 * This class only handles UI presentation
 */
public class LoginView extends JFrame {
    private final AuthController authController;
    
    // UI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        this.authController = new AuthController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Patient Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        loginButton = ButtonStyleUtil.createPrimaryButton("Login");
        registerButton = ButtonStyleUtil.createSecondaryButton("Register");
        
        loginButton.setPreferredSize(new Dimension(100, 30));
        registerButton.setPreferredSize(new Dimension(100, 30));
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);

        // Event listeners
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
        
        // Enter key on password field triggers login
        passwordField.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (authController.login(username, password)) {
            // Login successful - open dashboard
            this.dispose();
            SwingUtilities.invokeLater(() -> new DashboardView());
        }
        
        // Clear password field on failed login
        passwordField.setText("");
    }

    private void handleRegister() {
        this.dispose();
        SwingUtilities.invokeLater(() -> new RegisterView());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}

