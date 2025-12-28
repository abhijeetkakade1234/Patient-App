package controller;

import model.User;
import service.AuthService;

import javax.swing.JOptionPane;

/**
 * Controller for Authentication operations
 * Mediates between UI and Service layer
 */
public class AuthController {
    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    /**
     * Validate user login
     * @param username Username
     * @param password Password
     * @return true if login successful
     */
    public boolean login(String username, String password) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Please enter username", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Please enter password", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        boolean success = authService.login(username, password);
        
        if (!success) {
            JOptionPane.showMessageDialog(null, 
                "Invalid username or password", 
                "Login Failed", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }

    /**
     * Register a new user
     * @param user User object
     * @return true if registration successful
     */
    public boolean register(User user) {
        // Validate input
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Please enter username", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Please enter password", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (user.getPassword().length() < 6) {
            JOptionPane.showMessageDialog(null, 
                "Password must be at least 6 characters long", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        boolean success = authService.register(user);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "Registration successful! You can now login.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Registration failed. Username may already exist.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }

    /**
     * Change password for current user
     * @param oldPassword Old password
     * @param newPassword New password
     * @return true if password changed successfully
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        // Validate input
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Please enter old password", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (newPassword == null || newPassword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Please enter new password", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (newPassword.length() < 6) {
            JOptionPane.showMessageDialog(null, 
                "New password must be at least 6 characters long", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        boolean success = authService.changePassword(oldPassword, newPassword);
        
        if (success) {
            JOptionPane.showMessageDialog(null, 
                "Password changed successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Failed to change password. Please check your old password.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }

    /**
     * Logout current user
     */
    public void logout() {
        authService.logout();
    }

    /**
     * Get current logged-in user
     * @return Current user or null if not logged in
     */
    public User getCurrentUser() {
        return authService.getCurrentUser();
    }

    /**
     * Check if user is logged in
     * @return true if user is logged in
     */
    public boolean isLoggedIn() {
        return authService.isLoggedIn();
    }
}

