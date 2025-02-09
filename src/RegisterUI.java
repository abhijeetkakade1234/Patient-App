import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class RegisterUI extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton registerBtn;
    private JButton cancelBtn;

    public RegisterUI() {
        initializeFrame();
        createUI();
        addListeners();
    }

    private void initializeFrame() {
        setTitle("AyurVault - Register");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        // Header
        JLabel headerLabel = new JLabel("Create New Account", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 20, 5);
        mainPanel.add(headerLabel, gbc);

        // Username field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(userLabel, gbc);

        userField = new JTextField();
        userField.setPreferredSize(new java.awt.Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(userField, gbc);

        // Password field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passLabel, gbc);

        passField = new JPasswordField();
        passField.setPreferredSize(new java.awt.Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(245, 245, 250));
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(5, 5, 5, 5);

        registerBtn = new JButton("Register");
        styleButton(registerBtn, new Color(52, 152, 219));
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;
        buttonPanel.add(registerBtn, buttonGbc);

        cancelBtn = new JButton("Cancel");
        styleButton(cancelBtn, new Color(231, 76, 60));
        buttonGbc.gridx = 1;
        buttonGbc.gridy = 0;
        buttonPanel.add(cancelBtn, buttonGbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void styleButton(JButton button, Color backgroundColor) {
        button.setPreferredSize(new java.awt.Dimension(120, 35));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorderPainted(false);
        button.setOpaque(true);

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    private void addListeners() {
        registerBtn.addActionListener(e -> handleRegistration());

        cancelBtn.addActionListener(e -> {
            dispose();
            new LoginUI().setVisible(true);
        });
    }

    private void handleRegistration() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        // Add validation logic here
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add registration logic here

        JOptionPane.showMessageDialog(this,
                "Registration Successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new LoginUI().setVisible(true);
    }
}