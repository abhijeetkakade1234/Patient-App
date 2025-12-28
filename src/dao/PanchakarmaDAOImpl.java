package dao;

import model.Panchakarma;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PanchakarmaDAO interface
 */
public class PanchakarmaDAOImpl implements PanchakarmaDAO {

    @Override
    public int insert(Panchakarma panchakarma) throws SQLException {
        String sql = "INSERT INTO panchakarma (patient_id, panchakarma_name, advised, no_of_days, day, " +
                "types_of_karma_and_medicines, price, duration_time, therapist_time, day_and_date, note) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setInt(1, panchakarma.getPatientId());
            pstmt.setString(2, panchakarma.getPanchakarmaName());
            pstmt.setString(3, panchakarma.getAdvised());
            pstmt.setInt(4, panchakarma.getNoOfDays());
            pstmt.setInt(5, panchakarma.getDay());
            pstmt.setString(6, panchakarma.getTypesOfKarmaAndMedicines());
            pstmt.setDouble(7, panchakarma.getPrice());
            pstmt.setString(8, panchakarma.getDurationTime());
            pstmt.setString(9, panchakarma.getTherapistTime());
            pstmt.setDate(10, panchakarma.getDayAndDate() != null ? Date.valueOf(panchakarma.getDayAndDate()) : null);
            pstmt.setString(11, panchakarma.getNote());
            
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
    public boolean update(Panchakarma panchakarma) throws SQLException {
        String sql = "UPDATE panchakarma SET panchakarma_name=?, advised=?, no_of_days=?, day=?, " +
                "types_of_karma_and_medicines=?, price=?, duration_time=?, therapist_time=?, " +
                "day_and_date=?, note=? WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, panchakarma.getPanchakarmaName());
            pstmt.setString(2, panchakarma.getAdvised());
            pstmt.setInt(3, panchakarma.getNoOfDays());
            pstmt.setInt(4, panchakarma.getDay());
            pstmt.setString(5, panchakarma.getTypesOfKarmaAndMedicines());
            pstmt.setDouble(6, panchakarma.getPrice());
            pstmt.setString(7, panchakarma.getDurationTime());
            pstmt.setString(8, panchakarma.getTherapistTime());
            pstmt.setDate(9, panchakarma.getDayAndDate() != null ? Date.valueOf(panchakarma.getDayAndDate()) : null);
            pstmt.setString(10, panchakarma.getNote());
            pstmt.setInt(11, panchakarma.getId());
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean delete(int panchakarmaId) throws SQLException {
        String sql = "DELETE FROM panchakarma WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, panchakarmaId);
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public Panchakarma findById(int panchakarmaId) throws SQLException {
        String sql = "SELECT * FROM panchakarma WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, panchakarmaId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractPanchakarmaFromResultSet(rs);
            }
            return null;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Panchakarma> findByPatientId(int patientId) throws SQLException {
        String sql = "SELECT * FROM panchakarma WHERE patient_id=? ORDER BY day_and_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Panchakarma> panchakarmaList = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                panchakarmaList.add(extractPanchakarmaFromResultSet(rs));
            }
            return panchakarmaList;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Panchakarma> findAll() throws SQLException {
        String sql = "SELECT * FROM panchakarma ORDER BY day_and_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Panchakarma> panchakarmaList = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                panchakarmaList.add(extractPanchakarmaFromResultSet(rs));
            }
            return panchakarmaList;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public int countByPatientId(int patientId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM panchakarma WHERE patient_id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, patientId);
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
    public List<Panchakarma> findByDate(java.time.LocalDate date) throws SQLException {
        String sql = "SELECT * FROM panchakarma WHERE day_and_date = ? ORDER BY id";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Panchakarma> panchakarmaList = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                panchakarmaList.add(extractPanchakarmaFromResultSet(rs));
            }
            return panchakarmaList;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Panchakarma> findByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate) throws SQLException {
        String sql = "SELECT * FROM panchakarma WHERE day_and_date BETWEEN ? AND ? ORDER BY day_and_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Panchakarma> panchakarmaList = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                panchakarmaList.add(extractPanchakarmaFromResultSet(rs));
            }
            return panchakarmaList;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<Panchakarma> findByMonth(int year, int month) throws SQLException {
        String sql = "SELECT * FROM panchakarma WHERE YEAR(day_and_date) = ? AND MONTH(day_and_date) = ? ORDER BY day_and_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Panchakarma> panchakarmaList = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                panchakarmaList.add(extractPanchakarmaFromResultSet(rs));
            }
            return panchakarmaList;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public int countByMonth(int year, int month) throws SQLException {
        String sql = "SELECT COUNT(*) FROM panchakarma WHERE YEAR(day_and_date) = ? AND MONTH(day_and_date) = ?";
        
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

    private Panchakarma extractPanchakarmaFromResultSet(ResultSet rs) throws SQLException {
        Panchakarma panchakarma = new Panchakarma();
        panchakarma.setId(rs.getInt("id"));
        panchakarma.setPatientId(rs.getInt("patient_id"));
        panchakarma.setPanchakarmaName(rs.getString("panchakarma_name"));
        panchakarma.setAdvised(rs.getString("advised"));
        panchakarma.setNoOfDays(rs.getInt("no_of_days"));
        panchakarma.setDay(rs.getInt("day"));
        panchakarma.setTypesOfKarmaAndMedicines(rs.getString("types_of_karma_and_medicines"));
        panchakarma.setPrice(rs.getDouble("price"));
        panchakarma.setDurationTime(rs.getString("duration_time"));
        panchakarma.setTherapistTime(rs.getString("therapist_time"));
        
        Date dayAndDate = rs.getDate("day_and_date");
        panchakarma.setDayAndDate(dayAndDate != null ? dayAndDate.toLocalDate() : null);
        
        panchakarma.setNote(rs.getString("note"));
        
        return panchakarma;
    }
}

