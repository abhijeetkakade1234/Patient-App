package dao;

import model.FollowUp;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of FollowUpDAO interface
 */
public class FollowUpDAOImpl implements FollowUpDAO {

    @Override
    public int insert(FollowUp followUp) throws SQLException {
        String sql = "INSERT INTO follow_ups (patient_id, visit_no, visit_date, height, weight, bmi, " +
                "blood_pressure, spo2, next_follow_up_date, nadi, samanya_lakshana, rx_treatment, days, " +
                "total_payment, pending_payment, notes, kco, ho, sho, mh, oh, ah, " +
                "mal, mutra, jivha, shudha, trushna, nidra, sweda, sparsha, druka, shabda, akruti) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setInt(1, followUp.getPatientId());
            pstmt.setInt(2, followUp.getVisitNo());
            pstmt.setDate(3, followUp.getVisitDate() != null ? Date.valueOf(followUp.getVisitDate()) : null);
            pstmt.setDouble(4, followUp.getHeight());
            pstmt.setDouble(5, followUp.getWeight());
            pstmt.setDouble(6, followUp.getBmi());
            pstmt.setString(7, followUp.getBloodPressure());
            pstmt.setInt(8, followUp.getSpo2());
            pstmt.setDate(9, followUp.getNextFollowUpDate() != null ? Date.valueOf(followUp.getNextFollowUpDate()) : null);
            pstmt.setString(10, followUp.getNadi());
            pstmt.setString(11, followUp.getSamanyaLakshana());
            pstmt.setString(12, followUp.getRxTreatment());
            pstmt.setInt(13, followUp.getDays());
            pstmt.setDouble(14, followUp.getTotalPayment());
            pstmt.setDouble(15, followUp.getPendingPayment());
            pstmt.setString(16, followUp.getNotes());
            pstmt.setString(17, followUp.getKco());
            pstmt.setString(18, followUp.getHo());
            pstmt.setString(19, followUp.getSho());
            pstmt.setString(20, followUp.getMh());
            pstmt.setString(21, followUp.getOh());
            pstmt.setString(22, followUp.getAh());
            pstmt.setString(23, followUp.getMal());
            pstmt.setString(24, followUp.getMutra());
            pstmt.setString(25, followUp.getJivha());
            pstmt.setString(26, followUp.getShudha());
            pstmt.setString(27, followUp.getTrushna());
            pstmt.setString(28, followUp.getNidra());
            pstmt.setString(29, followUp.getSweda());
            pstmt.setString(30, followUp.getSparsha());
            pstmt.setString(31, followUp.getDruka());
            pstmt.setString(32, followUp.getShabda());
            pstmt.setString(33, followUp.getAkruti());
            
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
    public boolean update(FollowUp followUp) throws SQLException {
        String sql = "UPDATE follow_ups SET visit_no=?, visit_date=?, height=?, weight=?, bmi=?, " +
                "blood_pressure=?, spo2=?, next_follow_up_date=?, nadi=?, samanya_lakshana=?, " +
                "rx_treatment=?, days=?, total_payment=?, pending_payment=?, notes=?, " +
                "kco=?, ho=?, sho=?, mh=?, oh=?, ah=?, " +
                "mal=?, mutra=?, jivha=?, shudha=?, trushna=?, nidra=?, sweda=?, sparsha=?, druka=?, shabda=?, akruti=? " +
                "WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, followUp.getVisitNo());
            pstmt.setDate(2, followUp.getVisitDate() != null ? Date.valueOf(followUp.getVisitDate()) : null);
            pstmt.setDouble(3, followUp.getHeight());
            pstmt.setDouble(4, followUp.getWeight());
            pstmt.setDouble(5, followUp.getBmi());
            pstmt.setString(6, followUp.getBloodPressure());
            pstmt.setInt(7, followUp.getSpo2());
            pstmt.setDate(8, followUp.getNextFollowUpDate() != null ? Date.valueOf(followUp.getNextFollowUpDate()) : null);
            pstmt.setString(9, followUp.getNadi());
            pstmt.setString(10, followUp.getSamanyaLakshana());
            pstmt.setString(11, followUp.getRxTreatment());
            pstmt.setInt(12, followUp.getDays());
            pstmt.setDouble(13, followUp.getTotalPayment());
            pstmt.setDouble(14, followUp.getPendingPayment());
            pstmt.setString(15, followUp.getNotes());
            pstmt.setString(16, followUp.getKco());
            pstmt.setString(17, followUp.getHo());
            pstmt.setString(18, followUp.getSho());
            pstmt.setString(19, followUp.getMh());
            pstmt.setString(20, followUp.getOh());
            pstmt.setString(21, followUp.getAh());
            pstmt.setString(22, followUp.getMal());
            pstmt.setString(23, followUp.getMutra());
            pstmt.setString(24, followUp.getJivha());
            pstmt.setString(25, followUp.getShudha());
            pstmt.setString(26, followUp.getTrushna());
            pstmt.setString(27, followUp.getNidra());
            pstmt.setString(28, followUp.getSweda());
            pstmt.setString(29, followUp.getSparsha());
            pstmt.setString(30, followUp.getDruka());
            pstmt.setString(31, followUp.getShabda());
            pstmt.setString(32, followUp.getAkruti());
            pstmt.setInt(33, followUp.getId());
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean delete(int followUpId) throws SQLException {
        String sql = "DELETE FROM follow_ups WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, followUpId);
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public FollowUp findById(int followUpId) throws SQLException {
        String sql = "SELECT * FROM follow_ups WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, followUpId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractFollowUpFromResultSet(rs);
            }
            return null;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<FollowUp> findByPatientId(int patientId) throws SQLException {
        String sql = "SELECT * FROM follow_ups WHERE patient_id=? ORDER BY visit_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FollowUp> followUps = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, patientId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                followUps.add(extractFollowUpFromResultSet(rs));
            }
            return followUps;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<FollowUp> findAll() throws SQLException {
        String sql = "SELECT * FROM follow_ups ORDER BY visit_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FollowUp> followUps = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                followUps.add(extractFollowUpFromResultSet(rs));
            }
            return followUps;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public int countByPatientId(int patientId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM follow_ups WHERE patient_id=?";
        
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
    public List<FollowUp> findByVisitDate(java.time.LocalDate date) throws SQLException {
        String sql = "SELECT * FROM follow_ups WHERE visit_date = ? ORDER BY id";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FollowUp> followUps = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                followUps.add(extractFollowUpFromResultSet(rs));
            }
            return followUps;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<FollowUp> findByVisitDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate) throws SQLException {
        String sql = "SELECT * FROM follow_ups WHERE visit_date BETWEEN ? AND ? ORDER BY visit_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FollowUp> followUps = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                followUps.add(extractFollowUpFromResultSet(rs));
            }
            return followUps;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<FollowUp> findUpcomingFollowUps() throws SQLException {
        String sql = "SELECT * FROM follow_ups WHERE next_follow_up_date >= CURDATE() ORDER BY next_follow_up_date";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FollowUp> followUps = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                followUps.add(extractFollowUpFromResultSet(rs));
            }
            return followUps;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public List<FollowUp> findDueFollowUps() throws SQLException {
        String sql = "SELECT * FROM follow_ups WHERE next_follow_up_date <= CURDATE() ORDER BY next_follow_up_date";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FollowUp> followUps = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                followUps.add(extractFollowUpFromResultSet(rs));
            }
            return followUps;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public int countByMonth(int year, int month) throws SQLException {
        String sql = "SELECT COUNT(*) FROM follow_ups WHERE YEAR(visit_date) = ? AND MONTH(visit_date) = ?";
        
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
    public List<FollowUp> findByMonth(int year, int month) throws SQLException {
        String sql = "SELECT * FROM follow_ups WHERE YEAR(visit_date) = ? AND MONTH(visit_date) = ? ORDER BY visit_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FollowUp> followUps = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                followUps.add(extractFollowUpFromResultSet(rs));
            }
            return followUps;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    private FollowUp extractFollowUpFromResultSet(ResultSet rs) throws SQLException {
        FollowUp followUp = new FollowUp();
        followUp.setId(rs.getInt("id"));
        followUp.setPatientId(rs.getInt("patient_id"));
        followUp.setVisitNo(rs.getInt("visit_no"));
        
        Date visitDate = rs.getDate("visit_date");
        followUp.setVisitDate(visitDate != null ? visitDate.toLocalDate() : null);
        
        followUp.setHeight(rs.getDouble("height"));
        followUp.setWeight(rs.getDouble("weight"));
        followUp.setBmi(rs.getDouble("bmi"));
        followUp.setBloodPressure(rs.getString("blood_pressure"));
        followUp.setSpo2(rs.getInt("spo2"));
        
        Date nextFollowUpDate = rs.getDate("next_follow_up_date");
        followUp.setNextFollowUpDate(nextFollowUpDate != null ? nextFollowUpDate.toLocalDate() : null);
        
        followUp.setNadi(rs.getString("nadi"));
        followUp.setSamanyaLakshana(rs.getString("samanya_lakshana"));
        followUp.setRxTreatment(rs.getString("rx_treatment"));
        followUp.setDays(rs.getInt("days"));
        followUp.setTotalPayment(rs.getDouble("total_payment"));
        followUp.setPendingPayment(rs.getDouble("pending_payment"));
        followUp.setNotes(rs.getString("notes"));
        followUp.setKco(rs.getString("kco"));
        followUp.setHo(rs.getString("ho"));
        followUp.setSho(rs.getString("sho"));
        followUp.setMh(rs.getString("mh"));
        followUp.setOh(rs.getString("oh"));
        followUp.setAh(rs.getString("ah"));
        followUp.setMal(rs.getString("mal"));
        followUp.setMutra(rs.getString("mutra"));
        followUp.setJivha(rs.getString("jivha"));
        followUp.setShudha(rs.getString("shudha"));
        followUp.setTrushna(rs.getString("trushna"));
        followUp.setNidra(rs.getString("nidra"));
        followUp.setSweda(rs.getString("sweda"));
        followUp.setSparsha(rs.getString("sparsha"));
        followUp.setDruka(rs.getString("druka"));
        followUp.setShabda(rs.getString("shabda"));
        followUp.setAkruti(rs.getString("akruti"));
        
        return followUp;
    }
}

