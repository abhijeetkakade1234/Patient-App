package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;

import java.sql.SQLException;

/**
 * Service layer for Authentication and User management
 */
public class AuthService {
    private final UserDAO userDAO;
    private User currentUser;

    public AuthService() {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * Validate user login
     * @param username Username
     * @param password Password
     * @return true if login successful
     */
    public boolean login(String username, String password) {
        try {
            // Validate input
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                return false;
            }
            
            // Check credentials
            boolean isValid = userDAO.validateLogin(username.trim(), password);
            
            if (isValid) {
                // Load user details
                currentUser = userDAO.findByUsername(username.trim());
                
                // Update last login time
                if (currentUser != null) {
                    userDAO.updateLastLogin(currentUser.getId());
                }
                
                return true;
            }
            
            return false;
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Register a new user
     * @param user User object
     * @return true if registration successful
     */
    public boolean register(User user) {
        try {
            // Validate input
            if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return false;
            }
            
            // Check if username already exists
            User existingUser = userDAO.findByUsername(user.getUsername().trim());
            if (existingUser != null) {
                return false; // Username already taken
            }
            
            // Insert new user
            int userId = userDAO.insert(user);
            return userId > 0;
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Change password for current user
     * @param oldPassword Old password
     * @param newPassword New password
     * @return true if password changed successfully
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        try {
            if (currentUser == null) {
                return false;
            }
            
            // Verify old password
            boolean isValid = userDAO.validateLogin(currentUser.getUsername(), oldPassword);
            if (!isValid) {
                return false;
            }
            
            // Update password
            return userDAO.updatePassword(currentUser.getId(), newPassword);
        } catch (SQLException e) {
            System.err.println("Password change error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Logout current user
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Get current logged-in user
     * @return Current user or null if not logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Check if user is logged in
     * @return true if user is logged in
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}

