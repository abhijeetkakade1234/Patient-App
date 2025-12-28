import javax.swing.*;

/**
 * Main Application Entry Point
 * Patient Management System
 * 
 * Launches the original Dashboard directly
 */
public class App {
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }
        
        // Launch original Dashboard
        SwingUtilities.invokeLater(() -> {
            new Dashboard();
        });
    }
}
