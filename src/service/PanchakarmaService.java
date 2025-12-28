package service;

import dao.PanchakarmaDAO;
import dao.PanchakarmaDAOImpl;
import model.Panchakarma;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for Panchakarma business logic
 */
public class PanchakarmaService {
    private final PanchakarmaDAO panchakarmaDAO;

    public PanchakarmaService() {
        this.panchakarmaDAO = new PanchakarmaDAOImpl();
    }

    /**
     * Add a new panchakarma record
     * @param panchakarma Panchakarma object
     * @return generated panchakarma ID
     * @throws SQLException if database error occurs
     */
    public int addPanchakarma(Panchakarma panchakarma) throws SQLException {
        return panchakarmaDAO.insert(panchakarma);
    }

    /**
     * Update existing panchakarma record
     * @param panchakarma Panchakarma object with updated data
     * @return true if update successful
     * @throws SQLException if database error occurs
     */
    public boolean updatePanchakarma(Panchakarma panchakarma) throws SQLException {
        return panchakarmaDAO.update(panchakarma);
    }

    /**
     * Delete panchakarma record by ID
     * @param panchakarmaId ID of panchakarma to delete
     * @return true if deletion successful
     * @throws SQLException if database error occurs
     */
    public boolean deletePanchakarma(int panchakarmaId) throws SQLException {
        return panchakarmaDAO.delete(panchakarmaId);
    }

    /**
     * Get panchakarma record by ID
     * @param panchakarmaId ID of panchakarma
     * @return Panchakarma object or null if not found
     * @throws SQLException if database error occurs
     */
    public Panchakarma getPanchakarmaById(int panchakarmaId) throws SQLException {
        return panchakarmaDAO.findById(panchakarmaId);
    }

    /**
     * Get all panchakarma records for a patient
     * @param patientId Patient ID
     * @return List of panchakarma records
     * @throws SQLException if database error occurs
     */
    public List<Panchakarma> getPanchakarmaByPatientId(int patientId) throws SQLException {
        return panchakarmaDAO.findByPatientId(patientId);
    }

    /**
     * Get all panchakarma records
     * @return List of all panchakarma records
     * @throws SQLException if database error occurs
     */
    public List<Panchakarma> getAllPanchakarma() throws SQLException {
        return panchakarmaDAO.findAll();
    }

    /**
     * Get panchakarma count for a patient
     * @param patientId Patient ID
     * @return Number of panchakarma records
     * @throws SQLException if database error occurs
     */
    public int getPanchakarmaCountByPatientId(int patientId) throws SQLException {
        return panchakarmaDAO.countByPatientId(patientId);
    }

    /**
     * Calculate total price for all panchakarma treatments of a patient
     * @param patientId Patient ID
     * @return Total price
     * @throws SQLException if database error occurs
     */
    public double getTotalPriceByPatientId(int patientId) throws SQLException {
        List<Panchakarma> panchakarmaList = panchakarmaDAO.findByPatientId(patientId);
        return panchakarmaList.stream()
                .mapToDouble(Panchakarma::getPrice)
                .sum();
    }

    /**
     * Get panchakarma treatments by date
     * @param date Treatment date
     * @return List of treatments
     * @throws SQLException if database error occurs
     */
    public List<Panchakarma> getPanchakarmaByDate(LocalDate date) throws SQLException {
        return panchakarmaDAO.findByDate(date);
    }

    /**
     * Get panchakarma treatments by month
     * @param year Year
     * @param month Month (1-12)
     * @return List of treatments
     * @throws SQLException if database error occurs
     */
    public List<Panchakarma> getPanchakarmaByMonth(int year, int month) throws SQLException {
        return panchakarmaDAO.findByMonth(year, month);
    }

    /**
     * Get panchakarma treatments by date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of treatments
     * @throws SQLException if database error occurs
     */
    public List<Panchakarma> getPanchakarmaByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        return panchakarmaDAO.findByDateRange(startDate, endDate);
    }

    /**
     * Count panchakarma treatments in a month
     * @param year Year
     * @param month Month (1-12)
     * @return Number of treatments
     * @throws SQLException if database error occurs
     */
    public int getPanchakarmaCountByMonth(int year, int month) throws SQLException {
        return panchakarmaDAO.countByMonth(year, month);
    }
}

