package service;

import dao.PatientDAO;
import dao.PatientDAOImpl;
import model.Patient;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for Patient business logic
 */
public class PatientService {
    private final PatientDAO patientDAO;

    public PatientService() {
        this.patientDAO = new PatientDAOImpl();
    }

    /**
     * Add a new patient
     * @param patient Patient object
     * @return generated patient ID
     * @throws SQLException if database error occurs
     */
    public int addPatient(Patient patient) throws SQLException {
        // Business logic: Calculate BMI if height and weight are provided
        if (patient.getHeight() > 0 && patient.getWeight() > 0) {
            double heightInMeters = patient.getHeight() / 100.0;
            double bmi = patient.getWeight() / (heightInMeters * heightInMeters);
            patient.setBmi(Math.round(bmi * 10.0) / 10.0);
        }
        
        // Set registration date if not set
        if (patient.getRegistrationDate() == null) {
            patient.setRegistrationDate(LocalDate.now());
        }
        
        return patientDAO.insert(patient);
    }

    /**
     * Update existing patient
     * @param patient Patient object with updated data
     * @return true if update successful
     * @throws SQLException if database error occurs
     */
    public boolean updatePatient(Patient patient) throws SQLException {
        // Recalculate BMI if height and weight are provided
        if (patient.getHeight() > 0 && patient.getWeight() > 0) {
            double heightInMeters = patient.getHeight() / 100.0;
            double bmi = patient.getWeight() / (heightInMeters * heightInMeters);
            patient.setBmi(Math.round(bmi * 10.0) / 10.0);
        }
        
        return patientDAO.update(patient);
    }

    /**
     * Delete patient by ID
     * @param patientId ID of patient to delete
     * @return true if deletion successful
     * @throws SQLException if database error occurs
     */
    public boolean deletePatient(int patientId) throws SQLException {
        return patientDAO.delete(patientId);
    }

    /**
     * Get patient by ID
     * @param patientId ID of patient
     * @return Patient object or null if not found
     * @throws SQLException if database error occurs
     */
    public Patient getPatientById(int patientId) throws SQLException {
        return patientDAO.findById(patientId);
    }

    /**
     * Search patients by name or phone
     * @param searchTerm Search term
     * @return List of matching patients
     * @throws SQLException if database error occurs
     */
    public List<Patient> searchPatients(String searchTerm) throws SQLException {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return patientDAO.findAll();
        }
        return patientDAO.search(searchTerm.trim());
    }

    /**
     * Get all patients
     * @return List of all patients
     * @throws SQLException if database error occurs
     */
    public List<Patient> getAllPatients() throws SQLException {
        return patientDAO.findAll();
    }

    /**
     * Get patients registered today
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    public List<Patient> getTodaysPatients() throws SQLException {
        return patientDAO.findByRegistrationDate(LocalDate.now());
    }

    /**
     * Get patients registered in current month
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    public List<Patient> getPatientsThisMonth() throws SQLException {
        LocalDate now = LocalDate.now();
        return patientDAO.findByMonth(now.getYear(), now.getMonthValue());
    }

    /**
     * Get patients registered in a specific month
     * @param year Year
     * @param month Month (1-12)
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    public List<Patient> getPatientsByMonth(int year, int month) throws SQLException {
        return patientDAO.findByMonth(year, month);
    }

    /**
     * Get patients registered in a specific year
     * @param year Year
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    public List<Patient> getPatientsByYear(int year) throws SQLException {
        return patientDAO.findByYear(year);
    }

    /**
     * Get total patient count
     * @return Total number of patients
     * @throws SQLException if database error occurs
     */
    public int getTotalPatientCount() throws SQLException {
        return patientDAO.countAll();
    }

    /**
     * Get patient count for a specific month
     * @param year Year
     * @param month Month (1-12)
     * @return Number of patients
     * @throws SQLException if database error occurs
     */
    public int getPatientCountByMonth(int year, int month) throws SQLException {
        return patientDAO.countByMonth(year, month);
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
     * Get patients by specific registration date
     * @param date Registration date
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    public List<Patient> getPatientsByDate(LocalDate date) throws SQLException {
        return patientDAO.findByRegistrationDate(date);
    }

    /**
     * Get patients with follow-up on specific date
     * @param date Follow-up date
     * @return List of patients
     * @throws SQLException if database error occurs
     */
    public List<Patient> getPatientsByFollowUpDate(LocalDate date) throws SQLException {
        return patientDAO.findByFollowUpDate(date);
    }
}

