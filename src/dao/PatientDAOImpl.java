package dao;

import model.Patient;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PatientDAO interface
 */
public class PatientDAOImpl implements PatientDAO {

    @Override
    public int insert(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (name, age, sex, dob, height, weight, bmi, job, " +
                "phone, address, reference, blood_pressure, spo2, disease, case_no, " +
                "pending_money, follow_up_date, previous_medicines, registration_date, remark) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, patient.getName());
            pstmt.setInt(2, patient.getAge());
            pstmt.setString(3, patient.getSex());
            pstmt.setDate(4, patient.getDob() != null ? Date.valueOf(patient.getDob()) : null);
            pstmt.setDouble(5, patient.getHeight());
            pstmt.setDouble(6, patient.getWeight());
            pstmt.setDouble(7, patient.getBmi());
            pstmt.setString(8, patient.getJob());
            pstmt.setString(9, patient.getPhone());
            pstmt.setString(10, patient.getAddress());
            pstmt.setString(11, patient.getReference());
            pstmt.setString(12, patient.getBloodPressure());
            pstmt.setInt(13, patient.getSpo2());
            pstmt.setString(14, patient.getDisease());
            pstmt.setString(15, patient.getCaseNo());
            pstmt.setDouble(16, patient.getPendingMoney());
            pstmt.setDate(17, patient.getFollowUpDate() != null ? Date.valueOf(patient.getFollowUpDate()) : null);
            pstmt.setString(18, patient.getPreviousMedicines());
            pstmt.setDate(19, patient.getRegistrationDate() != null ? Date.valueOf(patient.getRegistrationDate()) : Date.valueOf(LocalDate.now()));
            pstmt.setString(20, patient.getRemark());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean update(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET name=?, age=?, sex=?, dob=?, height=?, weight=?, " +
                "bmi=?, job=?, phone=?, address=?, reference=?, blood_pressure=?, spo2=?, " +
                "disease=?, case_no=?, pending_money=?, follow_up_date=?, previous_medicines=?, remark=? " +
                "WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, patient.getName());
            pstmt.setInt(2, patient.getAge());
            pstmt.setString(3, patient.getSex());
            pstmt.setDate(4, patient.getDob() != null ? Date.valueOf(patient.getDob()) : null);
            pstmt.setDouble(5, patient.getHeight());
            pstmt.setDouble(6, patient.getWeight());
            pstmt.setDouble(7, patient.getBmi());
            pstmt.setString(8, patient.getJob());
            pstmt.setString(9, patient.getPhone());
            pstmt.setString(10, patient.getAddress());
            pstmt.setString(11, patient.getReference());
            pstmt.setString(12, patient.getBloodPressure());
            pstmt.setInt(13, patient.getSpo2());
            pstmt.setString(14, patient.getDisease());
            pstmt.setString(15, patient.getCaseNo());
            pstmt.setDouble(16, patient.getPendingMoney());
            pstmt.setDate(17, patient.getFollowUpDate() != null ? Date.valueOf(patient.getFollowUpDate()) : null);
            pstmt.setString(18, patient.getPreviousMedicines());
            pstmt.setString(19, patient.getRemark());
            pstmt.setInt(20, patient.getId());
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean delete(int patientId) throws SQLException {
        String sql = "DELETE FROM patients WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, patientId);
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public Patient findById(int patientId) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractPatientFromResultSet(rs);
            }
            return null;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Patient> searchByName(String name) throws SQLException {
        String sql = "SELECT * FROM patients WHERE name LIKE ? ORDER BY name";
        return searchPatients(sql, "%" + name + "%");
    }

    @Override
    public List<Patient> searchByPhone(String phone) throws SQLException {
        String sql = "SELECT * FROM patients WHERE phone LIKE ? ORDER BY name";
        return searchPatients(sql, "%" + phone + "%");
    }

    @Override
    public List<Patient> search(String searchTerm) throws SQLException {
        String sql = "SELECT * FROM patients WHERE name LIKE ? OR phone LIKE ? ORDER BY name";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Patient> patients = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            String term = "%" + searchTerm + "%";
            pstmt.setString(1, term);
            pstmt.setString(2, term);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        String sql = "SELECT * FROM patients ORDER BY registration_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Patient> patients = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Patient> findByRegistrationDate(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM patients WHERE registration_date = ? ORDER BY name";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Patient> patients = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Patient> findByMonth(int year, int month) throws SQLException {
        String sql = "SELECT * FROM patients WHERE YEAR(registration_date) = ? AND MONTH(registration_date) = ? ORDER BY registration_date";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Patient> patients = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Patient> findByYear(int year) throws SQLException {
        String sql = "SELECT * FROM patients WHERE YEAR(registration_date) = ? ORDER BY registration_date";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Patient> patients = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, year);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM patients";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public int countByMonth(int year, int month) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patients WHERE YEAR(registration_date) = ? AND MONTH(registration_date) = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public int countByYear(int year) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patients WHERE YEAR(registration_date) = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, year);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Patient> findByFollowUpDate(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM patients WHERE follow_up_date = ? ORDER BY name";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Patient> patients = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Patient> findByFollowUpDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT * FROM patients WHERE follow_up_date BETWEEN ? AND ? ORDER BY follow_up_date, name";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Patient> patients = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    // Helper methods
    private List<Patient> searchPatients(String sql, String param) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Patient> patients = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, param);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
            return patients;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    private Patient extractPatientFromResultSet(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setName(rs.getString("name"));
        patient.setAge(rs.getInt("age"));
        patient.setSex(rs.getString("sex"));
        
        Date dob = rs.getDate("dob");
        patient.setDob(dob != null ? dob.toLocalDate() : null);
        
        patient.setHeight(rs.getDouble("height"));
        patient.setWeight(rs.getDouble("weight"));
        patient.setBmi(rs.getDouble("bmi"));
        patient.setJob(rs.getString("job"));
        patient.setPhone(rs.getString("phone"));
        patient.setAddress(rs.getString("address"));
        patient.setReference(rs.getString("reference"));
        patient.setBloodPressure(rs.getString("blood_pressure"));
        patient.setSpo2(rs.getInt("spo2"));
        patient.setDisease(rs.getString("disease"));
        patient.setCaseNo(rs.getString("case_no"));
        patient.setPendingMoney(rs.getDouble("pending_money"));
        
        Date followUpDate = rs.getDate("follow_up_date");
        patient.setFollowUpDate(followUpDate != null ? followUpDate.toLocalDate() : null);
        
        patient.setPreviousMedicines(rs.getString("previous_medicines"));
        
        Date registrationDate = rs.getDate("registration_date");
        patient.setRegistrationDate(registrationDate != null ? registrationDate.toLocalDate() : null);
        
        patient.setRemark(rs.getString("remark"));
        
        return patient;
    }
}

