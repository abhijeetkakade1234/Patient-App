import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginUI() {
        initializeFrame();
        createUI();
        addListeners();
    }

    private void initializeFrame() {
        setTitle("AyurVault - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createUI() {
        // Main panel setup
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(new Color(245, 245, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Header/Logo section
        JLabel logoLabel = new JLabel("AyurVault", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 28));
        logoLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(logoLabel, gbc);

        JLabel subtitleLabel = new JLabel("Welcome Back!", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 20, 5);
        mainPanel.add(subtitleLabel, gbc);

        // Username field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(userLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // Password field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(245, 245, 250));
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(5, 5, 5, 5);

        loginButton = new JButton("Login");
        styleButton(loginButton, new Color(52, 152, 219));
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;
        buttonPanel.add(loginButton, buttonGbc);

        registerButton = new JButton("Register");
        styleButton(registerButton, new Color(46, 204, 113));
        buttonGbc.gridx = 1;
        buttonPanel.add(registerButton, buttonGbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setPreferredSize(new Dimension(120, 35));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setOpaque(true);

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    private void addListeners() {
        loginButton.addActionListener(e -> authenticateUser());
        registerButton.addActionListener(e -> openRegistrationPage());

        // Add Enter key listener for login
        ActionListener loginAction = e -> authenticateUser();
        usernameField.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Auth.validateLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose();
            new Dashboard();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }

    private void openRegistrationPage() {
        dispose();
        new RegisterUI().setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}