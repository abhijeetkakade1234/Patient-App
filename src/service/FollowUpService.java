package service;

import dao.FollowUpDAO;
import dao.FollowUpDAOImpl;
import model.FollowUp;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for FollowUp business logic
 */
public class FollowUpService {
    private final FollowUpDAO followUpDAO;

    public FollowUpService() {
        this.followUpDAO = new FollowUpDAOImpl();
    }

    /**
     * Add a new follow-up
     * @param followUp FollowUp object
     * @return generated follow-up ID
     * @throws SQLException if database error occurs
     */
    public int addFollowUp(FollowUp followUp) throws SQLException {
        // Business logic: Calculate BMI if height and weight are provided
        if (followUp.getHeight() > 0 && followUp.getWeight() > 0) {
            double heightInMeters = followUp.getHeight() / 100.0;
            double bmi = followUp.getWeight() / (heightInMeters * heightInMeters);
            followUp.setBmi(Math.round(bmi * 10.0) / 10.0);
        }
        
        return followUpDAO.insert(followUp);
    }

    /**
     * Update existing follow-up
     * @param followUp FollowUp object with updated data
     * @return true if update successful
     * @throws SQLException if database error occurs
     */
    public boolean updateFollowUp(FollowUp followUp) throws SQLException {
        // Recalculate BMI if height and weight are provided
        if (followUp.getHeight() > 0 && followUp.getWeight() > 0) {
            double heightInMeters = followUp.getHeight() / 100.0;
            double bmi = followUp.getWeight() / (heightInMeters * heightInMeters);
            followUp.setBmi(Math.round(bmi * 10.0) / 10.0);
        }
        
        return followUpDAO.update(followUp);
    }

    /**
     * Delete follow-up by ID
     * @param followUpId ID of follow-up to delete
     * @return true if deletion successful
     * @throws SQLException if database error occurs
     */
    public boolean deleteFollowUp(int followUpId) throws SQLException {
        return followUpDAO.delete(followUpId);
    }

    /**
     * Get follow-up by ID
     * @param followUpId ID of follow-up
     * @return FollowUp object or null if not found
     * @throws SQLException if database error occurs
     */
    public FollowUp getFollowUpById(int followUpId) throws SQLException {
        return followUpDAO.findById(followUpId);
    }

    /**
     * Get all follow-ups for a patient
     * @param patientId Patient ID
     * @return List of follow-ups
     * @throws SQLException if database error occurs
     */
    public List<FollowUp> getFollowUpsByPatientId(int patientId) throws SQLException {
        return followUpDAO.findByPatientId(patientId);
    }

    /**
     * Get all follow-ups
     * @return List of all follow-ups
     * @throws SQLException if database error occurs
     */
    public List<FollowUp> getAllFollowUps() throws SQLException {
        return followUpDAO.findAll();
    }

    /**
     * Get follow-up count for a patient
     * @param patientId Patient ID
     * @return Number of follow-ups
     * @throws SQLException if database error occurs
     */
    public int getFollowUpCountByPatientId(int patientId) throws SQLException {
        return followUpDAO.countByPatientId(patientId);
    }

    /**
     * Calculate BMI
     * @param heightCm Height in centimeters
     * @param weightKg Weight in kilograms
     * @return BMI value
     */
    public double calculateBMI(double heightCm, double weightKg) {
        if (heightCm <= 0 || weightKg <= 0) {
            return 0.0;
        }
        double heightInMeters = heightCm / 100.0;
        double bmi = weightKg / (heightInMeters * heightInMeters);
        return Math.round(bmi * 10.0) / 10.0;
    }

    /**
     * Get follow-ups by visit date
     * @param date Visit date
     * @return List of follow-ups
     * @throws SQLException if database error occurs
     */
    public List<FollowUp> getFollowUpsByDate(LocalDate date) throws SQLException {
        return followUpDAO.findByVisitDate(date);
    }

    /**
     * Get upcoming follow-ups
     * @return List of follow-ups with future next_follow_up_date
     * @throws SQLException if database error occurs
     */
    public List<FollowUp> getUpcomingFollowUps() throws SQLException {
        return followUpDAO.findUpcomingFollowUps();
    }

    /**
     * Get due follow-ups (today or overdue)
     * @return List of follow-ups
     * @throws SQLException if database error occurs
     */
    public List<FollowUp> getDueFollowUps() throws SQLException {
        return followUpDAO.findDueFollowUps();
    }

    /**
     * Get follow-ups by month
     * @param year Year
     * @param month Month (1-12)
     * @return List of follow-ups
     * @throws SQLException if database error occurs
     */
    public List<FollowUp> getFollowUpsByMonth(int year, int month) throws SQLException {
        return followUpDAO.findByMonth(year, month);
    }

    /**
     * Count follow-ups by month
     * @param year Year
     * @param month Month (1-12)
     * @return Number of follow-ups
     * @throws SQLException if database error occurs
     */
    public int getFollowUpCountByMonth(int year, int month) throws SQLException {
        return followUpDAO.countByMonth(year, month);
    }
}

