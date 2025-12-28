package dao;

import model.Panchakarma;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Panchakarma operations
 */
public interface PanchakarmaDAO {
    
    int insert(Panchakarma panchakarma) throws SQLException;
    
    boolean update(Panchakarma panchakarma) throws SQLException;
    
    boolean delete(int panchakarmaId) throws SQLException;
    
    Panchakarma findById(int panchakarmaId) throws SQLException;
    
    List<Panchakarma> findByPatientId(int patientId) throws SQLException;
    
    List<Panchakarma> findAll() throws SQLException;
    
    int countByPatientId(int patientId) throws SQLException;
    
    /**
     * Find panchakarma treatments by date
     * @param date Treatment date
     * @return List of panchakarma treatments
     * @throws SQLException if database error occurs
     */
    List<Panchakarma> findByDate(java.time.LocalDate date) throws SQLException;
    
    /**
     * Find panchakarma treatments by date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of panchakarma treatments
     * @throws SQLException if database error occurs
     */
    List<Panchakarma> findByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate) throws SQLException;
    
    /**
     * Find panchakarma treatments by month
     * @param year Year
     * @param month Month (1-12)
     * @return List of panchakarma treatments
     * @throws SQLException if database error occurs
     */
    List<Panchakarma> findByMonth(int year, int month) throws SQLException;
    
    /**
     * Count panchakarma treatments by month
     * @param year Year
     * @param month Month (1-12)
     * @return Number of treatments
     * @throws SQLException if database error occurs
     */
    int countByMonth(int year, int month) throws SQLException;
}

