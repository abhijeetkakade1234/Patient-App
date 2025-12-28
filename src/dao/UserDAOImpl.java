package dao;

import model.User;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Implementation of UserDAO interface
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public int insert(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, full_name, is_active) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword()); // Should be hashed in production
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getFullName());
            pstmt.setBoolean(5, user.isActive());
            
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
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET username=?, email=?, full_name=?, is_active=? WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getFullName());
            pstmt.setBoolean(4, user.isActive());
            pstmt.setInt(5, user.getId());
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean delete(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public User findById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
            return null;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
            return null;
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean validateLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password=? AND is_active=TRUE";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password); // Should compare with hashed password in production
            rs = pstmt.executeQuery();
            
            return rs.next();
        } finally {
            DBUtil.closeQuietly(rs);
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean updatePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password=? WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPassword); // Should be hashed in production
            pstmt.setInt(2, userId);
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    @Override
    public boolean updateLastLogin(int userId) throws SQLException {
        String sql = "UPDATE users SET last_login=NOW() WHERE id=?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            return pstmt.executeUpdate() > 0;
        } finally {
            DBUtil.closeQuietly(pstmt);
            DBUtil.closeQuietly(conn);
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setFullName(rs.getString("full_name"));
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        user.setCreatedAt(createdAt != null ? createdAt.toLocalDateTime() : null);
        
        Timestamp lastLogin = rs.getTimestamp("last_login");
        user.setLastLogin(lastLogin != null ? lastLogin.toLocalDateTime() : null);
        
        user.setActive(rs.getBoolean("is_active"));
        
        return user;
    }
}

