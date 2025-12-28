package controller;

import model.Patient;
import service.PatientService;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller for Patient operations
 * Mediates between UI and Service layer
 */
public class PatientController {
    private final PatientService patientService;

    public PatientController() {
        this.patientService = new PatientService();
    }

    /**
     * Add a new patient
     * @param patient Patient object
     * @return generated patient ID or -1 if failed
     */
    public int addPatient(Patient patient) {
        try {
            int patientId = patientService.addPatient(patient);
            if (patientId > 0) {
                JOptionPane.showMessageDialog(null, 
                    "Patient added successfully! ID: " + patientId, 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                return patientId;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to add patient", 
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
     * Update existing patient
     * @param patient Patient object with updated data
     * @return true if update successful
     */
    public boolean updatePatient(Patient patient) {
        try {
            boolean success = patientService.updatePatient(patient);
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Patient updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to update patient", 
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
     * Delete patient by ID
     * @param patientId ID of patient to delete
     * @return true if deletion successful
     */
    public boolean deletePatient(int patientId) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to delete this patient?\nThis will also delete all follow-ups and panchakarma records.", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = patientService.deletePatient(patientId);
                if (success) {
                    JOptionPane.showMessageDialog(null, 
                        "Patient deleted successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Failed to delete patient", 
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
     * Get patient by ID
     * @param patientId ID of patient
     * @return Patient object or null if not found
     */
    public Patient getPatientById(int patientId) {
        try {
            return patientService.getPatientById(patientId);
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
     * Search patients by name or phone
     * @param searchTerm Search term
     * @return List of matching patients
     */
    public List<Patient> searchPatients(String searchTerm) {
        try {
            return patientService.searchPatients(searchTerm);
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
     * Get all patients
     * @return List of all patients
     */
    public List<Patient> getAllPatients() {
        try {
            return patientService.getAllPatients();
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
     * Get patients registered today
     * @return List of patients
     */
    public List<Patient> getTodaysPatients() {
        try {
            return patientService.getTodaysPatients();
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
     * Get patients registered in current month
     * @return List of patients
     */
    public List<Patient> getPatientsThisMonth() {
        try {
            return patientService.getPatientsThisMonth();
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
     * Get patients registered in a specific month
     * @param year Year
     * @param month Month (1-12)
     * @return List of patients
     */
    public List<Patient> getPatientsByMonth(int year, int month) {
        try {
            return patientService.getPatientsByMonth(year, month);
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
     * Get patients registered in a specific year
     * @param year Year
     * @return List of patients
     */
    public List<Patient> getPatientsByYear(int year) {
        try {
            return patientService.getPatientsByYear(year);
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
     * Get total patient count
     * @return Total number of patients
     */
    public int getTotalPatientCount() {
        try {
            return patientService.getTotalPatientCount();
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
        return patientService.calculateBMI(heightCm, weightKg);
    }

    /**
     * Get patients by specific date
     * @param date Registration date
     * @return List of patients
     */
    public List<Patient> getPatientsByDate(java.time.LocalDate date) {
        try {
            return patientService.getPatientsByDate(date);
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
     * Get patients with follow-up on specific date
     * @param date Follow-up date
     * @return List of patients
     */
    public List<Patient> getPatientsByFollowUpDate(java.time.LocalDate date) {
        try {
            return patientService.getPatientsByFollowUpDate(date);
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
     * Get patient count for current month
     * @return Number of patients registered this month
     */
    public int getPatientCountThisMonth() {
        try {
            java.time.LocalDate now = java.time.LocalDate.now();
            return patientService.getPatientCountByMonth(now.getYear(), now.getMonthValue());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

