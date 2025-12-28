package dao;

import model.FollowUp;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for FollowUp operations
 */
public interface FollowUpDAO {
    
    int insert(FollowUp followUp) throws SQLException;
    
    boolean update(FollowUp followUp) throws SQLException;
    
    boolean delete(int followUpId) throws SQLException;
    
    FollowUp findById(int followUpId) throws SQLException;
    
    List<FollowUp> findByPatientId(int patientId) throws SQLException;
    
    List<FollowUp> findAll() throws SQLException;
    
    int countByPatientId(int patientId) throws SQLException;
    
    /**
     * Find follow-ups by visit date
     * @param date Visit date
     * @return List of follow-ups
     * @throws SQLException if database error occurs
     */
    List<FollowUp> findByVisitDate(java.time.LocalDate date) throws SQLException;
    
    /**
     * Find follow-ups by visit date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of follow-ups
     * @throws SQLException if database error occurs
     */
    List<FollowUp> findByVisitDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate) throws SQLException;
    
    /**
     * Find upcoming follow-ups (patients due for follow-up)
     * @return List of follow-ups with next_follow_up_date in future
     * @throws SQLException if database error occurs
     */
    List<FollowUp> findUpcomingFollowUps() throws SQLException;
    
    /**
     * Find follow-ups due today or overdue
     * @return List of follow-ups
     * @throws SQLException if database error occurs
     */
    List<FollowUp> findDueFollowUps() throws SQLException;
    
    /**
     * Count follow-ups by month
     * @param year Year
     * @param month Month (1-12)
     * @return Number of follow-ups
     * @throws SQLException if database error occurs
     */
    int countByMonth(int year, int month) throws SQLException;
    
    /**
     * Find follow-ups by month
     * @param year Year
     * @param month Month (1-12)
     * @return List of follow-ups
     * @throws SQLException if database error occurs
     */
    List<FollowUp> findByMonth(int year, int month) throws SQLException;
}

