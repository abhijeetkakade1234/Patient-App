import java.awt.*;
import javax.swing.*;

public class LoginAndRegistrationPage {

    JFrame frame;
    JPanel logiPanel, regPanel;
    JButton loginButton, registerButton, loginSuccessButton, registerSuccessButton;
    JTextField loginUsernameField, loginPasswordField, registerUsernameField, registerPasswordField;

    public LoginAndRegistrationPage() {
        frame = new JFrame("Login and Registration Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        display();
        frame.setVisible(true); // Set visibility after components are added
    }

    public final void display() {

        // Initialize panels
        logiPanel = new JPanel(new GridBagLayout());
        regPanel = new JPanel(new GridBagLayout());

        // Initialize text fields for login
        loginUsernameField = new JTextField();
        loginPasswordField = new JPasswordField();
        loginUsernameField.setPreferredSize(new Dimension(200, 25));
        loginPasswordField.setPreferredSize(new Dimension(200, 25));

        // Initialize text fields for registration
        registerUsernameField = new JTextField();
        registerPasswordField = new JPasswordField();
        registerUsernameField.setPreferredSize(new Dimension(200, 25));
        registerPasswordField.setPreferredSize(new Dimension(200, 25));

        // Initialize buttons
        loginButton = new JButton("Go to Registration");
        registerButton = new JButton("Go to Login");
        loginSuccessButton = new JButton("Login Successful");
        registerSuccessButton = new JButton("Registration Successful");

        // Add components to the login panel
        addComponentsToPanel(logiPanel, new JLabel("Login"), 0);
        addComponentsToPanel(logiPanel, new JLabel("Username:"), 1);
        addComponentsToPanel(logiPanel, loginUsernameField, 2);
        addComponentsToPanel(logiPanel, new JLabel("Password:"), 3);
        addComponentsToPanel(logiPanel, loginPasswordField, 4);
        addComponentsToPanel(logiPanel, loginSuccessButton, 5);
        addComponentsToPanel(logiPanel, loginButton, 6);

        // Add components to the registration panel
        addComponentsToPanel(regPanel, new JLabel("Register"), 0);
        addComponentsToPanel(regPanel, new JLabel("Username:"), 1);
        addComponentsToPanel(regPanel, registerUsernameField, 2);
        addComponentsToPanel(regPanel, new JLabel("Password:"), 3);
        addComponentsToPanel(regPanel, registerPasswordField, 4);
        addComponentsToPanel(regPanel, registerSuccessButton, 5);
        addComponentsToPanel(regPanel, registerButton, 6);

        // Initially, add the login panel to the frame
        frame.add(logiPanel);

        // Add action listeners for switching panels and actions
        loginButton.addActionListener(e -> {
            frame.remove(logiPanel);  // Remove login panel
            frame.add(regPanel);     // Add registration panel
            frame.revalidate();      // Revalidate the frame
            frame.repaint();         // Repaint the frame
        });

        registerButton.addActionListener(e -> {
            frame.remove(regPanel);  // Remove registration panel
            frame.add(logiPanel);   // Add login panel
            frame.revalidate();     // Revalidate the frame
            frame.repaint();        // Repaint the frame
        });

        loginSuccessButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Login Successful!"));
        registerSuccessButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Registration Successful!"));
    }

    private void addComponentsToPanel(JPanel panel, Component component, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(component, gbc);
    }

    public static void main(String[] args) {
        new LoginAndRegistrationPage();
    }
}
