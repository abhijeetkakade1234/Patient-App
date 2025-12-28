package dao;

import model.Patient;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object interface for Patient operations
 */
public interface PatientDAO {
    
    /**
     * Insert a new patient
     * @param patient Patient object to insert
     * @return generated patient ID
     * @throws SQLException if database error occurs
     */
    int insert(Patient patient) throws SQLException;
    
    /**
     * Update existing patient
     * @param patient Patient object with updated data
     * @return true if update successful
     * @throws SQLException if database error occurs
     */
    boolean update(Patient patient) throws SQLException;
    
    /**
     * Delete patient by ID
     * @param patientId ID of patient to delete
     * @return true if deletion successful
     * @throws SQLException if database error occurs
     */
    boolean delete(int patientId) throws SQLException;
    
    /**
     * Find patient by ID
     * @param patientId ID of patient to find
     * @return Patient object or null if not found
     * @throws SQLException if database error occurs
     */
    Patient findById(int patientId) throws SQLException;
    
    /**
     * Search patients by name
     * @param name Name to search (partial match)
     * @return List of matching patients
     * @throws SQLException if database error occurs
     */
    List<Patient> searchByName(String name) throws SQLException;
    
    /**
     * Search patients by phone
     * @param phone Phone number to search
     * @return List of matching patients
     * @throws SQLException if database error occurs
     */
    List<Patient> searchByPhone(String phone) throws SQLException;
    
    /**
     * Search patients by name or phone
     * @param searchTerm Search term
     * @return List of matching patients
     * @throws SQLException if database error occurs
     */
    List<Patient> search(String searchTerm) throws SQLException;
    
    /**
     * Get all patients
     * @return List of all patients
     * @throws SQLException if database error occurs
     */
    List<Patient> findAll() throws SQLException;
    
    /**
     * Get patients registered on a specific date
     * @param date Registration date
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    List<Patient> findByRegistrationDate(LocalDate date) throws SQLException;
    
    /**
     * Get patients registered in a specific month
     * @param year Year
     * @param month Month (1-12)
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    List<Patient> findByMonth(int year, int month) throws SQLException;
    
    /**
     * Get patients registered in a specific year
     * @param year Year
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    List<Patient> findByYear(int year) throws SQLException;
    
    /**
     * Count total patients
     * @return Total number of patients
     * @throws SQLException if database error occurs
     */
    int countAll() throws SQLException;
    
    /**
     * Count patients in a specific month
     * @param year Year
     * @param month Month (1-12)
     * @return Number of patients
     * @throws SQLException if database error occurs
     */
    int countByMonth(int year, int month) throws SQLException;
    
    /**
     * Count patients in a specific year
     * @param year Year
     * @return Number of patients
     * @throws SQLException if database error occurs
     */
    int countByYear(int year) throws SQLException;
    
    /**
     * Find patients with follow-up on a specific date
     * @param date Follow-up date
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    List<Patient> findByFollowUpDate(LocalDate date) throws SQLException;
    
    /**
     * Find patients with follow-up in date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    List<Patient> findByFollowUpDateRange(LocalDate startDate, LocalDate endDate) throws SQLException;
}

