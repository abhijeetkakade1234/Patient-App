package controller;

import model.FollowUp;
import service.FollowUpService;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller for FollowUp operations
 * Mediates between UI and Service layer
 */
public class FollowUpController {
    private final FollowUpService followUpService;

    public FollowUpController() {
        this.followUpService = new FollowUpService();
    }

    /**
     * Add a new follow-up
     * @param followUp FollowUp object
     * @return generated follow-up ID or -1 if failed
     */
    public int addFollowUp(FollowUp followUp) {
        try {
            int followUpId = followUpService.addFollowUp(followUp);
            if (followUpId > 0) {
                JOptionPane.showMessageDialog(null, 
                    "Follow-up added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                return followUpId;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to add follow-up", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Update existing follow-up
     * @param followUp FollowUp object with updated data
     * @return true if update successful
     */
    public boolean updateFollowUp(FollowUp followUp) {
        try {
            boolean success = followUpService.updateFollowUp(followUp);
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Follow-up updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to update follow-up", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            return success;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete follow-up by ID
     * @param followUpId ID of follow-up to delete
     * @return true if deletion successful
     */
    public boolean deleteFollowUp(int followUpId) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete this follow-up record?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = followUpService.deleteFollowUp(followUpId);
                if (success) {
                    JOptionPane.showMessageDialog(null, 
                        "Follow-up deleted successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Failed to delete follow-up", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
                return success;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Database error: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Get follow-up by ID
     * @param followUpId ID of follow-up
     * @return FollowUp object or null if not found
     */
    public FollowUp getFollowUpById(int followUpId) {
        try {
            return followUpService.getFollowUpById(followUpId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all follow-ups for a patient
     * @param patientId Patient ID
     * @return List of follow-ups
     */
    public List<FollowUp> getFollowUpsByPatientId(int patientId) {
        try {
            return followUpService.getFollowUpsByPatientId(patientId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Get all follow-ups
     * @return List of all follow-ups
     */
    public List<FollowUp> getAllFollowUps() {
        try {
            return followUpService.getAllFollowUps();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Get follow-up count for a patient
     * @param patientId Patient ID
     * @return Number of follow-ups
     */
    public int getFollowUpCountByPatientId(int patientId) {
        try {
            return followUpService.getFollowUpCountByPatientId(patientId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Calculate BMI
     * @param heightCm Height in centimeters
     * @param weightKg Weight in kilograms
     * @return BMI value
     */
    public double calculateBMI(double heightCm, double weightKg) {
        return followUpService.calculateBMI(heightCm, weightKg);
    }

    /**
     * Get follow-ups by visit date
     * @param date Visit date
     * @return List of follow-ups
     */
    public List<FollowUp> getFollowUpsByDate(java.time.LocalDate date) {
        try {
            return followUpService.getFollowUpsByDate(date);
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Database error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Get upcoming follow-ups
     * @return List of follow-ups with future next_follow_up_date
     */
    public List<FollowUp> getUpcomingFollowUps() {
        try {
            return followUpService.getUpcomingFollowUps();
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Database error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Get due follow-ups (today or overdue)
     * @return List of follow-ups
     */
    public List<FollowUp> getDueFollowUps() {
        try {
            return followUpService.getDueFollowUps();
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Database error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Get follow-ups by month
     * @param year Year
     * @param month Month (1-12)
     * @return List of follow-ups
     */
    public List<FollowUp> getFollowUpsByMonth(int year, int month) {
        try {
            return followUpService.getFollowUpsByMonth(year, month);
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Database error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Count follow-ups by month
     * @param year Year
     * @param month Month (1-12)
     * @return Number of follow-ups
     */
    public int getFollowUpCountByMonth(int year, int month) {
        try {
            return followUpService.getFollowUpCountByMonth(year, month);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

