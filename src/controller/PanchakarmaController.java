package controller;

import model.Panchakarma;
import service.PanchakarmaService;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for Panchakarma operations
 * Mediates between UI and Service layer
 */
public class PanchakarmaController {
    private final PanchakarmaService panchakarmaService;

    public PanchakarmaController() {
        this.panchakarmaService = new PanchakarmaService();
    }

    /**
     * Add a new panchakarma treatment
     * @param panchakarma Panchakarma object
     * @return generated ID or -1 if failed
     */
    public int addPanchakarma(Panchakarma panchakarma) {
        try {
            int id = panchakarmaService.addPanchakarma(panchakarma);
            if (id > 0) {
                JOptionPane.showMessageDialog(null,
                    "Panchakarma treatment added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                return id;
            } else {
                JOptionPane.showMessageDialog(null,
                    "Failed to add panchakarma treatment",
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
     * Update existing panchakarma treatment
     * @param panchakarma Panchakarma object with updated data
     * @return true if update successful
     */
    public boolean updatePanchakarma(Panchakarma panchakarma) {
        try {
            boolean success = panchakarmaService.updatePanchakarma(panchakarma);
            if (success) {
                JOptionPane.showMessageDialog(null,
                    "Panchakarma treatment updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Failed to update panchakarma treatment",
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
     * Delete panchakarma treatment
     * @param panchakarmaId ID of treatment to delete
     * @return true if deletion successful
     */
    public boolean deletePanchakarma(int panchakarmaId) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to delete this panchakarma treatment?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = panchakarmaService.deletePanchakarma(panchakarmaId);
                if (success) {
                    JOptionPane.showMessageDialog(null,
                        "Panchakarma treatment deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Failed to delete panchakarma treatment",
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
     * Get panchakarma by ID
     * @param panchakarmaId ID of treatment
     * @return Panchakarma object or null if not found
     */
    public Panchakarma getPanchakarmaById(int panchakarmaId) {
        try {
            return panchakarmaService.getPanchakarmaById(panchakarmaId);
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
     * Get all panchakarma treatments for a patient
     * @param patientId Patient ID
     * @return List of panchakarma treatments
     */
    public List<Panchakarma> getPanchakarmaByPatientId(int patientId) {
        try {
            return panchakarmaService.getPanchakarmaByPatientId(patientId);
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
     * Get all panchakarma treatments
     * @return List of all treatments
     */
    public List<Panchakarma> getAllPanchakarma() {
        try {
            return panchakarmaService.getAllPanchakarma();
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
     * Get panchakarma treatments by date
     * @param date Treatment date
     * @return List of treatments
     */
    public List<Panchakarma> getPanchakarmaByDate(LocalDate date) {
        try {
            return panchakarmaService.getPanchakarmaByDate(date);
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
     * Get panchakarma treatments by month
     * @param year Year
     * @param month Month (1-12)
     * @return List of treatments
     */
    public List<Panchakarma> getPanchakarmaByMonth(int year, int month) {
        try {
            return panchakarmaService.getPanchakarmaByMonth(year, month);
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
     * Get panchakarma count for a patient
     * @param patientId Patient ID
     * @return Number of treatments
     */
    public int getPanchakarmaCountByPatientId(int patientId) {
        try {
            return panchakarmaService.getPanchakarmaCountByPatientId(patientId);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

